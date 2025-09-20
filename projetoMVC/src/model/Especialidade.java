package model;

import java.util.Arrays;

public enum Especialidade {
    CARDIOLOGIA("Cardiologia"),
    DERMATOLOGIA("Dermatologia"),
    CLINICO_GERAL("Clinico geral"),
    ORTOPEDIA("Ortopedia");

    private final String descricao;

    Especialidade(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Especialidade fromDescricao(String descricao) {
        return Arrays.stream(Especialidade.values())
                .filter(e -> e.getDescricao().equalsIgnoreCase(descricao))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Especialidade inválida: " + descricao));
    }



}
