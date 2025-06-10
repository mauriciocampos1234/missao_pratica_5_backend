package controller;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Produto;

public class ProdutoJpaController {

    private EntityManagerFactory emf;

    public ProdutoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Produto> findProdutoEntities() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Produto p", Produto.class).getResultList();
        } finally {
            em.close();
        }
    }
    
    public Produto findProduto(int id) {
        return getEntityManager().find(Produto.class, id);
    }
    
    public Produto findByNome(String nome) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Produto p WHERE p.nome = :nome", Produto.class)
                     .setParameter("nome", nome)
                     .getResultStream()
                     .findFirst()
                     .orElse(null);
        } finally {
            em.close();
        }
    }

    public void edit(Produto produto) throws Exception {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(produto);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}
