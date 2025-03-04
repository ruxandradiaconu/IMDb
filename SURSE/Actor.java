package org.example;

import java.util.HashMap;
import java.util.Map;

public class Actor implements ComparableItem{
    private String actorName;
    private Map<String, String> roles;
    private String biography;

    public Actor(String actorName, String biography) {
        this.actorName = actorName;
        this.roles = new HashMap<>();
        this.biography = biography;
    }

    public void addRole(String filmName, String filmType){
        roles.put(filmName, filmType);
    }

    public String getActorName() {
        return actorName;
    }

    public Map<String, String> getRoles() {
        return roles;
    }

    public String getBiography() {
        return biography;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public void setRoles(Map<String, String> roles) {
        this.roles = roles;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    @Override
    public String toString() {
        return "Actor : " + actorName + "\n" +
                "Roles : " + roles + "\n" +
                "Biography : " + biography + "\n";
    }

    public void displayInfo(){
        System.out.println("Actor name: " + getActorName());
        System.out.println("Roles: " + getRoles());
        System.out.println("Biography: "+ getBiography());
        System.out.println();
    }
}
