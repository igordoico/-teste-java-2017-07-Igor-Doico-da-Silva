package br.com.igordoico;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Stream;


public class App {
    private static Scanner scn = new Scanner(System.in);

    public static void main(String[] args) {
        Stream<String> stream = null;
        String separador;
        String extensao;
        do {
            System.out.println("Digite o caminho do arquivo (o caminho nao deve conter espacos)");
            String caminho = scn.next();
            extensao = getExtension(caminho);
            if (extensao != null)
                stream = getLines(caminho, extensao);
        } while (stream == null);

        separador = getSeparador(extensao);

        DataFrame df = new DataFrame(stream, separador);
        boolean exit = false;
        String[] comando;
        System.out.println("Digite o comando desejado");
        System.out.println("para ajuda digite: help");
        scn.nextLine();
        comando = scn.nextLine().split(" ");
        ArrayList<String> resultado;
        do {
            resultado = comandos(df, comando);
            resultado.forEach(System.out::println);
            System.out.println();
            System.out.println("Digite o comando desejado");
            comando = scn.nextLine().split(" ");
            if (comando[0].equals("exit")) {
                System.out.println("Deseja sair? S/N");
                String res = scn.next();
                exit = res.equals("S") || res.equals("s");
            }
        } while (!exit);
        System.out.println("Encerrando");
    }

    /**
     * @param df      DataFrame o qual deseja realizar os comandos
     * @param comando string com o comando desejado
     */
    static ArrayList<String> comandos(DataFrame df, String[] comando) {
        ArrayList<String> resultado = new ArrayList<>();
        if (comando.length > 0) {
            switch (comando[0]) {
                case "count":
                    if (comando.length > 1) {
                        String tipo = comando[1];
                        String propriedade = comando.length > 2 ? comando[2] : null;
                        HashMap<String, Integer> result = df.getCount(tipo, propriedade);
                        if (result != null) {
                            ArrayList<String> lambdaResult = resultado;
                            result.forEach((key, value) -> lambdaResult.add(key + " - " + value.toString()));
                            resultado = lambdaResult;
                        } else {
                            resultado.add("Propriedade invalida.");
                            resultado.add("Propriedades disponiveis:");
                            resultado.add(df.getCabecalhoAsSortedArray().toString());
                        }
                    } else {
                        resultado.add("Comando incorreto. Utilize o comando help para ajuda");
                    }
                    break;
                case "filter":
                    if (comando.length >= 2) {
                        String propriedade = comando[1];
                        String valor = comando.length == 2 ? null : comando[2];
                        ArrayList<ArrayList<String>> result = df.filter(propriedade, valor);
                        if (result != null) {
                            ArrayList<String> lambdaResult = new ArrayList<>();
                            result.forEach(item -> {
                                StringBuilder line = new StringBuilder();
                                for (String it : item) {
                                    line.append(it).append(", ");
                                }
                                lambdaResult.add(line.toString());
                            });
                            resultado = lambdaResult;

                        } else {
                            resultado.add("Propriedade invalida.");
                            resultado.add("Propriedades disponiveis:");
                            resultado.add(df.getCabecalhoAsSortedArray().toString());
                        }
                    } else {
                        resultado.add("Comando incorreto. Utilize o comando help para ajuda");
                    }
                    break;
                case "help":
                    resultado = listCommands();
                    break;
                default:
                    resultado.add("Comando incorreto. Utilize o comando help para ajuda");
                    break;
            }
        }
        return resultado;
    }

    /**
     * lista os comandos disponiveis
     */
    private static ArrayList<String> listCommands() {
        ArrayList<String> comandos = new ArrayList<>();
        comandos.add("Lista de Comandos");
        comandos.add("-----------------");
        comandos.add("help - Lista de comandos");
        comandos.add("exit - Sair do programa");
        comandos.add("count * - Contar total de registros");
        comandos.add("count distinct [propriedade] - Contar total de valores distintos da propriedade");
        comandos.add("filter [propriedade] [valor] - Listar cabecalho e linhas que correspondem ao valor em dada propriedade");
        comandos.add("filter [propriedade] - Listar cabecalho e linhas aonde a propriedade é vazia");
        comandos.add("");
        return comandos;
    }

    /**
     * @param caminho  caminho do arquivo
     * @param extensao extensao do arquivo
     * @return retorna Stream com os dados do arquivo
     */
    private static Stream<String> getLines(String caminho, String extensao) {
        Stream<String> stringStream = null;
        switch (extensao) {
            case "csv":
            case "tsv": {
                try {
                    stringStream = Files.lines(Paths.get(caminho));
                } catch (IOException e) {
                    System.out.println("Arquivo nao encontrado");
                }
                break;
            }
            default:
                break;
        }
        return stringStream;
    }

    /**
     * @param extension extensao do arquivo
     * @return String separador de colunas do arquivo
     */
    private static String getSeparador(String extension) {
        String separador = null;
        if (extension != null) {
            switch (extension) {
                case "csv": {
                    System.out.println("Digite o separador de colunas do arquivo");
                    separador = scn.next();
                    break;
                }
                case "tsv": {
                    separador = "\t";
                    break;
                }
                default: {
                    break;
                }
            }
        }
        return separador;

    }

    /**
     * @param caminho caminho do arquivo
     * @return extensao do arquivo
     */
    private static String getExtension(String caminho) {
        String[] splitCaminho = caminho.split("\\.");
        String extension = null;
        if (splitCaminho.length > 1) {
            extension = splitCaminho[splitCaminho.length - 1];
        }
        return extension;
    }


}
