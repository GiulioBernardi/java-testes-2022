package services;



import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import codar.gerenciador.GerenciadorDeArquivos;
import codar.service.AlunoService;

public class AlunosServiceTest {
	@InjectMocks
    AlunoService service;
    GerenciadorDeArquivos gerenciadorDeArquivos = mock(GerenciadorDeArquivos.class);

    private final ByteArrayOutputStream saida = new ByteArrayOutputStream();

    @Before
    public void beforeEach(){
        System.setOut(new PrintStream(saida));
        service = new AlunoService(gerenciadorDeArquivos);
    }

    @Test
    public void deveChamarMetododeCriarAlunoComDadosDoAlunoAoTerTodosOsDadosCorretos() throws IOException {
        List<String> dadosAluno = new ArrayList<>();
        dadosAluno.add("Giulio Cesar Costa Bernardi");
        dadosAluno.add("giulioccbernardi@gmail.com");
        dadosAluno.add("21");
        dadosAluno.add("(13) 98847-3800");
        service.salvarAluno(dadosAluno);
        Mockito.verify(gerenciadorDeArquivos).criarNovoAluno(dadosAluno);
    }

    @Test
    public void deveLancarExceptionAoReceberDataMenorQueDezesseis() throws IOException {
        List<String> dadosAluno = new ArrayList<>();
        dadosAluno.add("Giulio Cesar Costa Bernardi");
        dadosAluno.add("giulioccbernardi@gmail.com");
        dadosAluno.add("15");
        dadosAluno.add("(13) 98847-3800");
        try{
            service.salvarAluno(dadosAluno);
        }catch (IllegalArgumentException e){
            Mockito.verify(gerenciadorDeArquivos, never()).criarNovoAluno(dadosAluno);

        }
    }

    @Test
    public void deveLancarExceptionAoReceberNomeVazio() throws IOException {
        List<String> dadosAluno = new ArrayList<>();
        dadosAluno.add("");
        dadosAluno.add("giulioccbernardi@gmail.com");
        dadosAluno.add("21");
        dadosAluno.add("(13) 98847-3800");
        try{
            service.salvarAluno(dadosAluno);
        }catch (IllegalArgumentException e){
            Mockito.verify(gerenciadorDeArquivos, never()).criarNovoAluno(dadosAluno);

        }
    }

    @Test
    public void deveLancarExceptionAoReceberEmailVazio() throws IOException {
        List<String> dadosAluno = new ArrayList<>();
        dadosAluno.add("Giulio Cesar");
        dadosAluno.add("");
        dadosAluno.add("21");
        dadosAluno.add("(13) 98847-3800");
        try{
            service.salvarAluno(dadosAluno);
        }catch (IllegalArgumentException e){
            Mockito.verify(gerenciadorDeArquivos, never()).criarNovoAluno(dadosAluno);

        }
    }

    @Test
    public void deveLancarExceptionAoNomeComNumerosOuCaracteresEspeciais(){
        List<String> dadosAluno = new ArrayList<>();
        dadosAluno.add("!@#$");
        dadosAluno.add("giulioccbernardi@gmail.com");
        dadosAluno.add("21");
        dadosAluno.add("(13) 98847-3800");
        try{
            service.salvarAluno(dadosAluno);
            Mockito.verify(gerenciadorDeArquivos).criarNovoAluno(dadosAluno);
        }catch (IllegalArgumentException e){
            Mockito.verifyNoInteractions(gerenciadorDeArquivos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void deveLancarExceptionPassarIdadeQueNãoSejaNumero(){
        List<String> dadosAluno = new ArrayList<>();
        dadosAluno.add("Giulio Cesar");
        dadosAluno.add("giulioccbernardi@gmail.com");
        dadosAluno.add("asd");
        dadosAluno.add("(13) 98847-3800");

        NumberFormatException exception = assertThrows(NumberFormatException.class, () -> service.salvarAluno(dadosAluno));
        assertEquals("A idade inserida deve ser um número inteiro", exception.getMessage());
    }

    @Test
    public void daErroAoBuscarAlunosSeNaoHouverNenhumCadastrado() throws IOException {
        HashMap mapVazio = new HashMap();
        Mockito.when(gerenciadorDeArquivos.mapeiaAlunoIdade()).thenReturn(mapVazio);
        assertThrows(IllegalStateException.class, ()-> service.agruparAlunosPorIdade());
    }

    @Test
    public void deveRetornarAlunoAoChamarMetodoDeConsulta() throws IOException {
        HashMap alunosMap = new HashMap();
        alunosMap.put("Giulio", 21);
        Mockito.when(gerenciadorDeArquivos.mapeiaAlunoIdade()).thenReturn(alunosMap);
        service.agruparAlunosPorIdade();
        Mockito.verify(gerenciadorDeArquivos).mapeiaAlunoIdade();
    }

    @Test
    public void deveTerSucessoAoBuscarAlunoExistenteComNomeEEmail() throws IOException {
        List<List<String>> alunos = new ArrayList<>();
        alunos.add(List.of("Giulio", "giulio@gmail.com", "21", "13131313131"));

        Mockito.when(gerenciadorDeArquivos.mapeiaAluno()).thenReturn(alunos);
        service.pesquisarPorEmailEIdade("Giulio", "giulio@gmail.com");
        Mockito.verify(gerenciadorDeArquivos).mapeiaAluno();
    }

    @Test
    public void deveDarErroAoTentarBuscarAlunoComNomeNull() throws IOException {
        List<List<String>> alunos = new ArrayList<>();
        alunos.add(List.of("Giulio", "giulio@gmail.com", "21", "13131313131"));

        Mockito.when(gerenciadorDeArquivos.mapeiaAluno()).thenReturn(alunos);

        assertThrows(IllegalArgumentException.class, ()->service.pesquisarPorEmailEIdade("", "giulio@gmail.com"));
    }

    @Test
    public void deveDarErroAoTentarBuscarAlunoComEmailNull() throws IOException {
        List<List<String>> alunos = new ArrayList<>();
        alunos.add(List.of("Giulio", "giulio@gmail.com", "21", "13131313131"));

        Mockito.when(gerenciadorDeArquivos.mapeiaAluno()).thenReturn(alunos);

        assertThrows(IllegalArgumentException.class, ()->service.pesquisarPorEmailEIdade("Giulio", ""));
    }

    @Test
    public void deveDarErroAoTentarBuscarAlunoComNomeEEmailQueNaoExistem() throws IOException {
        List<List<String>> alunos = new ArrayList<>();
        alunos.add(List.of("Giulio", "giulio@gmail.com", "21", "13131313131"));

        Mockito.when(gerenciadorDeArquivos.mapeiaAluno()).thenReturn(alunos);

        assertThrows(NoSuchElementException.class, ()->service.pesquisarPorEmailEIdade("Nome Não Existe", "email@naoexiste.com"));
    }

    @Test
    public void deveDarErroAoTentarBuscarAlunoComNomeQueNaoExiste() throws IOException {
        List<List<String>> alunos = new ArrayList<>();
        alunos.add(List.of("Giulio", "giulio@gmail.com", "21", "13131313131"));

        Mockito.when(gerenciadorDeArquivos.mapeiaAluno()).thenReturn(alunos);

        assertThrows(NoSuchElementException.class, ()->service.pesquisarPorEmailEIdade("Nome Não Existe", "giulio@gmail.com"));
    }

    @Test
    public void deveDarErroAoTentarBuscarAlunoComEmailQueNaoExiste() throws IOException {
        List<List<String>> alunos = new ArrayList<>();
        alunos.add(List.of("Giulio", "giulio@gmail.com", "21", "13131313131"));

        Mockito.when(gerenciadorDeArquivos.mapeiaAluno()).thenReturn(alunos);

        assertThrows(NoSuchElementException.class, ()->service.pesquisarPorEmailEIdade("Giulio", "email@fake.com"));
    }

    @Test
    public void deveDarErroAoTentarBuscarAlunoComNomeQueTenhaNumero() throws IOException {
        List<List<String>> alunos = new ArrayList<>();
        alunos.add(List.of("Giulio", "giulio@gmail.com", "21", "13131313131"));

        Mockito.when(gerenciadorDeArquivos.mapeiaAluno()).thenReturn(alunos);

        assertThrows(IllegalArgumentException.class, ()->service.pesquisarPorEmailEIdade("Giulio123456", "email@fake.com"));
    }

    @Test
    public void deveDarErroAoChamarOMetodoDeConsultarAlunoSemTerAluno() throws IOException {
        assertThrows(IllegalStateException.class, ()-> service.somarPorIdade());
    }

    @Test
    public void deveChamarOMetodoDeConsultarAlunoAoCairEmSomarPorIdade() throws IOException {

        HashMap<String, Integer> mapMockado = new HashMap<>();
        mapMockado.put("Giulio", 21);
        Mockito.when(gerenciadorDeArquivos.mapeiaAlunoIdade()).thenReturn(mapMockado);
        service.somarPorIdade();

        Mockito.verify(gerenciadorDeArquivos).mapeiaAlunoIdade();
        assertEquals("21 anos: 1", saida.toString());
    }

    @Test
    public void deveEntrarEmIfComNomeQueContenhaEspacoAoBuscarPorEmailENome() throws IOException {
        List<List<String>> alunos = new ArrayList<>();
        alunos.add(List.of("Giulio Cesar", "giulio@gmail.com", "21", "13131313131"));

        Mockito.when(gerenciadorDeArquivos.mapeiaAluno()).thenReturn(alunos);
        service.pesquisarPorEmailEIdade("Giulio Cesar", "giulio@gmail.com");

        Assert.assertEquals(true, alunos.get(0).get(0).contains(" "));
    }

    @Test
    public void deveDarErroAoCadastrarAlunoComEmailJaExistente() throws IOException {
        List<String> aluno1 = new ArrayList<>();
        aluno1.add("Giulio");
        aluno1.add("giulio@gmail.com.br");
        aluno1.add("21");
        aluno1.add("13958475859");
        when(gerenciadorDeArquivos.mapeiaAluno()).thenReturn(List.of(aluno1));

        List<String> aluno2 = new ArrayList<>();
        aluno2.add("Giulio");
        aluno2.add("giulio@gmail.com.br");
        aluno2.add("21");
        aluno2.add("13958475859");
        assertThrows(IllegalArgumentException.class, ()->service.salvarAluno(aluno2));
    }

}