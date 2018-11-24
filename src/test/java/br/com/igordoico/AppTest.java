package br.com.igordoico;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static br.com.igordoico.App.comandos;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AppTest {
    private List<String> list = Arrays.asList("campo1,campo2,campo3,campo4", "valor1,valor2,valor3,valor4", "valor1,valor6,valor7,valor8");
    private DataFrame dataFrame = new DataFrame(list.stream(), ",");

    @Test
    public void comandosTest() {
        ArrayList<String> countAll = comandos(dataFrame, new String[]{"count", "*"});
        assertEquals(1, countAll.size());

        ArrayList<String> countDistinct = comandos(dataFrame, new String[]{"count", "distinct", "campo1"});
        assertEquals(1, countDistinct.size());

        ArrayList<String> countCampoInexistente = comandos(dataFrame, new String[]{"count", "distinct", "campoInexistente"});
        assertEquals(3, countCampoInexistente.size());

        ArrayList<String> filterCampo1 = comandos(dataFrame, new String[]{"filter", "campo1", "valor1"});
        assertEquals(3,filterCampo1.size());

        ArrayList<String> filterCampoInexistente = comandos(dataFrame, new String[]{"filter", "filterCampoInexistente", "valor1"});
        assertEquals(3,filterCampoInexistente.size());

        ArrayList<String> filterValorInexistente = comandos(dataFrame, new String[]{"filter", "campo1", "valorInexistente"});
        assertEquals(1,filterValorInexistente.size());

        ArrayList<String> help = comandos(dataFrame, new String[]{"help"});
        assertNotNull(help);

        ArrayList<String> comandoInexistente = comandos(dataFrame, new String[]{"comandoInexistente"});
        System.out.println(comandoInexistente);
        assertEquals(2,comandoInexistente.size());

    }


}