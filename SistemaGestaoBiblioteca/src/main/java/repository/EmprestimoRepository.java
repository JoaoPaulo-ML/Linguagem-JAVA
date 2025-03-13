package repository;

import model.EmprestimoModel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class EmprestimoRepository {
    private static EmprestimoRepository instance;
    protected EntityManager entityManager;

    private EmprestimoRepository() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudHibernatePU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }

    public static EmprestimoRepository getInstance() {
        if (instance == null) {
            instance = new EmprestimoRepository();
        }
        return instance;
    }

    public String salvar(EmprestimoModel emprestimo) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(emprestimo);
            entityManager.getTransaction().commit();
            return "Empréstimo salvo com sucesso.";
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return "Erro ao salvar empréstimo: " + e.getMessage();
        }
    }

    public EmprestimoModel buscarPorId(int id) {
        return entityManager.find(EmprestimoModel.class, id);
    }

    public String atualizar(EmprestimoModel emprestimo) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(emprestimo);
            entityManager.getTransaction().commit();
            return "Empréstimo atualizado com sucesso.";
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return "Erro ao atualizar empréstimo: " + e.getMessage();
        }
    }

    public List<EmprestimoModel> buscarTodos() {
        return entityManager.createQuery("FROM EmprestimoModel", EmprestimoModel.class).getResultList();
    }


}
