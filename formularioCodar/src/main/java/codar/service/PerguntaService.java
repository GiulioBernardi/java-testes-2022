package codar.service;

import java.io.IOException;

import codar.gerenciador.GerenciadorDeArquivos;

public class PerguntaService {
	
	GerenciadorDeArquivos gerenciadorDeArquivos;

    public PerguntaService(GerenciadorDeArquivos gerenciadorDeArquivos) {
        this.gerenciadorDeArquivos = gerenciadorDeArquivos;
    }

    public void adicionarPergunta(String pergunta) throws IOException {
        if(!pergunta.isEmpty()){
            gerenciadorDeArquivos.escreverPergunta(pergunta);
        }else{
            throw new IllegalArgumentException("É necessário informar qua é a pergunta");
        }
    }

    public void removerPergunta(int numeroDaPergunta) {
        long quantidadeDePerguntas = gerenciadorDeArquivos.contaQuantidadeDePerguntas();
        if(numeroDaPergunta > 4 && numeroDaPergunta <= quantidadeDePerguntas){

        }
        if(numeroDaPergunta > 4){
            if(numeroDaPergunta <= quantidadeDePerguntas){
                try{
                    gerenciadorDeArquivos.excluirPergunta(numeroDaPergunta);
                }catch (NumberFormatException e){
                    e.printStackTrace();
                }
            }else{
                throw new IllegalArgumentException("Essa pergunta não existe");
            }

        }else{
            throw new IllegalArgumentException("Não é possível excluir as 4 perguntas iniciais");
        }

    }
}
