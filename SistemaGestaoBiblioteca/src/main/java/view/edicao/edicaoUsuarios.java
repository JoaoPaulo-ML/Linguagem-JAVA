package view.edicao;

import controller.UsuarioController;
import model.UsuariosModel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class edicaoUsuarios extends JFrame {
    private JTextField textFieldId;
    private JTextField textFieldNome;
    private JTextField textFieldSexo;
    private JTextField textFieldNumero;
    private JTextField textFieldEmail;
    private JTable tableList;
    private JPanel JpanelPrincipal;
    
    private JButton buttonAtualizar;
    private UsuarioController usuarioController;

    public edicaoUsuarios() {
        this.setTitle("Edição de Usuários");
        this.setSize(640, 480);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        usuarioController = new UsuarioController();
        JpanelPrincipal = new JPanel();
        JpanelPrincipal.setLayout(new BoxLayout(JpanelPrincipal, BoxLayout.Y_AXIS));


        tableList = new JTable(new UsuarioModeloDeTabela());
        JScrollPane scrollPane = new JScrollPane(tableList);
        JpanelPrincipal.add(scrollPane);


        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));

        formPanel.add(new JLabel("ID:"));
        textFieldId = new JTextField();
        formPanel.add(textFieldId);

        formPanel.add(new JLabel("Nome:"));
        textFieldNome = new JTextField();
        formPanel.add(textFieldNome);

        formPanel.add(new JLabel("Sexo:"));
        textFieldSexo = new JTextField();
        formPanel.add(textFieldSexo);

        formPanel.add(new JLabel("Número:"));
        textFieldNumero = new JTextField();
        formPanel.add(textFieldNumero);

        formPanel.add(new JLabel("Email:"));
        textFieldEmail = new JTextField();
        formPanel.add(textFieldEmail);

        JpanelPrincipal.add(formPanel);

        buttonAtualizar = new JButton("Salvar");
        buttonAtualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarEdicao();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(buttonAtualizar);
        JpanelPrincipal.add(buttonPanel);

        this.setContentPane(JpanelPrincipal);
        this.setVisible(true);
    }

//    private void buscarUsuario() {
//        try {
//            int id = Integer.parseInt(textFieldId.getText());
//            UsuariosModel usuario = usuarioController.buscarPorId(id);
//
//            if (usuario != null) {
//                textFieldNome.setText(usuario.getNome());
//                textFieldSexo.setText(usuario.getSexo());
//                textFieldNumero.setText(usuario.getNumero());
//                textFieldEmail.setText(usuario.getEmail());
//            } else {
//                JOptionPane.showMessageDialog(this, "Usuário não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
//            }
//        } catch (NumberFormatException e) {
//            JOptionPane.showMessageDialog(this, "ID inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
//        }
//    }

    private void salvarEdicao() {
        try {
            int id = Integer.parseInt(textFieldId.getText());
            UsuariosModel usuario = new UsuariosModel();
            usuario.setId(id);
            usuario.setNome(textFieldNome.getText());
            usuario.setSexo(textFieldSexo.getText());
            usuario.setNumeroCelular(textFieldNumero.getText());
            usuario.setEmail(textFieldEmail.getText());

            String mensagem = usuarioController.atualizar(usuario);
            JOptionPane.showMessageDialog(this, mensagem);

            tableList.setModel(new UsuarioModeloDeTabela());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar edição: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static class UsuarioModeloDeTabela extends AbstractTableModel {
        private UsuarioController usuarioController = new UsuarioController();
        private final String[] colunas = new String[]{"Id", "Nome", "Sexo", "Número", "Email"};
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
    }

    public static void main(String[] args) {
        new edicaoUsuarios();
    }
}
