package model;

public class Consulta {
    private Long idConsulta;
    private Medico medico;
    private Paciente paciente;
    private boolean status;

    public Consulta(Long idConsulta, Medico medico, Paciente paciente, boolean status) {
        this.idConsulta = idConsulta;
        this.medico = medico;
        this.paciente = paciente;
        this.status = status;
    }

    public Long getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(Long idConsulta) {
        this.idConsulta = idConsulta;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
