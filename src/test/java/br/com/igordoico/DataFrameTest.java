package br.com.igordoico;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DataFrameTest {

    private List<String> list = Arrays.asList("campo1,campo2,campo3,campo4", "valor1,valor2,valor3,valor4", "valor1,valor6,valor7,valor8");
    private DataFrame dataFrame = new DataFrame(list.stream(), ",");

    @Test
    public void getCount() {
        HashMap<String, Integer> countAll = dataFrame.getCount("*", null);
        assertEquals(2, countAll.get("Total").intValue());
        HashMap<String, Integer> campo1 = dataFrame.getCount("distinct", "campo1");
        assertEquals(1, campo1.get("campo1").intValue());
        HashMap<String, Integer> campo2 = dataFrame.getCount("distinct", "campo2");
        assertEquals(2, campo2.get("campo2").intValue());
        HashMap<String, Integer> campoInexistente = dataFrame.getCount("distinct", "campoInexistente");
        assertNull(campoInexistente);
    }

    @Test
    public void filter() {
        List<List<String>> campo1 = dataFrame.filter("campo1", "valor1");
        assertEquals(3, campo1.size());
        List<List<String>> campo2 = dataFrame.filter("campo2", "valor2");
        assertEquals(2, campo2.size());
        List<List<String>> valorInexistente = dataFrame.filter("campo2", "valorInexistente");
        assertEquals(1, valorInexistente.size());
        List<List<String>> campoInexistente = dataFrame.filter("campoInexistente", "valor2");
        assertNull(campoInexistente);
    }

    @Test
    public void getCabecalhoAsSortedArray() {
        List<String> cabecalhoAsSortedArray = dataFrame.getSortedCabecalho();
        assertEquals(cabecalhoAsSortedArray.get(0), "campo1");
        assertEquals(cabecalhoAsSortedArray.get(3), "campo4");
    }

}