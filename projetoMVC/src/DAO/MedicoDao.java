package DAO;

import model.Convenio;
import model.Especialidade;
import model.Medico;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicoDao implements GenericDAO{

    private Connection connection;

    public MedicoDao() throws Exception {
        try {
            this.connection = ConnectionFactory.getConnection();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Object> getAll() {

        List<Object> medicoList = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM medicos ORDER BY id_medico";

        try {
            stmt = this.connection.prepareStatement(sql);
            rs = stmt.executeQuery();

            // rs.next() vai buscar o próximo registro encontrado no SELECT anterior
            // para CADA registro encontrado, será executado o bloco abaixo
            while (rs.next()) {
                // Declaro um objeto da classe Medico pra ser populado com as informações do bancc
                Medico medico = new Medico();

                // Fazemos um match entre o nome da coluna no banco de dados com o nome do atributo
                // correspondente do objeto
                medico.setId(rs.getLong("id_medico"));
                medico.setNome(rs.getString("nome"));
                medico.setIdade(rs.getInt("idade"));
                medico.setTelefone(rs.getString("telefone"));
                medico.setCrm(rs.getString("crm"));

                String especialidadeStr = rs.getString("especialidade");
                Especialidade especialidadeEnum = Especialidade.fromDescricao(especialidadeStr);
                medico.setEspecialidade(especialidadeEnum);

                String convenioStr = rs.getString("convenio");
                Convenio convenioEnum = Convenio.fromDescricaoConvenio(convenioStr);
                medico.setConvenio(convenioEnum);

                medico.setEfetivo(rs.getBoolean("efetivo"));

                // Inserir este objeto medico na lista
                medicoList.add(medico);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeConnection(stmt, rs);
        }
        return medicoList;
    }

    @Override
    public Object getById(int id) {
        Medico medicoEncontrado = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM medicos WHERE id_medico = ?";
        try {
            stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                medicoEncontrado = new Medico();
                medicoEncontrado.setId(rs.getLong("id_medico"));
                medicoEncontrado.setNome(rs.getString("nome"));
                medicoEncontrado.setIdade(rs.getInt("idade"));
                medicoEncontrado.setTelefone(rs.getString("telefone"));
                medicoEncontrado.setCrm(rs.getString("crm"));

                String especialidadeStr = rs.getString("especialidade");
                Especialidade especialidadeEnum = Especialidade.fromDescricao(especialidadeStr);
                medicoEncontrado.setEspecialidade(especialidadeEnum);

                String convenioStr = rs.getString("convenio");
                Convenio convenioEnum = Convenio.fromDescricaoConvenio(convenioStr);
                medicoEncontrado.setConvenio(convenioEnum);

                medicoEncontrado.setEfetivo(rs.getBoolean("efetivo"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeConnection(stmt, rs);
        }

        return medicoEncontrado;
    }


    @Override
    public Boolean insert(Object object) {

        // Convertendo o objeto genérico em um objeto do tipo específico
        Medico medico = (Medico) object;

        // Instanciando um objeto da classe que "formata" o comando sql
        PreparedStatement stmt = null;

        // Escrevendo a sql para inserir um novo registro na tabela 'medico'
        String sql = "INSERT INTO medicos (nome, idade, telefone, crm, especialidade, convenio, efetivo) VALUES (?,?,?,?,?,?,?)";

        try {
            // Transforma a String sql em um comando válido para ser executado no banco
            stmt = this.connection.prepareStatement(sql);

            // Inserindo valor em cada ponto de interrogação de forma sequencial
            // onde cada ? refere-se à uma coluna da tabela 'medico'
            // atentando para o tipo de cada coluna com o tipo do valor a ser enviado

            stmt.setString(1, medico.getNome());
            stmt.setInt(2, medico.getIdade());
            stmt.setString(3, medico.getTelefone());
            stmt.setString(4, medico.getCrm());

            if (medico.getEspecialidade() != null) {
                stmt.setString(5, medico.getEspecialidade().getDescricao());
            } else {
                stmt.setNull(5, java.sql.Types.VARCHAR);  // se for nulo, setar null no banco
            }

            // Para o enum convenio, envie o nome (string) dele para o banco
            if (medico.getConvenio() != null) {
                stmt.setString(6, medico.getConvenio().getDescricao());
            } else {
                stmt.setNull(6, java.sql.Types.VARCHAR);  // se for nulo, setar null no banco
            }

            stmt.setBoolean(7, medico.isEfetivo());

            stmt.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Problemas ao inserir medico. Erro: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            this.closeConnection(stmt, null);
        }
    }

    @Override
    public Boolean update(Object object) {
        Medico medico = (Medico) object;
        PreparedStatement stmt = null;
        String sql = "" +
                "UPDATE medicos SET " +
                "   nome = ?, " +
                "   idade = ?, " +
                "   telefone = ?, " +
                "   crm = ?, " +
                "   especialidade = ?, " +
                "   convenio = ?, " +
                "   efetivo = ? " +
                "WHERE " +
                "   id_medico = ?";
        try {
            stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, medico.getNome());
            stmt.setInt(2, medico.getIdade());
            stmt.setString(3, medico.getTelefone());

            stmt.setString(4, medico.getCrm());

            if (medico.getEspecialidade() != null) {
                stmt.setString(5, medico.getEspecialidade().getDescricao());
            } else {
                stmt.setNull(5, java.sql.Types.VARCHAR);  // se for nulo, setar null no banco
            }

            // Para o enum convenio, envie o nome (string) dele para o banco
            if (medico.getConvenio() != null) {
                stmt.setString(6, medico.getConvenio().getDescricao());
            } else {
                stmt.setNull(6, java.sql.Types.VARCHAR);  // se for nulo, setar null no banco
            }

            stmt.setBoolean(7, medico.isEfetivo());

            stmt.setLong(8, medico.getId());
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
        String sql = "DELETE FROM medicos WHERE id_medico = ?";
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
