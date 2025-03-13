package view.lista;

import controller.EmprestimoController;
import model.EmprestimoModel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.sql.SQLException;
import java.util.List;

public class listaDevolucao extends JFrame{
    private JTable tableList;
    private JScrollPane Jcrollpane;

    public listaDevolucao() {
        this.setTitle("Lista de Devoluções");
        tableList = new JTable(new DevolucaoModeloDeTabela());
        tableList.setAutoCreateRowSorter(true);
        Jcrollpane = new JScrollPane(tableList);

        this.add(Jcrollpane);
        this.setSize(800, 600);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    public static class DevolucaoModeloDeTabela extends AbstractTableModel {
        private EmprestimoController emprestimoController = new EmprestimoController();
        private final String[] colunas = {"ID", "Livro", "Usuário", "Data Empréstimo", "Data Devolvida"};
        private List<EmprestimoModel> listaDeDevolucoes;

        public DevolucaoModeloDeTabela() {
            listaDeDevolucoes = buscarDevolucoes();
        }

        private List<EmprestimoModel> buscarDevolucoes() {
            try {
                return emprestimoController.buscarTodos()
                        .stream()
                        .filter(emprestimo -> emprestimo.getDataDevolvida() != null)
                        .toList();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao buscar devoluções: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return List.of();
            }
        }


        @Override
        public int getRowCount() {
            return listaDeDevolucoes.size();
        }

        @Override
        public int getColumnCount() {
            return colunas.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            EmprestimoModel emprestimo = listaDeDevolucoes.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> emprestimo.getIdEmprestimo();
                case 1 -> emprestimo.getLivro().getTitulo();
                case 2 -> emprestimo.getUsuario().getNome();
                case 3 -> emprestimo.getDataEmprestimo();
                case 4 -> emprestimo.getDataDevolvida(); // Exibir a data de devolução
                default -> "-";
            };
        }

        @Override
        public String getColumnName(int columnIndex) {
            return colunas[columnIndex];
        }
    }

    public static void main(String[] args) {
        new listaDevolucao();
    }
}
