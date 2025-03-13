package view.cadastro;

import controller.EmprestimoController;
import model.EmprestimoModel;
import view.lista.listaEmprestimo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class cadastrarDevolucao extends JFrame {
    private JTextField textFieldIdEmprestimo;
    private JTextField textFieldDataDevolucao;
    private JButton buttonEnviar;
    private JTable tableEmprestimo;
    private JScrollPane JscrollpaneEmprestimo;
    private EmprestimoController emprestimoController;

    public cadastrarDevolucao() {
        this.setTitle("Editar Devolução");
        this.setSize(800, 500);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());

        emprestimoController = new EmprestimoController();

        JPanel panelForm = new JPanel();
        panelForm.setLayout(new GridLayout(3, 2, 5, 5));

        panelForm.add(new JLabel("ID Empréstimo:"));
        textFieldIdEmprestimo = new JTextField();
        panelForm.add(textFieldIdEmprestimo);

        panelForm.add(new JLabel("Nova Data de Devolução (YYYY-MM-DD):"));
        textFieldDataDevolucao = new JTextField();
        panelForm.add(textFieldDataDevolucao);

        buttonEnviar = new JButton("Atualizar");
        buttonEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarEmprestimo();
            }
        });
        panelForm.add(buttonEnviar);

        this.add(panelForm, BorderLayout.NORTH);

        tableEmprestimo = new JTable(new listaEmprestimo.EmprestimoModeloDeTabela());
        JscrollpaneEmprestimo = new JScrollPane(tableEmprestimo);
        this.add(JscrollpaneEmprestimo, BorderLayout.CENTER);

        this.setVisible(true);
    }

    private void atualizarEmprestimo() {
        try {
            int idEmprestimo = Integer.parseInt(textFieldIdEmprestimo.getText());
            String dataDevolucaoStr = textFieldDataDevolucao.getText();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dataDevolvida = sdf.parse(dataDevolucaoStr);

            EmprestimoModel emprestimo = emprestimoController.buscarPorId(idEmprestimo);

            if (emprestimo == null) {
                JOptionPane.showMessageDialog(this, "Empréstimo não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            emprestimo.setDataDevolvida(dataDevolvida);
            String mensagem = emprestimoController.atualizar(emprestimo);

            JOptionPane.showMessageDialog(this, mensagem);
            this.dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de data inválido! Use YYYY-MM-DD", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new cadastrarDevolucao();
    }
}
