package controller;

import model.LivrosModel;
import repository.LivrosRepository;

import java.sql.SQLException;
import java.util.List;

public class LivrosController {
    private LivrosRepository livrosRepository = LivrosRepository.getInstance();

    public String salvar(LivrosModel livro) throws SQLException {
        return livrosRepository.salvar(livro);
    }

    public String atualizar(LivrosModel livro) throws SQLException {
        return livrosRepository.atualizar(livro);
    }

    public List<LivrosModel> buscarTodos() throws SQLException {
        return livrosRepository.buscarTodos();
    }

    public String remover(int idLivroSelecionado) throws SQLException {
        LivrosModel livro = livrosRepository.buscarPorId(idLivroSelecionado);
        if (livro != null) {
            return livrosRepository.remover(livro);
        } else {
            return "Livro não encontrado!";
        }
    }

    public LivrosModel buscarPorId(int id) {
        return livrosRepository.buscarPorId(id);
    }
}
