package view.lista;

import controller.EmprestimoController;
import model.EmprestimoModel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.sql.SQLException;
import java.util.List;

public class listaEmprestimo extends JFrame{
    private JTable tableList;
    private JScrollPane Jscrollpane;
    private JPanel JpanelPrincipal;

    public listaEmprestimo() {
        this.setTitle("Lista de Empréstimos");
        tableList = new JTable(new EmprestimoModeloDeTabela());
        tableList.setAutoCreateRowSorter(true);
        Jscrollpane = new JScrollPane(tableList);

        this.add(Jscrollpane);
        this.setSize(800, 600);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    public static class EmprestimoModeloDeTabela extends AbstractTableModel {
        private EmprestimoController emprestimoController = new EmprestimoController();
        private final String[] colunas = {"ID", "Livro", "Usuário", "Data Empréstimo", "Data Prevista Devolução"};
        private List<EmprestimoModel> listaDeEmprestimos;

        public EmprestimoModeloDeTabela() {
            listaDeEmprestimos = buscarEmprestimos();
        }

        private List<EmprestimoModel> buscarEmprestimos() {
            try {
                return emprestimoController.buscarTodos();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao buscar empréstimos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return List.of();
            }
        }

        @Override
        public int getRowCount() {
            return listaDeEmprestimos.size();
        }

        @Override
        public int getColumnCount() {
            return colunas.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            EmprestimoModel emprestimo = listaDeEmprestimos.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> emprestimo.getIdEmprestimo();
                case 1 -> emprestimo.getLivro().getTitulo();
                case 2 -> emprestimo.getUsuario().getNome();
                case 3 -> emprestimo.getDataEmprestimo();
                case 4 -> emprestimo.getDataPrevistaDevolucao();
                default -> "-";
            };
        }

        @Override
        public String getColumnName(int columnIndex) {
            return colunas[columnIndex];
        }
    }

    public static void main(String[] args) {
        new listaEmprestimo();
    }
}
