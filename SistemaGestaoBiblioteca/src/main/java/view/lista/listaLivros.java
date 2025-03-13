package view.lista;


import controller.LivrosController;
import model.LivrosModel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class listaLivros extends JFrame {
    private LivrosController livroController = new LivrosController();
    private JTextField textField;
    private JButton buttonBuscar;
    private JTable tableBuscarLivros;
    private JButton removerButton;
    private JScrollPane scrollPaneLivros;
    private JPanel panelPrincipal;

    public listaLivros() {
        this.setTitle("Lista de Livros");
        LivroModeloDeTabela livroModeloDeTabela = new LivroModeloDeTabela();
        tableBuscarLivros.setModel(livroModeloDeTabela);
        tableBuscarLivros.setAutoCreateRowSorter(true);
        this.setContentPane(panelPrincipal);
        this.setSize(640, 480);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);

        buttonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String busca = textField.getText();
                LivroModeloDeTabela modeloFiltrado = new LivroModeloDeTabela(busca);
                tableBuscarLivros.setModel(modeloFiltrado);
            }
        });

        removerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = tableBuscarLivros.getSelectedRow();
                if (linhaSelecionada != -1) {
                    Integer idLivroSelecionado = Integer.parseInt(tableBuscarLivros.getValueAt(linhaSelecionada, 0).toString());
                    try {
                        String mensagem = livroController.remover(idLivroSelecionado);
                        JOptionPane.showMessageDialog(null, mensagem);

                        LivroModeloDeTabela modeloAtualizado = new LivroModeloDeTabela();
                        tableBuscarLivros.setModel(modeloAtualizado);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao remover o livro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione o registro que deseja remover");
                }
            }
        });
    }

    private static class LivroModeloDeTabela extends AbstractTableModel {
        private LivrosController livroController = new LivrosController();
        private final String[] colunas = new String[]{"Id", "Título", "Tema", "Autor", "ISBN", "Data Publicação", "Quantidade Disponível"};
        private List<LivrosModel> listaDeLivros;

        public LivroModeloDeTabela() {
            listaDeLivros = buscarLivros(null);
        }

        public LivroModeloDeTabela(String busca) {
            listaDeLivros = buscarLivros(busca);
        }

        private List<LivrosModel> buscarLivros(String filtro) {
            try {
                if (filtro == null || filtro.isEmpty()) {
                    return livroController.buscarTodos();
                } else {
                    return livroController.buscarTodos().stream()
                            .filter(livro -> livro.getTitulo().toLowerCase().contains(filtro.toLowerCase()))
                            .toList();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao buscar livros: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return List.of();
            }
        }

        @Override
        public int getRowCount() {
            return listaDeLivros.size();
        }

        @Override
        public int getColumnCount() {
            return colunas.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            LivrosModel livro = listaDeLivros.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> livro.getId();
                case 1 -> livro.getTitulo();
                case 2 -> livro.getTema();
                case 3 -> livro.getAutor();
                case 4 -> livro.getIsbn();
                case 5 -> livro.getDataPublicacao();
                case 6 -> livro.getQuantidadeDisponivel();
                default -> "-";
            };
        }

        @Override
        public String getColumnName(int columnIndex) {
            return colunas[columnIndex];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (getRowCount() > 0) {
                Object value = getValueAt(0, columnIndex);
                return value != null ? value.getClass() : Object.class;
            }
            return Object.class;
        }
    }


    public static void main(String[] args) {
        new listaLivros();
    }
}
