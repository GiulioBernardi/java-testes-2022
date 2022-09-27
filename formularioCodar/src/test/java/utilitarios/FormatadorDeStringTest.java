package utilitarios;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

import codar.utilitarios.FormatadorDeString;

public class FormatadorDeStringTest {

    @Test
    public void deveRetornarStringEmCamelCase(){
        String formatado = FormatadorDeString.converterNomeParaCamelCase("giulio cesar costa bernardi");
        assertEquals("giulioCesarCostaBernardi", formatado);
    }

    @Test
    public void deveApenasMudarPrimeiraLetraParaMinusculoCasoTenhaApenasUmaPalavra(){
        String formatado = FormatadorDeString.converterNomeParaCamelCase("Giulio");
        assertEquals("giulio", formatado);
    }

    @Test
    public void deveTransformarParaCamelCaseTirandoAcentosEtc(){
        String formatado = FormatadorDeString.converterNomeParaCamelCase("Giulio Césãr Côstä");
        assertEquals("giulioCesarCosta", formatado);
    }

    @Test
    public void deveDarErroSeStringDeParametroForVazia(){
        assertThrows(StringIndexOutOfBoundsException.class, () -> FormatadorDeString.converterNomeParaCamelCase(""));
    }




}
