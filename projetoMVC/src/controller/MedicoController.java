package controller;

import DAO.GenericDAO;
import DAO.MedicoDao;
import model.Medico;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoController {

    public String insert(Medico medico) throws Exception {
        GenericDAO dao = new MedicoDao();
        boolean isCadastrado = dao.insert(medico);
        if (isCadastrado) {
            return "Cadastro realizado com sucesso!";
        } else {
            return "Erro ao cadastrar medico";
        }
    }

    public List<Medico> getAll() throws Exception {
        List<Medico> medicoList = new ArrayList<>();
        GenericDAO dao = new MedicoDao();

        // Obter a lista genérica vinda da MedicoDAO
        List<Object> listaGenerica = dao.getAll();

        // Converter o List<Object>, ou seja, a lista de objetos genéricos
        // que é retornada da MedicoDAO em uma lista propriamente da
        // classe Medico
        for (Object objetoGenerico : listaGenerica) {
            // Converter para um objeto do tipo Medico
            medicoList.add( (Medico) objetoGenerico);
        }

        return medicoList;
    }

    public void printFormatedList(List<Medico> lista) {

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não existem medicos na lista.");
        }

        StringBuilder listaImprimir = new StringBuilder();
        listaImprimir.append("*** Lista de Medicos ***\n\n");

        for (Medico medico : lista) {
            listaImprimir.append(medico.getId());
            listaImprimir.append(" - ");
            listaImprimir.append(medico.getNome());
            listaImprimir.append(" - ");
            listaImprimir.append(medico.getTelefone());
            listaImprimir.append("\n");
            listaImprimir.append(medico.getIdade());
            listaImprimir.append("\n");
            listaImprimir.append(medico.getCrm());
            listaImprimir.append("\n");
            listaImprimir.append(medico.getEspecialidade());
            listaImprimir.append("\n");
            listaImprimir.append(medico.getConvenio());
            listaImprimir.append("\n");
            listaImprimir.append(medico.isEfetivo());
            listaImprimir.append("\n");

        }

        JOptionPane.showMessageDialog(null, listaImprimir.toString());
    }

    public Medico getById(int id) throws Exception {
        GenericDAO dao = new MedicoDao();
        Medico medicoEncontrado = (Medico) dao.getById(id);
        return medicoEncontrado;
    }

    public void delete(int id) throws Exception {
        Medico medicoEncontrado = this.getById(id);

        if (medicoEncontrado == null) {
            JOptionPane.showMessageDialog(
                    null,
                    "Nâo foi encontrado registro para o código " + id);
        } else {
            MedicoDao dao = new MedicoDao();
            dao.delete(id);
            JOptionPane.showMessageDialog(
                    null,
                    "*** Medico excluído com sucesso! ***\n" +
                            "\nCódigo: " + medicoEncontrado.getId() +
                            "\nNome: " + medicoEncontrado.getNome() +
                            "\nIdade: " + medicoEncontrado.getIdade() +
                            "\nTelefone: " + medicoEncontrado.getTelefone() +
                            "\nCrm: " + medicoEncontrado.getCrm() +
                            "\nEspecialidade: " + medicoEncontrado.getEspecialidade() +
                            "\nConvenio: " + medicoEncontrado.getConvenio() +
                            "\nDoador: " + medicoEncontrado.isEfetivo()
            );
        }

    }

    public void update(Medico medico) throws Exception {
        GenericDAO dao = new MedicoDao();
        if (dao.update(medico)) {
            JOptionPane.showMessageDialog(
                    null,
                    "Medico " + medico.getNome() + " foi atualizado com sucesso!"
            );
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Não foi possível alterar os dados do medico " + medico.getNome()
            );
        }
    }





}
