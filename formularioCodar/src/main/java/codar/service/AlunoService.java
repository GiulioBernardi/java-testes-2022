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

            boolean alunoJaExiste = emailJaExiste(dadosAluno.get(1));
            if(!alunoJaExiste){
                if(idade >= 16 && !nome.isEmpty() && !email.isEmpty() && emailEstaValido(email) && nomeNaoTemCaracterNumero(nome)){
                    gerenciadorDeArquivos.criarNovoAluno(dadosAluno);
                }else{
                    throw new IllegalArgumentException("Dado inválido");
                }
            }else{
                throw new IllegalArgumentException("Esse aluno já existe");
            }

        }catch (NumberFormatException | IOException e){
            throw new NumberFormatException("A idade inserida deve ser um número inteiro");
        }
    }

    private boolean emailJaExiste(String email) throws IOException {
        List<List<String>> alunos = gerenciadorDeArquivos.mapeiaAluno();
        Boolean encontrouAlunoRepetido = false;
        String aluno1 = null;
        String aluno2 = null;
        String email1 = email;
        for (int i = 0; i < alunos.size(); i++) {
            String email2 = alunos.get(i).get(1);
            if (email1.equals(email2)) {
                return true;
            }
        }
        return false;
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
        consultaAlunosParaMap().forEach((key, value) -> System.out.print(key + " anos: " + value.size()));
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
                    if (primeiroNome.equals(nome.substring(0, 1).toUpperCase() + nome.substring(1, nomeCadastrado.indexOf(' '))) && emailCadastrado.equals(email)) {
                        alunoEncontrado = aluno;
                    }
                } else {
                    primeiroNome = nomeCadastrado.substring(0, nomeCadastrado.length());
                    if (primeiroNome.equals(nome.substring(0, 1).toUpperCase() + nome.substring(1)) && emailCadastrado.equals(email)) {
                        alunoEncontrado = aluno;
                    }

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

    public void verificaUnicidadeDeAlunos() throws IOException {
        List<List<String>> alunos = gerenciadorDeArquivos.mapeiaAluno();
        Boolean encontrouAlunoRepetido = false;
        String aluno1 = null;
        String aluno2 = null;
        for (int i = 0; i < alunos.size(); i++) {
            String email1 = alunos.get(i).get(1);

            for (int j = 1; j < alunos.size() - i; j++) {
                String email2 = alunos.get(i + j).get(1);
                if (email1.equals(email2)) {
                    encontrouAlunoRepetido = true;
                    aluno1 = alunos.get(i).get(0);
                    aluno2 = alunos.get(i + j).get(0);
                }
            }
        }
        if (encontrouAlunoRepetido) {
            System.out.println(
                    "Foram encontrados dois alunos com o mesmo email, verifique!\n" + aluno1 + " e " + aluno2);
        } else {
            System.out.println("Nenhum aluno repetido, obrigado por verificar!");
        }
    }


    public static final Pattern REGEX_DE_EMAIL_VALIDO = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private boolean emailEstaValido(String email) {
        Matcher matcher = REGEX_DE_EMAIL_VALIDO.matcher(email);
        return matcher.find();
    }
}

