package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@NamedQueries({
    @NamedQuery(name = "Venda.findAll", query = "SELECT v FROM Venda v")
})
public class Venda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_venda")
    private Integer idVenda;

    @Basic(optional = false)
    private int quantidade;

    @Basic(optional = false)
    @Column(name = "preco_unitario")
    private BigDecimal precoUnitario;

    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    private Date data;

    @JoinColumn(name = "id_pessoa_fis", referencedColumnName = "id_pessoa")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PessoaFisica idPessoaFis;

    @JoinColumn(name = "id_produto", referencedColumnName = "id_produto")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Produto idProduto;

    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario idUsuario;

    public Venda() {
    }

    public Venda(Integer idVenda) {
        this.idVenda = idVenda;
    }

    public Venda(Integer idVenda, int quantidade, BigDecimal precoUnitario, Date data) {
        this.idVenda = idVenda;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.data = data;
    }

    public Integer getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(Integer idVenda) {
        this.idVenda = idVenda;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public PessoaFisica getIdPessoaFis() {
        return idPessoaFis;
    }

    public void setIdPessoaFis(PessoaFisica idPessoaFis) {
        this.idPessoaFis = idPessoaFis;
    }

    public Produto getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Produto idProduto) {
        this.idProduto = idProduto;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVenda != null ? idVenda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Venda)) {
            return false;
        }
        Venda other = (Venda) object;
        if ((this.idVenda == null && other.idVenda != null) || 
            (this.idVenda != null && !this.idVenda.equals(other.idVenda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Venda[ idVenda=" + idVenda + " ]";
    }
}
