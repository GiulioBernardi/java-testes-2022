package codar.gerenciador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import codar.utilitarios.FormatadorDeString;

public class GerenciadorDeArquivos {

    Integer qtdDeArquivos = 0;


    public void criarNovoAluno(List<String> dadosAluno) throws IOException {
        qtdDeArquivos++;
        File respostasDoForm = new File(String.format("%s-%s.txt", qtdDeArquivos.toString(), FormatadorDeString.converterNomeParaCamelCase(dadosAluno.get(0))));
        FileWriter writer = new FileWriter(respostasDoForm);

        dadosAluno.forEach((r) -> {
            try {
                writer.write(r + System.getProperty("line.separator"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        writer.flush();
        writer.close();
        System.out.println("Aluno cadastrado");
        System.out.println(dadosAluno.get(0));
    }

    public void escreverPergunta(String pergunta) throws IOException {
        File formulario = new File("formulario.txt");
        BufferedReader br = new BufferedReader(new FileReader(formulario));
        int linhas = 0;
        while(br.readLine() != null){
            linhas++;
        }

        PrintWriter out = new PrintWriter(new FileWriter(formulario, true));
        out.append(System.getProperty("line.separator") + "P" + (linhas+1) + "|" + pergunta);
        System.out.println("Pergunta inserida");
        out.close();
        br.close();
    }

    public void excluirPergunta(int linhaParaDeletar) {
        String tempFile = "temp.txt";
        File newFile = new File(tempFile);

        int line = 0;
        String currentLine;

        try {
            FileWriter fw = new FileWriter(tempFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            FileReader fr = new FileReader("formulario.txt");
            BufferedReader br = new BufferedReader(fr);

            while ((currentLine = br.readLine()) != null) {
                line++;

                if (linhaParaDeletar != line) {
                    pw.println(currentLine);
                }
            }

            pw.flush();
            pw.close();
            fr.close();
            br.close();
            bw.close();
            fw.close();

            FileWriter writer = new FileWriter("formulario.txt", false);

            Scanner leituraTemp = new Scanner(new File("temp.txt"));
            int i=0;
            while(leituraTemp.hasNextLine()) {

                i++;
                writer.write(leituraTemp.nextLine() + System.getProperty("line.separator"));

            }

            writer.close();
            newFile.delete();
            System.out.println("Questão apagada");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Integer> mapeiaAlunoIdade() throws IOException {
        File dir = new File(System.getProperty("user.dir"));
        HashMap<String, Integer> alunos = new HashMap<>();

        FileFilter filter = new FileFilter() {

            public boolean accept(File f) {
                String nomeDoArquivo = f.getName();
                if (Character.isDigit(nomeDoArquivo.charAt(0))) {
                    String a = nomeDoArquivo.substring(0, 1);
                    return f.getName().startsWith(a);

                }
                return f.getName().endsWith("nada");
            }
        };

        File[] files = dir.listFiles(filter);
        for (File f : files) {
            if (f.isFile()) {
                BufferedReader inputStream = null;

                inputStream = new BufferedReader(new FileReader(f));
                String linha;
                List<String> linhas = new ArrayList<>();

                while ((linha = inputStream.readLine()) != null) {
                    linhas.add(linha);
                }
                alunos.put(linhas.get(0), Integer.parseInt(linhas.get(2)));
                inputStream.close();
            }
        }
        return alunos;
    }




    public List<List<String>> mapeiaAluno() throws IOException {
        File dir = new File(System.getProperty("user.dir"));
        List<List<String>> alunos = new ArrayList<>();

        FileFilter filter = new FileFilter() {

            public boolean accept(File f) {
                String nomeDoArquivo = f.getName();
                if (Character.isDigit(nomeDoArquivo.charAt(0))) {
                    String a = nomeDoArquivo.substring(0, 1);
                    return f.getName().startsWith(a);

                }
                return f.getName().endsWith("nada");
            }
        };

        File[] files = dir.listFiles(filter);
        for (File f : files) {
            if (f.isFile()) {
                BufferedReader inputStream = null;

                inputStream = new BufferedReader(new FileReader(f));
                String linha;
                List<String> linhas = new ArrayList<>();

                while ((linha = inputStream.readLine()) != null) {
                    linhas.add(linha);
                }
                alunos.add(linhas);
                inputStream.close();
            }
        }
        return alunos;
    }

    public long contaQuantidadeDePerguntas() {

        long lines = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("formulario.txt"))) {
            while (reader.readLine() != null) lines++;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;

    }


}