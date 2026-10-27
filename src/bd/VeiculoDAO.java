package bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Caminhonete;
import model.Carro;
import model.Moto;
import model.Veiculo;

public class VeiculoDAO {

    public Veiculo create(Veiculo veiculo) throws SQLException {
        String sql = "INSERT INTO veiculo (tipo, modelo, placa, valor_diaria, disponivel) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // O tipo é o nome da classe: Carro, Moto ou Caminhonete
            stmt.setString(1, veiculo.getClass().getSimpleName());
            stmt.setString(2, veiculo.getModelo());
            stmt.setString(3, veiculo.getPlaca());
            stmt.setDouble(4, veiculo.getValorDiaria());
            stmt.setBoolean(5, veiculo.getDisponivel());
            stmt.executeUpdate();

            try (ResultSet chaves = stmt.getGeneratedKeys()) {
                if (chaves.next()) {
                    veiculo.setId(chaves.getLong(1));
                }
            }
            return veiculo;
        }
    }

    public List<Veiculo> readAll() throws SQLException {
        List<Veiculo> veiculos = new ArrayList<>();
        String sql = "SELECT * FROM veiculo";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                veiculos.add(montar(rs));
            }
        }
        return veiculos;
    }

    public Veiculo readById(long id) throws SQLException {
        String sql = "SELECT * FROM veiculo WHERE id = ?";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? montar(rs) : null;
            }
        }
    }

    public boolean update(Veiculo veiculo) throws SQLException {
        String sql = "UPDATE veiculo SET modelo = ?, placa = ?, valor_diaria = ?, disponivel = ? WHERE id = ?";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, veiculo.getModelo());
            stmt.setString(2, veiculo.getPlaca());
            stmt.setDouble(3, veiculo.getValorDiaria());
            stmt.setBoolean(4, veiculo.getDisponivel());
            stmt.setLong(5, veiculo.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(long id) throws SQLException {
        String sql = "DELETE FROM veiculo WHERE id = ?";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    // Cria a subclasse certa de acordo com a coluna "tipo"
    private Veiculo montar(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String modelo = rs.getString("modelo");
        String placa = rs.getString("placa");
        double valorDiaria = rs.getDouble("valor_diaria");

        Veiculo veiculo;
        switch (rs.getString("tipo")) {
            case "Moto":
                veiculo = new Moto(id, modelo, placa, valorDiaria);
                break;
            case "Caminhonete":
                veiculo = new Caminhonete(id, modelo, placa, valorDiaria);
                break;
            default:
                veiculo = new Carro(id, modelo, placa, valorDiaria);
        }
        veiculo.setDisponivel(rs.getBoolean("disponivel"));
        return veiculo;
    }
}
