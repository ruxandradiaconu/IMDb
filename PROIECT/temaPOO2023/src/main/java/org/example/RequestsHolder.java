package org.example;

import java.util.ArrayList;
import java.util.List;

public class RequestsHolder {
    // TODO fa o clasa interna lui Admin
    private static List<Request> requests = new ArrayList<>();

    private void RequestsHolder() {
    }

    public static List<Request> getRequests() {
        return requests;
    }

    public static void setRequests(List<Request> requests) {
        RequestsHolder.requests = requests;
    }

    public static void addRequest(Request request) {
        requests.add(request);
        System.out.println("Cerere adăugată cu succes.");
    }

    public static void removeRequest(Request request) {
        requests.remove(request);
        System.out.println("Cerere ștearsă cu succes.");
    }
}
