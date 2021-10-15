package com.company.screen;

import java.io.IOException;

public class Screen {

    public static void clear(){
        for (int i = 0; i < 50; ++i)
            System.out.println();
    }

    private static void showHeader(){
        System.out.println("XYZ COMERCIO DE PRODUTOS LTDA");
        System.out.println("--CONTROLE DE LOCADORA DE VIDEO--");
        System.out.println("");

    }
    public static void showMainMenu(){
        showHeader();
        System.out.println("Menu Principal");
        System.out.println("[1] - Cadastro de clientes");
        System.out.println("[2] - Cadastro de filmes");
        System.out.println("[3] - Movimentação");
        System.out.println("[4] - Relatórios");
        System.out.println("[0] - Finalizar");
    }

    public static void showClientMenu(){
        showHeader();
        System.out.println("Cadastro de Clientes");
        System.out.println("[1] - Inclusão");
        System.out.println("[2] - Alteração");
        System.out.println("[3] - Consulta");
        System.out.println("[4] - Exclusão");
        System.out.println("[0] - Retornar");
    }
    public static void showMovieRegistration(){
        showHeader();
        System.out.println("Cadastro de Filmes");
        System.out.println("[1] - Inclusão");
        System.out.println("[2] - Alteração");
        System.out.println("[3] - Consulta");
        System.out.println("[4] - Exclusão");
        System.out.println("[0] - Retornar");
    }
    public static void showLocationMenu(){
        showHeader();
        System.out.println("[1] - Locação");
        System.out.println("[2] - Devolução");
        System.out.println("[0] - Retornar");
    }
    public static void showReportsMenu(){
        showHeader();
        System.out.println("[1] - Balanço do Acervo");
        System.out.println("[2] - Balanço de movimentações");
        System.out.println("[0] - Retornar");
    }

}
