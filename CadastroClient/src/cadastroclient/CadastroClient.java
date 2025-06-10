package cadastroclient;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import model.Produto;

public class CadastroClient {
    public static void main(String[] args) {
        final String HOST = "localhost";
        final int PORTA = 4321;

        try (
            Socket socket = new Socket(HOST, PORTA);
            PrintWriter saida = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);
        ) {
            System.out.println("1. Conectado ao servidor.");

            String login = "op1";
            String senha = "op1";
            saida.println(login);
            saida.println(senha);
            System.out.println("2. Enviado login/senha: " + login + "/" + senha);

            String resp = entrada.readLine();
            System.out.println("3. Resposta do servidor: " + resp);

            if (!"ok".equalsIgnoreCase(resp)) {
                System.out.println("4. Login inválido.");
                return;
            }

            while (true) {
                System.out.println("\n== MENU ==");
                System.out.println("L - Listar produtos");
                System.out.println("E - Entrada de produtos");
                System.out.println("S - Saída de produtos");
                System.out.println("X - Sair");
                System.out.print("Escolha: ");
                String comando = scanner.nextLine().trim().toUpperCase();

                saida.println(comando);

                if (comando.equals("L")) {
                    ObjectInputStream objEntrada = new ObjectInputStream(socket.getInputStream());
                    @SuppressWarnings("unchecked")
                    List<Produto> produtos = (List<Produto>) objEntrada.readObject();

                    System.out.println("\n--- Lista de Produtos ---");
                    for (Produto p : produtos) {
                        System.out.printf("ID: %d | Nome: %s | Qtde: %d | Preço: R$ %.2f\n",
                                p.getIdProduto(), p.getNome(), p.getQuantidade(), p.getPrecoVenda().doubleValue());
                    }

                } else if (comando.equals("E") || comando.equals("S")) {
                    System.out.print("ID do Produto: ");
                    int id = Integer.parseInt(scanner.nextLine());

                    System.out.print("Quantidade: ");
                    int qtd = Integer.parseInt(scanner.nextLine());

                    saida.println(id);
                    saida.println(qtd);

                    String resultado = entrada.readLine();
                    System.out.println("Servidor: " + resultado);

                } else if (comando.equals("X")) {
                    System.out.println("Encerrando conexão...");
                    break;
                } else {
                    System.out.println("Comando inválido!");
                }
            }

        } catch (Exception e) {
            System.out.println("ERRO:");
            e.printStackTrace();
        }
    }
}
