package org.example;

import java.util.List;

public class Movie extends Production{
    private String duration;
    private int releaseYear;

    public Movie(String title, String type, List<String> directors, List<String> actors, List<Genre> genres, List<Rating> ratings, String plot, Double averageRating, String duration, int releaseYear) {
        super(title, type, directors, actors, genres, ratings, plot, averageRating);
        this.duration = duration;
        this.releaseYear = releaseYear;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    @Override
    public void displayInfo() {
        System.out.println("Title: " + getTitle());
        System.out.println("Directors: " + getDirectors());
        System.out.println("Actors: " + getActors());
        System.out.println("Genres: " + getGenres());
        System.out.println("Release Year: " + releaseYear);
        System.out.println("Duration: " + duration);
        System.out.println("Plot Description: " + getPlot());
        System.out.println("Overall Rating: " + getAverageRating());
        System.out.println();
    }

    //TODO tostring
    @Override
    public String toString() {
        return "Movie : " + getTitle() + "\n" +
                "Directors : " + getDirectors() + "\n" +
                "Actors : " + getActors() + "\n" +
                "Genres: " + getGenres() + "\n" +
                "Release year : " + getReleaseYear() + "\n" +
                "Duration : " + getDuration() + "\n" +
                "Description : " + getPlot() + "\n" +
                "Overall rating: " + getAverageRating() + "\n";
    }
}
