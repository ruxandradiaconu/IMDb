package org.example;

public class Rating {

    String username;
    int score;
    String comment;

    public Rating(String username, int score, String comment) {
        this.username = username;
        this.score = score;
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public String getComment() {
        return comment;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Rating: username= " + username + ", rating= " + score + ", comment=" + comment ;
    }
}
