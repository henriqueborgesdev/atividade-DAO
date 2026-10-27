package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Administrador;

public class AdministradorDAO {

    public Administrador create(Administrador administrador) throws SQLException {
        String sql = "INSERT INTO administrador (nome, cpf, telefone, email, endereco, usuario) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, administrador.getNome());
            stmt.setString(2, administrador.getCpf());
            stmt.setString(3, administrador.getTelefone());
            stmt.setString(4, administrador.getEmail());
            stmt.setString(5, administrador.getEndereco());
            stmt.setString(6, administrador.getUsuario());
            stmt.executeUpdate();

            try (ResultSet chaves = stmt.getGeneratedKeys()) {
                if (chaves.next()) {
                    administrador.setId(chaves.getLong(1));
                }
            }
            return administrador;
        }
    }

    public List<Administrador> readAll() throws SQLException {
        List<Administrador> administradores = new ArrayList<>();
        String sql = "SELECT * FROM administrador";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                administradores.add(montar(rs));
            }
        }
        return administradores;
    }

    public Administrador readById(long id) throws SQLException {
        String sql = "SELECT * FROM administrador WHERE id = ?";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? montar(rs) : null;
            }
        }
    }

    public boolean update(Administrador administrador) throws SQLException {
        String sql = "UPDATE administrador SET nome = ?, cpf = ?, telefone = ?, email = ?, endereco = ?, usuario = ? WHERE id = ?";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, administrador.getNome());
            stmt.setString(2, administrador.getCpf());
            stmt.setString(3, administrador.getTelefone());
            stmt.setString(4, administrador.getEmail());
            stmt.setString(5, administrador.getEndereco());
            stmt.setString(6, administrador.getUsuario());
            stmt.setLong(7, administrador.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(long id) throws SQLException {
        String sql = "DELETE FROM administrador WHERE id = ?";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    private Administrador montar(ResultSet rs) throws SQLException {
        return new Administrador(rs.getLong("id"), rs.getString("nome"), rs.getString("cpf"),
                rs.getString("telefone"), rs.getString("email"), rs.getString("endereco"), rs.getString("usuario"));
    }
}
