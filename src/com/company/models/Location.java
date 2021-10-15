package com.company.models;

import java.util.Date;

public class Location {
    public int locationId;
    public int clientId;
    public int movieId;
    public Date locationDate;
    public Date returnDate;

    public Location(int clientId, int movieId){
        this.clientId = clientId;
        this.movieId = movieId;
    }
    public Location(int locationId,int clientId, int movieId){
        this.locationId = locationId;
        this.clientId = clientId;
        this.movieId = movieId;
    }
}
