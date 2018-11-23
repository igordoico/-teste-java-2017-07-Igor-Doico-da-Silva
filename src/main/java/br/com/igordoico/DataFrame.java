package br.com.igordoico;

import java.util.*;
import java.util.stream.Stream;

public class DataFrame {

    private int index;
    private Map<String, Integer> cabecalho = new HashMap<>();
    private ArrayList<ArrayList<String>> dados = new ArrayList<>();

    DataFrame(Stream<String> dados, String separador) {
        index = 0;
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


    /**
     * @param tipo        tipo de contagem (*, distinct)
     * @param propriedade nome da proprieade a qual deseja a contagem de valores distintos(nullable)
     * @return retorna um HashMap<String,Integer> sendo Chave: nome da propriedade, Valor: total da contagem
     */
    HashMap<String, Integer> getCount(String tipo, String propriedade) {
        HashMap<String, Integer> ret = new HashMap<>();
        switch (tipo) {
            case "*":
                propriedade = "Total";
                int total = this.dados.size();
                ret.put(propriedade, total);
                break;
            case "distinct":
                Integer index = this.cabecalho.get(propriedade);
                HashSet<String> distinct = new HashSet<>();
                for (ArrayList<String> dado : this.dados) {
                    distinct.add(dado.get(index));
                }
                ret.put(propriedade, distinct.size());
                break;
            default:
                break;

        }
        return ret;
    }

    ArrayList<ArrayList<String>> filter(String propriedade, String valor) {
        ArrayList<String> headers = new ArrayList<>(this.cabecalho.keySet());

        headers.sort(Comparator.comparing(h -> this.cabecalho.get(h)));

        ArrayList<ArrayList<String>> filtro = new ArrayList<>();
        filtro.add(headers);
        Integer index = this.cabecalho.get(propriedade);
        for (ArrayList<String> dado : this.dados) {
            if (dado.get(index).equals(valor)) {
                filtro.add(dado);
            }
        }
        return filtro;
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
