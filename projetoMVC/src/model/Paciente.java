package model;

public class Paciente extends Pessoa{
    private String tipoSanguineo;
    private boolean doador;
    private Convenio convenio;

    public Paciente(Long id, String nome, int idade, String telefone, String tipoSanguineo, boolean doador, Convenio convenio) {
        super(id, nome, idade, telefone);
        this.tipoSanguineo = tipoSanguineo;
        this.doador = doador;
        this.convenio = convenio;
    }

    public Paciente() {
    }

    public String getTipoSanguineo() {
        return tipoSanguineo;
    }

    public void setTipoSanguineo(String tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
    }

    public boolean isDoador() {
        return doador;
    }

    public void setDoador(boolean doador) {
        this.doador = doador;
    }

    public Convenio getConvenio() {
        return convenio;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }
}
