package controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;
import model.Usuario;

public class UsuarioJpaController implements Serializable {

    private EntityManagerFactory emf = null;

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Usuario findByLoginSenha(String login, String senha) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Usuario> query = em.createQuery(
                "SELECT u FROM Usuario u WHERE u.login = :login AND u.senha = :senha", Usuario.class);
            query.setParameter("login", login);
            query.setParameter("senha", senha);

            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public Usuario findUsuario(String login) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Usuario> query = em.createQuery(
                "SELECT u FROM Usuario u WHERE u.login = :login", Usuario.class);
            query.setParameter("login", login);
            List<Usuario> resultados = query.getResultList();
            return resultados.isEmpty() ? null : resultados.get(0);
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }


}
