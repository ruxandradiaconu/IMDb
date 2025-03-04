package org.example;

public class Episode {
    String title;
    String duration;
    public Episode(String title, String duration){
        this.title = title;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public String getDuration() {
        return duration;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }


    @Override
    public String toString() {
        return "Title = " + title + " ; Duration in minutes = " + duration ;
    }
}
