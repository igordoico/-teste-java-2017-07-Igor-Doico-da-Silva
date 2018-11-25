package br.com.igordoico;

import java.util.*;
import java.util.stream.Stream;

class DataFrame {

    private int index;
    private Map<String, Integer> cabecalho = new HashMap<>();
    private List<List<String>> dados = new ArrayList<>();

    DataFrame(Stream<String> dataStream, String separador) {
        index = 0;
        dataStream.forEach(l -> {

            String[] linha = (separador != null) ? l.split(separador) : new String[]{l};
            if (index == 0) {
                for (int i = 0; i < linha.length; i++) {
                    this.cabecalho.put(linha[i], i);
                }
            } else {
                this.dados.add(new ArrayList<>(Arrays.asList(linha)));
            }
            index++;
        });
    }

    /**
     * @param countType   countType de contagem (*, distinct)
     * @param propriedade nome da proprieade a qual deseja a contagem de valores distintos(nullable)
     * @return retorna um HashMap<String,Integer> sendo Chave: nome da propriedade, Valor: total da contagem
     */
    HashMap<String, Integer> getCount(String countType, String propriedade) {
        HashMap<String, Integer> retorno = new HashMap<>();
        switch (countType) {
            case "*":
                propriedade = "Total";
                int total = this.dados.size();
                retorno.put(propriedade, total);
                break;
            case "distinct":
                Integer index = this.cabecalho.get(propriedade);
                if (index == null) return null;
                HashSet<String> distinct = new HashSet<>();
                for (List<String> dado : this.dados) {
                    distinct.add(dado.get(index));
                }
                retorno.put(propriedade, distinct.size());
                break;
            default:
                System.out.println("Comando desconhecido.");
                break;

        }
        return retorno;
    }

    /**
     * @param propriedade nome da propriedade a qual deseja fazer o filtro
     * @param valor       valor do filtro em dada propriedade
     * @return lista das linhas filtradas
     */
    List<List<String>> filter(String propriedade, String valor) {
        List<String> sortedHeader = getSortedCabecalho();
        List<List<String>> filtro = new ArrayList<>();
        filtro.add(sortedHeader);
        Integer propertyIndex = this.cabecalho.get(propriedade);
        if (propertyIndex == null) return null;
        if (valor == null) {
            for (List<String> linha : this.dados) {
                if (linha.get(propertyIndex).equals("")) {
                    filtro.add(linha);
                }
            }
        } else {
            for (List<String> dado : this.dados) {
                if (dado.get(propertyIndex).toUpperCase().equals(valor.toUpperCase())) {
                    filtro.add(dado);
                }
            }
        }
        return filtro;
    }

    Map<String, Integer> getCabecalho() {
        return cabecalho;
    }

    void setCabecalho(Map<String, Integer> cabecalho) {
        this.cabecalho = cabecalho;
    }

    /**
     * @return retorna cabecalho como uma lista ordenado exatamente como no arquivo.
     */
    List<String> getSortedCabecalho() {
        List<String> sortedHeader = new ArrayList<>(this.cabecalho.keySet());
        sortedHeader.sort(Comparator.comparing(h -> this.cabecalho.get(h)));
        return sortedHeader;
    }

    List<List<String>> getDados() {
        return dados;
    }

    void setDados(List<List<String>> dados) {
        this.dados = dados;
    }

}
