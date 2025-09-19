package DAO;

import model.Convenio;
import model.Paciente;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PacienteDao implements GenericDAO{

    private Connection connection;

    public PacienteDao () throws Exception {
        try {
            this.connection = ConnectionFactory.getConnection();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Object> getAll() {

        List<Object> pacienteList = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM pacientes ORDER BY id_paciente";

        try {
            stmt = this.connection.prepareStatement(sql);
            rs = stmt.executeQuery();

            // rs.next() vai buscar o próximo registro encontrado no SELECT anterior
            // para CADA registro encontrado, será executado o bloco abaixo
            while (rs.next()) {
                // Declaro um objeto da classe Paciente pra ser populado com as informações do bancc
                Paciente paciente = new Paciente();

                // Fazemos um match entre o nome da coluna no banco de dados com o nome do atributo
                // correspondente do objeto
                paciente.setId(rs.getLong("id_paciente"));
                paciente.setNome(rs.getString("nome"));
                paciente.setIdade(rs.getInt("idade"));
                paciente.setTelefone(rs.getString("telefone"));
                paciente.setTipoSanguineo(rs.getString("tipo_sanguineo"));
                paciente.setDoador(rs.getBoolean("doador"));
                String convenioStr = rs.getString("convenio");
                Convenio convenioEnum = Convenio.valueOf(convenioStr);
                paciente.setConvenio(convenioEnum);
                // Inserir este objeto paciente na lista
                pacienteList.add(paciente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeConnection(stmt, rs);
        }
        return pacienteList;
    }

    @Override
    public Object getById(int id) {
        Paciente pacienteEncontrado = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM pacientes WHERE id_paciente = ?";
        try {
            stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                pacienteEncontrado = new Paciente();
                pacienteEncontrado.setId(rs.getLong("id_paciente"));
                pacienteEncontrado.setNome(rs.getString("nome"));
                pacienteEncontrado.setIdade(rs.getInt("idade"));
                pacienteEncontrado.setTelefone(rs.getString("telefone"));
                pacienteEncontrado.setTipoSanguineo(rs.getString("tipo_sanguineo"));
                pacienteEncontrado.setDoador(rs.getBoolean("doador"));
                String convenioStr = rs.getString("convenio");
                Convenio convenioEnum = Convenio.valueOf(convenioStr);
                pacienteEncontrado.setConvenio(convenioEnum);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeConnection(stmt, rs);
        }

        return pacienteEncontrado;
    }


    @Override
    public Boolean insert(Object object) {

        // Convertendo o objeto genérico em um objeto do tipo específico
        Paciente paciente = (Paciente) object;

        // Instanciando um objeto da classe que "formata" o comando sql
        PreparedStatement stmt = null;

        // Escrevendo a sql para inserir um novo registro na tabela 'paciente'
        String sql = "INSERT INTO pacientes (nome, idade, telefone, tipo_sanguineo, doador, convenio) VALUES (?,?,?,?,?,?)";

        try {
            // Transforma a String sql em um comando válido para ser executado no banco
            stmt = this.connection.prepareStatement(sql);

            // Inserindo valor em cada ponto de interrogação de forma sequencial
            // onde cada ? refere-se à uma coluna da tabela 'paciente'
            // atentando para o tipo de cada coluna com o tipo do valor a ser enviado

            stmt.setString(1, paciente.getNome());
            stmt.setInt(2, paciente.getIdade());
            stmt.setString(3, paciente.getTelefone());
            stmt.setString(4, paciente.getTipoSanguineo());
            stmt.setBoolean(5, paciente.isDoador());

            // Para o enum convenio, envie o nome (string) dele para o banco
            if (paciente.getConvenio() != null) {
                stmt.setString(6, paciente.getConvenio().name());
            } else {
                stmt.setNull(6, java.sql.Types.VARCHAR);  // se for nulo, setar null no banco
            }
            stmt.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Problemas ao inserir paciente. Erro: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            this.closeConnection(stmt, null);
        }
    }

    @Override
    public Boolean update(Object object) {
        Paciente paciente = (Paciente) object;
        PreparedStatement stmt = null;
        String sql = "" +
                "UPDATE pacientes SET " +
                "   nome = ?, " +
                "   idade = ?, " +
                "   telefone = ?, " +
                "   tipo_sanguineo = ?, " +
                "   doador = ?, " +
                "   convenio = ? " +
                "WHERE " +
                "   id_paciente = ?";
        try {
            stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, paciente.getNome());
            stmt.setInt(2, paciente.getIdade());
            stmt.setString(3, paciente.getTelefone());
            stmt.setString(4, paciente.getTipoSanguineo());
            stmt.setBoolean(5, paciente.isDoador());

            // Para o enum convenio, envie o nome (string) dele para o banco
            if (paciente.getConvenio() != null) {
                stmt.setString(6, paciente.getConvenio().name());
            } else {
                stmt.setNull(6, java.sql.Types.VARCHAR);  // se for nulo, setar null no banco
            }
            stmt.setLong(7, paciente.getId());
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
        String sql = "DELETE FROM pacientes WHERE id_paciente = ?";
        try {
            stmt = this.connection.prepareStatement(sql);
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
            ConnectionFactory.closeConnection(connection, stmt, rs);
        } catch (Exception ex) {
            System.out.println("Problemas ao fechar conexão. Erro: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
