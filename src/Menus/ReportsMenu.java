package Menus;

import com.company.screen.Screen;

import java.sql.SQLException;
import java.util.Scanner;

public class ReportsMenu {
    public static Scanner reader = new Scanner(System.in);

    public static void ReadReportsMenu() throws SQLException {
        while(true){
            System.out.print("Digite o número da sua escolha: ");
            String choice = reader.nextLine();
            if(choice.equals("1")){
            }else if(choice.equals("2")){
            }else if(choice.equals("3")){
                Screen.showMainMenu();
                break;
            }
        }
    }
}
