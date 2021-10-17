package com.company.database;
import com.company.models.Client;
import com.company.models.Location;
import com.company.models.Movie;
import java.sql.*;
import java.util.ArrayList;

//classe que lida com todas as consultas no banco de dados
public class Database {
    private static Connection connection;
    static {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:dbVideoRental.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Statement statement;
    static {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertClient(Client client) throws SQLException {
        String sqlQuery = "INSERT INTO CLIENT(NAME, PHONE) VALUES ('"+client.name+"','"+client.phone+"')";
        statement.execute(sqlQuery);
    }
    public static void insertMovie(Movie movie) throws SQLException {
        String sqlQuery = "INSERT INTO MOVIE(TITLE, TYPE, QUANTITY) VALUES ('"+movie.title+"','"+movie.type+"','"+movie.quantity+"')";
        statement.execute(sqlQuery);
    }
    public static void insertLocation(Location location) throws SQLException {
        String sqlQuery = "INSERT INTO LOCATION(CLIENT_ID, MOVIE_ID, LATEDAYS, RETURNED) VALUES ('"+location.clientId+"','"+location.movieId+"','"+location.lateDays+"','"+location.returned+"')";
        statement.execute(sqlQuery);
    }

    public static ArrayList<Client> getAllClients() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM CLIENT");
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Client> clients = new ArrayList<>();

        while (resultSet.next()){
            Integer id = resultSet.getInt("ID");
            String name = resultSet.getString("NAME");
            String phone = resultSet.getString("PHONE");
            Client client = new Client(id, name, phone);
            clients.add(client);
        }

        return clients;
    }

    public static int getTotalLocations() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS TOTAL FROM LOCATION");
        ResultSet resultSet = statement.executeQuery();

        return resultSet.getInt("TOTAL");
    }

    public static ArrayList<Movie> getAllMovies() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM MOVIE");
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Movie> movies = new ArrayList<>();

        while (resultSet.next()){
            Integer id = resultSet.getInt("ID");
            String title = resultSet.getString("TITLE");
            Integer type = resultSet.getInt("TYPE");
            Integer quantity = resultSet.getInt("QUANTITY");

            Movie movie = new Movie(id,title,type,quantity);
            movies.add(movie);
        }

        return movies;
    }

    public static ArrayList<Location> getAllLocations() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM LOCATION");
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Location> locations = new ArrayList<>();

        while (resultSet.next()){
            Integer id = resultSet.getInt("ID");
            Integer clientId = resultSet.getInt("CLIENT_ID");
            Integer movieId = resultSet.getInt("MOVIE_ID");
            Integer lateDays = resultSet.getInt("LATEDAYS");
            String returned = resultSet.getString("RETURNED");

            Location location = new Location(id,clientId,movieId,lateDays,returned);
            locations.add(location);
        }

        return locations;
    }

    public static float getAverageLateDays() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT LATEDAYS FROM LOCATION");
        ResultSet resultSet = statement.executeQuery();
        int totalLateDays = 0;
        int totalLocations = 0;
        float average = 0;

        while (resultSet.next()){
            Integer lateDays = resultSet.getInt("LATEDAYS");

            totalLateDays += lateDays;
            totalLocations++;
        }
        average = totalLateDays / totalLocations;
        return average;
    }

    public static Movie selectMovieById(int movieId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM MOVIE WHERE ID = "+movieId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            Integer id = resultSet.getInt("ID");
            String title = resultSet.getString("TITLE");
            Integer type = resultSet.getInt("TYPE");
            Integer quantity = resultSet.getInt("QUANTITY");

            return new Movie(id,title,type,quantity);
        }
        return null;
    }

    public static Location getLocationByClientAndMovieId(int client_id, int movie_id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM LOCATION WHERE CLIENT_ID = "+client_id+" AND MOVIE_ID = "+movie_id);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            Integer id = resultSet.getInt("ID");
            Integer clientId = resultSet.getInt("CLIENT_ID");
            Integer movieId = resultSet.getInt("MOVIE_ID");
            Integer lateDays = resultSet.getInt("LATEDAYS");
            String returned = resultSet.getString("RETURNED");
            return new Location(id,clientId,movieId,lateDays,returned);
        }
        return null;
    }

    public static Location getActualLocationByClientAndMovieId(int client_id, int movie_id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM LOCATION WHERE CLIENT_ID = '"+client_id+"' AND MOVIE_ID = "+movie_id);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            Integer id = resultSet.getInt("ID");
            Integer clientId = resultSet.getInt("CLIENT_ID");
            Integer movieId = resultSet.getInt("MOVIE_ID");
            Integer lateDays = resultSet.getInt("LATEDAYS");
            String returned = resultSet.getString("RETURNED");

            if(returned.equals("no")){
                return new Location(id,clientId,movieId,lateDays,returned);
            }
        }
        return null;
    }

    public static ArrayList<Movie> getMoviesFromClientId(int clientId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM LOCATION WHERE CLIENT_ID = "+clientId);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Movie> movies = new ArrayList<>();

        while (resultSet.next()){
            Integer movieId = resultSet.getInt("MOVIE_ID");
            String returned = resultSet.getString("RETURNED");
            if(returned.equals("no")){
                Movie movie = selectMovieById(movieId);
                movies.add(movie);
            }
        }
        return movies;
    }

    public static void deleteClient(int id) throws SQLException {
        String sqlQuery = "DELETE FROM CLIENT WHERE ID = "+id;
        statement.execute(sqlQuery);
    }

    public static void deleteMovie(int id) throws SQLException {
        String sqlQuery = "DELETE FROM MOVIE WHERE ID = "+id;
        statement.execute(sqlQuery);
    }

    public static void updateClient(Client client) throws SQLException {
        String sqlQuery = "UPDATE CLIENT SET NAME = '"+client.name+"', PHONE = '"+client.phone+"' WHERE ID = "+client.id;
        statement.execute(sqlQuery);
    }

    public static void updateMovie(Movie movie) throws SQLException {
        String sqlQuery = "UPDATE MOVIE SET TITLE = '"+movie.title+"', TYPE = '"+movie.type+"', QUANTITY = '"+movie.quantity+"' WHERE ID = "+movie.id;
        statement.execute(sqlQuery);
    }

    public static void updateLocation(Location location) throws SQLException {
        String sqlQuery = "UPDATE LOCATION SET LATEDAYS  = '"+location.lateDays+"', RETURNED = '"+location.returned+"' WHERE ID = "+location.locationId;
        statement.execute(sqlQuery);
    }

}
