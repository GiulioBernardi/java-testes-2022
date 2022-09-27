package codar;

import java.io.IOException;
import java.util.Scanner;

import codar.controlador.FormularioContolador;

public class Main {
    public static void main(String[] args) throws IOException {

        FormularioContolador formularioContolador = new FormularioContolador();
        Scanner scan = new Scanner(System.in);

        boolean saiu = false;

        while(!saiu){
            System.out.println("Digite uma opção \n"
                + "1 - Candidatar-se\n"
                + "2 - Adicionar pergunta ao formulário\n"
                + "3 - Remover pergunta do formulário\n"
                + "4 - Listar formulários cadastrados\n"
                + "5 - Pesquisar formulários cadastrados\n"
                + "6 - Validar formulários\n"
                + "7 - Sair");

            int opcaoSelecioada = scan.nextInt();
            if(opcaoSelecioada >= 1 && opcaoSelecioada <= 6){
                formularioContolador.direcionaChamada(opcaoSelecioada);
            }
            else if(opcaoSelecioada == 7){
                saiu = true;
            }else{
                System.out.println("Opção inválida");
            }
        }


    }
}