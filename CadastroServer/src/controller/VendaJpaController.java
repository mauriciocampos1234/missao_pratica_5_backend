package controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Venda;
import java.io.Serializable;

public class VendaJpaController implements Serializable {

    private EntityManagerFactory emf = null;

    public VendaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Venda venda) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(venda);
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
