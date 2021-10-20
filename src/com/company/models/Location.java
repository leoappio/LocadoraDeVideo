package com.company.models;


public class Location {
    public int locationId;
    public int clientId;
    public int movieId;
    public int lateDays;
    public String returned;

    public Location(int clientId, int movieId, int lateDays, String returned){
        this.clientId = clientId;
        this.movieId = movieId;
        this.lateDays = lateDays;
        this.returned = returned;
    }
    public Location(int locationId, int clientId, int movieId, int lateDays, String returned){
        this.locationId = locationId;
        this.clientId = clientId;
        this.movieId = movieId;
        this.lateDays = lateDays;
        this.returned = returned;
    }

    public void setLateDays(int lateDays) {
        this.lateDays = lateDays;
    }

    public void setReturned(String returned) {
        this.returned = returned;
    }
}
