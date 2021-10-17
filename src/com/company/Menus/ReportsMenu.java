package com.company.Menus;

import com.company.models.Client;
import com.company.models.Movie;
import com.company.models.VideoRentalShop;
import com.company.screen.Screen;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReportsMenu {
    public static Scanner reader = new Scanner(System.in);

    public static void ReadReportsMenu() throws SQLException {
        bigger:
        while(true){
            System.out.print("Digite o número da sua escolha: ");
            String choice = reader.nextLine();
            switch (choice) {
                case "1":
                    collectionBalance();
                    break;
                case "2":
                    movementsBalance();
                    break;
                case "3":
                    Screen.showMainMenu();
                    break bigger;
            }
        }
    }

    public static void collectionBalance() throws SQLException {
        Screen.clear();
        System.out.println("XYZ COMERCIO DE PRODUTOS LTDA.");
        System.out.println("CONTROLE DE LOCADORA DE VÍDEO");
        System.out.println();
        System.out.println("BALANÇO DE ACERVO");
        VideoRentalShop store = new VideoRentalShop();
        ArrayList<Movie> movies = store.getMovies();
        System.out.println("COD |   TITULO   |   TIPO   | QUANTIDADE");
        for (Movie movie : movies) {
            if (movie.type == 1) {
                System.out.println(movie.id + "- " + movie.title + "- Devolução em 24H- " + movie.quantity);
            } else {
                System.out.println(movie.id + "- " + movie.title + "- Devolução em 48H- " + movie.quantity);
            }
            System.out.println("-------------------------------------");
        }

        returnToMenu();

    }

    public static void movementsBalance() throws SQLException {
        System.out.println("XYZ COMERCIO DE PRODUTOS LTDA.");
        System.out.println("CONTROLE DE LOCADORA DE VÍDEO");
        System.out.println();
        System.out.println("BALANÇO DE MOVIMENTAÇÕES");

        VideoRentalShop shop = new VideoRentalShop();
        int totalClients = shop.getTotalClients();
        int totalMovies = shop.getTotalMovies();
        int totalLocations = shop.getTotalLocations();
        int total24hMovies = shop.getTotal24HoursMovies();
        int total48hMovies = shop.getTotal48HoursMovies();
        float averageLocations = totalLocations / totalClients;
        float averageLateDays = shop.getAverageLate();

        System.out.println("Total de clientes: "+ totalClients);
        System.out.println("Total de filmes: "+ totalMovies);
        System.out.println("Total de locações no mês: "+ totalLocations);
        System.out.println("Filmes 24 Horas: "+ total24hMovies);
        System.out.println("Filmes 48 Horas: "+ total48hMovies);
        System.out.println("Média de locações por usuário: "+ averageLocations);
        System.out.println("Média de tempo de atraso na devolução dos livros (em dias): "+ averageLateDays);

        System.out.println();
        System.out.println("10 FILMES MAIS LOCADOS:");
        ArrayList<Movie> movies = shop.getTop10Movies();

        for(int i = 0; i<movies.size();i++){
            int movieTotalLocations = shop.getTotalLocationsByMovieId(movies.get(i).id);
            System.out.println((i+1)+" - COD: "+movies.get(i).id+" - "+movies.get(i).title+" - Nº de locações: "+movieTotalLocations);
        }

        System.out.println();
        System.out.println("10 MELHORES CLIENTES:");
        ArrayList<Client> clients = shop.getTop10Clients();

        for(int i = 0; i<clients.size();i++){
            int clientTotalLocations = shop.getTotalLocationsByClientId(clients.get(i).id);
            System.out.println((i+1)+" - COD: "+clients.get(i).id+" - "+clients.get(i).name+" - Nº de locações: "+clientTotalLocations);
        }

        returnToMenu();
    }

    public static void returnToMenu(){
        System.out.println();
        System.out.println("Aperte enter para voltar ao menu");
        reader.nextLine();
        Screen.clear();
        Screen.showReportsMenu();
    }
}
