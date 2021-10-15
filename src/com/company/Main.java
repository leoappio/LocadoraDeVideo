package com.company;

import com.company.Menus.ClientMenu;
import com.company.Menus.LocationMenu;
import com.company.Menus.MovieMenu;
import com.company.Menus.ReportsMenu;
import com.company.screen.Screen;

import java.util.Scanner;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException{
        Scanner reader = new Scanner(System.in);
        Screen.showMainMenu();
        while(true){
            System.out.print("Digite o número da sua escolha: ");
            String choice = reader.nextLine();
            if(choice.equals("1")){
                Screen.clear();
                Screen.showClientMenu();
                ClientMenu.ReadClientMenu();

            }else if(choice.equals("2")){
                Screen.clear();
                Screen.showMovieRegistration();
                MovieMenu.ReadMovieMenu();

            }else if(choice.equals("3")){
                Screen.clear();
                Screen.showLocationMenu();
                LocationMenu.ReadLocationMenu();

            }else if(choice.equals("4")){
                Screen.clear();
                Screen.showReportsMenu();
                ReportsMenu.ReadReportsMenu();

            }else if(choice.equals("0")){
                System.out.println("Programa finalizado!");
                break;
            }else{
                System.out.println("Escolha inválida!");
            }
        }
    }
}
