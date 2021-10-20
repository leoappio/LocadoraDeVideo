package com.company.models;

import com.company.database.Database;
import java.sql.SQLException;
import java.util.ArrayList;

public class VideoRentalShop {
    public ArrayList<Client> clients;
    public ArrayList<Movie> movies;

    public VideoRentalShop(){
        this.clients = new ArrayList<>();
        this.movies = new ArrayList<>();
    }

    private void attClientList() throws SQLException {
        this.clients = Database.getAllClients();
    }
    private void attMovieList() throws SQLException {
        this.movies = Database.getAllMovies();
    }

    public ArrayList<Client> getClients() throws SQLException {
        this.attClientList();
        return clients;
    }

    public int getTotalClients() throws SQLException {
        this.attClientList();
        return clients.size();
    }

    public int getTotalMovies() throws SQLException {
        this.attMovieList();
        return movies.size();
    }

    public int getTotalLocations() throws SQLException {
        return Database.getTotalLocations();
    }

    public ArrayList<Movie> getMovies() throws SQLException {
        this.attMovieList();
        return movies;
    }

    public Client getClientById(int id){
        for(int i = 0; i< this.clients.size();i++){
            if(this.clients.get(i).id == id){
                return clients.get(i);
            }
        }
        return null;
    }

    public Movie getMovieById(int id) throws SQLException {
        this.attMovieList();
        for(int i = 0; i< this.movies.size();i++){
            if(this.movies.get(i).id == id){
                return movies.get(i);
            }
        }
        return null;
    }

    public int getTotal24HoursMovies() throws SQLException {
        this.attMovieList();
        int total = 0;
        for(int i = 0; i< this.movies.size();i++){
            if(this.movies.get(i).type == 1){
                total++;
            }
        }
        return total;
    }

    public int getTotalLocationsByMovieId(int movieId) throws SQLException {
        int locations = Database.getTotalLocationsByMovieId(movieId);
        return locations;
    }
    public int getTotalLocationsByClientId(int clientId) throws SQLException {
        int locations = Database.getTotalLocationsByClientId(clientId);
        return locations;
    }

    public int getTotal48HoursMovies() throws SQLException {
        this.attMovieList();
        int total = 0;
        for(int i = 0; i< this.movies.size();i++){
            if(this.movies.get(i).type == 2){
                total++;
            }
        }
        return total;
    }

    public float getAverageLate() throws SQLException {
        float average = Database.getAverageLateDays();
        return average;
    }

    public ArrayList<Movie> getTop10Movies() throws SQLException {
        ArrayList<Movie> movies = Database.getTop10Movies();
        return movies;
    }

    public ArrayList<Client> getTop10Clients() throws SQLException {
        ArrayList<Client> clients = Database.getTop10Clients();
        return clients;
    }

}
