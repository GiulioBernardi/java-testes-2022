package codar.gerenciador;

import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Encapsula os métodos que recebem algum tipo de input
 * @author Giulio Bernardi
 */
public class GerenciadorDeInput {


    /**
     * Lê e printa na tela cada pergunta do arquivo 'formulário.txt'.
     * A cada linha lida é esperado um input (String).
     * @return List de String
     * @throws IOException
     */
    public List<String> obtemDadosDeAluno() throws IOException {
        List<String> respostas = new ArrayList<>();
        File formulario = new File("formulario.txt");
        BufferedReader br = new BufferedReader(new FileReader(formulario));
        Scanner respostaScan = new Scanner(System.in);
        String st;
        while((st = br.readLine()) != null) {
            System.out.println(st);
            String resposta = respostaScan.nextLine();
            respostas.add(resposta.trim());
        }
        return respostas;
    }

    public String obtemPergunta() {
        System.out.println("Insira uma pergunta:");
        Scanner novaPerguntaScan = new Scanner(System.in);
        return novaPerguntaScan.nextLine();
    }

    public int obtemNumeroDaPergunta() throws InputMismatchException {
        System.out.println("Número da pergunta que deseja apagar:");
        Scanner numeroDaQuestaoScan = new Scanner(System.in);
        return numeroDaQuestaoScan.nextInt();
    }

    public String obtemEmail(){
        System.out.println("Digite o email do aluno: ");
        Scanner emailScan = new Scanner(System.in);
        return emailScan.nextLine();
    }

    public String obtemNome(){
        System.out.println("Digite o email do aluno: ");
        Scanner nomeScan = new Scanner(System.in);
        return nomeScan.nextLine();
    }



}
