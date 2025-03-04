package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class Admin<T extends Comparable<T>> extends Staff<T>{
    private List<Request> requestsList;
    private SortedSet<T> contributionList;

    private SortedSet<T> adminContributionList = new TreeSet<>();

    public Admin(String name, AccountType accountType, String userExperience, String username, String email, String password) {
        super(name, accountType, userExperience, username, email, password);
        this.requestsList = new ArrayList<>();
        this.contributionList = new TreeSet<>();
    }

    public <t> void addToContributionList(t item) {
        contributionList.add((T) item);
    }

    public <t> void removeFromContributionList(t item) {
        contributionList.remove((T) item);
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

    public SortedSet<T> getAdminContributionList() {
        return adminContributionList;
    }

    public <t> void addToAdminContributionList(t item) {
        adminContributionList.add((T)item);
    }

    public <t> void removeFromAdminContributionList(t item) {
        adminContributionList.remove((T)item);
    }

    public void removeFromAdminContributionList(T item) {
        adminContributionList.remove(item);
    }

    public void addProductionSystem(Production p) {
        super.addProductionSystem(p);
    }

    public void addActorSystem(Actor a) {
        super.addActorSystem(a);
    }
}
