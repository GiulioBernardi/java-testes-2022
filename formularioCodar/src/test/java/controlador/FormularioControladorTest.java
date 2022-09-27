package controlador;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import codar.controlador.FormularioContolador;
import codar.gerenciador.GerenciadorDeArquivos;
import codar.gerenciador.GerenciadorDeInput;
import codar.service.AlunoService;
import codar.service.PerguntaService;

public class FormularioControladorTest {

    @InjectMocks
    FormularioContolador formularioContolador;

    GerenciadorDeArquivos gerenciadorDeArquivos = mock(GerenciadorDeArquivos.class);
    AlunoService alunoService = mock(AlunoService.class);
    PerguntaService perguntaService = mock(PerguntaService.class);

    @Mock
    GerenciadorDeInput gerenciadorDeInput = mock(GerenciadorDeInput.class);

    @Before
    public void beforeEach(){
        formularioContolador = new FormularioContolador();
    }

    @Test
    public void deveDarErroAoReceberOpcaoQueNoaExiste(){
        int opcao = 8;
        assertThrows(IllegalArgumentException.class, () -> formularioContolador.direcionaChamada(opcao));
    }

    @Test
    public void deveDarErroAoReceberOpcaoQueNaoENumero(){
        String opcao = "dado errado";
        assertThrows(IllegalArgumentException.class, () -> formularioContolador.direcionaChamada(Integer.parseInt(opcao)));
    }
}
