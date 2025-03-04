package org.example;

import java.util.List;
import java.util.Map;

public class Series extends Production{
    int releaseYear;
    int nrOfSeasons;
    private Map<String, List<Episode>> seasons;

    public Series(String title, String type, List<String> directors, List<String> actors, List<Genre> genres, List<Rating> ratings, String plot, Double averageRating, int releaseYear, int nrOfSeasons, Map<String, List<Episode>> seasons) {
        super(title, type, directors, actors, genres, ratings, plot, averageRating);
        this.releaseYear = releaseYear;
        this.nrOfSeasons = nrOfSeasons;
        this.seasons = seasons;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getNrOfSeasons() {
        return nrOfSeasons;
    }

    public void setNrOfSeasons(int nrOfSeasons) {
        this.nrOfSeasons = nrOfSeasons;
    }

    public Map<String, List<Episode>> getSeasons() {
        return seasons;
    }

    public void setSeasons(Map<String, List<Episode>> seasons) {
        this.seasons = seasons;
    }

    @Override
    public void displayInfo() {
        System.out.println("Title: " + getTitle());
        System.out.println("Directors: " + getDirectors());
        System.out.println("Actors: " + getActors());
        System.out.println("Genres: " + getGenres());
        System.out.println("Release Year: " + releaseYear);
        System.out.println("Number of Seasons: " + nrOfSeasons);
        System.out.println("Plot Description: " + getPlot());
        System.out.println("Overall Rating: " + getAverageRating());
        System.out.println("Seasons: ");
        for (Map.Entry<String, List<Episode>> entry : seasons.entrySet()) {
            System.out.println("    " + entry.getKey() + ":");
            for (Episode episode : entry.getValue()) {
                System.out.println("        Episode " + (entry.getValue().indexOf(episode) + 1) + ": " + episode);
            }
        }
        System.out.println();
    }
}
