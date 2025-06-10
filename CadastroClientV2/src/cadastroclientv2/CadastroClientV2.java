package cadastroclientv2;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class CadastroClientV2 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SaidaFrame frame = new SaidaFrame();

            new Thread(() -> {
                try (
                    Socket socket = new Socket("localhost", 4321);
                    ObjectOutputStream saida = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
                    BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
                ) {
                    frame.texto.append("Conectado ao servidor.\n");

                    saida.writeUTF("op1");
                    saida.writeUTF("op1");
                    saida.flush();

                    Object respLogin = entrada.readObject();
                    if ("erro".equals(respLogin)) {
                        frame.texto.append("Login inválido.\n");
                        return;
                    }
                    frame.texto.append("Login efetuado.\n");

                    ThreadClient thread = new ThreadClient(entrada, frame.texto);
                    thread.start();

                    while (true) {
                        System.out.println("===== Menu =====");
                        System.out.println("L - Listar produtos");
                        System.out.println("E - Entrada");
                        System.out.println("S - Saída");
                        System.out.println("X - Finalizar");
                        System.out.print("Escolha uma opção: ");
                        String opcao = teclado.readLine().trim().toUpperCase();

                        if ("X".equals(opcao)) {
                            saida.writeUTF("X");
                            saida.flush();
                            break;
                        }

                        if ("L".equals(opcao)) {
                            saida.writeUTF("L");
                            saida.flush();
                        }
                        else if ("E".equals(opcao) || "S".equals(opcao)) {
                            saida.writeUTF(opcao);
                            saida.flush();

                            System.out.print("Digite o ID da Pessoa: ");
                            int idPessoa = Integer.parseInt(teclado.readLine());
                            saida.writeInt(idPessoa);
                            saida.flush();

                            System.out.print("Digite o ID do Produto: ");
                            int idProduto = Integer.parseInt(teclado.readLine());
                            saida.writeInt(idProduto);
                            saida.flush();

                            System.out.print("Digite a quantidade: ");
                            int quantidade = Integer.parseInt(teclado.readLine());
                            saida.writeInt(quantidade);
                            saida.flush();

                            System.out.print("Digite o valor unit\u00e1rio (ex: 10.50): ");
                            double valorUnitario = Double.parseDouble(teclado.readLine());
                            saida.writeDouble(valorUnitario);
                            saida.flush();
                        } else {
                            System.out.println("Opção inválida. Tente novamente.");
                        }
                    }

                } catch (Exception e) {
                    frame.texto.append("Erro: " + e.getMessage() + "\n");
                }
            }).start();
        });
    }
}
