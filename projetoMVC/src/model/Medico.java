package model;

import java.util.List;

public class Medico extends Pessoa{
    private String crm;
    private Especialidade especialidade;
    private Convenio convenio;
    private List<Pessoa> listaPessoa;

    public Medico(Long id, String nome, int idade, String telefone, String crm, Especialidade especialidade, Convenio convenio, List<Pessoa> listaPessoa) {
        super(id, nome, idade, telefone);
        this.crm = crm;
        this.especialidade = especialidade;
        this.convenio = convenio;
        this.listaPessoa = listaPessoa;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public Convenio getConvenio() {
        return convenio;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }

    public List<Pessoa> getListaPessoa() {
        return listaPessoa;
    }

    public void setListaPessoa(List<Pessoa> listaPessoa) {
        this.listaPessoa = listaPessoa;
    }
}
