package view.lista;

import controller.UsuarioController;
import model.UsuariosModel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class listaUsuario extends JFrame {
    private UsuarioController usuarioController = new UsuarioController();
    private JTextField textFieldBusca;
    private JButton buttonBuscar;
    private JTable tableBuscaUsuario;
    private JButton removerButton;
    private JPanel panelPrincipal;
    private JScrollPane scrollPaneUsuario;


    public listaUsuario() {
        this.setTitle("Lista de Usuários");
        UsuarioModeloDeTabela usuarioModeloDeTabela = new UsuarioModeloDeTabela();
        tableBuscaUsuario.setModel(usuarioModeloDeTabela);
        tableBuscaUsuario.setAutoCreateRowSorter(true);
        this.setContentPane(panelPrincipal);
        this.setSize(640, 480);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);


        buttonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String busca = textFieldBusca.getText();
                UsuarioModeloDeTabela modeloFiltrado = new UsuarioModeloDeTabela(busca);
                tableBuscaUsuario.setModel(modeloFiltrado);
            }
        });


        removerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = tableBuscaUsuario.getSelectedRow();
                if (linhaSelecionada != -1) {
                    Integer idUsuarioSelecionado = Integer.parseInt(tableBuscaUsuario.getValueAt(linhaSelecionada, 0).toString());
                    try {
                        String mensagem = usuarioController.remover(idUsuarioSelecionado);
                        JOptionPane.showMessageDialog(null, mensagem);


                        UsuarioModeloDeTabela modeloAtualizado = new UsuarioModeloDeTabela();
                        tableBuscaUsuario.setModel(modeloAtualizado);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao remover o usuário: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione o registro que deseja remover");
                }
            }
        });



    }

    private static class UsuarioModeloDeTabela extends AbstractTableModel {
        private UsuarioController usuarioController = new UsuarioController();
        private final String[] colunas = new String[]{"Id", "Nome", "Sexo", "Número", "Email"};
        private List<UsuariosModel> listaDeUsuarios;

        public UsuarioModeloDeTabela() {
            listaDeUsuarios = buscarUsuarios(null);
        }

        public UsuarioModeloDeTabela(String busca) {
            listaDeUsuarios = buscarUsuarios(busca);
        }

        private List<UsuariosModel> buscarUsuarios(String filtro) {
            try {
                if (filtro == null || filtro.isEmpty()) {
                    return usuarioController.buscarTodos();
                } else {

                    return usuarioController.buscarTodos().stream()
                            .filter(usuario -> usuario.getNome().toLowerCase().contains(filtro.toLowerCase()))
                            .toList();
                }
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
                case 2 -> usuario.getSexo();
                case 3 -> usuario.getNumero();
                case 4 -> usuario.getEmail();
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
        new listaUsuario();
    }
}
