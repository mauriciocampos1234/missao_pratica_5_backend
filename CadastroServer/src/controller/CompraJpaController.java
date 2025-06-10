package controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Compra;
import java.io.Serializable;

public class CompraJpaController implements Serializable {

    private EntityManagerFactory emf = null;

    public CompraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Compra compra) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(compra);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
