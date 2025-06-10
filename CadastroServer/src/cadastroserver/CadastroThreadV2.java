package cadastroserver;

import controller.PessoaJpaController;
import controller.ProdutoJpaController;
import controller.UsuarioJpaController;
import controller.VendaJpaController;
import controller.CompraJpaController;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Pessoa;
import model.Produto;
import model.Usuario;
import model.Venda;
import model.Compra;
import java.math.BigDecimal;

public class CadastroThreadV2 extends Thread {
    private final Socket socket;
    private final ProdutoJpaController ctrlProd;
    private final VendaJpaController ctrlVenda;
    private final CompraJpaController ctrlCompra;
    private final PessoaJpaController ctrlPessoa;
    private final UsuarioJpaController ctrlUsuario;

    public CadastroThreadV2(Socket socket) {
        this.socket = socket;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CadastroServerPU");
        this.ctrlProd = new ProdutoJpaController(emf);
        this.ctrlVenda = new VendaJpaController(emf);
        this.ctrlCompra = new CompraJpaController(emf);
        this.ctrlPessoa = new PessoaJpaController(emf);
        this.ctrlUsuario = new UsuarioJpaController(emf);
    }

    @Override
    public void run() {
        Usuario usuario = null;
        try (
            ObjectOutputStream saida = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
        ) {
            String login = entrada.readUTF();
            String senha = entrada.readUTF();

            usuario = ctrlUsuario.findByLoginSenha(login, senha);
            if (usuario == null) {
                saida.writeObject("erro");
                saida.flush();
                return;
            } else {
                saida.writeObject("ok");
                saida.flush();
            }

            while (true) {
                String comando = entrada.readUTF();
                if (comando == null || comando.equalsIgnoreCase("X")) break;

                if (comando.equalsIgnoreCase("E") || comando.equalsIgnoreCase("S")) {
                    int idPessoa = entrada.readInt();
                    int idProduto = entrada.readInt();
                    int quantidade = entrada.readInt();
                    double valorUnitario = entrada.readDouble();

                    Pessoa pessoa = ctrlPessoa.findPessoa(idPessoa);
                    Produto produto = ctrlProd.findProduto(idProduto);

                    if (pessoa == null || produto == null) {
                        saida.writeObject("Erro: Pessoa ou Produto não encontrado.");
                        saida.flush();
                        continue;
                    }

                    if (comando.equalsIgnoreCase("S") && produto.getQuantidade() < quantidade) {
                        saida.writeObject("Erro: Estoque insuficiente.");
                        saida.flush();
                        continue;
                    }

                    int novaQuantidade = comando.equalsIgnoreCase("E")
                        ? produto.getQuantidade() + quantidade
                        : produto.getQuantidade() - quantidade;
                    produto.setQuantidade(novaQuantidade);
                    ctrlProd.edit(produto);

                    Date dataMovimento = new Date();

                    if (comando.equalsIgnoreCase("E")) {
                        if (pessoa.getPessoaJuridica() == null) {
                            saida.writeObject("Erro: Para entrada, pessoa deve ser Jurídica.");
                            saida.flush();
                            continue;
                        }
                        Compra compra = new Compra();
                        compra.setIdPessoaJur(pessoa.getPessoaJuridica());
                        compra.setIdProduto(produto);
                        compra.setIdUsuario(usuario);
                        compra.setQuantidade(quantidade);
                        compra.setPrecoUnitario(new BigDecimal(valorUnitario));
                        compra.setData(dataMovimento);

                        try {
                            ctrlCompra.create(compra);
                            saida.writeObject("Entrada registrada.");
                        } catch (Exception ex) {
                            produto.setQuantidade(produto.getQuantidade() - quantidade);
                            ctrlProd.edit(produto);
                            saida.writeObject("Erro ao registrar entrada: " + ex.getMessage());
                        }
                        saida.flush();
                    }
                    else {
                        if (pessoa.getPessoaFisica() == null) {
                            saida.writeObject("Erro: Para saída, pessoa deve ser Física.");
                            saida.flush();
                            continue;
                        }
                        Venda venda = new Venda();
                        venda.setIdPessoaFis(pessoa.getPessoaFisica());
                        venda.setIdProduto(produto);
                        venda.setIdUsuario(usuario);
                        venda.setQuantidade(quantidade);
                        venda.setPrecoUnitario(new BigDecimal(valorUnitario));
                        venda.setData(dataMovimento);

                        try {
                            ctrlVenda.create(venda);
                            saida.writeObject("Saída registrada.");
                        } catch (Exception ex) {
                            produto.setQuantidade(produto.getQuantidade() + quantidade);
                            ctrlProd.edit(produto);
                            saida.writeObject("Erro ao registrar saída: " + ex.getMessage());
                        }
                        saida.flush();
                    }
                }
                else if (comando.equalsIgnoreCase("L")) {
                    List<Produto> produtosOriginais = ctrlProd.findProdutoEntities();
                    List<Produto> produtosLimpos = new ArrayList<>();
                    for (Produto p : produtosOriginais) {
                        Produto novo = new Produto();
                        novo.setIdProduto(p.getIdProduto());
                        novo.setNome(p.getNome());
                        novo.setQuantidade(p.getQuantidade());
                        novo.setPrecoVenda(p.getPrecoVenda());
                        produtosLimpos.add(novo);
                    }

                    saida.writeObject(produtosLimpos);
                    saida.flush();
                }
                else {
                    saida.writeObject("Comando inválido.");
                    saida.flush();
                }
            }

        } catch (Exception e) {
            System.out.println("Erro na thread: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
                System.out.println("Erro ao fechar socket: " + e.getMessage());
            }
        }
    }
}
