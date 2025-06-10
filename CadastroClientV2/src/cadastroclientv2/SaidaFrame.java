package cadastroclientv2;

import javax.swing.*;

public class SaidaFrame extends JDialog {
    public JTextArea texto;

    public SaidaFrame() {
        setTitle("Mensagens do Servidor");
        setBounds(100, 100, 400, 300);
        setModal(false);
        texto = new JTextArea();
        texto.setEditable(false);
        add(new JScrollPane(texto));

        setVisible(true);
    }
}
