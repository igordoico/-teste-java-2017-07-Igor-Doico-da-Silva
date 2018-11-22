package br.com.igordoico;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 *
 *
 */
public class App {
    public static String[][] linhas;
    private static String[] cabecalho;
    private static Scanner scn = new Scanner(System.in);


    public static void main(String[] args) {
        Stream<String> stream = getLines();

        System.out.println("Digite o separador de colunas do arquivo");
        final String separador = scn.next();

        DataFrame df = new DataFrame(stream, separador);
        comandos(df);

    }

    private static void comandos(DataFrame df) {
        boolean exit = false;
        String[] comando;
        do {
            System.out.println("Digite o comando desejado");
            System.out.println("para ajuda digite: help");
            scn.nextLine();
            comando = scn.nextLine().split(" ");
            System.out.println(Arrays.toString(comando));


            if (comando.length > 0) {
                switch (comando[0]) {
                    case "count":
                        if (comando.length > 1) {
                            String tipo = comando[1];
                            String propriedade = comando.length > 2 ? comando[2] : null;
                            df.getCount(tipo,propriedade);
                        } else {
                            System.out.println("Comando incorreto. count * |  distinct [propriedade]");
                        }
                        break;
                    case "filter":
                        if (comando.length > 3) {
                            String propriedade = comando[2];
                            String valor = comando[3];
                            df.filter(propriedade,valor);
                        } else {
                            System.out.println("Comando incorreto. filter  [propriedade] [valor]");
                        }
                        break;

                    case "exit":
                        System.out.println("Deseja sair? S/N");
                        exit = scn.next().equals("S") || scn.next().equals("s");
                        break;
                    case "help":
                        listCommands();
                        break;
                    default:
                        System.out.println("Comando nao permitido");
                        break;
                }
            } else {
                exit = true;
            }

        } while (!exit);
        System.out.println("Encerrando");
    }

    private static void listCommands() {
        System.out.println("Lista de Comandos");
        System.out.println("-----------------");
        System.out.println("help - Lista de comandos");
        System.out.println("exit - Sair do programa");
        System.out.println("count * - contar total de registros");
        System.out.println("count distinct [propriedade] - contar total de valores distintos da propriedade");
        System.out.println("filter [propriedade] [valor] - listar cabecalho e linhas que correspondem ao valor em dada propriedade");
        System.out.println();
        System.out.println();

    }


    private static Stream<String> getLines() {
        Stream<String> stringStream;

        do {
            System.out.println("Digite o caminho do arquivo");
            String caminho = scn.next();
            stringStream = getFile(caminho);

        } while (stringStream == null);

        return stringStream;
    }


    private static Stream<String> getFile(String caminho) {
        Stream<String> lines = null;
        try {
            lines = Files.lines(Paths.get(caminho));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
