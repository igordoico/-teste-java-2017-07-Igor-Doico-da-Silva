package br.com.igordoico;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class DataFrame {

    private int index = 0;
    private Map<String, Integer> cabecalho = new HashMap<>();
    private ArrayList<ArrayList<String>> dados =  new ArrayList<>();

    DataFrame(Stream<String> dados, String separador) {

        dados.forEach(l -> {
            String[] linha = l.split(separador);
            if (index == 0) {
                for (int i = 0; i < linha.length; i++) {
                    this.cabecalho.put(linha[i], i);
                }
            } else {
                this.dados.add(new ArrayList<>(Arrays.asList(linha)));
            }
            index++;
        });

        ArrayList<ArrayList<String>> linhas = new ArrayList<>();

    }


    void getCount(String tipo, String propriedade) {
        System.out.println("Contando");
    }

    void filter(String propriedade, String valor) {
        System.out.println("filtrando");
    }

    public Map<String, Integer> getCabecalho() {
        return cabecalho;
    }

    public void setCabecalho(Map<String, Integer> cabecalho) {
        this.cabecalho = cabecalho;
    }

    public ArrayList<ArrayList<String>> getDados() {
        return dados;
    }

    public void setDados(ArrayList<ArrayList<String>> dados) {
        this.dados = dados;
    }


}
