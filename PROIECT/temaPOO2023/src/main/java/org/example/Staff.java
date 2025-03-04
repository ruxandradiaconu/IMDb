package org.example;

import java.util.*;

public abstract class Staff<T extends Comparable<T>> extends User<T> implements StaffInterface{
    private List<Request> requestsList;
    private SortedSet<T> contributionList;
    public Staff(String name, AccountType accountType, String userExperience, String username,String email, String password) {
        super(name, accountType, userExperience, username,email, password);
        this.requestsList = new ArrayList<>();
        this.contributionList = new TreeSet<>();
    }

    public List<Request> getRequestsList() {
        return requestsList;
    }

    public void setRequestsList(List<Request> requestsList) {
        this.requestsList = requestsList;
    }

    public SortedSet<T> getContributionList() {
        return contributionList;
    }

    public void setContributionList(SortedSet<T> contributionList) {
        this.contributionList = contributionList;
    }

    public <fav> void addToContributionList(fav item) {
        contributionList.add((T) item);
    }
    public <fav> void removeFromContributionList(fav item) {
        contributionList.remove((T) item);
    }

    @Override
    public void addProductionSystem(Production p) {
        contributionList.add((T) p);
    }

    @Override
    public void addActorSystem(Actor a) {
        contributionList.add((T) a);
    }

    @Override
    public void removeProductionSystem(String name) {
        for(T production : getContributionList())
            if(production instanceof Production)
                if(((Production) production).getTitle().equals(name))
                    contributionList.remove(production);
    }

    @Override
    public void removeActorSystem(String name) {
        for(T actor : getContributionList())
            if(actor instanceof Actor)
                if(((Actor) actor).getActorName().equals(name))
                    contributionList.remove(actor);
    }

    @Override
    public void updateProduction(Production p) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("This " + p.getType().toLowerCase() + " looks like this now:");
        p.displayInfo();
        if (p instanceof Movie) {
            System.out.println("What would you like to update: title (T), directors list (DIR), actors list (ACT), genres (G), plot description (P), average rating (AR), duration (DUR) or release year (RY) ?");
            System.out.println("Type T, DIR, ACT, G, P, AR, DUR or RY");
            boolean validInput = false;
            do {
                try {
                    String input = scanner.nextLine();
                    switch (input.toUpperCase()) {
                        case "T" -> {
                            System.out.println("Enter the updated title:");
                            String title = scanner.nextLine();
                            p.setTitle(title);
                            validInput = true;
                        }
                        case "P" -> {
                            System.out.println("Enter the updated plot description:");
                            String plot = scanner.nextLine();
                            p.setPlot(plot);
                            validInput = true;
                        }
                        case "AR" -> {
                            System.out.println("Enter the updated average rating:");
                            Double averageRating = Double.valueOf(scanner.nextLine());
                            p.setAverageRating(averageRating);
                            validInput = true;
                        }
                        case "DUR" -> {
                            System.out.println("Enter the updated duration (enter in the format: x minutes) :");
                            String duration = scanner.nextLine();
                            ((Movie) p).setDuration(duration);
                            validInput = true;
                        }
                        case "RY" -> {
                            System.out.println("Enter the updated release year:");
                            int releaseyear = Integer.parseInt(scanner.nextLine());
                            ((Movie) p).setReleaseYear(releaseyear);
                            validInput = true;
                        }
                        case "DIR" -> {
                            System.out.println("Would you like to add or delete a director?");
                            System.out.println("Type ADD or DELETE");
                            boolean validInput2 = false;
                            do {
                                try {
                                    String input2 = scanner.nextLine();
                                    if (input2.toUpperCase().equals("ADD")) {
                                        System.out.println("Your directors list is now : ");
                                        for (String director : p.getDirectors())
                                            System.out.print(director + ", ");
                                        System.out.println();
                                        System.out.println("Enter the name of the new director : ");
                                        String added = scanner.nextLine();
                                        p.getDirectors().add(added);
                                        System.out.println("Your updated directors list is : ");
                                        for (String director : p.getDirectors())
                                            System.out.print(director + ", ");
                                        System.out.println();
                                        validInput2 = true;
                                    } else if (input2.toUpperCase().equals("DELETE")) {
                                        System.out.println("Which director on the list do you want to delete?");
                                        boolean validInput3 = false;
                                        do {
                                            try {
                                                String removed = scanner.nextLine();
                                                if (p.getDirectors().contains(removed)) {
                                                    p.getDirectors().remove(removed);
                                                    validInput3 = true;
                                                } else {
                                                    throw new IMDB.InvalidCommandException("The director name you entered is not on the list! Try entering again.");
                                                }
                                            } catch (IMDB.InvalidCommandException e) {
                                                System.out.println(e.getMessage());
                                                System.out.println();
                                            }
                                        } while (!validInput3);
                                        validInput2 = true;
                                    } else {
                                        throw new IMDB.InvalidCommandException("Invalid input. Please enter ADD or DELETE.");
                                    }
                                } catch (IMDB.InvalidCommandException e) {
                                    System.out.println(e.getMessage());
                                }
                            } while (!validInput2);
                            validInput = true;
                        }
                        case "ACT" -> {
                            System.out.println("Would you like to add or delete a actor?");
                            System.out.println("Type ADD or DELETE");
                            boolean validInput2 = false;
                            do {
                                try {
                                    String input2 = scanner.nextLine();
                                    if (input2.toUpperCase().equals("ADD")) {
                                        System.out.println("Your actors list is now : ");
                                        for (String actor : p.getActors())
                                            System.out.print(actor + ", ");
                                        System.out.println();
                                        System.out.println("Enter the name of the new actor : ");
                                        String added = scanner.nextLine();
                                        p.getActors().add(added);
                                        System.out.println("Your updated actors list is : ");
                                        for (String actor : p.getActors())
                                            System.out.print(actor + ", ");
                                        System.out.println();
                                        validInput2 = true;
                                    } else if (input2.toUpperCase().equals("DELETE")) {
                                        System.out.println("Which actor on the list do you want to delete?");
                                        boolean validInput3 = false;
                                        do {
                                            try {
                                                String removed = scanner.nextLine();
                                                if (p.getActors().contains(removed)) {
                                                    p.getActors().remove(removed);
                                                    validInput3 = true;
                                                } else {
                                                    throw new IMDB.InvalidCommandException("The actor name you entered is not on the list! Try entering again.");
                                                }
                                            } catch (IMDB.InvalidCommandException e) {
                                                System.out.println(e.getMessage());
                                                System.out.println();
                                            }
                                        } while (!validInput3);
                                        validInput2 = true;
                                    } else {
                                        throw new IMDB.InvalidCommandException("Invalid input. Please enter ADD or DELETE.");
                                    }
                                } catch (IMDB.InvalidCommandException e) {
                                    System.out.println(e.getMessage());
                                }
                            } while (!validInput2);
                            validInput = true;
                        }
                        case "G" -> {
                            Genre[] genres = Genre.values();
                            List<String> stringGenres = new ArrayList<>();
                            for (Genre genre : Genre.values())
                                stringGenres.add(genre.name());
                            System.out.println("Would you like to add or delete a genre?");
                            System.out.println("Type ADD or DELETE");
                            boolean validInput2 = false;
                            do {
                                try {
                                    String input2 = scanner.nextLine();
                                    if (input2.toUpperCase().equals("ADD")) {
                                        System.out.println("Your genres list is now : ");
                                        for (Genre genre : p.getGenres())
                                            System.out.print(genre + ", ");
                                        System.out.println();
                                        boolean validInput3 = false;
                                        do {
                                            try {
                                                System.out.println("Enter the name of the new genre: ");
                                                System.out.println("You can choose from: ");
                                                for (Genre genre : genres)
                                                    for (Genre g : p.getGenres())
                                                        if (!g.equals(genre))
                                                            System.out.print(genre + ", ");
                                                System.out.println();
                                                String added = scanner.nextLine();
                                                if (stringGenres.contains(added)) {
                                                    p.getGenres().add(Genre.valueOf(added));
                                                    validInput3 = true;
                                                } else
                                                    throw new IMDB.InvalidCommandException("The genre you entered is not correct! Try entering again.");
                                            } catch (IMDB.InvalidCommandException e) {
                                                System.out.println(e.getMessage());
                                            }
                                        } while (!validInput3);
                                        validInput2 = true;
                                    } else if (input2.toUpperCase().equals("DELETE")) {
                                        System.out.println("Which genre on the list do you want to delete?");
                                        boolean validInput3 = false;
                                        do {
                                            try {
                                                String removed = scanner.nextLine();
                                                //Genre removedGenre = Genre.valueOf(removed);
                                                if (stringGenres.contains(removed))
                                                    for (Genre genre : p.getGenres())
                                                        if (genre.equals(Genre.valueOf(removed))) {
                                                            p.getGenres().remove(Genre.valueOf(removed));
                                                            validInput3 = true;
                                                        }
                                                if (!validInput3) {
                                                    throw new IMDB.InvalidCommandException("The genre you entered is not on the list! Try entering again.");
                                                }
                                            } catch (IMDB.InvalidCommandException e) {
                                                System.out.println(e.getMessage());
                                            }
                                        } while (!validInput3);
                                        validInput2 = true;
                                    } else {
                                        throw new IMDB.InvalidCommandException("Invalid input. Please enter ADD or DELETE.");
                                    }
                                } catch (IMDB.InvalidCommandException e) {
                                    System.out.println(e.getMessage());
                                }
                            } while (!validInput2);
                            validInput = true;
                        }
                        default ->
                                throw new IMDB.InvalidCommandException("Invalid input. Please enter T, DIR, ACT, G, P, AR, DUR or RY.");
                    }
                } catch (IMDB.InvalidCommandException e) {
                    System.out.println(e.getMessage());
                }
            } while (!validInput);
            System.out.println("Your movie has now the following information:");
            p.displayInfo();
            System.out.println("Would you like to update another thing for this movie?");
            System.out.println("    Type YES if you do");
            String again = scanner.nextLine();
            if (again.toUpperCase().equals("YES"))
                updateProduction(p);
        }else if(p instanceof Series){
            System.out.println("What would you like to update: title (T), directors list (DIR), actors list (ACT), genres (G), seasons list (S), plot description (P), average rating (AR) or release year (RY) ?");
            System.out.println("Type T, DIR, ACT, G, S, P, AR or RY");
            boolean validInput = false;
            do {
                try {
                    String input = scanner.nextLine();
                    switch (input.toUpperCase()) {
                        case "T" -> {
                            System.out.println("Enter the updated title:");
                            String title = scanner.nextLine();
                            p.setTitle(title);
                            validInput = true;
                        }
                        case "P" -> {
                            System.out.println("Enter the updated plot description:");
                            String plot = scanner.nextLine();
                            p.setPlot(plot);
                            validInput = true;
                        }
                        case "AR" -> {
                            System.out.println("Enter the updated average rating:");
                            Double averageRating = Double.valueOf(scanner.nextLine());
                            p.setAverageRating(averageRating);
                            validInput = true;
                        }
                        case "RY" -> {
                            System.out.println("Enter the updated release year:");
                            int releaseyear = Integer.parseInt(scanner.nextLine());
                            ((Series) p).setReleaseYear(releaseyear);
                            validInput = true;
                        }
                        case "DIR" -> {
                            System.out.println("Would you like to add or delete a director?");
                            System.out.println("Type ADD or DELETE");
                            boolean validInput2 = false;
                            do {
                                try {
                                    String input2 = scanner.nextLine();
                                    if (input2.toUpperCase().equals("ADD")) {
                                        System.out.println("Your directors list is now : ");
                                        for (String director : p.getDirectors())
                                            System.out.print(director + ", ");
                                        System.out.println();
                                        System.out.println("Enter the name of the new director : ");
                                        String added = scanner.nextLine();
                                        p.getDirectors().add(added);
                                        System.out.println("Your updated directors list is : ");
                                        for (String director : p.getDirectors())
                                            System.out.print(director + ", ");
                                        System.out.println();
                                        validInput2 = true;
                                    } else if (input2.toUpperCase().equals("DELETE")) {
                                        System.out.println("Which director on the list do you want to delete?");
                                        boolean validInput3 = false;
                                        do {
                                            try {
                                                String removed = scanner.nextLine();
                                                if (p.getDirectors().contains(removed)) {
                                                    p.getDirectors().remove(removed);
                                                    validInput3 = true;
                                                } else {
                                                    throw new IMDB.InvalidCommandException("The director name you entered is not on the list! Try entering again.");
                                                }
                                            } catch (IMDB.InvalidCommandException e) {
                                                System.out.println(e.getMessage());
                                                System.out.println();
                                            }
                                        } while (!validInput3);
                                        validInput2 = true;
                                    } else {
                                        throw new IMDB.InvalidCommandException("Invalid input. Please enter ADD or DELETE.");
                                    }
                                } catch (IMDB.InvalidCommandException e) {
                                    System.out.println(e.getMessage());
                                }
                            } while (!validInput2);
                            validInput = true;
                        }
                        case "ACT" -> {
                            System.out.println("Would you like to add or delete a actor?");
                            System.out.println("Type ADD or DELETE");
                            boolean validInput2 = false;
                            do {
                                try {
                                    String input2 = scanner.nextLine();
                                    if (input2.toUpperCase().equals("ADD")) {
                                        System.out.println("Your actors list is now : ");
                                        for (String actor : p.getActors())
                                            System.out.print(actor + ", ");
                                        System.out.println();
                                        System.out.println("Enter the name of the new actor : ");
                                        String added = scanner.nextLine();
                                        p.getActors().add(added);
                                        System.out.println("Your updated actors list is : ");
                                        for (String actor : p.getActors())
                                            System.out.print(actor + ", ");
                                        System.out.println();
                                        validInput2 = true;
                                    } else if (input2.toUpperCase().equals("DELETE")) {
                                        System.out.println("Which actor on the list do you want to delete?");
                                        boolean validInput3 = false;
                                        do {
                                            try {
                                                String removed = scanner.nextLine();
                                                if (p.getActors().contains(removed)) {
                                                    p.getActors().remove(removed);
                                                    validInput3 = true;
                                                } else {
                                                    throw new IMDB.InvalidCommandException("The actor name you entered is not on the list! Try entering again.");
                                                }
                                            } catch (IMDB.InvalidCommandException e) {
                                                System.out.println(e.getMessage());
                                                System.out.println();
                                            }
                                        } while (!validInput3);
                                        validInput2 = true;
                                    } else {
                                        throw new IMDB.InvalidCommandException("Invalid input. Please enter ADD or DELETE.");
                                    }
                                } catch (IMDB.InvalidCommandException e) {
                                    System.out.println(e.getMessage());
                                }
                            } while (!validInput2);
                            validInput = true;
                        }
                        case "G" -> {
                            Genre[] genres = Genre.values();
                            List<String> stringGenres = new ArrayList<>();
                            for (Genre genre : Genre.values())
                                stringGenres.add(genre.name());
                            System.out.println("Would you like to add or delete a genre?");
                            System.out.println("Type ADD or DELETE");
                            boolean validInput2 = false;
                            do {
                                try {
                                    String input2 = scanner.nextLine();
                                    if (input2.toUpperCase().equals("ADD")) {
                                        System.out.println("Your genres list is now : ");
                                        for (Genre genre : p.getGenres())
                                            System.out.print(genre + ", ");
                                        System.out.println();
                                        boolean validInput3 = false;
                                        do {
                                            try {
                                                System.out.println("Enter the name of the new genre: ");
                                                System.out.println("You can choose from: ");
                                                for (Genre genre : genres)
                                                    for (Genre g : p.getGenres())
                                                        if (!g.equals(genre))
                                                            System.out.print(genre + ", ");
                                                System.out.println();
                                                String added = scanner.nextLine();
                                                if (stringGenres.contains(added)) {
                                                    p.getGenres().add(Genre.valueOf(added));
                                                    validInput3 = true;
                                                } else
                                                    throw new IMDB.InvalidCommandException("The genre you entered is not correct! Try entering again.");
                                            } catch (IMDB.InvalidCommandException e) {
                                                System.out.println(e.getMessage());
                                            }
                                        } while (!validInput3);
                                        validInput2 = true;
                                    } else if (input2.toUpperCase().equals("DELETE")) {
                                        System.out.println("Which genre on the list do you want to delete?");
                                        boolean validInput3 = false;
                                        do {
                                            try {
                                                String removed = scanner.nextLine();
                                                //Genre removedGenre = Genre.valueOf(removed);
                                                if (stringGenres.contains(removed))
                                                    for (Genre genre : p.getGenres())
                                                        if (genre.equals(Genre.valueOf(removed))) {
                                                            p.getGenres().remove(Genre.valueOf(removed));
                                                            validInput3 = true;
                                                        }
                                                if (!validInput3) {
                                                    throw new IMDB.InvalidCommandException("The genre you entered is not on the list! Try entering again.");
                                                }
                                            } catch (IMDB.InvalidCommandException e) {
                                                System.out.println(e.getMessage());
                                            }
                                        } while (!validInput3);
                                        validInput2 = true;
                                    } else {
                                        throw new IMDB.InvalidCommandException("Invalid input. Please enter ADD or DELETE.");
                                    }
                                } catch (IMDB.InvalidCommandException e) {
                                    System.out.println(e.getMessage());
                                }
                            } while (!validInput2);
                            validInput = true;
                        }
                        case "S" -> {
                            System.out.println("Would you like to add or delete a season?");
                            System.out.println("Type ADD or DELETE");
                            boolean validInput2 = false;
                            do {
                                try {
                                    String input2 = scanner.nextLine();
                                    if (input2.toUpperCase().equals("ADD")) {
                                        System.out.println("Enter the name of the new season : ");
                                        String seasonname = scanner.nextLine();
                                        System.out.println("Enter the list of episodes:");
                                        List<Episode> episodesList = new ArrayList<>();
                                        int nrep = 0;
                                        String again;
                                        do {
                                            nrep++;
                                            System.out.println("Enter the name of the episode " + nrep + " : ");
                                            String episodename = scanner.nextLine();
                                            System.out.println("Enter its duration (in the format : x minutes) :");
                                            String episodeduration = scanner.nextLine();
                                            episodesList.add(new Episode(episodename, episodeduration));
                                            System.out.println("Do you want to add another episode?");
                                            System.out.println("    Type YES if you do");
                                            again = scanner.nextLine();
                                        } while (again.toUpperCase().equals("YES"));

                                        ((Series) p).getSeasons().put(seasonname, episodesList);
                                        System.out.println("Your updated list of seasons is : ");
                                        System.out.println(((Series) p).getSeasons());
                                        ((Series) p).setNrOfSeasons(((Series) p).getSeasons().size());
                                        validInput2 = true;
                                    } else if (input2.toUpperCase().equals("DELETE")) {
                                        System.out.println("Which season name on the list do you want to delete?");
                                        boolean validInput3 = false;
                                        do {
                                            try {
                                                String removed = scanner.nextLine();
                                                if (((Series) p).getSeasons().containsKey(removed)) {
                                                    ((Series) p).getSeasons().remove(removed);
                                                    ((Series) p).setNrOfSeasons(((Series) p).getSeasons().size());
                                                    validInput3 = true;
                                                } else {
                                                    throw new IMDB.InvalidCommandException("The season name you entered is not on the list! Try entering again.");
                                                }
                                            } catch (IMDB.InvalidCommandException e) {
                                                System.out.println(e.getMessage());
                                                System.out.println();
                                            }
                                        } while (!validInput3);
                                        validInput2 = true;
                                    } else {
                                        throw new IMDB.InvalidCommandException("Invalid input. Please enter ADD or DELETE.");
                                    }
                                } catch (IMDB.InvalidCommandException e) {
                                    System.out.println(e.getMessage());
                                }
                            } while (!validInput2);
                            validInput = true;
                        }
                        default ->
                                throw new IMDB.InvalidCommandException("Invalid input. Please enter T, DIR, ACT, G, P, AR or RY.");
                    }
                } catch (IMDB.InvalidCommandException e) {
                    System.out.println(e.getMessage());
                }
            } while (!validInput);
            System.out.println("Your series has now the following information:");
            p.displayInfo();
            System.out.println("Would you like to update another thing for this series?");
            System.out.println("    Type YES if you do");
            String again = scanner.nextLine();
            if (again.toUpperCase().equals("YES"))
                updateProduction(p);
        }
    }

    @Override
    public void updateActor(Actor a) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("This actor looks like this now:");
        System.out.println(a);
        System.out.println("What would you like to update: name, biography or one of the roles?");
        System.out.println("Type N, B or R");
        boolean validInput = false;
        do {
            try {
                String input = scanner.nextLine();
                switch (input.toUpperCase()) {
                    case "N" -> {
                        System.out.println("Enter the updated name:");
                        String name = scanner.nextLine();
                        a.setActorName(name);
                        validInput = true;
                    }
                    case "R" -> {
                        System.out.println("Would you like to add or delete a role?");
                        System.out.println("Type ADD or DELETE");
                        boolean validInput2 = false;
                        do {
                            try {
                                String input2 = scanner.nextLine();
                                if (input2.toUpperCase().equals("ADD")) {
                                    System.out.println("You can add these productions:");
                                    List<Production> productionsList = IMDB.getProductions();
                                    for (Production p : productionsList)
                                        if (!contributionList.contains(p))
                                            System.out.println(p.getTitle());
                                    System.out.println("Enter the title of the production you want to add in the roles list:");
                                    String title = scanner.nextLine();
                                    for (Production p : productionsList)
                                        if (p.getTitle().equals(title))
                                            if (p instanceof Movie)
                                                a.getRoles().put(title, "Movie");
                                            else if (p instanceof Series)
                                                a.getRoles().put(title, "Series");
                                    validInput2 = true;
                                } else if (input2.toUpperCase().equals("DELETE")) {
                                    System.out.println("Which production in the roles list do you want to delete?");
                                    String role = scanner.nextLine();
                                    a.getRoles().remove(role);
                                    validInput2 = true;
                                } else {
                                    throw new IMDB.InvalidCommandException("Invalid input. Please enter ADD or DELETE.");
                                }
                            } catch (IMDB.InvalidCommandException e) {
                                System.out.println(e.getMessage());
                            }
                        } while (!validInput2);
                        validInput = true;
                    }
                    case "B" -> {
                        System.out.println("Enter the updated biography:");
                        String bio = scanner.nextLine();
                        a.setBiography(bio);
                        validInput = true;
                    }
                    default -> throw new IMDB.InvalidCommandException("Invalid input. Please enter N, B or R.");
                }
            } catch (IMDB.InvalidCommandException e) {
                System.out.println(e.getMessage());
            }
        } while (!validInput);
        System.out.println("Your actor has now the following information:");
        System.out.println(a);
        System.out.println("Would you like to update another thing for this actor?");
        System.out.println("    Type YES if you do");
        String again = scanner.nextLine();
        if(again.toUpperCase().equals("YES"))
            updateActor(a);
    }

    @Override
    public void resolveRequests(List<Request> requests) {

    }
}
