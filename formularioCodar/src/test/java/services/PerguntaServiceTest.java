package services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import codar.gerenciador.GerenciadorDeArquivos;
import codar.service.PerguntaService;


public class PerguntaServiceTest {

    PerguntaService service;

    GerenciadorDeArquivos gerenciadorDeArquivos = mock(GerenciadorDeArquivos.class);

    @Before
    public void beforeEach(){
        service = new PerguntaService(gerenciadorDeArquivos);
    }

    @Test
    public void deveChamarMeotodoDeEscreverPerguntaCorretamente(){
        String pergunta = "Qual seu cpf?";
        try{
            service.adicionarPergunta(pergunta);
            Mockito.verify(gerenciadorDeArquivos).escreverPergunta(pergunta);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void naoDeveChamarMetodoDeEscreverPerguntaCasoPerguntaVazia(){
        String pergunta = "";
        try{
            service.adicionarPergunta(pergunta);
        }catch (IllegalArgumentException | IOException e){
            e.printStackTrace();
        }
        Mockito.verifyNoInteractions(gerenciadorDeArquivos);

    }

    @Test
    public void deveRetornarSucessoAoApagarPerguntaCinco(){
        int numeroP = 5;
        try{
            service.removerPergunta(numeroP);
        }catch (Exception e){
            e.printStackTrace();
        }
        Mockito.verify(gerenciadorDeArquivos).excluirPergunta(numeroP);
    }

    @Test
    public void deveDarErroAoPassarNumeroMenorOuIgualAQuatro(){
        int numeroP = 3;
        try{
            service.removerPergunta(numeroP);
        }catch (Exception e){
            e.printStackTrace();
        }
        assertThrows(IllegalArgumentException.class, () -> service.removerPergunta(numeroP));
        Mockito.verifyNoInteractions(gerenciadorDeArquivos);
    }

    @Test
    public void deveDarErroSePassarCharComoNumeroDaPergunta(){
        String dadoErrado = "Aqui devereia ser um numero";
        try{
            service.removerPergunta(Integer.parseInt(dadoErrado)); //tentando, de qualquer modo, transformar em número
        }catch (Exception e){
            e.printStackTrace();
        }
        assertThrows(NumberFormatException.class, () -> service.removerPergunta(Integer.parseInt(dadoErrado)));
        Mockito.verifyNoInteractions(gerenciadorDeArquivos);
    }

    @Test
    public void deveDarErroSeTentarApagarPerguntaQueNaoExiste(){
        int numeroP = 256;
        try{
            service.removerPergunta(numeroP);
        }catch (Exception e){
            e.printStackTrace();
        }
        assertThrows(IllegalArgumentException.class, () -> service.removerPergunta(numeroP));
    }

    @Test
    public void deveDarErroAoPassarLetraAoChamarRemoverPergunta(){
        String pergntaASerRemovida= "aa";
        service.removerPergunta(Integer.parseInt(pergntaASerRemovida));
    }
}
