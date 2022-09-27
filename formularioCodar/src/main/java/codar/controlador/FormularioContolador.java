package codar.controlador;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import codar.gerenciador.GerenciadorDeArquivos;
import codar.gerenciador.GerenciadorDeInput;
import codar.service.AlunoService;
import codar.service.PerguntaService;

public class FormularioContolador {
    GerenciadorDeInput gerenciadorDeInput = new GerenciadorDeInput();
    Scanner scan = new Scanner(System.in);
    GerenciadorDeArquivos gerenciadorDeArquivos = new GerenciadorDeArquivos();
    AlunoService alunoService = new AlunoService(new GerenciadorDeArquivos());
    PerguntaService perguntaService = new PerguntaService(gerenciadorDeArquivos);

    public void direcionaChamada(int opcaoSelecioada) throws IOException {

        if(opcaoSelecioada == 1){
            try {
                List<String> dadosDoAluno = gerenciadorDeInput.obtemDadosDeAluno();
                alunoService.salvarAluno(dadosDoAluno);
            }catch (NumberFormatException inptuErradoParaIdade){
                System.out.println(inptuErradoParaIdade.getMessage());
            }catch (IllegalArgumentException inputErradoParaNome){
                System.out.println(inputErradoParaNome.getMessage());
            }
        }else if (opcaoSelecioada == 2) {
            perguntaService.adicionarPergunta(gerenciadorDeInput.obtemPergunta());
        }else if(opcaoSelecioada == 3){
            try{
                perguntaService.removerPergunta(gerenciadorDeInput.obtemNumeroDaPergunta());
            }catch (IllegalArgumentException | InputMismatchException e){
                System.out.println(e.getMessage());
            }
        }else if(opcaoSelecioada == 4){
            System.out.println("---LISTAGEM DE ALUNOS---\n1 - Listar nomes agrupando por idade\n2 - Quantidade agrupando por idade\n3 - Voltar");
            int opcao = scan.nextInt();
            if (opcao == 1) {
                alunoService.agruparAlunosPorIdade();
            }
            if (opcao == 2) {
                alunoService.somarPorIdade();
            }
            if (opcao == 3) {
                System.out.println("Voltando...");
            }
            if (opcao > 3 || opcao < 1) {
                System.out.println("Opção inválida");
            }

        }else if(opcaoSelecioada == 5){
            alunoService.pesquisarPorEmailEIdade(gerenciadorDeInput.obtemEmail(), gerenciadorDeInput.obtemNome());
        }else if(opcaoSelecioada == 6){

        }else if(opcaoSelecioada == 7){

        }else{
            throw new IllegalArgumentException("Digite uma opção de 1 a 7");
        }
    }

}
