package controller;

import DAO.GenericDAO;
import DAO.ProdutoDAO;
import model.Produto;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoController {

    public String insert(Produto produto) throws Exception {
        GenericDAO dao = new ProdutoDAO();
        boolean isCadastrado = dao.insert(produto);
        if (isCadastrado) {
            return "Cadastro realizado com sucesso!";
        } else {
            return "Erro ao cadastrar produto";
        }
    }

    public List<Produto> getAll() throws Exception {
        List<Produto> produtoList = new ArrayList<>();
        GenericDAO dao = new ProdutoDAO();

        // Obter a lista genérica vinda da ProdutoDAO
        List<Object> listaGenerica = dao.getAll();

        // Converter o List<Object>, ou seja, a lista de objetos genéricos
        // que é retornada da ProdutoDAO em uma lista propriamente da
        // classe Produto
        for (Object objetoGenerico : listaGenerica) {
            // Converter para um objeto do tipo Produto
            produtoList.add( (Produto) objetoGenerico);
        }

        return produtoList;
    }

    public void printFormatedList(List<Produto> lista) {

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não existem produtos na lista.");
        }

        StringBuilder listaImprimir = new StringBuilder();
        listaImprimir.append("*** Lista de Produtos ***\n\n");

        for (Produto produto : lista) {
            listaImprimir.append(produto.getId());
            listaImprimir.append(" - ");
            listaImprimir.append(produto.getDescricao());
            listaImprimir.append(" - ");
            listaImprimir.append(produto.getPreco());
            listaImprimir.append("\n");
        }

        JOptionPane.showMessageDialog(null, listaImprimir.toString());
    }

    public Produto getById(int id) throws Exception {
        GenericDAO dao = new ProdutoDAO();
        Produto produtoEncontrado = (Produto) dao.getById(id);
        return produtoEncontrado;
    }

    public void delete(int id) throws Exception {
        Produto produtoEncontrado = this.getById(id);

        if (produtoEncontrado == null) {
            JOptionPane.showMessageDialog(
                    null,
                    "Nâo foi encontrado registro para o código " + id);
        } else {
            ProdutoDAO dao = new ProdutoDAO();
            dao.delete(id);
            JOptionPane.showMessageDialog(
                    null,
                    "*** Produto excluído com sucesso! ***\n" +
                            "\nCódigo: " + produtoEncontrado.getId() +
                            "\nDescrição: " + produtoEncontrado.getDescricao()
            );
        }

    }

    public List<Produto> getByDescricao(String descricao) throws Exception {
        ProdutoDAO dao = new ProdutoDAO();
        return dao.getByDescricao(descricao);
    }

    public void update(Produto produto) throws Exception {
        GenericDAO dao = new ProdutoDAO();
        if (dao.update(produto) == true) {
            JOptionPane.showMessageDialog(
                    null,
                    produto.getDescricao() + " foi atualizado com sucesso!"
            );
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Não foi possível alterar o produto " + produto.getDescricao()
            );
        }
    }

}
