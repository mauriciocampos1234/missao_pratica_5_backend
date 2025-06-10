package controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.Serializable;
import java.util.List;
import model.Pessoa;

public class PessoaJpaController implements Serializable {

    private EntityManagerFactory emf = null;

    public PessoaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Pessoa findPessoa(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pessoa.class, id);
        } finally {
            em.close();
        }
    }

    public List<Pessoa> findPessoaEntities() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Pessoa p", Pessoa.class).getResultList();
        } finally {
            em.close();
        }
    }
}
