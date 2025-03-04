package controller;

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
}
