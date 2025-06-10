package model;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.PessoaJuridica;
import model.Produto;
import model.Usuario;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-06-04T14:26:12", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Compra.class)
public class Compra_ { 

    public static volatile SingularAttribute<Compra, BigDecimal> precoUnitario;
    public static volatile SingularAttribute<Compra, Produto> idProduto;
    public static volatile SingularAttribute<Compra, Date> data;
    public static volatile SingularAttribute<Compra, Integer> idCompra;
    public static volatile SingularAttribute<Compra, Usuario> idUsuario;
    public static volatile SingularAttribute<Compra, Integer> quantidade;
    public static volatile SingularAttribute<Compra, PessoaJuridica> idPessoaJur;

}