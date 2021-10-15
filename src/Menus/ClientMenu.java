package Menus;

import com.company.database.Database;
import com.company.models.Client;
import com.company.models.Movie;
import com.company.models.VideoRentalShop;
import com.company.screen.Screen;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

//classe que lida com todas as interações do usuario com o menu do cliente
public class ClientMenu {
    public static Scanner reader = new Scanner(System.in);

    public static void ReadClientMenu() throws SQLException {
        while(true){
            System.out.print("Digite o número da sua escolha: ");
            String choice = reader.nextLine();
            if(choice.equals("1")){
                createUser();
            }else if(choice.equals("2")){
                editClient();
            }else if(choice.equals("3")){
                consultClients();
            }else if(choice.equals("4")){
                deleteClient();
            }else if(choice.equals("0")){
                Screen.showMainMenu();
                break;
            }
        }
    }

    public static void createUser() throws SQLException {
        System.out.print("Digite o nome do cliente: ");
        String name = reader.nextLine();
        System.out.print("Digite o telefone do cliente: ");
        String phone = reader.nextLine();

        Client client = new Client(name,phone);
        System.out.println("Cadastrando cliente...");
        Database.insertClient(client);

        System.out.println("Cliente cadastrado com sucesso!");
        returnToMenu();
    }

    public static void consultClients() throws SQLException {
        VideoRentalShop store = new VideoRentalShop();
        ArrayList<Client> clients = store.getClients();

        for(int i = 0; i<clients.size(); i++){
            System.out.println("Cliente "+clients.get(i).id);
            System.out.println("---Nome: "+clients.get(i).name);
            System.out.println("---Telefone: "+clients.get(i).phone);
            System.out.println("---------------------------");
        }
        returnToMenu();
    }

    public static void deleteClient() throws SQLException {
        Client clientToDelete = chooseClient("apagar");
        Database.deleteClient(clientToDelete.id);
        System.out.print("Cliente apagado com sucesso!");
        returnToMenu();
    }

    public static void returnToMenu(){
        System.out.println();
        System.out.println("Aperte enter para voltar ao menu");
        reader.nextLine();
        Screen.clear();
        Screen.showClientMenu();
    }

    public static void editClient() throws SQLException {
        Client clientToEdit = chooseClient("editar");
        System.out.println("[1] - Editar nome");
        System.out.println("[2] - Editar telefone");
        while(true){
            System.out.println("Digite o numero da escolha que deseja editar: ");
            String choice = reader.nextLine();
            if(choice.equals("1")){
                System.out.print("Digite o novo nome: ");
                String newName = reader.nextLine();
                clientToEdit.setName(newName);
                Database.updateClient(clientToEdit);
                break;
            }else if(choice.equals("2")){
                System.out.print("Digite o novo telefone: ");
                String newPhone = reader.nextLine();

                clientToEdit.setPhone(newPhone);
                Database.updateClient(clientToEdit);
                break;
            }else{
                System.out.println("Escolha inválida, escolha novamente");
            }
        }
        System.out.println("Cliente editado com sucesso");
        returnToMenu();
    }

    public static Client chooseClient(String editOrDelete) throws SQLException {
        VideoRentalShop store = new VideoRentalShop();
        ArrayList<Client> clients = store.getClients();
        int maiorId = 0;

        for(int i = 0; i<clients.size(); i++){
            System.out.println("["+clients.get(i).id+"] Cliente ");
            System.out.println("---Nome: "+clients.get(i).name);
            System.out.println("---Telefone: "+clients.get(i).phone);
            System.out.println("---------------------------");

            if(clients.get(i).id > maiorId){
                maiorId = clients.get(i).id;
            }
        }

        while(true){
            System.out.print("Digite o número do cliente para "+editOrDelete+":");
            String choiceString = reader.nextLine();
            int choice = Integer.parseInt(choiceString);
            if(choice > 0 && choice <= maiorId){
                Client clientToReturn = store.getClientById(choice);
                if(clientToReturn != null){
                    return clientToReturn;
                }else{
                    System.out.println("Não existe cliente com esse número!");
                }
            }else{
                System.out.println("Número inválido, escolha outro!");
            }
        }

    }

}
