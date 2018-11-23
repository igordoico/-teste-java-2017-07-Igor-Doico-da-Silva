package br.com.igordoico;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
        System.out.println(df.getDados().toString());
        comandos(df);
    }

    /**
     * @param df DataFrame o qual deseja realizar os comandos
     */
    private static void comandos(DataFrame df) {
        boolean exit = false;
        String[] comando;
        System.out.println("Digite o comando desejado");
        System.out.println("para ajuda digite: help");
        scn.nextLine();
        comando = scn.nextLine().split(" ");
        do {
            if (comando.length > 0) {
                switch (comando[0]) {
                    case "count":
                        if (comando.length > 1) {
                            String tipo = comando[1];
                            String propriedade = comando.length > 2 ? comando[2] : null;
                            HashMap<String, Integer> result = df.getCount(tipo, propriedade);
                            if (result != null) {
                                result.forEach((key, value) -> System.out.println(key + " - " + value.toString()));

                            } else {
                                System.out.println("Propriedade invalida.");
                                System.out.println("Propriedades disponiveis:");
                                System.out.println(df.getCabecalhoAsSortedArray());

                            }
                        } else {
                            System.out.println("Comando incorreto. Utilize o comando help para ajuda");
                        }
                        break;
                    case "filter":
                        if (comando.length >= 2) {
                            String propriedade = comando[1];
                            String valor = comando.length == 2 ? null : comando[2];
                            ArrayList<ArrayList<String>> result = df.filter(propriedade, valor);
                            if (result != null) {
                                result.forEach(item -> {
                                    StringBuilder line = new StringBuilder();
                                    for (String it : item) {
                                        line.append(it).append(", ");
                                    }
                                    System.out.println(line.toString());
                                });
                            } else {
                                System.out.println("Propriedade invalida.");
                                System.out.println("Propriedades disponiveis:");
                                System.out.println(df.getCabecalhoAsSortedArray());
                            }
                        } else {
                            System.out.println("Comando incorreto. Utilize o comando help para ajuda");
                        }
                        break;
                    case "exit":
                        System.out.println("Deseja sair? S/N");
                        String res = scn.next();
                        exit = res.equals("S") || res.equals("s");
                        break;
                    case "help":
                        listCommands();
                        break;
                    default:
                        System.out.println("Comando incorreto. Utilize o comando help para ajuda");
                        break;
                }
                System.out.println();
                System.out.println("Digite o comando desejado");
                comando = scn.nextLine().split(" ");
            }
        } while (!exit);
        System.out.println("Encerrando");
    }

    /**
     * lista os comandos disponiveis
     */
    private static void listCommands() {
        System.out.println("Lista de Comandos");
        System.out.println("-----------------");
        System.out.println("help - Lista de comandos");
        System.out.println("exit - Sair do programa");
        System.out.println("count * - Contar total de registros");
        System.out.println("count distinct [propriedade] - Contar total de valores distintos da propriedade");
        System.out.println("filter [propriedade] [valor] - Listar cabecalho e linhas que correspondem ao valor em dada propriedade");
        System.out.println("filter [propriedade] - Listar cabecalho e linhas aonde a propriedade é vazia");
        System.out.println();
    }

    /**
     * @param caminho  caminho do arquivo
     * @param extensao extensao do arquivo
     * @return retorna Stream com os dados do arquivo
     */
    private static Stream<String> getLines(String caminho, String extensao) {
        Stream<String> stringStream = null;
        Path path = Paths.get(caminho);
        switch (extensao) {
            case "csv":
            case "tsv": {
                try {
                    stringStream = Files.lines(path);
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
