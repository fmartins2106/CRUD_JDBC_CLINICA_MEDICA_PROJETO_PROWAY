package model;

public enum Convenio {
    UNIMED("Unimed"),
    BRADESCO_SAUDE("Bradesco saúde"),
    SALVAMED("Salvamed"),
    BOA_VIDA("Boa Vida");

    private final String descricao;

    Convenio(String descricao) {
        this.descricao = descricao;
    }
}
