package controller;

import model.EmprestimoModel;
import model.LivrosModel;
import model.UsuariosModel;
import repository.EmprestimoRepository;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EmprestimoController {
    private EmprestimoRepository emprestimoRepository = EmprestimoRepository.getInstance();

    public String salvar(EmprestimoModel emprestimo) throws SQLException {
        return emprestimoRepository.salvar(emprestimo);
    }

    public String atualizar(EmprestimoModel emprestimo) throws SQLException {
        return emprestimoRepository.atualizar(emprestimo);
    }

    public List<EmprestimoModel> buscarTodos() throws SQLException {
        return emprestimoRepository.buscarTodos();
    }

    public EmprestimoModel buscarPorId(int id) throws SQLException {
        return emprestimoRepository.buscarPorId(id);
    }


    public static EmprestimoModel cadastrarEmprestimo(LivrosModel livro, UsuariosModel usuario, String dataEmprestimoStr, String dataPrevistaDevolucaoStr) {
        if (livro == null || usuario == null) {
            throw new IllegalArgumentException("Livro e usuário são obrigatórios para o empréstimo.");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dataEmprestimo;
        Date dataPrevistaDevolucao;

        try {
            dataEmprestimo = sdf.parse(dataEmprestimoStr);
            dataPrevistaDevolucao = sdf.parse(dataPrevistaDevolucaoStr);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Formato de data inválido! Use dd/MM/yyyy.");
        }

        EmprestimoModel emprestimo = new EmprestimoModel();
        emprestimo.setLivro(livro);
        emprestimo.setUsuario(usuario);
        emprestimo.setDataEmprestimo(dataEmprestimo);
        emprestimo.setDataPrevistaDevolucao(dataPrevistaDevolucao);

        return emprestimo;
    }
}
