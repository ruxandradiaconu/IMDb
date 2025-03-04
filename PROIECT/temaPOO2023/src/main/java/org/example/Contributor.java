package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class Contributor<T extends Comparable<T>> extends Staff<T> implements RequestsManager{
    private List<Request> requestsList;
    private SortedSet<T> contributionList;
    public Contributor(String name, AccountType accountType, String userExperience, String username, String email, String password) {
        super(name, accountType, userExperience, username, email, password);
        this.requestsList = new ArrayList<>();
        this.contributionList = new TreeSet<>();
    }

    public <fav> void addToContributionList(fav item) {
        contributionList.add((T) item);
    }

    public <fav> void removeFromContributionList(fav item) {
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

    public void addProductionSystem(Production p) {
        super.addProductionSystem(p);
    }

    public void addActorSystem(Actor a) {
        super.addActorSystem(a);
    }

    @Override
    public void createRequest(Request r) {
        getRequestsList().add(r);
    }

    @Override
    public void removeRequest(Request r) {
        getRequestsList().remove(r);
    }
}
