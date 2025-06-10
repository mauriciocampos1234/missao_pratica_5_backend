package cadastroserver;

import controller.ProdutoJpaController;
import controller.UsuarioJpaController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Produto;
import model.Usuario;

public class CadastroThread extends Thread {

    private final Socket socket;
    private final EntityManagerFactory emf;

    public CadastroThread(Socket socket) {
        this.socket = socket;
        this.emf = Persistence.createEntityManagerFactory("CadastroServerPU");
    }

        @Override
    public void run() {
        try {
            try (socket) {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintStream out = new PrintStream(socket.getOutputStream());
                
                String login = in.readLine();
                String senha = in.readLine();
                
                Usuario usuario = new UsuarioJpaController(emf).findByLoginSenha(login, senha);
                
                if (usuario != null) {
                    out.println("LOGIN_OK");
                } else {
                    try (socket) {
                        out.println("LOGIN_FAIL");
                    }
                    return;
                }
                
                String comando;
                while ((comando = in.readLine()) != null) {
                    if (comando.equals("LISTAR_PRODUTOS")) {
                        List<Produto> produtos = new ProdutoJpaController(emf).findProdutoEntities();
                        for (Produto p : produtos) {
                            out.println(p.getIdProduto() + " - " + p.getNome() + " - " + p.getQuantidade() + " - R$" + p.getPrecoVenda());
                            
                        }
                        out.println("FIM_PRODUTOS");
                    }
                }
            }
        } catch (IOException e) {
        }
    }
}