package model;

public class Produto {

    private int id;
    private String descricao;
    private Double preco;
    private boolean status;

    public Produto() {
    }

    public Produto(int id, String descricao, Double preco, boolean status) {
        this.id = id;
        this.descricao = descricao;
        this.preco = preco;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
