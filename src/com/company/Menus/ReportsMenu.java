package com.company.Menus;

import com.company.models.Movie;
import com.company.models.VideoRentalShop;
import com.company.screen.Screen;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReportsMenu {
    public static Scanner reader = new Scanner(System.in);

    public static void ReadReportsMenu() throws SQLException {
        while(true){
            System.out.print("Digite o número da sua escolha: ");
            String choice = reader.nextLine();
            if(choice.equals("1")){
                collectionBalance();
            }else if(choice.equals("2")){
                movementsBalance();
            }else if(choice.equals("3")){
                Screen.showMainMenu();
                break;
            }
        }
    }

    public static void collectionBalance() throws SQLException {
        Screen.clear();
        System.out.println("XYZ COMERCIO DE PRODUTOS LTDA.");
        System.out.println("CONTROLE DE LOCADORA DE VÍDEO");
        System.out.println("");
        System.out.println("BALANÇO DE ACERVO");
        VideoRentalShop store = new VideoRentalShop();
        ArrayList<Movie> movies = store.getMovies();
        System.out.println("COD |   TITULO   |   TIPO   | QUANTIDADE");
        for(int i = 0; i< movies.size();i++){
            if(movies.get(i).type == 1){
                System.out.println(movies.get(i).id+"- "+movies.get(i).title+"- Devolução em 24H- "+movies.get(i).quantity);
            }else{
                System.out.println(movies.get(i).id+"- "+movies.get(i).title+"- Devolução em 48H- "+movies.get(i).quantity);
            }
            System.out.println("-------------------------------------");
        }

        returnToMenu();

    }

    public static void movementsBalance() throws SQLException {
        System.out.println("XYZ COMERCIO DE PRODUTOS LTDA.");
        System.out.println("CONTROLE DE LOCADORA DE VÍDEO");
        System.out.println("");
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
        System.out.println("Total de locações no mês: "+ totalLocations);


    }

    public static void returnToMenu(){
        System.out.println();
        System.out.println("Aperte enter para voltar ao menu");
        reader.nextLine();
        Screen.clear();
        Screen.showReportsMenu();
    }
}
