package repository;

import java.util.List;
import model.TarefasModel;
import java.sql.SQLException;
import javax.persistence.TypedQuery;
import javax.persistence.Persistence;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class TarefasRepository {
    private static TarefasRepository instance;
    protected EntityManager entityManager;

    public TarefasRepository() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudHibernatePU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }

    public static TarefasRepository getInstance() {
        if (instance == null) {
            instance = new TarefasRepository();
        }
        return instance;
    }

    public String salvar(TarefasModel tarefas)throws SQLException {
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(tarefas);
            entityManager.getTransaction().commit();
            return "Tarefa salva com sucesso.";
        }catch (Exception e ){
            entityManager.getTransaction().rollback();
            return "Erro ao salvar a tarefa: " + e.getMessage();
        }
    }

    public List<TarefasModel> listarTarefas() {
        String jpql = "SELECT t FROM TarefasModel t";
        TypedQuery<TarefasModel> query = entityManager.createQuery(jpql, TarefasModel.class);
        return query.getResultList();
    }
}
