package org.example;

import java.time.LocalDateTime;
import java.util.*;

public abstract class User<T extends Comparable<T>> implements Comparable<User<T>>{
    public static class Information {
        private Credentials credentials ;
        private final String UserName;
        private final String UserCountry;
        private final long UserAge;
        private final String UserGender;
        private final LocalDateTime UserDateOfBirth;

        private Information(InformationBuilder builder) {
            this.credentials = builder.credentials != null ? builder.credentials : new Credentials();
            this.UserName = builder.UserName;
            this.UserCountry = builder.UserCountry;
            this.UserAge = builder.UserAge;
            this.UserGender = builder.UserGender;
            this.UserDateOfBirth = builder.UserDateOfBirth;
        }
        public static class InformationBuilder {
            Credentials credentials;
            private String UserName;
            private String UserCountry;
            private long UserAge;
            private String UserGender;
            private LocalDateTime UserDateOfBirth;

            public InformationBuilder(String userName){
                this.UserName = userName;
            }

            public InformationBuilder credentials(Credentials credentials) {
                Objects.requireNonNull(credentials, "Credentials cannot be null");
                this.credentials = credentials;
                return this;
            }

            public Information.InformationBuilder setUserName(String userName) {
                Objects.requireNonNull(userName, "User name cannot be null");
                this.UserName = userName;
                return this;
            }

            public InformationBuilder UserCountry(String userCountry) {
                this.UserCountry = userCountry;
                return this;
            }

            public InformationBuilder UserAge(long userAge) {
                this.UserAge = userAge;
                return this;
            }

            public InformationBuilder UserGender(String userGender) {
                this.UserGender = userGender;
                return this;
            }

            public InformationBuilder UserDateOfBirth(LocalDateTime userDateOfBirth) {
                this.UserDateOfBirth = userDateOfBirth;
                return this;
            }
            public Information build() {
                return new Information(this);
            }

        }

        public void setCredentials(Credentials credentials) {
            this.credentials = credentials;
        }

        public Credentials getCredentials() {
            return credentials;
        }

        public String getUserName() {
            return UserName;
        }

        public String getUserCountry() {
            return UserCountry;
        }

        public long getUserAge() {
            return UserAge;
        }

        public String getUserGender() {
            return UserGender;
        }

        public LocalDateTime getUserDateOfBirth() {
            return UserDateOfBirth;
        }
    }

    private Information information;
    private AccountType accountType;
    private String username;
    private String userExperience;
    private List<String> notifications;
    private SortedSet<T> favorites;

    public User(String name, AccountType accountType, String userExperience, String username,String email, String password) {
        try {
            this.information = new Information.InformationBuilder(name).build();
            this.accountType = accountType;
            this.username = username;
            this.userExperience = userExperience;
            this.notifications = new ArrayList<>();
            this.favorites = new TreeSet<>();
            this.information.credentials.setEmail(email);
            this.information.credentials.setPassword(password);
            if (this.information.credentials.getEmail() == null || this.information.credentials.getPassword() == null) {
                throw new IMDB.InformationIncompleteException("Credentials are incomplete");
            }
            if (this.getUsername() == null) {
                throw new IMDB.InformationIncompleteException("User's name is missing");
            }
        } catch (IMDB.InformationIncompleteException ignored) {
        }
    }

    public String getUsername() {
        return username;
    }

    public Information getInformation() {
        return information;
    }

    public void setInformation(Information.InformationBuilder informationBuilder) {
        this.information = informationBuilder.build();
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserExperience() {
        return userExperience;
    }

    public void setUserExperience(String userExperience) {
        this.userExperience = userExperience;
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<String> notifications) {
        this.notifications = notifications;
    }

    public SortedSet<T> getFavorites() {
        return favorites;
    }

    public void setFavorites(SortedSet<T> favorites) {
        this.favorites = favorites;
    }

    private String generateUsername(String name) {
        return name.toLowerCase().replace(" ", "_") + "_123";
    }

    private String generatePassword(String name){
        return name.toLowerCase().replace(" ", "");
    }

    public <fav> void addToFavorites(fav item) {
        favorites.add((T) item);
    }
    public <fav> void removeFromFavorites(fav item) {
        favorites.remove((T) item);
    }

    public void updateExperience(String newExperience) {
        this.userExperience = newExperience;
    }

    public void logout() {
        System.out.println();
        System.out.println("           !You are logged out now!");
        System.exit(0);
    }

    @Override
    public int compareTo(User<T> otherUser) {
        return this.userExperience.compareTo(otherUser.userExperience);
    }
}

