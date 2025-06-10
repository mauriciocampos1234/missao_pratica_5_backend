package cadastroclient;



import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import javax.swing.JOptionPane;
import model.Produto;

public class CadastroClientV2disabled {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 4321);
            ObjectOutputStream saida = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());

            
            String login = JOptionPane.showInputDialog("Login:");
            String senha = JOptionPane.showInputDialog("Senha:");
            saida.writeObject(login);
            saida.writeObject(senha);

            String resposta = (String) entrada.readObject();
            JOptionPane.showMessageDialog(null, resposta);

            if (!resposta.equalsIgnoreCase("Login efetuado com sucesso!")) {
                socket.close();
                return;
            }

            while (true) {
                String[] opcoes = {"Listar produtos (L)", "Entrada (E)", "Saída (S)", "Sair (X)"};
                int opcao = JOptionPane.showOptionDialog(null, "Escolha uma opção:", "Menu",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);

                String comando;
                if (opcao == 0) comando = "L";
                else if (opcao == 1) comando = "E";
                else if (opcao == 2) comando = "S";
                else comando = "X";

                saida.writeObject(comando);

                if (comando.equalsIgnoreCase("X")) {
                    String msg = (String) entrada.readObject();
                    JOptionPane.showMessageDialog(null, msg);
                    break;
                }

                if (comando.equalsIgnoreCase("L")) {
                    List<Produto> lista = (List<Produto>) entrada.readObject();
                    StringBuilder sb = new StringBuilder("Produtos cadastrados:\n\n");
                    for (Produto p : lista) {
                        sb.append("ID: ").append(p.getIdProduto()).append(" - ")
                          .append(p.getNome()).append(" - Qtd: ").append(p.getQuantidade())
                          .append(p.getQuantidade()).append(" - R$: ")
                          .append(" - R$ ").append(p.getPrecoVenda()).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, sb.toString());
                }

                if (comando.equalsIgnoreCase("E") || comando.equalsIgnoreCase("S")) {
                    int idPessoa = Integer.parseInt(JOptionPane.showInputDialog("ID da Pessoa:"));
                    int idProduto = Integer.parseInt(JOptionPane.showInputDialog("ID do Produto:"));
                    int qtd = Integer.parseInt(JOptionPane.showInputDialog("Quantidade:"));
                    double val = Double.parseDouble(JOptionPane.showInputDialog("Valor total:"));

                    saida.writeObject(idPessoa);
                    saida.writeObject(idProduto);
                    saida.writeObject(qtd);
                    saida.writeObject(val);

                    String msg = (String) entrada.readObject();
                    JOptionPane.showMessageDialog(null, msg);
                }
            }

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
    }
}
