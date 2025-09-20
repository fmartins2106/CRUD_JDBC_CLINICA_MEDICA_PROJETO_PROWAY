import controller.MedicoController;
import controller.PacienteController;
import model.Especialidade;
import model.Medico;
import model.Paciente;
import model.Convenio;

import javax.swing.*;
import java.util.List;

public class main {

    public static void main(String[] args) throws Exception {


        int opcaoPrincipal = -1;

        while (opcaoPrincipal != 0) {
            String menuPrincipal = """
                    *** Menu Principal ***
                    [1] Gerenciar Pacientes
                    [2] Gerenciar Médicos
                    [0] Sair
                    
                    Escolha uma opção:
                    """;
            try {
                opcaoPrincipal = Integer.parseInt(JOptionPane.showInputDialog(menuPrincipal));
                switch (opcaoPrincipal) {
                    case 1 -> menuPaciente();
                    case 2 -> menuMedico();
                    case 0 -> JOptionPane.showMessageDialog(null, "Saindo do sistema...");
                    default -> JOptionPane.showMessageDialog(null, "Opção inválida!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Entrada inválida. Digite um número.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
            }
        }
    }


//    -------------------------------------------------------------------------------------------
//    PACIENTE

    public static void menuPaciente() throws Exception {
        PacienteController controller = new PacienteController();
        int opcao = -1;

        while (opcao != 0) {
            String menu = """
                *** Menu Pacientes ***
                [1] Cadastrar paciente
                [2] Listar todos
                [3] Buscar por ID
                [4] Excluir
                [5] Alterar
                [0] Voltar
                
                Escolha uma opção:
                """;

            try {
                opcao = Integer.parseInt(JOptionPane.showInputDialog(menu));

                switch (opcao) {
                    case 1 -> {
                        String nome = JOptionPane.showInputDialog("Nome do paciente:");
                        int idade = Integer.parseInt(JOptionPane.showInputDialog("Idade do paciente:"));
                        String telefone = JOptionPane.showInputDialog("Telefone do paciente:");
                        String tipoSanguineo = JOptionPane.showInputDialog("Tipo sanguíneo:");
                        boolean doador = JOptionPane.showConfirmDialog(null, "O paciente é doador de órgãos?", "Doador", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

                        // Selecionar convênio
                        Convenio[] convenios = Convenio.values();
                        StringBuilder menuConvenio = new StringBuilder("Escolha o convênio:\n");
                        for (int i = 0; i < convenios.length; i++) {
                            menuConvenio.append("[").append(i + 1).append("] ").append(convenios[i].name()).append("\n");
                        }
                        int opcaoConvenio = Integer.parseInt(JOptionPane.showInputDialog(menuConvenio.toString()));
                        Convenio convenioEscolhido = (opcaoConvenio >= 1 && opcaoConvenio <= convenios.length)
                                ? convenios[opcaoConvenio - 1] : null;

                        Paciente paciente = new Paciente();
                        paciente.setNome(nome);
                        paciente.setIdade(idade);
                        paciente.setTelefone(telefone);
                        paciente.setTipoSanguineo(tipoSanguineo);
                        paciente.setDoador(doador);
                        paciente.setConvenio(convenioEscolhido);

                        String mensagem = controller.insert(paciente);
                        JOptionPane.showMessageDialog(null, mensagem);
                    }

                    case 2 -> {
                        List<Paciente> pacientes = controller.getAll();
                        controller.printFormatedList(pacientes);
                    }

                    case 3 -> {
                        int idBuscar = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do paciente:"));
                        Paciente paciente = controller.getById(idBuscar);

                        if (paciente != null) {
                            JOptionPane.showMessageDialog(null,
                                    "*** Paciente Encontrado ***\n" +
                                            "\nID: " + paciente.getId() +
                                            "\nNome: " + paciente.getNome() +
                                            "\nIdade: " + paciente.getIdade() +
                                            "\nTelefone: " + paciente.getTelefone() +
                                            "\nTipo sanguíneo: " + paciente.getTipoSanguineo() +
                                            "\nDoador: " + (paciente.isDoador() ? "Sim" : "Não") +
                                            "\nConvênio: " + (paciente.getConvenio() != null ? paciente.getConvenio().name() : "Nenhum"));
                        } else {
                            JOptionPane.showMessageDialog(null, "Paciente não encontrado.");
                        }
                    }

                    case 4 -> {
                        int idExcluir = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do paciente para excluir:"));
                        Paciente paciente = controller.getById(idExcluir);

                        if (paciente != null) {
                            int confirma = JOptionPane.showConfirmDialog(null,
                                    "Deseja realmente excluir o paciente " + paciente.getNome() + "?",
                                    "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
                            if (confirma == JOptionPane.YES_OPTION) {
                                controller.delete(idExcluir);
                                JOptionPane.showMessageDialog(null, "Paciente excluído com sucesso.");
                            } else {
                                JOptionPane.showMessageDialog(null, "Exclusão cancelada.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Paciente não encontrado.");
                        }
                    }

                    case 5 -> {
                        int idAlterar = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do paciente para alterar:"));
                        Paciente paciente = controller.getById(idAlterar);

                        if (paciente != null) {
                            String novoNome = JOptionPane.showInputDialog("Nome:", paciente.getNome());
                            int novaIdade = Integer.parseInt(JOptionPane.showInputDialog("Idade:", paciente.getIdade()));
                            String novoTelefone = JOptionPane.showInputDialog("Telefone:", paciente.getTelefone());
                            String novoTipoSanguineo = JOptionPane.showInputDialog("Tipo sanguíneo:", paciente.getTipoSanguineo());
                            boolean novoDoador = JOptionPane.showConfirmDialog(null, "O paciente é doador de órgãos?", "Doador", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

                            // Convênio
                            Convenio[] convenios = Convenio.values();
                            StringBuilder menuConvenio = new StringBuilder("Escolha o novo convênio:\n");
                            for (int i = 0; i < convenios.length; i++) {
                                menuConvenio.append("[").append(i + 1).append("] ").append(convenios[i].name()).append("\n");
                            }
                            int opcaoConvenio = Integer.parseInt(JOptionPane.showInputDialog(menuConvenio.toString()));
                            Convenio convenioEscolhido = (opcaoConvenio >= 1 && opcaoConvenio <= convenios.length)
                                    ? convenios[opcaoConvenio - 1] : null;

                            paciente.setNome(novoNome);
                            paciente.setIdade(novaIdade);
                            paciente.setTelefone(novoTelefone);
                            paciente.setTipoSanguineo(novoTipoSanguineo);
                            paciente.setDoador(novoDoador);
                            paciente.setConvenio(convenioEscolhido);

                            controller.update(paciente);
                            JOptionPane.showMessageDialog(null, "Paciente alterado com sucesso.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Paciente não encontrado.");
                        }
                    }

                    case 0 -> JOptionPane.showMessageDialog(null, "Voltando ao menu principal...");

                    default -> JOptionPane.showMessageDialog(null, "Opção inválida!");
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Entrada inválida. Digite um número válido.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
            }
        }
    }



//    ---------------------------------------------------------------------------------------
//medico

public static void menuMedico() throws Exception {
    MedicoController controller = new MedicoController();
    int opcao = -1;

    while (opcao != 0) {
        String menu = """
                    *** Menu Médicos ***
                    [1] Cadastrar médico
                    [2] Listar todos
                    [3] Buscar por ID
                    [4] Excluir
                    [5] Alterar
                    [0] Voltar
                    
                    Escolha uma opção:
                    """;
        opcao = Integer.parseInt(JOptionPane.showInputDialog(menu));

        switch (opcao) {
            case 1 -> {
                // Cadastrar médico
                String nome = JOptionPane.showInputDialog("Nome do médico:");
                int idade = Integer.parseInt(JOptionPane.showInputDialog("Idade do médico:"));
                String telefone = JOptionPane.showInputDialog("Telefone do médico:");
                String crm = JOptionPane.showInputDialog("CRM do médico:");

                // Escolher especialidade
                Especialidade[] especialidades = Especialidade.values();
                StringBuilder menuEspecialidade = new StringBuilder("Escolha a especialidade:\n");
                for (int i = 0; i < especialidades.length; i++) {
                    menuEspecialidade.append("[").append(i + 1).append("] ").append(especialidades[i].name()).append("\n");
                }
                int opcaoEspecialidade = Integer.parseInt(JOptionPane.showInputDialog(menuEspecialidade.toString()));
                Especialidade especialidadeEscolhida = especialidades[opcaoEspecialidade - 1];

                // Escolher convênio
                Convenio[] convenios = Convenio.values();
                StringBuilder menuConvenio = new StringBuilder("Escolha o convênio:\n");
                for (int i = 0; i < convenios.length; i++) {
                    menuConvenio.append("[").append(i + 1).append("] ").append(convenios[i].name()).append("\n");
                }
                int opcaoConvenio = Integer.parseInt(JOptionPane.showInputDialog(menuConvenio.toString()));
                Convenio convenioEscolhido = convenios[opcaoConvenio - 1];

                // Criar objeto médico
                Medico medico = new Medico();
                medico.setNome(nome);
                medico.setIdade(idade);
                medico.setTelefone(telefone);
                medico.setCrm(crm);
                medico.setEspecialidade(especialidadeEscolhida);
                medico.setConvenio(convenioEscolhido);

                String msg = controller.insert(medico);
                JOptionPane.showMessageDialog(null, msg);
            }

            case 2 -> {
                List<Medico> medicos = controller.getAll();
                controller.printFormatedList(medicos);
            }

            case 3 -> {
                int idBuscar = Integer.parseInt(JOptionPane.showInputDialog("ID do médico:"));
                Medico medico = controller.getById(idBuscar);
                if (medico != null) {
                    JOptionPane.showMessageDialog(null, "Médico encontrado:\n" + medico);
                } else {
                    JOptionPane.showMessageDialog(null, "Médico não encontrado.");
                }
            }

            case 4 -> {
                int idExcluir = Integer.parseInt(JOptionPane.showInputDialog("ID do médico para excluir:"));
                controller.delete(idExcluir);
                JOptionPane.showMessageDialog(null, "Médico excluído.");
            }

            case 5 -> {
                // Alterar médico (semelhante ao alterar paciente)
            }

            case 0 -> JOptionPane.showMessageDialog(null, "Voltando ao menu principal...");
            default -> JOptionPane.showMessageDialog(null, "Opção inválida!");
        }
    }
}

}
