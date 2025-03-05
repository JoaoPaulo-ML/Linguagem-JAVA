package controller;

import java.time.LocalDate;
import java.util.List;
import model.TarefasModel;
import java.sql.SQLException;
import repository.TarefasRepository;

public class TarefasController {
    TarefasRepository tarefasRepository = new TarefasRepository();

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

    public String excluirTarefa(int id) {
        return tarefasRepository.excluirTarefa(id);
    }

    public String editarTarefa(int idEdicao, String novaDescricao, int novoStatus, LocalDate novaDataVencimento) {
        return tarefasRepository.editarTarefa(idEdicao, novaDescricao, novoStatus, novaDataVencimento);
    }
}
