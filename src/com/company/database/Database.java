package com.company.database;
import com.company.models.Client;
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

}
