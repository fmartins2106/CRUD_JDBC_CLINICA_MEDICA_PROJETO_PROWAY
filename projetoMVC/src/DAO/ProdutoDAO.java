package DAO;

import model.Produto;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO implements GenericDAO {

    private Connection conn;

    public ProdutoDAO () throws Exception {
        try {
            this.conn = ConnectionFactory.getConnection();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Object> getAll() {

        List<Object> produtoList = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM produto ORDER BY id";

        try {
            stmt = this.conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            // rs.next() vai buscar o próximo registro encontrado no SELECT anterior
            // para CADA registro encontrado, será executado o bloco abaixo
            while (rs.next()) {
                // Declaro um objeto da classe Produto pra ser populado com as informações do bancc
                Produto produto = new Produto();

                // Fazemos um match entre o nome da coluna no banco de dados com o nome do atributo
                // correspondente do objeto
                produto.setId(rs.getInt("id"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setStatus(rs.getBoolean("status"));

                // Inserir este objeto produto na lista
                produtoList.add(produto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeConnection(stmt, rs);
        }
        return produtoList;
    }

    @Override
    public Object getById(int id) {
        Produto produtoEncontrado = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM produto WHERE id = ?";
        try {
            stmt = this.conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                produtoEncontrado = new Produto();

                produtoEncontrado.setId(rs.getInt("id"));
                produtoEncontrado.setDescricao(rs.getString("descricao"));
                produtoEncontrado.setPreco(rs.getDouble("preco"));
                produtoEncontrado.setStatus(rs.getBoolean("status"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeConnection(stmt, rs);
        }

        return produtoEncontrado;
    }

    public List<Produto> getByDescricao(String descricao) {
        List<Produto> produtoList = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM produto WHERE descricao ILIKE(?)";
        try {
            stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, "%" + descricao + "%");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setStatus(rs.getBoolean("status"));
                produtoList.add(produto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeConnection(stmt, rs);
        }

        return produtoList;
    }

    @Override
    public Boolean insert(Object object) {

        // Convertendo o objeto genérico em um objeto do tipo específico
        Produto produto = (Produto) object;

        // Instanciando um objeto da classe que "formata" o comando sql
        PreparedStatement stmt = null;

        // Escrevendo a sql para inserir um novo registro na tabela 'produto'
        String sql = "INSERT INTO produto (descricao, preco, status) VALUES (?,?,?)";

        try {
            // Transforma a String sql em um comando válido para ser executado no banco
            stmt = this.conn.prepareStatement(sql);

            // Inserindo valor em cada ponto de interrogação de forma sequencial
            // onde cada ? refere-se à uma coluna da tabela 'produto'
            // atentando para o tipo de cada coluna com o tipo do valor a ser enviado
            stmt.setString(1, produto.getDescricao());
            stmt.setDouble(2, produto.getPreco());
            stmt.setBoolean(3, produto.isStatus());

            // executa a sql no banco
            stmt.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Problemas ao inserir produto. Erro: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            this.closeConnection(stmt, null);
        }
    }

    @Override
    public Boolean update(Object object) {
        Produto produto = (Produto) object;
        PreparedStatement stmt = null;
        String sql = "" +
                "UPDATE produto SET " +
                "   descricao = ?, " +
                "   preco = ?, " +
                "   status = ? " +
                "WHERE " +
                "   id = ?";
        try {
            stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, produto.getDescricao());
            stmt.setDouble(2, produto.getPreco());
            stmt.setBoolean(3, produto.isStatus());
            stmt.setInt(4, produto.getId());
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            this.closeConnection(stmt, null);
        }
    }

    @Override
    public void delete(int id) {
        PreparedStatement stmt = null;
        String sql = "DELETE FROM produto WHERE id = ?";
        try {
            stmt = this.conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeConnection(stmt, null);
        }
    }

    private void closeConnection(PreparedStatement stmt, ResultSet rs) {
        try {
            ConnectionFactory.closeConnection(conn, stmt, rs);
        } catch (Exception ex) {
            System.out.println("Problemas ao fechar conexão. Erro: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
