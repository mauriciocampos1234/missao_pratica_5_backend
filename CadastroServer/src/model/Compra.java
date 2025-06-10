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
    @NamedQuery(name = "Compra.findAll", query = "SELECT c FROM Compra c")
})
public class Compra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_compra")
    private Integer idCompra;

    @Basic(optional = false)
    private int quantidade;

    @Basic(optional = false)
    @Column(name = "preco_unitario")
    private BigDecimal precoUnitario;

    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    private Date data;

    @JoinColumn(name = "id_pessoa_jur", referencedColumnName = "id_pessoa")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PessoaJuridica idPessoaJur;

    @JoinColumn(name = "id_produto", referencedColumnName = "id_produto")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Produto idProduto;

    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario idUsuario;

    public Compra() {
    }

    public Compra(Integer idCompra) {
        this.idCompra = idCompra;
    }

    public Compra(Integer idCompra, int quantidade, BigDecimal precoUnitario, Date data) {
        this.idCompra = idCompra;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.data = data;
    }

    public Integer getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Integer idCompra) {
        this.idCompra = idCompra;
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

    public PessoaJuridica getIdPessoaJur() {
        return idPessoaJur;
    }

    public void setIdPessoaJur(PessoaJuridica idPessoaJur) {
        this.idPessoaJur = idPessoaJur;
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
        hash += (idCompra != null ? idCompra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Compra)) {
            return false;
        }
        Compra other = (Compra) object;
        if ((this.idCompra == null && other.idCompra != null) || 
            (this.idCompra != null && !this.idCompra.equals(other.idCompra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Compra[ idCompra=" + idCompra + " ]";
    }
}
