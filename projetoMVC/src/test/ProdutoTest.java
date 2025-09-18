package test;

import controller.ProdutoController;
import model.Produto;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoTest {

    public static void main(String[] args) throws Exception {

        ProdutoController controller = new ProdutoController();

        // Processo de cadatrar um produto novo
//        Produto novoProduto = new Produto();
//        novoProduto.setDescricao("Garrafinha de inox 500ml");
//        novoProduto.setPreco(59.90);
//        novoProduto.setStatus(true);
//
//        String mensagemInsert = controller.insert(novoProduto);
//        JOptionPane.showMessageDialog(null, mensagemInsert);

        // Processo de apresentar a lista de produtos

        // Obter a lista do tipo Produto
        List<Produto> produtoList = controller.getAll();

        // Crio uma variável para construir a mensagem de saída de dados
        StringBuilder listaImprimir = new StringBuilder();

        // Para cada elemento encontrado na lista de produtos, montar o texto
        // que será apresentado no painel com os atributos do objeto
        for (Produto produto : produtoList) {
            listaImprimir.append(produto.getDescricao());
            listaImprimir.append(" - ");
            listaImprimir.append(produto.getPreco());
            listaImprimir.append("\n");
        }

        // Imprimir na tela a lista de produtos
        JOptionPane.showMessageDialog(
                null,
                listaImprimir.toString());

    }

}
