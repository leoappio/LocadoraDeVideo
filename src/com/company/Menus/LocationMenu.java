package com.company.Menus;

import com.company.database.Database;
import com.company.models.Client;
import com.company.models.Location;
import com.company.models.Movie;
import com.company.models.VideoRentalShop;
import com.company.screen.Screen;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class LocationMenu {
    public static Scanner reader = new Scanner(System.in);

    public static void ReadLocationMenu() throws SQLException {
        while (true) {
            System.out.print("Digite o número da sua escolha: ");
            String choice = reader.nextLine();
            if (choice.equals("1")) {
                location();
            } else if (choice.equals("2")) {
                devolution();
            } else if (choice.equals("3")) {
                Screen.showMainMenu();
                break;
            }
        }
    }

    public static void location() throws SQLException {
        Client client = ClientMenu.chooseClient("locar");
        Movie movie = chooseAvailableMovie();

        Location verifyLocation = Database.getLocationByClientAndMovieId(client.id, movie.id);

        if (confirmOperation()) {
            if (verifyLocation == null || verifyLocation.returned.equals("yes")) {
                Location location = new Location(client.id, movie.id, 0, "no");
                Database.insertLocation(location);
                movie.decreaseQuantity();
                Database.updateMovie(movie);
                System.out.println("Locação Realizada!");
            } else {
                System.out.println("Este cliente já alugou este filme e não devolveu! Não é possível alugar o mesmo filme sem devolver");
            }
        } else {
            System.out.println("Operação Cancelada!");

        }
        returnToMenu();
    }

    public static void devolution() throws SQLException {
        Client client = ClientMenu.chooseClient("devolver um filme");
        ArrayList<Movie> movies = Database.getMoviesFromClientId(client.id);

        if (confirmOperation()) {
            if (movies.size() > 0) {
                Movie movieToReturn = chooseMovieToReturn(movies);
                if (movieToReturn.type == 1) {
                    System.out.println("Este filme era pra ser devolvido em 24H, ele está atrasado?(S/N)");
                } else {
                    System.out.println("Este filme era pra ser devolvido em 48H, ele está atrasado? (S/N)");
                }
                String choice = reader.nextLine();
                int lateDays = 0;
                if (choice.equalsIgnoreCase("S")) {
                    System.out.println("Quantos dias ele está atrasado?");
                    String lateDaysString = reader.nextLine();
                    lateDays = Integer.parseInt(lateDaysString);
                }
                Location location = Database.getActualLocationByClientAndMovieId(client.id, movieToReturn.id);
                location.setLateDays(lateDays);
                location.setReturned("yes");

                Database.updateLocation(location);
                movieToReturn.increaseQuantity();
                Database.updateMovie(movieToReturn);
                System.out.println("Devolução Realizada!");
            } else {
                System.out.println("Este cliente não tem nenhum filme alugado!");
            }
        } else {
            System.out.println("Operação Cancelada!");
        }
        returnToMenu();
    }

    public static Movie chooseMovieToReturn(ArrayList<Movie> movies) throws SQLException {
        VideoRentalShop store = new VideoRentalShop();
        int maiorId = 0;

        for (int i = 0; i < movies.size(); i++) {
            System.out.println("[" + movies.get(i).id + "]Filme");
            System.out.println("---Titulo: " + movies.get(i).title);
            if (movies.get(i).type == 1) {
                System.out.println("---Tipo: Devolução em 24H");
            } else {
                System.out.println("---Tipo: Devolução em 48H");
            }
            System.out.println("---------------------------");

            if (movies.get(i).id > maiorId) {
                maiorId = movies.get(i).id;
            }
        }

        while (true) {
            System.out.print("Digite o número do filme para devolver:");
            String choiceString = reader.nextLine();
            int choice = Integer.parseInt(choiceString);
            if (choice > 0 && choice <= maiorId) {
                Movie movieToReturn = store.getMovieById(choice);
                if (movieToReturn != null) {
                    return movieToReturn;
                } else {
                    System.out.println("Não existe filme com esse número!");
                }
            } else {
                System.out.println("Número inválido, escolha outro!");
            }
        }

    }

    public static Movie chooseAvailableMovie() throws SQLException {
        VideoRentalShop store = new VideoRentalShop();
        ArrayList<Movie> movies = store.getMovies();
        int maiorId = 0;
        ArrayList<Integer> availableIds = new ArrayList<Integer>();
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).quantity > 0) {
                System.out.println("[" + movies.get(i).id + "]Filme");
                System.out.println("---Titulo: " + movies.get(i).title);
                if (movies.get(i).type == 1) {
                    System.out.println("---Tipo: Devolução em 24H");
                } else {
                    System.out.println("---Tipo: Devolução em 48H");
                }
                System.out.println("---Quantidade: " + movies.get(i).quantity);
                System.out.println("---------------------------");

                if (movies.get(i).id > maiorId) {
                    maiorId = movies.get(i).id;
                }
                availableIds.add(movies.get(i).id);
            }
        }
        while (true) {
            System.out.print("Digite o número do filme para alugar:");
            String choiceString = reader.nextLine();
            int choice = Integer.parseInt(choiceString);
            if (choice > 0 && choice <= maiorId) {
                if (availableIds.contains(choice)) {
                    Movie movieToReturn = store.getMovieById(choice);
                    if (movieToReturn != null) {
                        return movieToReturn;
                    } else {
                        System.out.println("Não existe filme com esse número!");
                    }
                } else {
                    System.out.println("Este filme não tem estoque!");
                }

            } else {
                System.out.println("Número inválido, escolha outro!");
            }
        }

    }

    public static void returnToMenu() {
        System.out.println();
        System.out.println("Aperte enter para voltar ao menu");
        reader.nextLine();
        Screen.clear();
        Screen.showLocationMenu();
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
