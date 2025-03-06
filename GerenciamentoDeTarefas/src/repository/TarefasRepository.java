package repository;

import java.util.List;
import model.TarefasModel;
import java.time.LocalDate;
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

    public String excluirTarefa(int id) {
        try {
            entityManager.getTransaction().begin();
            TarefasModel tarefa = entityManager.find(TarefasModel.class, id);

            if (tarefa != null) {
                entityManager.remove(tarefa);
                entityManager.getTransaction().commit();
                return "Tarefa excluída com sucesso.";
            } else {
                entityManager.getTransaction().rollback();
                return "Erro: Tarefa não encontrada.";
            }
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return "Erro ao excluir a tarefa: " + e.getMessage();
        }
    }

    public String editarTarefa(int idEdicao, String novaDescricao, int novoStatus, LocalDate novaDataVencimento) {
        try {
            entityManager.getTransaction().begin();
            TarefasModel tarefa = entityManager.find(TarefasModel.class, idEdicao);

            if (tarefa != null) {
                tarefa.setDescricao(novaDescricao);
                tarefa.setStatus(novoStatus);
                tarefa.setDataVencimento(novaDataVencimento);

                entityManager.merge(tarefa);
                entityManager.getTransaction().commit();
                return "Tarefa editada com sucesso.";
            } else {
                return "Erro: Tarefa não encontrada.";
            }
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return "Erro ao editar a tarefa: " + e.getMessage();
        }
    }
}
