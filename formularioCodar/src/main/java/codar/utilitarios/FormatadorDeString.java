package codar.utilitarios;

import java.text.Normalizer;

public class FormatadorDeString {
    public static String converterNomeParaCamelCase(String nome){
        String[] nomes = nome.split(" ");
        String nomeCamelCase = "";
        for(String n:nomes) {
            nomeCamelCase += n.substring(0, 1).toUpperCase() + n.substring(1, n.length()).toLowerCase();
        }
        String nomeReturn = nomeCamelCase.substring(0, 1).toLowerCase()+ nomeCamelCase.substring(1, nomeCamelCase.length())+"";
        return Normalizer.normalize(nomeReturn, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }
}
