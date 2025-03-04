package org.example;

import java.time.LocalDateTime;

public class Request {
    private RequestTypes requestType;
    private LocalDateTime localDateTime;
    private String name;
    private String problemDescription;
    private String usernameRequest;
    private String usernameResolve;

    public Request(RequestTypes requestType, LocalDateTime time, String name, String problemDescription, String usernameRequest, String usernameResolve) {
        this.requestType = requestType;
        this.localDateTime = time;
        this.name = name;
        this.problemDescription = problemDescription;
        this.usernameRequest = usernameRequest;
        this.usernameResolve = usernameResolve;
    }

    public RequestTypes getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestTypes requestType) {
        this.requestType = requestType;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public String getUsernameRequest() {
        return usernameRequest;
    }

    public void setUsernameRequest(String usernameRequest) {
        this.usernameRequest = usernameRequest;
    }

    public String getUsernameResolve() {
        return usernameResolve;
    }

    public void setUsernameResolve(String usernameResolve) {
        this.usernameResolve = usernameResolve;
    }

    @Override
    public String toString() {
        return "Request{" + "requestType=" + requestType +
                ", localDateTime=" + localDateTime +
                ", name='" + name + '\'' +
                ", problemDescription='" + problemDescription + '\'' +
                ", usernameRequest='" + usernameRequest + '\'' +
                ", usernameResolve='" + usernameResolve + '\'' +
                '}';
    }
}
