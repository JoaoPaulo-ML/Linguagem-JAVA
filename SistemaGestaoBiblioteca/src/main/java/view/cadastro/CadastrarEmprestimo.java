package view.cadastro;

import controller.EmprestimoController;
import controller.LivrosController;
import controller.UsuarioController;
import model.EmprestimoModel;
import model.LivrosModel;
import model.UsuariosModel;
import repository.EmprestimoRepository;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class CadastrarEmprestimo extends JFrame {
    private UsuarioController usuarioController = new UsuarioController();
    private LivrosController livroController = new LivrosController();

    private JPanel panelEmprestimo;
    private JTextField textFieldIdLivro;
    private JTextField textFieldIdUsuario;

    private JTextField textFieldDataEmprestimo;
    private JTextField textFieldDataPrevistaDevolucao;
    private JTextField textFieldDataEntrega;

    private JButton btnSalvar;
    private JTable tableUsuarios;
    private JScrollPane scrollPaneUsuarios;
    private JTable tableLivros;
    private JScrollPane scrollPaneLivros;


    public CadastrarEmprestimo() {
        this.setTitle("Cadastrar Empréstimo");
        tableUsuarios.setModel(new UsuarioModeloDeTabela());
        tableLivros.setModel(new LivroModeloDeTabela());
        tableUsuarios.setAutoCreateRowSorter(true);
        tableLivros.setAutoCreateRowSorter(true);
        this.setSize(640, 480);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setContentPane(panelEmprestimo);
        this.setVisible(true);

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarEmprestimo();
            }
        });
    }

    private void salvarEmprestimo() {
        try {
            int idLivro = Integer.parseInt(textFieldIdLivro.getText());
            int idUsuario = Integer.parseInt(textFieldIdUsuario.getText());
            String dataEmprestimo = textFieldDataEmprestimo.getText();
            String dataPrevistaDevolucao = textFieldDataPrevistaDevolucao.getText();
            String dataDevolvida = textFieldDataEntrega.getText();

            LivrosModel livro = new LivrosModel();
            livro.setId(idLivro);

            UsuariosModel usuario = new UsuariosModel();
            usuario.setId(idUsuario);


            EmprestimoModel emprestimo = EmprestimoController.cadastrarEmprestimo(livro, usuario, dataEmprestimo, dataPrevistaDevolucao);


            EmprestimoRepository repository = EmprestimoRepository.getInstance();
            String mensagem = repository.salvar(emprestimo);

            JOptionPane.showMessageDialog(this, mensagem);
            limparCampos();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar empréstimo: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        textFieldIdLivro.setText("");
        textFieldIdUsuario.setText("");
        textFieldDataEmprestimo.setText("");
        textFieldDataPrevistaDevolucao.setText("");
    }


    private static class UsuarioModeloDeTabela extends AbstractTableModel {
        private UsuarioController usuarioController = new UsuarioController();
        private final String[] colunas = {"Id", "Nome", "Email", "Sexo", "Número"};
        private List<UsuariosModel> listaDeUsuarios;

        public UsuarioModeloDeTabela() {
            listaDeUsuarios = buscarUsuarios();
        }

        private List<UsuariosModel> buscarUsuarios() {
            try {
                return usuarioController.buscarTodos();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao buscar usuários: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return List.of();
            }
        }

        @Override
        public int getRowCount() {
            return listaDeUsuarios.size();
        }

        @Override
        public int getColumnCount() {
            return colunas.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            UsuariosModel usuario = listaDeUsuarios.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> usuario.getId();
                case 1 -> usuario.getNome();
                case 2 -> usuario.getEmail();
                case 3 -> usuario.getSexo();
                case 4 -> usuario.getNumero();
                default -> "-";
            };
        }

        @Override
        public String getColumnName(int columnIndex) {
            return colunas[columnIndex];
        }
    }

    private static class LivroModeloDeTabela extends AbstractTableModel {
        private LivrosController livroController = new LivrosController();
        private final String[] colunas = {"Id", "Título", "Tema", "Autor", "ISBN", "Data Publicação", "Quantidade Disponível"};
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
    }

    public static void main(String[] args) {
        new CadastrarEmprestimo();
    }
}
