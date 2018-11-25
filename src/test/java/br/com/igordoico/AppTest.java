package br.com.igordoico;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static br.com.igordoico.App.comandos;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AppTest {
    private List<String> list = Arrays.asList(
            "campo1,campo2,campo3,campo4",
            "valor1,valor2,valor3,valor4",
            "valor1,valor6,valor7,valor8"
    );
    private DataFrame dataFrame = new DataFrame(list.stream(), ",");

    @Test
    public void comandos() {
        List<String> countAll = App.comandos(dataFrame, new String[]{"count", "*"});
        assertEquals(1, countAll.size());

        List<String> countDistinct = App.comandos(dataFrame, new String[]{"count", "distinct", "campo1"});
        assertEquals(1, countDistinct.size());

        List<String> countCampoInexistente = App.comandos(dataFrame, new String[]{"count", "distinct", "campoInexistente"});
        assertEquals(3, countCampoInexistente.size());

        List<String> filterCampo1 = App.comandos(dataFrame, new String[]{"filter", "campo1", "valor1"});
        assertEquals(3, filterCampo1.size());

        List<String> filterCampoInexistente = App.comandos(dataFrame, new String[]{"filter", "filterCampoInexistente", "valor1"});
        assertEquals(3, filterCampoInexistente.size());

        List<String> filterValorInexistente = App.comandos(dataFrame, new String[]{"filter", "campo1", "valorInexistente"});
        assertEquals(1, filterValorInexistente.size());

        List<String> help = App.comandos(dataFrame, new String[]{"help"});
        assertNotNull(help);

        List<String> comandoInexistente = App.comandos(dataFrame, new String[]{"comandoInexistente"});
        assertEquals(1, comandoInexistente.size());

    }



}