package controller;

import java.util.List;
import model.TarefasModel;
import java.util.ArrayList;
import java.time.LocalDate;
import java.sql.SQLException;
import repository.TarefasRepository;

public class TarefasController {
    TarefasRepository tarefasRepository = new TarefasRepository();
    TarefasModel tarefasModel = new TarefasModel();

    public String salvar(TarefasModel tarefas) throws SQLException {
        return tarefasRepository.salvar(tarefas);
    }

    public List<TarefasModel> listarTarefas() {
        return tarefasRepository.listarTarefas();
    }

    public static String descricaoStatus(int status) {
        return switch (status) {
            case 1 -> "Pendente";
            case 2 -> "Em andamento";
            case 3 -> "Concluído";
            default -> "Desconhecido";
        };
    }

    public List<TarefasModel> filtrarTarefasPorStatus(int status) {
        List<TarefasModel> todasTarefas = tarefasRepository.listarTarefas();
        List<TarefasModel> tarefasFiltradas = new ArrayList<>();

        for (TarefasModel tarefa : todasTarefas) {
            if (tarefa.getStatus() == status) {
                tarefasFiltradas.add(tarefa);
            }
        }

        return tarefasFiltradas;
    }

    public String excluirTarefa(int id) {
        return tarefasRepository.excluirTarefa(id);
    }

    public String editarTarefa(int idEdicao, String novaDescricao, int novoStatus, LocalDate novaDataVencimento) {
        return tarefasRepository.editarTarefa(idEdicao, novaDescricao, novoStatus, novaDataVencimento);
    }
}
