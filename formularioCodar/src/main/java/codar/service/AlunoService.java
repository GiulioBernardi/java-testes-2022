package codar.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import codar.gerenciador.GerenciadorDeArquivos;

public class AlunoService {
    private GerenciadorDeArquivos gerenciadorDeArquivos;

    public AlunoService(GerenciadorDeArquivos gerenciadorDeArquivos) {
        this.gerenciadorDeArquivos = gerenciadorDeArquivos;
    }

    public void salvarAluno(List<String> dadosAluno) {
        String nome = dadosAluno.get(0);
        String email = dadosAluno.get(1);

        try{
            int idade = Integer.parseInt(dadosAluno.get(2));

            Pattern padrao = Pattern.compile("[^A-Za-z0-9 ]");
            Matcher m = padrao.matcher(nome);
            boolean naoTemCaracterEspecial = !m.find();

            if(idade >= 16 && !nome.isEmpty() && !email.isEmpty() && emailEstaValido(email) && nomeNaoTemCaracterNumero(nome)){
                gerenciadorDeArquivos.criarNovoAluno(dadosAluno);
            }else{
                throw new IllegalArgumentException("Dado inválido");
            }
        }catch (NumberFormatException e){
            throw new NumberFormatException("A idade inserida deve ser um número inteiro");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<Integer, List<String>> consultaAlunosParaMap() throws IOException {
        HashMap<String, Integer> alunosMap = gerenciadorDeArquivos.mapeiaAlunoIdade();
        if(alunosMap.isEmpty()){
            throw new IllegalStateException("Não há alunos para listar");
        }
        return alunosMap.keySet().stream().collect(Collectors.groupingBy(alunosMap::get));
    }

    public void agruparAlunosPorIdade() throws IOException {
        consultaAlunosParaMap().forEach((key, value) -> System.out.println(key + " anos: " + value));
    }

    public void somarPorIdade() throws IOException {
        consultaAlunosParaMap().forEach((key, value) -> System.out.println(key + " anos: " + value.size()));
    }

    public void pesquisarPorEmailEIdade(String nome, String email) throws IOException {
        if(nomeNaoTemCaracterNumero(nome) && emailEstaValido(email) && !nome.isEmpty() && !email.isEmpty()){
            List<List<String>> alunos = gerenciadorDeArquivos.mapeiaAluno();
            System.out.println(alunos);
            List<String> alunoEncontrado = null;

            for (List<String> aluno : alunos) {
                String emailCadastrado = aluno.get(1);
                String nomeCadastrado = aluno.get(0);
                String primeiroNome;
                if (aluno.get(0).contains(" ")) {
                    primeiroNome = nomeCadastrado.substring(0, nomeCadastrado.indexOf(' '));
                } else {
                    primeiroNome = nomeCadastrado.substring(0, nomeCadastrado.length());

                }

                if (primeiroNome.equals(nome.substring(0, 1).toUpperCase() + nome.substring(1)) && emailCadastrado.equals(email)) {
                    alunoEncontrado = aluno;
                }
            }
            if (alunoEncontrado != null) {
                System.out.println("\nAluno encontrado:");
                System.out.println("nome: " + alunoEncontrado.get(0) + "\n" + "email: " + alunoEncontrado.get(1)
                        + "\n" + "idade: " + alunoEncontrado.get(2) + "\n" + "telefone: " + alunoEncontrado.get(3)
                        + "\n\n");
            } else {
                throw new NoSuchElementException("Aluno não encontrado");
            }

        }else{
            throw new IllegalArgumentException("Nome ou email inválidos, veriique os dados inseridos");
        }



    }


    public static final Pattern REGEX_NUMERO = Pattern.compile("[0-9]+", Pattern.CASE_INSENSITIVE);

    private boolean nomeNaoTemCaracterNumero(String nome){
        Matcher matcher = REGEX_NUMERO.matcher(nome);
        return !matcher.find();
    }


    public static final Pattern REGEX_DE_EMAIL_VALIDO = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private boolean emailEstaValido(String email) {
        Matcher matcher = REGEX_DE_EMAIL_VALIDO.matcher(email);
        return matcher.find();
    }
}

