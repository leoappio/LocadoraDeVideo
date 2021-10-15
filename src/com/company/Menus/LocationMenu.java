package com.company.Menus;

import com.company.models.Client;
import com.company.models.Movie;
import com.company.models.VideoRentalShop;
import com.company.screen.Screen;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class LocationMenu {
    public static Scanner reader = new Scanner(System.in);

    public static void ReadLocationMenu() throws SQLException {
        while(true){
            System.out.print("Digite o número da sua escolha: ");
            String choice = reader.nextLine();
            if(choice.equals("1")){
                location();
            }else if(choice.equals("2")){
                devolution();
            }else if(choice.equals("3")){
                Screen.showMainMenu();
                break;
            }
        }
    }

    public static void location() throws SQLException {
        Client client = ClientMenu.chooseClient("locar");
        Movie movie = chooseAvailableMovie();
    }

    public static void devolution(){

    }

    public static Movie chooseAvailableMovie() throws SQLException {
        VideoRentalShop store = new VideoRentalShop();
        ArrayList<Movie> movies = store.getMovies();
        int maiorId = 0;
        ArrayList<Integer> availableIds = new ArrayList<Integer>();
        for(int i = 0; i<movies.size(); i++){
            if(movies.get(i).quantity > 0){
                System.out.println("["+movies.get(i).id+"]Filme");
                System.out.println("---Titulo: "+movies.get(i).title);
                if(movies.get(i).type == 1){
                    System.out.println("---Tipo: Devolução em 24H");
                }else{
                    System.out.println("---Tipo: Devolução em 48H");
                }
                System.out.println("---Quantidade: "+movies.get(i).quantity);
                System.out.println("---------------------------");

                if(movies.get(i).id > maiorId){
                    maiorId = movies.get(i).id;
                }
                availableIds.add(movies.get(i).id);
            }
        }

        while(true){
            System.out.print("Digite o número do filme para alugar:");
            String choiceString = reader.nextLine();
            int choice = Integer.parseInt(choiceString);
            if(choice > 0 && choice <= maiorId){
                if(availableIds.contains(choice)){
                    Movie movieToReturn = store.getMovieById(choice);
                    if(movieToReturn != null){
                        return movieToReturn;
                    }else{
                        System.out.println("Não existe filme com esse número!");
                    }
                }else{
                    System.out.println("Este filme não tem estoque!");
                }

            }else{
                System.out.println("Número inválido, escolha outro!");
            }
        }

    }
}
