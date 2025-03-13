package repository;

import model.LivrosModel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class LivrosRepository {
    private static LivrosRepository instance;
    protected EntityManager entityManager;

    private LivrosRepository() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudHibernatePU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }

    public static LivrosRepository getInstance() {
        if (instance == null) {
            instance = new LivrosRepository();
        }
        return instance;
    }

    public String salvar(LivrosModel livro) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(livro);
            entityManager.getTransaction().commit();
            return "Livro salvo com sucesso.";
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return "Erro ao salvar livro: " + e.getMessage();
        }
    }

    public List<LivrosModel> buscarTodos() {
        try {
            return entityManager.createQuery("from LivrosModel", LivrosModel.class).getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public String remover(LivrosModel livro) {
        try {
            entityManager.getTransaction().begin();
            if (!entityManager.contains(livro)) {
                livro = entityManager.merge(livro);
            }
            entityManager.remove(livro);
            entityManager.getTransaction().commit();
            return "Livro removido com sucesso!";
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return "Erro ao remover livro: " + e.getMessage();
        }
    }

    public LivrosModel buscarPorId(int id) {
        try {
            return entityManager.find(LivrosModel.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    public String atualizar(LivrosModel livro) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(livro);
            entityManager.getTransaction().commit();
            return "Livro atualizado com sucesso.";
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return "Erro ao atualizar livro: " + e.getMessage();
        }
    }
}
