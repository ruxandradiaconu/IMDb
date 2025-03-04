package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
@SuppressWarnings("ALL")

public class IMDB {
    private static List<User<?>> accounts;
    private static List<Actor> actors;
    private static List<Request> requests;
    private static List<Production> productions;

    private static Scanner scanner;

    public static List<User<?>> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<User<?>> accounts) {
        this.accounts = accounts;
    }

    public static List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public static List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public static List<Production> getProductions() {
        return productions;
    }

    public void setProductions(List<Production> productions) {
        this.productions = productions;
    }

    private static IMDB instance;

    private IMDB() {
        scanner = new Scanner(System.in);
    }

    public static IMDB getInstance() {
        if (instance == null) {
            instance = new IMDB();
        }
        return instance;
    }

    public static void run() throws InvalidCommandException {
        productionsReading("C:\\Users\\Ruxi\\IdeaProjects\\temaPOO2023\\src\\main\\resources\\input\\production.json");
        actorsReading("C:\\Users\\Ruxi\\IdeaProjects\\temaPOO2023\\src\\main\\resources\\input\\actors.json");
        requestsReading("C:\\Users\\Ruxi\\IdeaProjects\\temaPOO2023\\src\\main\\resources\\input\\requests.json");
        accountsReading("C:\\Users\\Ruxi\\IdeaProjects\\temaPOO2023\\src\\main\\resources\\input\\accounts.json");

        System.out.println("Welcome!");
        System.out.println("Enter the way you would like to start the application ( please type 1 )");
        System.out.println("1. CLI (terminal) ");
        System.out.println("2. GUI (graphic interface) ");
        String appMode = scanner.nextLine();
        while(!appMode.equals("1")) {
            System.out.println("Please select command 1");
            appMode = scanner.nextLine();
        }
        credentialsLogin();
        scanner.close();
    }

    public static void actions(User<?> thisUser){
        System.out.println("Choose action:");

        AccountType userType = thisUser.getAccountType();
        switch (userType) {
            case REGULAR:
                System.out.println("    1) View all productions details");
                System.out.println("    2) View all actors details");
                System.out.println("    3) View notifications");
                System.out.println("    4) Search for actor/movie/series");
                System.out.println("    5) Add/Delete actor/movie/series to/from favorites");
                System.out.println("    6) Add/Delete request");
                System.out.println("    7) Add/Delete rating for a movie/series");
                System.out.println("    8) Logout");
                break;
            case CONTRIBUTOR:
                System.out.println("    1) View productions details");
                System.out.println("    2) View actors details");
                System.out.println("    3) View notifications");
                System.out.println("    4) Search for actor/movie/series");
                System.out.println("    5) Add/Delete actor/movie/series to/from favorites");
                System.out.println("    6) Add/Delete request");
                System.out.println("    7) Add/Delete actor/movie/series to/from system");
                System.out.println("    8) Solve a request");
                System.out.println("    9) Update Movie/Actor Details");
                System.out.println("    10) Logout");
                break;
            case ADMIN:
                System.out.println("    1) View productions details");
                System.out.println("    2) View actors details");
                System.out.println("    3) View notifications");
                System.out.println("    4) Search for actor/movie/series");
                System.out.println("    5) Add/Delete actor/movie/series to/from favorites");
                System.out.println("    6) Add/Delete actor/movie/series from system");
                System.out.println("    7) Solve a request");
                System.out.println("    8) Update Movie/Actor Details");
                System.out.println("    9) Add/Delete user");
                System.out.println("    10) Logout");
                break;
        }

        int ok;
        int action = 0;
        do {
            String actionString = scanner.nextLine().trim();
            ok = 1;
            try {
                if (actionString.isEmpty()) {
                    throw new InvalidCommandException(" ");
                }
                action = Integer.parseInt(actionString);
                switch (userType) {
                    case REGULAR:
                        if (action < 1 || action > 8)
                            throw new InvalidCommandException("Invalid command. Please enter a valid action.");
                        break;
                    case CONTRIBUTOR:
                        if (action < 1 || action > 10)
                            throw new InvalidCommandException("Invalid command. Please enter a valid action.");
                        break;
                    case ADMIN:
                        if (action < 1 || action > 10)
                            throw new InvalidCommandException("Invalid command. Please enter a valid action.");
                        break;
                    default:
                        throw new InvalidCommandException("Invalid user type.");
                }
            } catch (NumberFormatException | InvalidCommandException e) {
                ok = 0;
                System.out.println(e.getMessage());
            }
        } while (ok == 0);

        switch (userType) {
            case REGULAR:
                switch (action) {
                    case 1: {
                        viewProductionsDetails();
                        break;
                    }
                    case 2: {
                        viewActorsDetails();
                        break;
                    }
                    case 3: {
                        viewNotifications(thisUser);
                        break;
                    }
                    case 4: {
                        System.out.println("Do you want to search for an actor, movie or series? Type ACTOR, MOVIE OR SERIES");
                        String searched = scanner.nextLine();
                        if (searched.toUpperCase().equals("ACTOR")) {
                            System.out.println("Introduce the actor's name: ");
                            String actorName = scanner.nextLine();
                            searchActor(actorName);
                        } else if (searched.toUpperCase().equals("MOVIE")) {
                            System.out.println("Introduce the movie's title: ");
                            String movieTitle = scanner.nextLine();
                            searchProduction(movieTitle);
                        } else if (searched.toUpperCase().equals("SERIES")) {
                            System.out.println("Introduce the series' title: ");
                            String seriesTitle = scanner.nextLine();
                            searchProduction(seriesTitle);
                        }
                        break;
                    }
                    case 5: {
                        System.out.println("Do you want to add or delete from the favourites list?");
                        System.out.println("    Type ADD or DELETE");
                        boolean validInput = false;

                        do {
                            try {
                                String input = scanner.nextLine();
                                if (input.toUpperCase().equals("ADD")) {
                                    System.out.println("Do you want to ADD an actor, movie or series into your favorites' list?");
                                    System.out.println("    Type ACTOR, MOVIE or SERIES");
                                    boolean validInput2 = false;
                                    do {
                                        try {
                                            String added = scanner.nextLine();
                                            if (added.toUpperCase().equals("ACTOR")) {
                                                addActorFavorites(thisUser);
                                                validInput2 = true;
                                            } else if (added.toUpperCase().equals("MOVIE")) {
                                                addMovieFavorites(thisUser);
                                                validInput2 = true;
                                            } else if (added.toUpperCase().equals("SERIES")) {
                                                addSeriesFavorites(thisUser);
                                                validInput2 = true;
                                            } else {
                                                throw new InvalidCommandException("Invalid input. Please enter ACTOR, MOVIE, or SERIES.");
                                            }
                                        } catch (InvalidCommandException e) {
                                            System.out.println(e.getMessage());
                                        }
                                    } while (!validInput2);
                                    validInput = true;
                                } else if (input.toUpperCase().equals("DELETE")) {
                                    System.out.println("Do you want to ADD an actor, movie or series into your favorites' list?");
                                    System.out.println("    Type ACTOR, MOVIE or SERIES");
                                    boolean validInput2 = false;
                                    do {
                                        try {
                                            String removed = scanner.nextLine();
                                            if (removed.toUpperCase().equals("ACTOR")) {
                                                removeActorFavorites(thisUser);
                                                validInput2 = true;
                                            } else if (removed.toUpperCase().equals("MOVIE")) {
                                                removeMovieFavorites(thisUser);
                                                validInput2 = true;
                                            } else if (removed.toUpperCase().equals("SERIES")) {
                                                removeSeriesFavorites(thisUser);
                                                validInput2 = true;
                                            } else {
                                                throw new InvalidCommandException("Invalid input. Please enter ACTOR, MOVIE, or SERIES.");
                                            }
                                        } catch (InvalidCommandException e) {
                                            System.out.println(e.getMessage());
                                        }
                                    } while (!validInput2);
                                    validInput = true;
                                } else {
                                    throw new InvalidCommandException("Invalid input. Please enter ADD or DELETE.");
                                }
                            } catch (InvalidCommandException e) {
                                System.out.println(e.getMessage());
                            }
                        } while (!validInput);
                        break;
                    }
                    case 6:{
                        System.out.println("Do you want to add or delete a request?");
                        System.out.println("    Type ADD or DELETE");
                        boolean validInput = false;
                        do {
                            try {
                                String input = scanner.nextLine();
                                if (input.toUpperCase().equals("ADD")) {
                                    createRequest(thisUser);
                                    validInput = true;
                                } else if (input.toUpperCase().equals("DELETE")) {
                                    removeRequest(thisUser);
                                    validInput = true;
                                } else {
                                    throw new InvalidCommandException("Invalid input. Please enter ADD or DELETE.");
                                }
                            } catch (InvalidCommandException e) {
                                System.out.println(e.getMessage());
                            }
                        } while (!validInput);
                        break;
                    }
                    case 7:{
                        System.out.println("Do you want to add or delete a rating?");
                        System.out.println("    Type ADD or DELETE");
                        boolean validInput = false;
                        do {
                            try {
                                String input = scanner.nextLine();
                                if (input.toUpperCase().equals("ADD")) {
                                    createRating(thisUser);
                                    validInput = true;
                                } else if (input.toUpperCase().equals("DELETE")) {
                                    removeRating(thisUser);
                                    validInput = true;
                                } else {
                                    throw new InvalidCommandException("Invalid input. Please enter ADD or DELETE.");
                                }
                            } catch (InvalidCommandException e) {
                                System.out.println(e.getMessage());
                            }
                        } while (!validInput);
                        break;
                    }
                    case 8: {
                        System.out.println("Do you want to log in with another credentials or log out?");
                        System.out.println("Type LOGIN if you want to log in again, otherwise you will be logged out");
                        String logout = scanner.nextLine();
                        if (logout.toUpperCase().equals("LOGIN"))
                            credentialsLogin();
                        else {
                            try {
                                Path filePath = FileSystems.getDefault().getPath("C:\\Users\\Ruxi\\IdeaProjects\\temaPOO2023\\src\\main\\resources\\input\\production.json");
                                Files.delete(filePath);
                                modifyProductionsInJSON("C:\\Users\\Ruxi\\IdeaProjects\\temaPOO2023\\src\\main\\resources\\input\\production.json", getProductions());
                                filePath = FileSystems.getDefault().getPath("C:\\Users\\Ruxi\\IdeaProjects\\temaPOO2023\\src\\main\\resources\\input\\actors.json");
                                Files.delete(filePath);
                                modifyActorsInJSON("C:\\Users\\Ruxi\\IdeaProjects\\temaPOO2023\\src\\main\\resources\\input\\actors.json", IMDB.getActors());
                                filePath = FileSystems.getDefault().getPath("C:\\Users\\Ruxi\\IdeaProjects\\temaPOO2023\\src\\main\\resources\\input\\requests.json");
                                Files.delete(filePath);
                                modifyRequestsInJSON("C:\\Users\\Ruxi\\IdeaProjects\\temaPOO2023\\src\\main\\resources\\input\\requests.json", IMDB.getRequests());

                                thisUser.logout();
                            }catch (IOException e) {
                                System.err.println("Unable to delete the file: " + e.getMessage());
                            }
                        }
                        break;
                    }
                }
                break;
            case CONTRIBUTOR:
                switch (action) {
                    case 1: {
                        viewProductionsDetails();
                        break;
                    }
                    case 2: {
                        viewActorsDetails();
                        break;
                    }
                    case 3: {
                        viewNotifications(thisUser);
                        break;
                    }
                    case 4: {
                        System.out.println("Do you want to search for an actor, movie or series?");
                        System.out.println("    Type ACTOR, MOVIE OR SERIES");
                        boolean validInput = false;

                        do {
                            try {
                                String searched = scanner.nextLine();
                                if (searched.toUpperCase().equals("ACTOR")) {
                                    System.out.println("Introduce the actor's name: ");
                                    String actorName = scanner.nextLine();
                                    searchActor(actorName);
                                    validInput = true;
                                } else if (searched.toUpperCase().equals("MOVIE")) {
                                    System.out.println("Introduce the movie's title: ");
                                    String movieTitle = scanner.nextLine();
                                    searchProduction(movieTitle);
                                    validInput = true;
                                } else if (searched.toUpperCase().equals("SERIES")) {
                                    System.out.println("Introduce the series' title: ");
                                    String seriesTitle = scanner.nextLine();
                                    searchProduction(seriesTitle);
                                    validInput = true;
                                } else {
                                    throw new InvalidCommandException("Invalid input. Please enter ACTOR, MOVIE, or SERIES.");
                                }
                            } catch (InvalidCommandException e) {
                                System.out.println(e.getMessage());
                            }
                        } while (!validInput);

                        break;
                    }
                    case 5: {
                        System.out.println("Do you want to add or delete from the favourites list?");
                        System.out.println("    Type ADD or DELETE");
                        boolean validInput = false;

                        do {
                            try {
                                String input = scanner.nextLine();
                                if (input.toUpperCase().equals("ADD")) {
                                    System.out.println("Do you want to ADD an actor, movie or series into your favorites' list?");
                                    System.out.println("    Type ACTOR, MOVIE or SERIES");
                                    boolean validInput2 = false;
                                    do {
                                        try {
                                            String added = scanner.nextLine();
                                            if (added.toUpperCase().equals("ACTOR")) {
                                                addActorFavorites(thisUser);
                                                validInput2 = true;
                                            } else if (added.toUpperCase().equals("MOVIE")) {
                                                addMovieFavorites(thisUser);
                                                validInput2 = true;
                                            } else if (added.toUpperCase().equals("SERIES")) {
                                                addSeriesFavorites(thisUser);
                                                validInput2 = true;
                                            } else {
                                                throw new InvalidCommandException("Invalid input. Please enter ACTOR, MOVIE, or SERIES.");
                                            }
                                        } catch (InvalidCommandException e) {
                                            System.out.println(e.getMessage());
                                        }
                                    } while (!validInput2);
                                    validInput = true;
                                } else if (input.toUpperCase().equals("DELETE")) {
                                    System.out.println("Do you want to ADD an actor, movie or series into your favorites' list?");
                                    System.out.println("    Type ACTOR, MOVIE or SERIES");
                                    boolean validInput2 = false;
                                    do {
                                        try {
                                            String removed = scanner.nextLine();
                                            if (removed.toUpperCase().equals("ACTOR")) {
                                                removeActorFavorites(thisUser);
                                                validInput2 = true;
                                            } else if (removed.toUpperCase().equals("MOVIE")) {
                                                removeMovieFavorites(thisUser);
                                                validInput2 = true;
                                            } else if (removed.toUpperCase().equals("SERIES")) {
                                                removeSeriesFavorites(thisUser);
                                                validInput2 = true;
                                            } else {
                                                throw new InvalidCommandException("Invalid input. Please enter ACTOR, MOVIE, or SERIES.");
                                            }
                                        } catch (InvalidCommandException e) {
                                            System.out.println(e.getMessage());
                                        }
                                    } while (!validInput2);
                                    validInput = true;
                                } else {
                                    throw new InvalidCommandException("Invalid input. Please enter ADD or DELETE.");
                                }
                            } catch (InvalidCommandException e) {
                                System.out.println(e.getMessage());
                            }
                        } while (!validInput);
                        break;
                    }
                    case 6:{
                        System.out.println("Do you want to add or delete a request?");
                        System.out.println("    Type ADD or DELETE");
                        boolean validInput = false;
                        do {
                            try {
                                String input = scanner.nextLine();
                                if (input.toUpperCase().equals("ADD")) {
                                    createRequest(thisUser);
                                    validInput = true;
                                } else if (input.toUpperCase().equals("DELETE")) {
                                    removeRequest(thisUser);
                                    validInput = true;
                                } else {
                                    throw new InvalidCommandException("Invalid input. Please enter ADD or DELETE.");
                                }
                            } catch (InvalidCommandException e) {
                                System.out.println(e.getMessage());
                            }
                        } while (!validInput);
                        break;
                    }
                    case 7:{
                        System.out.println("Do you want to 'add' or 'delete' an actor/movie/series?");
                        System.out.println("    Type ADD or DELETE");
                        boolean validInput = false;
                        do {
                            try {
                                String input = scanner.nextLine();
                                if (input.toUpperCase().equals("ADD")) {
                                    System.out.println("Do you want to add an actor, movie or series?");
                                    System.out.println("    Type ACTOR, MOVIE OR SERIES");
                                    boolean validInput2 = false;

                                    do {
                                        try {
                                            String searched = scanner.nextLine();
                                            if (searched.toUpperCase().equals("ACTOR")) {
                                                addActorToSystem(thisUser);
                                                validInput2 = true;
                                            } else if (searched.toUpperCase().equals("MOVIE")) {
                                                optionNotImplemented();
                                                validInput2 = true;
                                            } else if (searched.toUpperCase().equals("SERIES")) {
                                                optionNotImplemented();
                                                validInput2 = true;
                                            } else {
                                                throw new InvalidCommandException("Invalid input. Please enter ACTOR, MOVIE, or SERIES.");
                                            }
                                        } catch (InvalidCommandException e) {
                                            System.out.println(e.getMessage());
                                        }
                                    } while (!validInput2);
                                    validInput = true;
                                } else if (input.toUpperCase().equals("DELETE")) {
                                    System.out.println("Do you want to remove an actor, movie or series?");
                                    System.out.println("    Type ACTOR, MOVIE OR SERIES");
                                    boolean validInput2 = false;
                                    do {
                                        try {
                                            String searched = scanner.nextLine();
                                            if (searched.toUpperCase().equals("ACTOR")) {
                                                deleteActorFromSystem(thisUser);
                                                validInput2 = true;
                                            } else if (searched.toUpperCase().equals("MOVIE")) {
                                                optionNotImplemented();
                                                validInput2 = true;
                                            } else if (searched.toUpperCase().equals("SERIES")) {
                                                optionNotImplemented();
                                                validInput2 = true;
                                            } else {
                                                throw new InvalidCommandException("Invalid input. Please enter ACTOR, MOVIE, or SERIES.");
                                            }
                                        } catch (InvalidCommandException e) {
                                            System.out.println(e.getMessage());
                                        }
                                    } while (!validInput2);
                                    validInput = true;
                                } else {
                                    throw new InvalidCommandException("Invalid input. Please enter ADD or DELETE.");
                                }
                            } catch (InvalidCommandException e) {
                                System.out.println(e.getMessage());
                            }
                        } while (!validInput);
                        break;
                    }
                    case 8:{
                        optionNotImplemented();
                        break;
                    }
                    case 9:{
                        System.out.println("Do you want to update information about an actor or movie/series?");
                        System.out.println("    Type ACTOR or PRODUCTION");
                        boolean validInput = false;
                        do {
                            try {
                                String input = scanner.nextLine();
                                if (input.toUpperCase().equals("ACTOR")) {
                                    updateActor(thisUser);
                                    validInput = true;
                                } else if (input.toUpperCase().equals("PRODUCTION")) {
                                    updateProduction(thisUser);
                                    validInput = true;
                                } else {
                                    throw new InvalidCommandException("Invalid input. Please enter ACTOR or PRODUCTION.");
                                }
                            } catch (InvalidCommandException e) {
                                System.out.println(e.getMessage());
                            }
                        } while (!validInput);
                        break;
                    }
                    case 10: {
                        System.out.println("Do you want to log in with another credentials or log out?");
                        System.out.println("Type LOGIN if you want to log in again, otherwise you will be logged out");
                        String logout = scanner.nextLine();
                        if (logout.toUpperCase().equals("LOGIN"))
                            credentialsLogin();
                        else {
                            try {

                                Path filePath = FileSystems.getDefault().getPath("C:\\Users\\Ruxi\\IdeaProjects\\temaPOO2023\\src\\main\\resources\\input\\production.json");
                                Files.delete(filePath);
                                modifyProductionsInJSON("C:\\Users\\Ruxi\\IdeaProjects\\temaPOO2023\\src\\main\\resources\\input\\production.json", IMDB.getProductions());
                                filePath = FileSystems.getDefault().getPath("C:\\Users\\Ruxi\\IdeaProjects\\temaPOO2023\\src\\main\\resources\\input\\actors.json");
                                Files.delete(filePath);
                                modifyActorsInJSON("C:\\Users\\Ruxi\\IdeaProjects\\temaPOO2023\\src\\main\\resources\\input\\actors.json", IMDB.getActors());
                                filePath = FileSystems.getDefault().getPath("C:\\Users\\Ruxi\\IdeaProjects\\temaPOO2023\\src\\main\\resources\\input\\requests.json");
                                Files.delete(filePath);
                                modifyRequestsInJSON("C:\\Users\\Ruxi\\IdeaProjects\\temaPOO2023\\src\\main\\resources\\input\\requests.json", IMDB.getRequests());
                                //filePath = FileSystems.getDefault().getPath("C:\\Users\\Ruxi\\IdeaProjects\\temaPOO2023\\src\\main\\resources\\input\\accounts.json");
                                //Files.delete(filePath);
                                //modifyAccountsInJSON("C:\\Users\\Ruxi\\IdeaProjects\\temaPOO2023\\src\\main\\resources\\input\\accounts.json", IMDB.getAccounts());

                                thisUser.logout();
                            }catch (IOException e) {
                                System.err.println("Unable to delete the file: " + e.getMessage());
                            }
                        }
                        break;
                    }
                }
                break;
            case ADMIN:
                switch (action) {
                    case 1: {
                        viewProductionsDetails();
                        break;
                    }
                    case 2: {
                        viewActorsDetails();
                        break;
                    }
                    case 3: {
                        viewNotifications(thisUser);
                        break;
                    }
                    case 4: {
                        System.out.println("Do you want to search for an actor, movie or series? Type ACTOR, MOVIE OR SERIES");
                        String searched = scanner.nextLine();
                        if (searched.toUpperCase().equals("ACTOR")) {
                            System.out.println("Introduce the actor's name: ");
                            String actorName = scanner.nextLine();
                            searchActor(actorName);
                        } else if (searched.toUpperCase().equals("MOVIE")) {
                            System.out.println("Introduce the movie's title: ");
                            String movieTitle = scanner.nextLine();
                            searchProduction(movieTitle);
                        } else if (searched.toUpperCase().equals("SERIES")) {
                            System.out.println("Introduce the series' title: ");
                            String seriesTitle = scanner.nextLine();
                            searchProduction(seriesTitle);
                        }
                        break;
                    }
                    case 5:{
                        System.out.println("Do you want to add or delete from the favourites list?");
                        System.out.println("    Type ADD or DELETE");
                        boolean validInput = false;

                        do {
                            try {
                                String input = scanner.nextLine();
                                if (input.toUpperCase().equals("ADD")) {
                                    System.out.println("Do you want to ADD an actor, movie or series into your favorites' list?");
                                    System.out.println("    Type ACTOR, MOVIE or SERIES");
                                    boolean validInput2 = false;
                                    do {
                                        try {
                                            String added = scanner.nextLine();
                                            if (added.toUpperCase().equals("ACTOR")) {
                                                addActorFavorites(thisUser);
                                                validInput2 = true;
                                            } else if (added.toUpperCase().equals("MOVIE")) {
                                                addMovieFavorites(thisUser);
                                                validInput2 = true;
                                            } else if (added.toUpperCase().equals("SERIES")) {
                                                addSeriesFavorites(thisUser);
                                                validInput2 = true;
                                            } else {
                                                throw new InvalidCommandException("Invalid input. Please enter ACTOR, MOVIE, or SERIES.");
                                            }
                                        } catch (InvalidCommandException e) {
                                            System.out.println(e.getMessage());
                                        }
                                    } while (!validInput2);
                                    validInput = true;
                                } else if (input.toUpperCase().equals("DELETE")) {
                                    System.out.println("Do you want to ADD an actor, movie or series into your favorites' list?");
                                    System.out.println("    Type ACTOR, MOVIE or SERIES");
                                    boolean validInput2 = false;
                                    do {
                                        try {
                                            String removed = scanner.nextLine();
                                            if (removed.toUpperCase().equals("ACTOR")) {
                                                removeActorFavorites(thisUser);
                                                validInput2 = true;
                                            } else if (removed.toUpperCase().equals("MOVIE")) {
                                                removeMovieFavorites(thisUser);
                                                validInput2 = true;
                                            } else if (removed.toUpperCase().equals("SERIES")) {
                                                removeSeriesFavorites(thisUser);
                                                validInput2 = true;
                                            } else {
                                                throw new InvalidCommandException("Invalid input. Please enter ACTOR, MOVIE, or SERIES.");
                                            }
                                        } catch (InvalidCommandException e) {
                                            System.out.println(e.getMessage());
                                        }
                                    } while (!validInput2);
                                    validInput = true;
                                } else {
                                    throw new InvalidCommandException("Invalid input. Please enter ADD or DELETE.");
                                }
                            } catch (InvalidCommandException e) {
                                System.out.println(e.getMessage());
                            }
                        } while (!validInput);
                        break;
                    }
                    case 6: {
                        System.out.println("Do you want to 'add' or 'delete' an actor/movie/series?");
                        System.out.println("    Type ADD or DELETE");
                        boolean validInput = false;
                        do {
                            try {
                                String input = scanner.nextLine();
                                if (input.toUpperCase().equals("ADD")) {
                                    System.out.println("Do you want to add an actor, movie or series?");
                                    System.out.println("    Type ACTOR, MOVIE OR SERIES");
                                    boolean validInput2 = false;

                                    do {
                                        try {
                                            String searched = scanner.nextLine();
                                            if (searched.toUpperCase().equals("ACTOR")) {
                                                addActorToSystem(thisUser);
                                                validInput2 = true;
                                            } else if (searched.toUpperCase().equals("MOVIE")) {
                                                optionNotImplemented();
                                                validInput2 = true;
                                            } else if (searched.toUpperCase().equals("SERIES")) {
                                                optionNotImplemented();
                                                validInput2 = true;
                                            } else {
                                                throw new InvalidCommandException("Invalid input. Please enter ACTOR, MOVIE, or SERIES.");
                                            }
                                        } catch (InvalidCommandException e) {
                                            System.out.println(e.getMessage());
                                        }
                                    } while (!validInput2);
                                    validInput = true;
                                } else if (input.toUpperCase().equals("DELETE")) {
                                    System.out.println("Do you want to remove an actor, movie or series?");
                                    System.out.println("    Type ACTOR, MOVIE OR SERIES");
                                    boolean validInput2 = false;
                                    do {
                                        try {
                                            String searched = scanner.nextLine();
                                            if (searched.toUpperCase().equals("ACTOR")) {
                                                deleteActorFromSystem(thisUser);
                                                validInput2 = true;
                                            } else if (searched.toUpperCase().equals("MOVIE")) {
                                                optionNotImplemented();
                                                validInput2 = true;
                                            } else if (searched.toUpperCase().equals("SERIES")) {
                                                optionNotImplemented();
                                                validInput2 = true;
                                            } else {
                                                throw new InvalidCommandException("Invalid input. Please enter ACTOR, MOVIE, or SERIES.");
                                            }
                                        } catch (InvalidCommandException e) {
                                            System.out.println(e.getMessage());
                                        }
                                    } while (!validInput2);
                                    validInput = true;
                                } else {
                                    throw new InvalidCommandException("Invalid input. Please enter ADD or DELETE.");
                                }
                            } catch (InvalidCommandException e) {
                                System.out.println(e.getMessage());
                            }
                        } while (!validInput);
                        break;
                    }
                    case 7:{
                        optionNotImplemented();
                        break;
                    }
                    case 8:{
                        System.out.println("Do you want to update information about an actor or movie/series?");
                        System.out.println("    Type ACTOR or PRODUCTION");
                        boolean validInput = false;
                        do {
                            try {
                                String input = scanner.nextLine();
                                if (input.toUpperCase().equals("ACTOR")) {
                                    updateActor(thisUser);
                                    validInput = true;
                                } else if (input.toUpperCase().equals("PRODUCTION")) {
                                    updateProduction(thisUser);
                                    validInput = true;
                                } else {
                                    throw new InvalidCommandException("Invalid input. Please enter ACTOR or PRODUCTION.");
                                }
                            } catch (InvalidCommandException e) {
                                System.out.println(e.getMessage());
                            }
                        } while (!validInput);
                        break;
                    }
                    case 9:{
                        optionNotImplemented();
                        break;
                    }
                    case 10: {
                        System.out.println("Do you want to log in with another credentials or log out?");
                        System.out.println("Type LOGIN if you want to log in again, otherwise you will be logged out");
                        String logout = scanner.nextLine();
                        if (logout.toUpperCase().equals("LOGIN"))
                            credentialsLogin();
                        else {
                            try {
                                Path filePath = FileSystems.getDefault().getPath("C:\\Users\\Ruxi\\IdeaProjects\\temaPOO2023\\src\\main\\resources\\input\\production.json");
                                Files.delete(filePath);
                                modifyProductionsInJSON("C:\\Users\\Ruxi\\IdeaProjects\\temaPOO2023\\src\\main\\resources\\input\\production.json", getProductions());
                                filePath = FileSystems.getDefault().getPath("C:\\Users\\Ruxi\\IdeaProjects\\temaPOO2023\\src\\main\\resources\\input\\actors.json");
                                Files.delete(filePath);
                                modifyActorsInJSON("C:\\Users\\Ruxi\\IdeaProjects\\temaPOO2023\\src\\main\\resources\\input\\actors.json", IMDB.getActors());
                                filePath = FileSystems.getDefault().getPath("C:\\Users\\Ruxi\\IdeaProjects\\temaPOO2023\\src\\main\\resources\\input\\requests.json");
                                Files.delete(filePath);
                                modifyRequestsInJSON("C:\\Users\\Ruxi\\IdeaProjects\\temaPOO2023\\src\\main\\resources\\input\\requests.json", IMDB.getRequests());

                                thisUser.logout();
                            }catch (IOException e) {
                                System.err.println("Unable to delete the file: " + e.getMessage());
                            }
                        }
                        break;
                    }
                }
                break;
        }
        System.out.println();
        actions(thisUser);
    }

    public static void optionNotImplemented(){
        System.out.println("This option is not available now!");
    }

    public static void viewProductionsDetails(){
        System.out.println("    Do you want to view the productions filtered by Genre, by number of ratings or randomly?");
        System.out.println("    Type G if you want by Genre or R if you want it by number of ratings, otherwise it will be viewed randomly");
        String decision = scanner.nextLine();
        if(decision.toUpperCase().equals("G")){
            Genre[] genres = Genre.values();
            for(Genre genre : genres){
                System.out.println(("                     " + genre + ":").toUpperCase());
                for(Production p : productions) {
                    List<Genre> productionGenres = p.getGenres();
                    for (Genre pg : productionGenres)
                        if(pg.equals(genre)) {
                            p.displayInfo();
                            break;
                        }
                }
            }
        }else if(decision.toUpperCase().equals("R")){
            List<Production> productions2 = new ArrayList<>(productions);
            for (int i = 0; i < productions2.size(); i++) {
                for (int j = i + 1; j < productions2.size(); j++) {
                    int size1 = productions2.get(i).getRatings().size();
                    int size2 = productions2.get(j).getRatings().size();
                    if(size1 == size2){
                        Double rating1 = productions2.get(i).getAverageRating();
                        Double rating2 = productions2.get(j).getAverageRating();
                        if(rating1 < rating2)
                            Collections.swap(productions2, i, j);
                    }
                    // Swap if the size of the ratings list is in descending order
                    if (size1 < size2) {
                        Collections.swap(productions2, i, j);
                    }
                }
            }

            for(Production p : productions2)
                p.displayInfo();
        } else {
            for (Production p : productions)
                p.displayInfo();
        }
    }

    public static void viewActorsDetails(){
        System.out.println("    Do you want to see the actors list alphabetically or randomly?");
        System.out.println("    Type A if you want it to be alphabetically, otherwise it will be viewed randomly");
        String decision = scanner.nextLine();
        if(decision.toUpperCase().equals("A")){
            List<Actor> actors2 = new ArrayList<>(actors);
            Collections.sort(actors2, (actor1, actor2) -> actor1.getActorName().compareTo(actor2.getActorName()));
            for (Actor a : actors2)
                a.displayInfo();
        } else {
            for (Actor a : actors)
                a.displayInfo();
        }
    }

    public static void viewNotifications(User<?> account){
        List<String> notifications = account.getNotifications();
        if(notifications.isEmpty())
            System.out.println("You don't have any notifications!");
        else
            for(String notification : notifications)
                System.out.println(notification);
    }

    public static void searchActor(String name){
        boolean found = false;
        for(Actor a : actors)
            if(a.getActorName().equals(name)){
                found = true;
                a.displayInfo();
            }
        if(found == false)
            System.out.println("The actor you are searching is not found");
    }

    public static void searchProduction(String title){
        boolean found = false;
        for(Production p : productions)
            if(p.getTitle().equals(title)){
                found = true;
                p.displayInfo();
            }
        if(found == false)
            System.out.println("The movie you are searching is not found");
    }

    public static void credentialsLogin(){
        User<?> thisUser = null;
        System.out.println("Please enter your credentials:");
        int ok1 = 0;
        while(ok1 == 0) {
            System.out.print("    email: ");
            String email = scanner.next();
            for (User user : accounts)
                if (user.getInformation().getCredentials().getEmail().equals(email)) {
                    ok1 = 1;
                    int ok2 = 0;
                    while(ok2 == 0) {
                        System.out.print("    password: ");
                        String password = scanner.next();
                        if (user.getInformation().getCredentials().getPassword().equals(password)) {
                            ok2 = 1;
                            thisUser = user;
                            System.out.println("Welcome back user " + user.getUsername() + "!");
                            System.out.println("Username: " + user.getUsername());
                            if(user.getAccountType().equals(AccountType.ADMIN))
                                System.out.println("User experience: - ");
                            else
                                System.out.println("User experience: " + user.getUserExperience() );
                        }
                        if(ok2 == 0)
                            System.out.println("    The password is incorrect. Please, try again!");
                    }
                }
            if(ok1 == 0)
                System.out.println("    The email you entered is not associated to any account. Please, try again!");
        }
        actions(thisUser);
    }

    public static void addActorFavorites(User<?> account){
        System.out.println("Your list of favorite actors now is:");
        boolean exists = false;
        for(Object obj : account.getFavorites()){
            if(obj instanceof Actor)
                exists = true;
        }
        if(exists == false)
            System.out.println(" - empty - ");
        SortedSet<Actor> favoriteActors = (SortedSet<Actor>) new TreeSet<>(account.getFavorites());
        for(Object obj : favoriteActors)
            if(obj instanceof Actor)
                System.out.println(((Actor) obj).getActorName());
        System.out.println();
        System.out.println("You can add one of the following actors into your favorites list: ");
        for (Actor actor : actors)
            if (!favoriteActors.contains(actor))
                System.out.print(actor.getActorName() + ", ");
        System.out.println();
        String actorName = scanner.nextLine();
        for (Actor actor : actors)
            if(actor.getActorName().equals(actorName))
                account.addToFavorites(actor);
        System.out.println();
        System.out.println("Your list of favorites now is: ");
        favoriteActors = (SortedSet<Actor>) new TreeSet<>(account.getFavorites());
        for(Object obj : favoriteActors)
            if(obj instanceof Actor)
                System.out.println(((Actor) obj).getActorName());
        System.out.println();
        System.out.println("Would you like to add another actor?");
        System.out.println("    Type YES if you do otherwise it will be considered as NO");
        String again = scanner.nextLine();
        if(again.toUpperCase().equals("YES"))
            addActorFavorites(account);
    }

    public static void addMovieFavorites(User<?> account){
        System.out.println("Your list of favorite movies now is:");
        boolean exists = false;
        for(Object obj : account.getFavorites()){
            if(obj instanceof Movie)
                exists = true;
        }
        if(exists == false)
            System.out.println(" - empty - ");
        SortedSet<Movie> favoriteMovies = (SortedSet<Movie>) new TreeSet<>(account.getFavorites());
        for(Object obj : favoriteMovies)
            if(obj instanceof Movie)
                System.out.println(((Movie) obj).getTitle());
        System.out.println();
        System.out.println("You can add one of the following movies into your favorites list: ");
        for (Production movie : productions)
            if(movie instanceof Movie)
                if (!favoriteMovies.contains(movie))
                    System.out.print(movie.getTitle() + ", ");
        System.out.println();
        String movieTitle = scanner.nextLine();
        for (Production movie : productions)
            if(movie instanceof Movie)
                if(movie.getTitle().equals(movieTitle))
                    account.addToFavorites(movie);
        System.out.println();
        System.out.println("Your list of favorites now is: ");
        favoriteMovies = (SortedSet<Movie>) new TreeSet<>(account.getFavorites());
        for(Object obj : favoriteMovies)
            if(obj instanceof Movie)
                System.out.println(((Movie) obj).getTitle());
        System.out.println();
        System.out.println("Would you like to add another movie?");
        System.out.println("    Type YES if you do otherwise it will be considered as NO");
        String again = scanner.nextLine();
        if(again.toUpperCase().equals("YES"))
            addMovieFavorites(account);
    }

    public static void addSeriesFavorites(User<?> account){
        System.out.println("Your list of favorite series now is:");
        boolean exists = false;
        for(Object obj : account.getFavorites()){
            if(obj instanceof Series)
                exists = true;
        }
        if(exists == false)
            System.out.println(" - empty - ");
        SortedSet<Series> favoriteSeries = (SortedSet<Series>) new TreeSet<>(account.getFavorites());
        for(Object obj : favoriteSeries)
            if(obj instanceof Series)
                System.out.println(((Series) obj).getTitle());
        System.out.println();
        System.out.println("You can add one of the following series into your favorites list: ");
        for (Production series : productions)
            if(series instanceof Series)
                if (!favoriteSeries.contains(series))
                    System.out.print(series.getTitle() + ", ");
        System.out.println();
        String seriesTitle = scanner.nextLine();
        for (Production series : productions)
            if(series instanceof Series)
                if(series.getTitle().equals(seriesTitle))
                    account.addToFavorites(series);
        System.out.println();
        System.out.println("Your list of favorites now is: ");
        favoriteSeries = (SortedSet<Series>) new TreeSet<>(account.getFavorites());
        for(Object obj : favoriteSeries)
            if(obj instanceof Series)
                System.out.println(((Series) obj).getTitle());
        System.out.println();
        System.out.println("Would you like to add another series?");
        System.out.println("    Type YES if you do otherwise it will be considered as NO");
        String again = scanner.nextLine();
        if(again.toUpperCase().equals("YES"))
            addSeriesFavorites(account);
    }

    public static void removeActorFavorites(User<?> account){
        boolean exists = false;
        for(Object obj : account.getFavorites()){
            if(obj instanceof Actor)
                exists = true;
        }
        if(exists == false) {
            System.out.println("Your favorites list is empty, you can't remove any actors");
            return;
        }
        System.out.println("Your list of favorite actors now is:");
        SortedSet<Actor> favoriteActors = (SortedSet<Actor>) new TreeSet<>(account.getFavorites());
        for(Object obj : favoriteActors)
            if(obj instanceof Actor)
                System.out.println(((Actor) obj).getActorName());
        System.out.println();
        System.out.println("Which of the actors above would you like to remove from your favorites list?" );
        String actorName = scanner.nextLine();
        for (Actor actor : actors)
            if(actor.getActorName().equals(actorName))
                account.removeFromFavorites(actor);
        System.out.println();
        System.out.println("Your list of favorites now is: ");
        favoriteActors = (SortedSet<Actor>) new TreeSet<>(account.getFavorites());
        for(Object obj : favoriteActors)
            if(obj instanceof Actor)
                System.out.println(((Actor) obj).getActorName());
        System.out.println();
        System.out.println("Would you like to remove another actor?");
        System.out.println("    Type YES if you do otherwise it will be considered as NO");
        String again = scanner.nextLine();
        if(again.toUpperCase().equals("YES"))
            removeActorFavorites(account);
    }

    public static void removeMovieFavorites(User<?> account){
        boolean exists = false;
        for(Object obj : account.getFavorites()){
            if(obj instanceof Movie)
                exists = true;
        }
        if(exists == false) {
            System.out.println("Your favorites list is empty, you can't remove any movies");
            return;
        }
        System.out.println("Your list of favorite movies now is:");
        SortedSet<Movie> favoriteMovies = (SortedSet<Movie>) new TreeSet<>(account.getFavorites());
        for(Object obj : favoriteMovies)
            if(obj instanceof Movie)
                System.out.println(((Movie) obj).getTitle());
        System.out.println();
        System.out.println("Which of the movies above would you like to remove from your favorites list?");
        String movieTitle = scanner.nextLine();
        for (Production movie : productions)
            if(movie instanceof Movie)
                if(movie.getTitle().equals(movieTitle))
                    account.removeFromFavorites(movie);
        System.out.println();
        System.out.println("Your list of favorites now is: ");
        favoriteMovies = (SortedSet<Movie>) new TreeSet<>(account.getFavorites());
        for(Object obj : favoriteMovies)
            if(obj instanceof Movie)
                System.out.println(((Movie) obj).getTitle());
        System.out.println();
        System.out.println("Would you like to remove another movie?");
        System.out.println("    Type YES if you do otherwise it will be considered as NO");
        String again = scanner.nextLine();
        if(again.toUpperCase().equals("YES"))
            removeMovieFavorites(account);
    }

    public static void removeSeriesFavorites(User<?> account){
        boolean exists = false;
        for(Object obj : account.getFavorites()){
            if(obj instanceof Series)
                exists = true;
        }
        if(exists == false) {
            System.out.println("Your favorites list is empty, you can't remove any series");
            return;
        }
        System.out.println("Your list of favorite series now is:");
        SortedSet<Series> favoriteSeries = (SortedSet<Series>) new TreeSet<>(account.getFavorites());
        for(Object obj : favoriteSeries)
            if(obj instanceof Series)
                System.out.println(((Series) obj).getTitle());
        System.out.println();
        System.out.println("Which of the series above would you like to remove from your favorites list?");
        String seriesTitle = scanner.nextLine();
        for (Production series : productions)
            if(series instanceof Series)
                if(series.getTitle().equals(seriesTitle))
                    account.removeFromFavorites(series);
        System.out.println();
        System.out.println("Your list of favorites now is: ");
        favoriteSeries = (SortedSet<Series>) new TreeSet<>(account.getFavorites());
        for(Object obj : favoriteSeries)
            if(obj instanceof Series)
                System.out.println(((Series) obj).getTitle());
        System.out.println();
        System.out.println("Would you like to remove another series?");
        System.out.println("    Type YES if you do otherwise it will be considered as NO");
        String again = scanner.nextLine();
        if(again.toUpperCase().equals("YES"))
            removeSeriesFavorites(account);
    }

    public static void createRequest(User<?> account){
        System.out.println("What is the issue you're encountering: actor, movie , deleting your account or other");
        System.out.println("    Type ACTOR, MOVIE, DELETE or OTHER");
        RequestTypes type = null;
        boolean validInput = false;
        do {
            try {
                String input = scanner.nextLine();
                if (input.toUpperCase().equals("ACTOR")) {
                    type = RequestTypes.ACTOR_ISSUE;
                    validInput = true;
                } else if (input.toUpperCase().equals("MOVIE")) {
                    type = RequestTypes.MOVIE_ISSUE;
                    validInput = true;
                } else if (input.toUpperCase().equals("DELETE")){
                    type = RequestTypes.DELETE_ACCOUNT;
                    validInput = true;
                } else if (input.toUpperCase().equals("OTHER")){
                    type = RequestTypes.OTHERS;
                    validInput = true;
                }
                else {
                    throw new InvalidCommandException("Invalid input. Please enter ACTOR, MOVIE, DELETE or OTHER.");
                }
            } catch (InvalidCommandException e) {
                System.out.println(e.getMessage());
            }
        } while (!validInput);
        System.out.println("Please write what is the description of the issue below");
        String problemDescription = scanner.nextLine();
        LocalDateTime currentTime = LocalDateTime.now();
        String name = null;
        String usernameResolve = null;
        if(type.equals(RequestTypes.ACTOR_ISSUE)){
            for(Actor actor : actors)
                if(problemDescription.contains(actor.getActorName()))
                    name = actor.getActorName();
            for(User<?> user : accounts) {
                if (user instanceof Contributor<?> || user instanceof Admin<?>)
                    for (Object obj : ((Staff<?>) user).getContributionList())
                        if (obj instanceof Actor)
                            if (((Actor) obj).getActorName().equals(name))
                                usernameResolve = user.getUsername();
            }
        }else if(type.equals(RequestTypes.MOVIE_ISSUE)){
            for(Production movie : productions)
                if(movie instanceof Movie)
                    if(problemDescription.contains(movie.getTitle()))
                        name = movie.getTitle();
            for(User<?> user : accounts) {
                if (user instanceof Contributor<?> || user instanceof Admin<?>)
                    for (Object obj : ((Staff<?>) user).getContributionList())
                        if (obj instanceof Movie)
                            if (((Movie) obj).getTitle().equals(name))
                                usernameResolve = user.getUsername();
            }
        }else if(type.equals(RequestTypes.DELETE_ACCOUNT) || type.equals(RequestTypes.OTHERS)){
            usernameResolve = "ADMIN";
        }

        Request newrequest = new Request(type, currentTime, name, problemDescription, account.getUsername(), usernameResolve);
        requests.add(newrequest);
        System.out.println("    Your request was created!");
    }

    public static void removeRequest(User<?> account){
        int exists = 0;
        for(Request r : requests){
            if(r.getUsernameRequest().equals(account.getUsername()))
                exists++;
        }
        if(exists == 0) {
            System.out.println("You don't have any requests made, so you can't delete any!");
            return;
        }
        if(exists == 1){
            System.out.println("Your request will be removed");
            for(Request r : requests) {
                if (r.getUsernameRequest().equals(account.getUsername())) {
                    requests.remove(r);
                    return;
                }
            }
        }
        if(exists > 1){
            System.out.println("You have " + exists + " requests right now:");
            System.out.println("The index of each of your requests is the waiting number for the request to be resolved");
            int index = 1;
            for(Request r : requests) {
                if (r.getUsernameRequest().equals(account.getUsername())) {
                    System.out.println(index + ". " + r);
                }
                index++;
            }
            System.out.println("Which one would you like to remove? Enter its index:");
            String requestToRemove = scanner.nextLine();
            try {
                int indexremoved = Integer.parseInt(requestToRemove);
                requests.remove(indexremoved);
            } catch (NumberFormatException e) {}
        }
    }

    public static void createRating(User<?> account){
        System.out.println("For what production would you like to make a rating?");
        for(Production p : productions)
            System.out.print(p.getTitle() + ", ");
        System.out.println();
        String title = scanner.nextLine();
        for(Production p : productions)
            if(p.getTitle().equals(title)){
                for(Rating r : p.getRatings())
                    if(r.getUsername().equals(account.getUsername())) {
                        System.out.println("You already have a rating for this production. You need to delete the other rating first!");
                        return;
                    }
                System.out.println("Enter the score:");
                String scoreString = scanner.nextLine();
                int score = 0;
                try {
                    score = Integer.parseInt(scoreString);
                    if(score < 0 || score > 10)
                        throw new InvalidCommandException("Please enter a number between 0 and 10!");
                } catch (NumberFormatException | InvalidCommandException e) {
                    System.out.println(e.getMessage());
                }
                System.out.println("Enter a comment:");
                String comment = scanner.nextLine();
                Rating newrating = new Rating(account.getUsername(), score, comment);
                p.getRatings().add(newrating);
            }
    }

    public static void removeRating(User<?> account){
        boolean exists = false;
        for(Production p : productions){
            for(Rating r : p.getRatings())
                if(r.getUsername().equals(account.getUsername()))
                    exists = true;
        }
        if(!exists){
            System.out.println("You have no ratings done!");
            return;
        }
        System.out.println("You have ratings for the following productions:");
        for(Production p : productions){
            for(Rating r : p.getRatings())
                if(r.getUsername().equals(account.getUsername()))
                    System.out.println(p.getTitle());
        }
        System.out.println("Which one would you like to remove? Enter the title: ");
        String title = scanner.nextLine();
        for(Production p : productions){
            if(p.getTitle().equals(title))
                for(Rating r : p.getRatings())
                    if(r.getUsername().equals(account.getUsername())){
                        p.getRatings().remove(r);
                        return;
                    }
        }
    }

    public static void updateActor(User<?> account){
        System.out.println("You have this list of actors:");
        for (Actor actor : actors)
            if(((Staff<?>) account).getContributionList().contains(actor))
                System.out.println(actor.getActorName());
        System.out.println();
        boolean validName = false;
        do {
            try {
                System.out.println("Which actor would you like to update? Enter its name:");
                String actorname = scanner.nextLine();
                for (Actor actor : actors)
                    if (actorname.equals(actor.getActorName())) {
                        if (((Staff<?>) account).getContributionList().contains(actor)) {
                            ((Staff<?>) account).updateActor(actor);
                            validName = true;
                            return;
                        }
                    }
                if(!validName)
                    throw new InvalidCommandException("The name you entered is not on the list! Enter it again.");
            }catch (InvalidCommandException e) {
                System.out.println(e.getMessage());
                System.out.println();
            }
        }while(!validName);
    }

    public static void updateProduction(User<?> account){
        System.out.println("You have this list of productions:");
        for (Production p : productions)
            if(((Staff<?>) account).getContributionList().contains(p))
                System.out.println(p.getTitle());
        System.out.println();
        boolean validName = false;
        do {
            try {
                System.out.println("Which production would you like to update? Enter its name:");
                String productiontitle = scanner.nextLine();
                for(Production p : productions)
                    if(productiontitle.equals(p.getTitle())) {
                        if(((Staff<?>) account).getContributionList().contains(p)) {
                            ((Staff<?>) account).updateProduction(p);
                            validName = true;
                            return;
                        }
                    }
                if(!validName)
                    throw new InvalidCommandException("The title you entered is not on the list! Enter it again.");
            }catch (InvalidCommandException e) {
                System.out.println(e.getMessage());
                System.out.println();
            }
        }while(!validName);
        //modifyProductionsInJSON("C:\\Users\\Ruxi\\IdeaProjects\\temaPOO2023\\src\\main\\resources\\input\\production2.json", getProductions());
    }

    public static void addActorToSystem(User<?> account) {
        Actor newActor;
        String name;
        boolean alreadyexists;
        do {
            alreadyexists = false;
            System.out.println("    Enter the name of the new actor:");
            name = scanner.nextLine();
            for (Actor actor : actors)
                if (actor.getActorName().equals(name)) {
                    System.out.println("The name of the actor you entered is already in the system! Introduce another one.");
                    alreadyexists = true;
                    break;
                }

        }while(alreadyexists);
        System.out.println("    Enter the biography of the new actor:");
        String biography = scanner.nextLine();

        newActor = new Actor(name, biography);

        boolean addRoles = true;
        System.out.println("    Enter the roles of the new actor");
        do{
            System.out.println("You can enter one of these productions:");
            for(Production p : productions)
                if(!newActor.getRoles().containsKey(p.getTitle()))
                    System.out.print(p.getTitle() + ", ");
            System.out.println();

            boolean inproductions = false;
            do{
                System.out.println("Enter the title of the production");
                String title = scanner.nextLine();
                for(Production p : productions)
                    if(p.getTitle().equals(title)){
                        newActor.addRole(p.getTitle(), p.getType());
                        inproductions = true;
                        break;
                    }
                if(!inproductions)
                    System.out.println("The title you entered in not in the system. Try again.");
            }while(!inproductions);

            System.out.println("Do you want to add another role? ");
            System.out.println("    Type 'YES' if you do");
            String addRolesInput = scanner.nextLine();
            if (!addRolesInput.toUpperCase().equals("YES"))
                addRoles = false;
        } while (addRoles);

        // Add the new actor to the list
        actors.add(newActor);

        System.out.println("New actor added successfully!");
        ((Staff<?>) account).addActorSystem(newActor);
    }

    public static void deleteActorFromSystem(User<?> account) {
        System.out.println("You can remove one of these actors:");
        for (Actor actor : actors)
            if(((Staff<?>) account).getContributionList().contains(actor))
                System.out.println(actor.getActorName());
        System.out.println();

        boolean found = false;
        do {
            System.out.println("Enter the name of the actor you want to delete:");
            String actorName = scanner.nextLine();
            for (Actor actor : actors)
                if (actorName.equals(actor.getActorName())) {
                    actors.remove(actor);
                    ((Staff<?>) account).removeActorSystem(actorName);
                    found = true;
                    break;
                }
            if (!found)
                System.out.println("The actor you want to delete is not in the system. Enter the name again.");
        }while(!found);
    }

    public static void accountsReading(String file){
        UserFactory userFactory = new UserFactory();
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(file)) {
            Object obj = jsonParser.parse(reader);


            JSONArray accountsList = (JSONArray) obj;

            accounts = new ArrayList<>();

            for (Object account : accountsList) {
                JSONObject accountObject = (JSONObject) account;
                String username = (String) accountObject.get("username");
                String userExperience = (String) accountObject.get("experience");
                String userType = (String) accountObject.get("userType");
                List<String> notifications = getStringList(accountObject, "notifications");
                JSONObject informationObject = (JSONObject) accountObject.get("information");
                JSONObject credentialsObject = (JSONObject) informationObject.get("credentials");
                String email = (String) credentialsObject.get("email");
                String password = (String) credentialsObject.get("password");
                String name = (String) informationObject.get("name");
                String country = (String) informationObject.get("country");
                long age = (long) informationObject.get("age");
                String gender = (String) informationObject.get("gender");
                LocalDateTime userDateOfBirth = getFormattedDateTime_Account((String) informationObject.get("birthDate"));
                List<String> productionsContribution = getStringList(accountObject, "productionsContribution");
                List<String> actorsContribution = getStringList(accountObject, "actorsContribution");
                List<String> favoriteProductions = getStringList(accountObject, "favoriteProductions");
                List<String> favoriteActors = getStringList(accountObject, "favoriteActors");

                User<?> currentUser = null;
                try{
                    if(userType.equals("Regular")){
                        Regular.Information.InformationBuilder currentInfoBuilder =
                                new Regular.Information.InformationBuilder(name)
                                        .credentials(new Credentials(email, password))
                                        .UserCountry(country)
                                        .UserAge(age)
                                        .UserGender(gender)
                                        .UserDateOfBirth(userDateOfBirth);
                        currentUser = userFactory.createUser(AccountType.REGULAR, name, userExperience, username, email, password);
                        //currentUser = new Regular<>(name, AccountType.REGULAR, userExperience, username, email, password);
                        currentUser.setInformation(currentInfoBuilder);
                        currentUser.setNotifications(notifications);
                        for(String productionTitle : favoriteProductions)
                            for(Production production : productions)
                                if(productionTitle.equals(production.getTitle()))
                                    currentUser.addToFavorites(production);
                        for(String actorName : favoriteActors)
                            for(Actor actor : actors)
                                if(actorName.equals(actor.getActorName()))
                                    currentUser.addToFavorites(actor);

                    }else if(userType.equals("Contributor")){
                        Contributor.Information.InformationBuilder currentInfoBuilder =
                                new Contributor.Information.InformationBuilder(name)
                                        .credentials(new Credentials(email, password))
                                        .UserCountry(country)
                                        .UserAge(age)
                                        .UserGender(gender)
                                        .UserDateOfBirth(userDateOfBirth);
                        currentUser = userFactory.createUser(AccountType.CONTRIBUTOR, name, userExperience, username, email, password);
                        //currentUser = new Contributor<>(name, AccountType.CONTRIBUTOR, userExperience, username, email, password);
                        currentUser.setInformation(currentInfoBuilder);
                        currentUser.setNotifications(notifications);
                        for(String productionTitle : favoriteProductions)
                            for(Production production : productions)
                                if(productionTitle.equals(production.getTitle()))
                                    currentUser.addToFavorites(production);
                        for(String actorName : favoriteActors)
                            for(Actor actor : actors)
                                if(actorName.equals(actor.getActorName()))
                                    currentUser.addToFavorites(actor);
                        for(String productionTitle : productionsContribution)
                            for(Production production : productions)
                                if(productionTitle.equals(production.getTitle()))
                                    ((Contributor<?>) currentUser).addToContributionList(production);
                        for(String actorName : actorsContribution)
                            for(Actor actor : actors)
                                if(actorName.equals(actor.getActorName()))
                                    ((Contributor<?>) currentUser).addToContributionList(actor);
                    }else if(userType.equals("Admin")){
                        Admin.Information.InformationBuilder currentInfoBuilder =
                                new Admin.Information.InformationBuilder(name)
                                        .credentials(new Credentials(email, password))
                                        .UserCountry(country)
                                        .UserAge(age)
                                        .UserGender(gender)
                                        .UserDateOfBirth(userDateOfBirth);
                        currentUser = userFactory.createUser(AccountType.ADMIN, name, userExperience, username, email, password);
                        //currentUser = new Admin<>(name, AccountType.ADMIN, userExperience, username, email, password);
                        currentUser.setInformation(currentInfoBuilder);
                        currentUser.setNotifications(notifications);
                        for(String productionTitle : favoriteProductions)
                            for(Production production : productions)
                                if(productionTitle.equals(production.getTitle()))
                                    currentUser.addToFavorites(production);
                        for(String actorName : favoriteActors)
                            for(Actor actor : actors)
                                if(actorName.equals(actor.getActorName()))
                                    currentUser.addToFavorites(actor);
                        for(String productionTitle : productionsContribution)
                            for(Production production : productions)
                                if(productionTitle.equals(production.getTitle()))
                                    ((Admin<?>) currentUser).addToContributionList(production);
                        for(String actorName : actorsContribution)
                            for(Actor actor : actors)
                                if(actorName.equals(actor.getActorName()))
                                    ((Admin<?>) currentUser).addToContributionList(actor);
                        /*for(String productionTitle : productionsContribution)
                            for(Production production : productions)
                                if(productionTitle.equals(production.getTitle()))
                                    ((Admin<?>) currentUser).addToAdminContributionList(production);
                        for(String actorName : actorsContribution)
                            for(Actor actor : actors)
                                if(actorName.equals(actor.getActorName()))
                                    ((Admin<?>) currentUser).addToAdminContributionList(actor);*/
                    }
                }catch(IMDB.InformationIncompleteException ignored){}
                if(currentUser != null)
                    accounts.add(currentUser);
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static void requestsReading(String file){
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(file)) {
            Object obj = jsonParser.parse(reader);

            JSONArray requestsList = (JSONArray) obj;

            requests = new ArrayList<>();

            for (Object request : requestsList) {
                JSONObject requestObject = (JSONObject) request;
                RequestTypes requestType = RequestTypes.valueOf((String) requestObject.get("type"));
                LocalDateTime localDateTime = getFormattedDateTime_Request((String) requestObject.get("createdDate"));
                String problemDescription = (String) requestObject.get("description");
                String usernameRequest = (String) requestObject.get("username");
                String usernameResolve = (String) requestObject.get("to");
                String name = "";
                if (requestType.equals(RequestTypes.ACTOR_ISSUE)) {
                    name = (String) requestObject.get("actorName");
                } else if (requestType.equals(RequestTypes.MOVIE_ISSUE)) {
                    name = (String) requestObject.get("movieTitle");
                }
                Request currentRequest = new Request(requestType, localDateTime, name, problemDescription, usernameRequest, usernameResolve);
                requests.add(currentRequest);
            }

            //for (Request request : requests)
            //    System.out.println(request);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static void actorsReading(String file){
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(file)) {
            Object obj = jsonParser.parse(reader);

            JSONArray actorsList = (JSONArray) obj;

            actors = new ArrayList<>();

            for (Object actor : actorsList) {
                JSONObject actorObject = (JSONObject) actor;
                String actorName = (String) actorObject.get("name");
                String biography = (String) actorObject.get("biography");
                Actor currentActor = new Actor(actorName, biography);

                JSONArray performancesArray = (JSONArray) actorObject.get("performances");
                for (Object performanceObj : performancesArray) {
                    JSONObject performanceJson = (JSONObject) performanceObj;
                    String title = (String) performanceJson.get("title");
                    String type = (String) performanceJson.get("type");
                    currentActor.addRole(title, type);
                }
                actors.add(currentActor);
            }

            //for (Actor actor : actors)
            //    System.out.println(actor);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static void productionsReading(String file){
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(file)) {
            Object obj = jsonParser.parse(reader);

            JSONArray productionList = (JSONArray) obj;

            productions = new ArrayList<>();

            for (Object production : productionList) {
                JSONObject productionObject = (JSONObject) production;
                String type = (String) productionObject.get("type");
                String title = (String) productionObject.get("title");
                List<String> directors = getStringList(productionObject, "directors");
                List<String> actors = getStringList(productionObject, "actors");
                List<Genre> genres = new ArrayList<>();
                List<String> genresArray = getStringList(productionObject, "genres");
                for(String genre : genresArray ) {
                    try {
                        Genre enumGenre = Genre.valueOf(genre);
                        genres.add(enumGenre);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid genre: " + genre);
                    }
                }
                List<Rating> ratings = new ArrayList<>();
                List<JSONObject> ratingsArray = getJsonObjectList(productionObject);
                for(JSONObject rating : ratingsArray ) {
                    String username = (String) rating.get("username");
                    int ratingValue = ((Long) rating.get("rating")).intValue();
                    String comment = (String) rating.get("comment");
                    ratings.add(new Rating(username, ratingValue, comment));
                }
                String plot = (String) productionObject.get("plot");
                Double averageRating = (Double) productionObject.get("averageRating");

                if("Movie".equals(type)){
                    String duration = (String) productionObject.get("duration");
                    int releaseYear = 0;
                    if(productionObject.get("releaseYear") != null)
                        releaseYear = ((Long) productionObject.get("releaseYear")).intValue();
                    Movie currentMovie = new Movie(title, type, directors, actors, genres, ratings, plot, averageRating, duration, releaseYear);
                    productions.add(currentMovie);
                }else if("Series".equals(type)){
                    int releaseYear = 0;
                    if(productionObject.get("releaseYear") != null)
                        releaseYear = ((Long) productionObject.get("releaseYear")).intValue();
                    int numSeasons = 0;
                    if(productionObject.get("numSeasons") != null)
                        numSeasons = ((Long) productionObject.get("numSeasons")).intValue();
                    Map<String, List<Episode>> seasons = new TreeMap<>();
                    JSONObject seasonsObject = (JSONObject) productionObject.get("seasons");
                    ArrayList<String> sortedSeasons = new ArrayList<>(seasonsObject.keySet());
                    Collections.sort(sortedSeasons);

                    for (String seasonName : sortedSeasons) {
                        JSONArray episodesArray = (JSONArray) seasonsObject.get(seasonName);

                        List<Episode> episodes = new ArrayList<>();
                        for (Object episodeObj : episodesArray) {
                            JSONObject episodeJson = (JSONObject) episodeObj;
                            String episodeName = (String) episodeJson.get("episodeName");
                            String duration = (String) episodeJson.get("duration");
                            episodes.add(new Episode(episodeName, duration));
                        }

                        seasons.put(seasonName, episodes);
                    }
                    Series currentSerie = new Series(title, type, directors, actors, genres, ratings, plot, averageRating, releaseYear, numSeasons, seasons);
                    productions.add(currentSerie);
                }
            }

            //for (Production production : productions)
            //    production.displayInfo();

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static void modifyAccountsInJSON(String file, List<User<?>> accounts) {
        JSONArray accountsArray = new JSONArray();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (User<?> user : accounts) {
            JSONObject accountObject = new JSONObject();

            accountObject.put("username", user.getUsername());
            accountObject.put("experience", user.getUserExperience());
            accountObject.put("userType", user.getAccountType().toString());
            accountObject.put("notifications", user.getNotifications());

            User.Information information = user.getInformation();
            JSONObject informationObject = new JSONObject();
            informationObject.put("name", information.getUserName());
            informationObject.put("country", information.getUserCountry());
            informationObject.put("age", information.getUserAge());
            informationObject.put("gender", information.getUserGender());
            informationObject.put("birthDate", information.getUserDateOfBirth().format(dateFormatter));

            // Add other fields as needed based on your Information class

            JSONObject credentialsObject = new JSONObject();
            credentialsObject.put("email", information.getCredentials().getEmail());
            credentialsObject.put("password", information.getCredentials().getPassword());
            informationObject.put("credentials", credentialsObject);

            accountObject.put("information", informationObject);

            // Add other fields as needed based on your User class

            accountsArray.add(accountObject);
        }
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(accountsArray.toJSONString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void modifyRequestsInJSON(String file, List<Request> requests) {
        JSONArray requestsArray = new JSONArray();
        for (Request request : requests) {
            JSONObject requestObject = new JSONObject();

            requestObject.put("type", request.getRequestType().toString());
            requestObject.put("createdDate", request.getLocalDateTime().toString());
            requestObject.put("description", request.getProblemDescription());
            requestObject.put("username", request.getUsernameRequest());
            requestObject.put("to", request.getUsernameResolve());

            // Add other fields as needed based on your Request class

            requestsArray.add(requestObject);
        }
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(requestsArray.toJSONString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void modifyActorsInJSON(String file, List<Actor> actors) {
        JSONArray actorsArray = new JSONArray();
        for (Actor actor : actors) {
            JSONObject actorObject = new JSONObject();

            actorObject.put("name", actor.getActorName());
            actorObject.put("biography", actor.getBiography());

            // Handle performances
            JSONArray performancesArray = new JSONArray();
            for (Map.Entry<String, String> entry : actor.getRoles().entrySet()) {
                JSONObject performanceObject = new JSONObject();
                performanceObject.put("title", entry.getKey());
                performanceObject.put("type", entry.getValue());
                performancesArray.add(performanceObject);
            }
            actorObject.put("performances", performancesArray);

            // Add other fields as needed based on your Actor class

            actorsArray.add(actorObject);
        }
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(actorsArray.toJSONString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void modifyProductionsInJSON(String file, List<Production> productions){
        JSONArray productionsArray = new JSONArray();
        for(Production production : productions){
            JSONObject productionObject = new JSONObject();

            productionObject.put("type", production.getType());
            productionObject.put("title", production.getTitle());
            productionObject.put("directors", production.getDirectors());
            productionObject.put("actors", production.getActors());

            //productionObject.put("genres", production.getGenres().toString());
            JSONArray genresArray = new JSONArray();
            for (Genre genre : production.getGenres()) {
                genresArray.add(genre.toString());
            }
            productionObject.put("genres", genresArray);

            // Handle ratings
            JSONArray ratingsArray = new JSONArray();
            for (Rating rating : production.getRatings()) {
                JSONObject ratingObject = new JSONObject();
                ratingObject.put("username", rating.getUsername());
                ratingObject.put("rating", rating.getScore());
                ratingObject.put("comment", rating.getComment());
                ratingsArray.add(ratingObject.toJSONString());
            }
            productionObject.put("ratings", ratingsArray);

            productionObject.put("plot", production.getPlot());
            productionObject.put("averageRating", production.getAverageRating());
            if (production instanceof Movie) {
                Movie movie = (Movie) production;
                productionObject.put("duration", movie.getDuration());
                productionObject.put("releaseYear", movie.getReleaseYear());
            } else if (production instanceof Series) {
                Series series = (Series) production;
                productionObject.put("releaseYear", series.getReleaseYear());
                productionObject.put("numSeasons", series.getNrOfSeasons());

                // Handle seasons for Series
                JSONObject seasonsObject = new JSONObject();
                for (Map.Entry<String, List<Episode>> entry : series.getSeasons().entrySet()) {
                    String seasonName = entry.getKey();
                    List<Episode> episodes = entry.getValue();

                    JSONArray episodesArray = new JSONArray();
                    for (Episode episode : episodes) {
                        JSONObject episodeJson = new JSONObject();
                        episodeJson.put("episodeName", episode.getTitle());
                        episodeJson.put("duration", episode.getDuration());
                        episodesArray.add(episodeJson);
                    }

                    seasonsObject.put(seasonName, episodesArray);
                }
                productionObject.put("seasons", seasonsObject);
            }

            // Add other fields as needed based on your Production class

            productionsArray.add(productionObject);
        }
        try(FileWriter fileWriter = new FileWriter(file)){
            //System.out.println("MODIFIED FILE");
            fileWriter.write(productionsArray.toJSONString());
            fileWriter.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private static List<String> getStringList(JSONObject object, String key) {
        List<String> list = new ArrayList<>();
        Object value = object.get(key);
        if (value instanceof JSONArray jsonArray) {
            for (Object item : jsonArray) {
                if (item instanceof String) {
                    list.add((String) item);
                }
            }
        }
        return list;
    }

    private static List<JSONObject> getJsonObjectList(JSONObject object) {
        List<JSONObject> list = new ArrayList<>();
        Object value = object.get("ratings");
        if (value instanceof JSONArray jsonArray) {
            for (Object item : jsonArray) {
                if (item instanceof JSONObject) {
                    list.add((JSONObject) item);
                }
            }
        }
        return list;
    }

    public static LocalDateTime getFormattedDateTime_Request(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return LocalDateTime.parse(dateTimeString, formatter);
    }
    public static LocalDateTime getFormattedDateTime_Account(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateTimeString, formatter).atStartOfDay();
    }
    public static class InformationIncompleteException extends RuntimeException {
        public InformationIncompleteException(String message) {
            super(message);
        }
    }
    public static class InvalidCommandException extends Exception {
        public InvalidCommandException(String message) {
            super(message);
        }
    }

    public static void main(String[] args) throws InvalidCommandException {
        IMDB imdb = IMDB.getInstance();
        imdb.run();
        //run();
    }
}
