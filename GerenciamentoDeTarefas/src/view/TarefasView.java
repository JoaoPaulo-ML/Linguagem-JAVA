package view;

import java.util.List;
import java.util.Scanner;
import model.TarefasModel;
import java.time.LocalDate;
import java.sql.SQLException;
import controller.TarefasController;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TarefasView {

    TarefasController tarefasController = new TarefasController();

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        TarefasView view = new TarefasView();

        int menu;
        do {
            System.out.println("\n0- Sair \n1- Cadastrar Tarefas \n2- Listar Tarefas \n3- Excluir Tarefas \n4- Editar Tarefas");
            menu = scanner.nextInt();
            scanner.nextLine();

            switch (menu) {
                case 1:
                    view.cadastrarTarefas(scanner);
                    break;
                case 2:
                    view.listarTarefas();
                    break;
                case 3:
                    view.excluirTarefas(scanner);
                    break;
                case 4:
                    view.editarTarefas(scanner);
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (menu != 0);
    }

    public void cadastrarTarefas(Scanner scanner) throws SQLException {

        TarefasModel tarefas = new TarefasModel();
        TarefasController tarefasController = new TarefasController();

        System.out.print("Digite a descrição da tarefa: ");
        String descricao = scanner.nextLine();
        tarefas.setDescricao(descricao);

        while (true) {
            System.out.println("Digite o status da tarefa: \n1: Pendente \n2: Em andamento \n3: Concluído");
            int status = scanner.nextInt();
            scanner.nextLine();

            if (status >= 1 && status <= 3) {
                tarefas.setStatus(status);
                break;
            } else {
                System.out.println("Erro! Status inválido. Tente novamente.");
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dataVencimento;
        while (true) {
            System.out.print("Digite a data de vencimento (DD-MM-YYYY): ");
            String dataStr = scanner.nextLine().trim();

            if (dataStr.isEmpty()) {
                System.out.println("Erro: A data não pode estar vazia.");
                continue;
            }

            try {
                dataVencimento = LocalDate.parse(dataStr, formatter);
                tarefas.setDataVencimento(dataVencimento);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Erro: Formato inválido! Use DD-MM-YYYY.");
            }
        }
        tarefasController.salvar(tarefas);
    }

    public void listarTarefas() {
        List<TarefasModel> lista = tarefasController.listarTarefas();

        if (lista.isEmpty()) {
            System.out.println("\nNenhuma tarefa encontrada.");
        } else {
            System.out.println("\nLista de Tarefas:");
            for (TarefasModel tarefa : lista) {
                System.out.println("ID: " + tarefa.getId() +
                        " | Descrição: " + tarefa.getDescricao() +
                        " | Status: " + TarefasController.descricaoStatus(tarefa.getStatus()) +
                        " | Data de Vencimento: " + tarefa.getDataVencimento());
            }
        }
    }

    public void excluirTarefas(Scanner scanner){

        System.out.print("Digite o ID da tarefa que deseja excluir: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        String resultado = tarefasController.excluirTarefa(id);
        System.out.println(resultado);
    }

    public void editarTarefas(Scanner scanner){

        System.out.print("Digite o ID da tarefa que deseja editar: ");
        int idEdicao = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite a nova descrição da tarefa: ");
        String descricao = scanner.nextLine();

        int status;
        while (true) {
            System.out.println("Digite o novo status da tarefa: \n1: Pendente \n2: Em andamento \n3: Concluído");
            status = scanner.nextInt();
            scanner.nextLine();

            if (status >= 1 && status <= 3) {
                break;
            } else {
                System.out.println("Erro! Status inválido. Tente novamente.");
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dataVencimento;
        while (true) {
            System.out.print("Digite a nova data de vencimento (DD-MM-YYYY): ");
            String dataStr = scanner.nextLine().trim();

            if (dataStr.isEmpty()) {
                System.out.println("Erro: A data não pode estar vazia.");
                continue;
            }

            try {
                dataVencimento = LocalDate.parse(dataStr, formatter);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Erro: Formato inválido! Use DD-MM-YYYY.");
            }
        }

        String resultadoEdicao = tarefasController.editarTarefa(idEdicao, descricao, status, dataVencimento);
        System.out.println(resultadoEdicao);
    }
}
