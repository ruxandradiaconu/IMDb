package org.example;

public class Regular<T extends Comparable<T>> extends User<T> implements RequestsManager{
    public Regular(String name, AccountType accountType, String userExperience, String username,String email, String password) {
        super(name, accountType, userExperience, username, email, password);
    }

    @Override
    public void createRequest(Request r) {

    }

    @Override
    public void removeRequest(Request r) {

    }

    public void addReview(int ratingPoints, String comment) {
        Rating rating = new Rating(super.getUsername(), ratingPoints, comment);
    }
}
