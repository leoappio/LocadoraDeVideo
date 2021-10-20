package com.company.Menus;

import com.company.database.Database;
import com.company.models.Movie;
import com.company.models.VideoRentalShop;
import com.company.screen.Screen;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

//classe que lida com todas as interações do usuario com o menu dos filmes
public class MovieMenu {
    public static Scanner reader = new Scanner(System.in);

    public static void ReadMovieMenu() throws SQLException {
        while(true){
            System.out.print("Digite o número da sua escolha: ");
            String choice = reader.nextLine();
            if(choice.equals("1")){
                createMovie();
            }else if(choice.equals("2")){
                editMovie();
            }else if(choice.equals("3")){
                consultMovies();
            }else if(choice.equals("4")){
                deleteMovie();
            }else if(choice.equals("0")){
                Screen.showMainMenu();
                break;
            }
        }
    }

    public static void createMovie() throws SQLException {
        System.out.print("Digite o titulo do filme: ");
        String title = reader.nextLine();
        System.out.println("[1] - Devolução em 24 Horas");
        System.out.println("[2] - Devolução em 48 Horas");
        int type;
        while(true){
            System.out.print("Digite o numero do tipo: ");
            String typeString = reader.nextLine();

            if(typeString.equals("1") || typeString.equals("2")){
                type = Integer.parseInt(typeString);
                break;
            }else{
                System.out.println("Escolha inválida, digite novamente");
            }
        }

        System.out.print("Digite a quantidade: ");
        String quantityString = reader.nextLine();
        int quantity = Integer.parseInt(quantityString);

        if(confirmOperation()){
            Movie movie = new Movie(title,type,quantity);
            System.out.println("Cadastrando filme...");
            Database.insertMovie(movie);

            System.out.println("Filme cadastrado com sucesso!");
        }else{
            System.out.println("Operação Cancelada!");
        }
        returnToMenu();
    }

    public static void consultMovies() throws SQLException {
        VideoRentalShop store = new VideoRentalShop();
        ArrayList<Movie> movies = store.getMovies();

        for(int i = 0; i<movies.size(); i++){
            System.out.println("Filme "+movies.get(i).id);
            System.out.println("---Titulo: "+movies.get(i).title);
            if(movies.get(i).type == 1){
                System.out.println("---Tipo: Devolução em 24H");
            }else{
                System.out.println("---Tipo: Devolução em 48H");
            }
            System.out.println("---Quantidade: "+movies.get(i).quantity);
            System.out.println("---------------------------");
        }
        returnToMenu();
    }

    public static Movie chooseMovie(String editOrDelete) throws SQLException {
        VideoRentalShop store = new VideoRentalShop();
        ArrayList<Movie> movies = store.getMovies();
        int maiorId = 0;

        for(int i = 0; i<movies.size(); i++){
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
        }

        while(true){
            System.out.print("Digite o número do filme para "+editOrDelete+":");
            String choiceString = reader.nextLine();
            int choice = Integer.parseInt(choiceString);
            if(choice > 0 && choice <= maiorId){
                Movie movieToReturn = store.getMovieById(choice);
                if(movieToReturn != null){
                    return movieToReturn;
                }else{
                    System.out.println("Não existe filme com esse número!");
                }
            }else{
                System.out.println("Número inválido, escolha outro!");
            }
        }

    }

    public static void deleteMovie() throws SQLException {
        Movie movieToDelete = chooseMovie("apagar");
        if(confirmOperation()){
            Database.deleteMovie(movieToDelete.id);
            System.out.println("Filme apagado com sucesso!");
        }else{
            System.out.println("Operação Cancelada!");
        }
        returnToMenu();
    }

    public static void editMovie() throws SQLException {
        Movie movieToEdit = chooseMovie("editar");
        System.out.println("[1] - Editar titulo");
        System.out.println("[2] - Editar tipo");
        System.out.println("[3] - Editar quantidade");
        while(true){
            System.out.println("Digite o numero da escolha que deseja editar: ");
            String choice = reader.nextLine();
            if(choice.equals("1")){
                System.out.print("Digite o novo titulo: ");
                String newTitle = reader.nextLine();
                if(confirmOperation()){
                    movieToEdit.setTitle(newTitle);
                    Database.updateMovie(movieToEdit);
                    System.out.println("Filme editado com sucesso");
                }else{
                    System.out.println("Operação Cancelada!");
                }
                break;
            }else if(choice.equals("2")){
                System.out.println("[1] - Devolução em 24 Horas");
                System.out.println("[2] - Devolução em 48 Horas");
                int newType;
                while(true){
                    System.out.print("Digite o numero do tipo: ");
                    String typeString = reader.nextLine();

                    if(typeString.equals("1") || typeString.equals("2")){
                        newType = Integer.parseInt(typeString);
                        break;
                    }else{
                        System.out.println("Escolha inválida, digite novamente");
                    }
                }

                if(confirmOperation()){
                    movieToEdit.setType(newType);
                    Database.updateMovie(movieToEdit);
                    System.out.println("Filme editado com sucesso");
                }else{
                    System.out.println("Operação Cancelada!");
                }
                break;
            }else if(choice.equals("3")){
                System.out.print("Digite a nova quantidade: ");
                String newQuantityString = reader.nextLine();
                int newQuantity = Integer.parseInt(newQuantityString);
                if(confirmOperation()){
                    movieToEdit.setQuantity(newQuantity);
                    Database.updateMovie(movieToEdit);
                    System.out.println("Filme editado com sucesso");
                }else{
                    System.out.println("Operação Cancelada!");
                }
                break;
            }else{
                System.out.println("Escolha inválida, escolha novamente");
            }
        }
        returnToMenu();
    }

    public static void returnToMenu(){
        System.out.println();
        System.out.println("Aperte enter para voltar ao menu");
        reader.nextLine();
        Screen.clear();
        Screen.showMovieRegistration();
    }

    public static boolean confirmOperation() {
        System.out.println("Deseja confirmar a operação? [S/N]");
        String choiceString = reader.nextLine();
        if (choiceString.equalsIgnoreCase("S")) {
            return true;
        }
        return false;
    }
}
