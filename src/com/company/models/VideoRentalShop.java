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


}
