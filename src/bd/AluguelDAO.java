package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Aluguel;

public class AluguelDAO {
    private ClienteDAO clienteDAO = new ClienteDAO();
    private VeiculoDAO veiculoDAO = new VeiculoDAO();
    private AdministradorDAO administradorDAO = new AdministradorDAO();

    public Aluguel create(Aluguel aluguel) throws SQLException {
        String sql = "INSERT INTO aluguel (cliente_id, veiculo_id, administrador_id, quantidade_dias, valor_total, finalizado) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, aluguel.getCliente().getId());
            stmt.setLong(2, aluguel.getVeiculo().getId());
            stmt.setLong(3, aluguel.getAdministrador().getId());
            stmt.setInt(4, aluguel.getQuantidadeDias());
            stmt.setDouble(5, aluguel.getValorTotal());
            stmt.setBoolean(6, aluguel.isFinalizado());
            stmt.executeUpdate();

            try (ResultSet chaves = stmt.getGeneratedKeys()) {
                if (chaves.next()) {
                    aluguel.setId(chaves.getLong(1));
                }
            }
            return aluguel;
        }
    }

    public List<Aluguel> readAll() throws SQLException {
        List<Aluguel> alugueis = new ArrayList<>();
        String sql = "SELECT * FROM aluguel";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                alugueis.add(montar(rs));
            }
        }
        return alugueis;
    }

    public Aluguel readById(long id) throws SQLException {
        String sql = "SELECT * FROM aluguel WHERE id = ?";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? montar(rs) : null;
            }
        }
    }

    // Só o status de finalizado muda depois que o aluguel é criado
    public boolean update(Aluguel aluguel) throws SQLException {
        String sql = "UPDATE aluguel SET finalizado = ? WHERE id = ?";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, aluguel.isFinalizado());
            stmt.setLong(2, aluguel.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(long id) throws SQLException {
        String sql = "DELETE FROM aluguel WHERE id = ?";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    // Busca cliente, veículo e administrador pelos IDs salvos na tabela
    private Aluguel montar(ResultSet rs) throws SQLException {
        return new Aluguel(
                rs.getLong("id"),
                clienteDAO.readById(rs.getLong("cliente_id")),
                veiculoDAO.readById(rs.getLong("veiculo_id")),
                administradorDAO.readById(rs.getLong("administrador_id")),
                rs.getInt("quantidade_dias"),
                rs.getDouble("valor_total"),
                rs.getBoolean("finalizado"));
    }
}
