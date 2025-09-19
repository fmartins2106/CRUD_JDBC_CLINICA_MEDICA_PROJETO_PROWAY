import controller.PacienteController;
import model.Paciente;
import model.Convenio;

import javax.swing.*;
import java.util.List;

public class main {

    public static void main(String[] args) throws Exception {

        PacienteController controller = new PacienteController();

        StringBuilder menu = new StringBuilder();
        menu.append("*** Menu do Sistema ***");
        menu.append("\n[1] Cadastrar paciente");
        menu.append("\n[2] Listar todos");
        menu.append("\n[3] Buscar por ID");
        menu.append("\n[5] Excluir");
        menu.append("\n[6] Alterar");
        menu.append("\n[0] Sair");
        menu.append("\n\nSelecione uma opção");

        int opcao = -1;

        while (opcao != 0) {
            try {
                opcao = Integer.parseInt(JOptionPane.showInputDialog(menu));

                switch (opcao) {
                    case 1:
                        // Cadastrar paciente
                        String nome = JOptionPane.showInputDialog("Nome do paciente");
                        int idade = Integer.parseInt(JOptionPane.showInputDialog("Idade do paciente"));
                        String telefone = JOptionPane.showInputDialog("Telefone do paciente");
                        String tipoSanguineo = JOptionPane.showInputDialog("Tipo sanguíneo do paciente");
                        boolean doador = JOptionPane.showConfirmDialog(null, "O paciente é doador de órgãos?", "Doador", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

                        // Mostrar opções de convenio do enum para o usuário escolher
                        Convenio[] convenios = Convenio.values();
                        StringBuilder convenioMenu = new StringBuilder("Escolha o convênio:\n");
                        for (int i = 0; i < convenios.length; i++) {
                            convenioMenu.append("[").append(i + 1).append("] ").append(convenios[i].name()).append("\n");
                        }
                        int opcaoConvenio = Integer.parseInt(JOptionPane.showInputDialog(convenioMenu.toString()));
                        Convenio convenioEscolhido = null;
                        if (opcaoConvenio >= 1 && opcaoConvenio <= convenios.length) {
                            convenioEscolhido = convenios[opcaoConvenio - 1];
                        }

                        Paciente novoPaciente = new Paciente();
                        novoPaciente.setNome(nome);
                        novoPaciente.setIdade(idade);
                        novoPaciente.setTelefone(telefone);
                        novoPaciente.setTipoSanguineo(tipoSanguineo);
                        novoPaciente.setDoador(doador);
                        novoPaciente.setConvenio(convenioEscolhido);

                        String mensagemInsert = controller.insert(novoPaciente);
                        JOptionPane.showMessageDialog(null, mensagemInsert);
                        break;

                    case 2:
                        // Listar todos pacientes
                        List<Paciente> listaTodosPacientes = controller.getAll();
                        controller.printFormatedList(listaTodosPacientes);
                        break;

                    case 3:
                        // Buscar por ID
                        int idBuscar = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID para buscar o paciente"));
                        Paciente pacienteEncontrado = controller.getById(idBuscar);
                        if (pacienteEncontrado != null) {
                            JOptionPane.showMessageDialog(null,
                                    "*** Paciente Encontrado! ***\n" +
                                            "\nID: " + pacienteEncontrado.getId() +
                                            "\nNome: " + pacienteEncontrado.getNome() +
                                            "\nIdade: " + pacienteEncontrado.getIdade() +
                                            "\nTelefone: " + pacienteEncontrado.getTelefone() +
                                            "\nTipo sanguíneo: " + pacienteEncontrado.getTipoSanguineo() +
                                            "\nDoador: " + (pacienteEncontrado.isDoador() ? "Sim" : "Não") +
                                            "\nConvênio: " + (pacienteEncontrado.getConvenio() != null ? pacienteEncontrado.getConvenio().name() : "Nenhum"));
                        } else {
                            JOptionPane.showMessageDialog(null, "Nenhum paciente encontrado com o ID " + idBuscar);
                        }
                        break;

                    case 4:
                        // Excluir paciente
                        int idDelete = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do paciente para excluir"));
                        Paciente pacienteParaExcluir = controller.getById(idDelete);
                        if (pacienteParaExcluir != null) {
                            int confirma = JOptionPane.showConfirmDialog(null,
                                    "Deseja realmente excluir o paciente " + pacienteParaExcluir.getNome() + "?",
                                    "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
                            if (confirma == JOptionPane.YES_OPTION) {
                                controller.delete(idDelete);
                                JOptionPane.showMessageDialog(null, "Paciente excluído com sucesso.");
                            } else {
                                JOptionPane.showMessageDialog(null, "Exclusão cancelada.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Paciente não encontrado para o ID " + idDelete);
                        }
                        break;

                    case 5:
                        // Alterar paciente
                        int idAlterar = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID para alterar o paciente"));
                        Paciente pacienteAlterar = controller.getById(idAlterar);

                        if (pacienteAlterar != null) {
                            String novoNome = JOptionPane.showInputDialog("Digite o novo nome", pacienteAlterar.getNome());
                            int novaIdade = Integer.parseInt(JOptionPane.showInputDialog("Digite a nova idade", pacienteAlterar.getIdade()));
                            String novoTelefone = JOptionPane.showInputDialog("Digite o novo telefone", pacienteAlterar.getTelefone());
                            String novoTipoSanguineo = JOptionPane.showInputDialog("Digite o novo tipo sanguíneo", pacienteAlterar.getTipoSanguineo());
                            boolean novoDoador = JOptionPane.showConfirmDialog(null, "O paciente é doador de órgãos?", "Doador", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

                            // Escolher convênio
                            Convenio[] conveniosAlt = Convenio.values();
                            StringBuilder convenioMenuAlt = new StringBuilder("Escolha o convênio:\n");
                            for (int i = 0; i < conveniosAlt.length; i++) {
                                convenioMenuAlt.append("[").append(i + 1).append("] ").append(conveniosAlt[i].name()).append("\n");
                            }
                            int opcaoConvenioAlt = Integer.parseInt(JOptionPane.showInputDialog(convenioMenuAlt.toString()));
                            Convenio convenioEscolhidoAlt = null;
                            if (opcaoConvenioAlt >= 1 && opcaoConvenioAlt <= conveniosAlt.length) {
                                convenioEscolhidoAlt = conveniosAlt[opcaoConvenioAlt - 1];
                            }

                            pacienteAlterar.setNome(novoNome);
                            pacienteAlterar.setIdade(novaIdade);
                            pacienteAlterar.setTelefone(novoTelefone);
                            pacienteAlterar.setTipoSanguineo(novoTipoSanguineo);
                            pacienteAlterar.setDoador(novoDoador);
                            pacienteAlterar.setConvenio(convenioEscolhidoAlt);

                            controller.update(pacienteAlterar);
                            JOptionPane.showMessageDialog(null, "Paciente alterado com sucesso.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Paciente não encontrado para o ID " + idAlterar);
                        }
                        break;

                    case 0:
                        JOptionPane.showMessageDialog(null, "Saindo do sistema ...");
                        break;

                    default:
                        JOptionPane.showMessageDialog(null, "Opção inválida!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Entrada inválida, digite um número válido.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
            }
        }
    }
}
