package model;

import java.util.List;

public class Medico extends Pessoa{
    private String crm;
    private Especialidade especialidade;
    private Convenio convenio;
    //private List<Pessoa> listaPessoa;
    private boolean efetivo;

    public Medico(Long id, String nome, int idade, String telefone, String crm, Especialidade especialidade, Convenio convenio, boolean efetivo) {
        super(id, nome, idade, telefone);
        this.crm = crm;
        this.especialidade = especialidade;
        this.convenio = convenio;
        this.efetivo = efetivo;
        //this.listaPessoa = listaPessoa;
    }

    public Medico(){

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

    public boolean isEfetivo() {
        return efetivo;
    }

    public void setEfetivo(boolean efetivo) {
        this.efetivo = efetivo;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;


    }

//    public List<Pessoa> getListaPessoa() {
//        return listaPessoa;
//    }
//
//    public void setListaPessoa(List<Pessoa> listaPessoa) {
//        this.listaPessoa = listaPessoa;
//    }
}
