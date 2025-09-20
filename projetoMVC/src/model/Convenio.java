package model;

import java.util.Arrays;

public enum Convenio {
    UNIMED("Unimed"),
    BRADESCO_SAUDE("Bradesco saúde"),
    SALVAMED("Salvamed"),
    BOA_VIDA("Boa Vida");

    private final String descricao;

    Convenio(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Convenio fromDescricaoConvenio(String descricao) {
        return Arrays.stream(Convenio.values())
                .filter(e -> e.getDescricao().equalsIgnoreCase(descricao))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Especialidade inválida: " + descricao));
    }

}
