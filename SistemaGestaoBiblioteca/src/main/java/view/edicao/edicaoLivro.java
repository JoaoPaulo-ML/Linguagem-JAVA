package view.edicao;

import controller.LivrosController;
import model.LivrosModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class edicaoLivro extends JFrame {
    private JTextField textFieldId;
    private JTextField textFieldTema;
    private JTextField textFieldAutor;
    private JTextField textFieldIsbn;
    private JTextField textFieldData;
    private JTextField textFieldQuantidade;
    private JTable tableEditar;
    private JPanel JpanelPrincipal;
    private JButton buttonEnviar;
    private LivrosController livroController;

    public edicaoLivro() {
        this.setTitle("Edição de Livros");
        this.setSize(640, 480);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        livroController = new LivrosController();
        JpanelPrincipal = new JPanel();
        JpanelPrincipal.setLayout(new BoxLayout(JpanelPrincipal, BoxLayout.Y_AXIS));

        tableEditar = new JTable(new LivroModeloDeTabela());
        JScrollPane scrollPane = new JScrollPane(tableEditar);
        JpanelPrincipal.add(scrollPane);

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));

        formPanel.add(new JLabel("ID:"));
        textFieldId = new JTextField();
        formPanel.add(textFieldId);

        formPanel.add(new JLabel("Tema:"));
        textFieldTema = new JTextField();
        formPanel.add(textFieldTema);

        formPanel.add(new JLabel("Autor:"));
        textFieldAutor = new JTextField();
        formPanel.add(textFieldAutor);

        formPanel.add(new JLabel("ISBN:"));
        textFieldIsbn = new JTextField();
        formPanel.add(textFieldIsbn);

        formPanel.add(new JLabel("Data:"));
        textFieldData = new JTextField();
        formPanel.add(textFieldData);

        formPanel.add(new JLabel("Quantidade:"));
        textFieldQuantidade = new JTextField();
        formPanel.add(textFieldQuantidade);

        JpanelPrincipal.add(formPanel);

        buttonEnviar = new JButton("Salvar");
        buttonEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarEdicao();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(buttonEnviar);
        JpanelPrincipal.add(buttonPanel);

        this.setContentPane(JpanelPrincipal);
        this.setVisible(true);
    }

    private void salvarEdicao() {
        try {
            int id = Integer.parseInt(textFieldId.getText());
            LivrosModel livro = new LivrosModel();
            livro.setId(id);
            livro.setTema(textFieldTema.getText());
            livro.setAutor(textFieldAutor.getText());
            livro.setIsbn(textFieldIsbn.getText());

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date dataPublicacao = sdf.parse(textFieldData.getText());
                livro.setDataPublicacao(dataPublicacao);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Data inválida! Use o formato dd/MM/yyyy.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            livro.setQuantidadeDisponivel(Integer.parseInt(textFieldQuantidade.getText()));

            String mensagem = livroController.atualizar(livro);
            JOptionPane.showMessageDialog(this, mensagem);

            tableEditar.setModel(new LivroModeloDeTabela());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID ou Quantidade inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar edição: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static class LivroModeloDeTabela extends AbstractTableModel {
        private LivrosController livroController = new LivrosController();
        private final String[] colunas = new String[]{"Id", "Tema", "Autor", "ISBN", "Data", "Quantidade"};
        private List<LivrosModel> listaDeLivros;

        public LivroModeloDeTabela() {
            listaDeLivros = buscarLivros();
        }

        private List<LivrosModel> buscarLivros() {
            try {
                return livroController.buscarTodos();
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
                case 1 -> livro.getTema();
                case 2 -> livro.getAutor();
                case 3 -> livro.getIsbn();
                case 4 -> livro.getDataPublicacao();
                case 5 -> livro.getQuantidadeDisponivel();
                default -> "-";
            };
        }

        @Override
        public String getColumnName(int columnIndex) {
            return colunas[columnIndex];
        }
    }

    public static void main(String[] args) {
        new edicaoLivro();
    }
}
