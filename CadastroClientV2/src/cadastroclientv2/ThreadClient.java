package cadastroclientv2;

import java.io.IOException;
import javax.swing.*;
import java.io.ObjectInputStream;
import java.util.List;
import model.Produto;

public class ThreadClient extends Thread {
    private final ObjectInputStream entrada;
    private final JTextArea textArea;

    public ThreadClient(ObjectInputStream entrada, JTextArea textArea) {
        this.entrada = entrada;
        this.textArea = textArea;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Object obj = entrada.readObject();

                if (obj instanceof String) {
                    String msg = (String) obj;
                    SwingUtilities.invokeLater(() -> textArea.append(msg + "\n"));
                } else if (obj instanceof List) {
                    List<?> lista = (List<?>) obj;
                    SwingUtilities.invokeLater(() -> {
                        textArea.append("Produtos recebidos:\n");
                        for (Object item : lista) {
                            if (item instanceof Produto) {
                                Produto p = (Produto) item;
                                textArea.append(" - " + p.getNome() + " | Qtde: " + p.getQuantidade() + "\n");
                            }
                        }
                        textArea.append("\n");
                    });
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            SwingUtilities.invokeLater(() -> textArea.append("Conexão encerrada.\n"));
        }
    }
}
