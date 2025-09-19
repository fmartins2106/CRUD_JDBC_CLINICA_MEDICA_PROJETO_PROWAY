package controller;

import DAO.GenericDAO;
import DAO.PacienteDao;
import model.Paciente;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteController {

    public String insert(Paciente paciente) throws Exception {
        GenericDAO dao = new PacienteDao();
        boolean isCadastrado = dao.insert(paciente);
        if (isCadastrado) {
            return "Cadastro realizado com sucesso!";
        } else {
            return "Erro ao cadastrar paciente";
        }
    }

    public List<Paciente> getAll() throws Exception {
        List<Paciente> pacienteList = new ArrayList<>();
        GenericDAO dao = new PacienteDao();

        // Obter a lista genérica vinda da PacienteDAO
        List<Object> listaGenerica = dao.getAll();

        // Converter o List<Object>, ou seja, a lista de objetos genéricos
        // que é retornada da PacienteDAO em uma lista propriamente da
        // classe Paciente
        for (Object objetoGenerico : listaGenerica) {
            // Converter para um objeto do tipo Paciente
            pacienteList.add( (Paciente) objetoGenerico);
        }

        return pacienteList;
    }

    public void printFormatedList(List<Paciente> lista) {

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não existem pacientes na lista.");
        }

        StringBuilder listaImprimir = new StringBuilder();
        listaImprimir.append("*** Lista de Pacientes ***\n\n");

        for (Paciente paciente : lista) {
            listaImprimir.append(paciente.getId());
            listaImprimir.append(" - ");
            listaImprimir.append(paciente.getNome());
            listaImprimir.append(" - ");
            listaImprimir.append(paciente.getTelefone());
            listaImprimir.append("\n");
            listaImprimir.append(paciente.getIdade());
            listaImprimir.append("\n");
            listaImprimir.append(paciente.getTipoSanguineo());
            listaImprimir.append("\n");
            listaImprimir.append(paciente.getConvenio());
            listaImprimir.append("\n");
        }

        JOptionPane.showMessageDialog(null, listaImprimir.toString());
    }

    public Paciente getById(int id) throws Exception {
        GenericDAO dao = new PacienteDao();
        Paciente pacienteEncontrado = (Paciente) dao.getById(id);
        return pacienteEncontrado;
    }

    public void delete(int id) throws Exception {
        Paciente pacienteEncontrado = this.getById(id);

        if (pacienteEncontrado == null) {
            JOptionPane.showMessageDialog(
                    null,
                    "Nâo foi encontrado registro para o código " + id);
        } else {
            PacienteDao dao = new PacienteDao();
            dao.delete(id);
            JOptionPane.showMessageDialog(
                    null,
                    "*** Paciente excluído com sucesso! ***\n" +
                            "\nCódigo: " + pacienteEncontrado.getId() +
                            "\nNome: " + pacienteEncontrado.getNome() +
                            "\nIdade: " + pacienteEncontrado.getIdade() +
                            "\nTelefone: " + pacienteEncontrado.getTelefone() +
                            "\nTipo_sanguineo: " + pacienteEncontrado.getTipoSanguineo() +
                            "\nDoador: " + pacienteEncontrado.isDoador() +
                            "\nConvenio: " + pacienteEncontrado.getConvenio()

            );
        }

    }

    public void update(Paciente paciente) throws Exception {
        GenericDAO dao = new PacienteDao();
        if (dao.update(paciente)) {
            JOptionPane.showMessageDialog(
                    null,
                    "Paciente " + paciente.getNome() + " foi atualizado com sucesso!"
            );
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Não foi possível alterar os dados do paciente " + paciente.getNome()
            );
        }
    }





}
