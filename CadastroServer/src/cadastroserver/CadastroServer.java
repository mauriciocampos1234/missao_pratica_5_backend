package cadastroserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CadastroServer {
    public static void main(String[] args) {
        try (ServerSocket servidor = new ServerSocket(4321)) {
            System.out.println("Servidor aguardando conexões na porta 4321...");

            while (true) {
                Socket socket = servidor.accept();
                System.out.println("Cliente conectado: " + socket.getInetAddress().getHostAddress());

                CadastroThreadV2 thread = new CadastroThreadV2(socket);
                thread.start();
            }

        } catch (IOException e) {
            System.err.println("Erro ao iniciar o servidor: " + e.getMessage());
        }
    }
}
