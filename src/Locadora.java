import java.sql.SQLException;
import java.util.List;

import bd.AdministradorDAO;
import bd.AluguelDAO;
import bd.ClienteDAO;
import bd.VeiculoDAO;
import model.Administrador;
import model.Aluguel;
import model.Cliente;
import model.Veiculo;

// Regras de negócio da locadora. Os dados ficam salvos no banco através dos DAOs.
public class Locadora {
    private ClienteDAO clienteDAO = new ClienteDAO();
    private AdministradorDAO administradorDAO = new AdministradorDAO();
    private VeiculoDAO veiculoDAO = new VeiculoDAO();
    private AluguelDAO aluguelDAO = new AluguelDAO();

    public void cadastrarCliente(Cliente cliente) throws SQLException {
        if (cliente == null) {
            throw new IllegalArgumentException("Informe um cliente.");
        }
        clienteDAO.create(cliente);
    }

    public void cadastrarAdministrador(Administrador administrador) throws SQLException {
        if (administrador == null) {
            throw new IllegalArgumentException("Informe um administrador.");
        }
        administradorDAO.create(administrador);
    }

    public void cadastrarVeiculo(Veiculo veiculo) throws SQLException {
        if (veiculo == null) {
            throw new IllegalArgumentException("Informe um veículo.");
        }
        veiculoDAO.create(veiculo);
    }

    public Cliente buscarCliente(long id) throws SQLException {
        Cliente cliente = clienteDAO.readById(id);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }
        return cliente;
    }

    public Veiculo buscarVeiculo(long id) throws SQLException {
        Veiculo veiculo = veiculoDAO.readById(id);
        if (veiculo == null) {
            throw new IllegalArgumentException("Veículo não encontrado.");
        }
        return veiculo;
    }

    public Aluguel alugarVeiculo(long clienteId, long veiculoId, int dias, Administrador administrador)
            throws SQLException {
        Cliente cliente = buscarCliente(clienteId);
        Veiculo veiculo = buscarVeiculo(veiculoId);

        // O construtor de Aluguel já marca o veículo como indisponível
        Aluguel aluguel = new Aluguel(0, cliente, veiculo, administrador, dias);
        aluguelDAO.create(aluguel);
        veiculoDAO.update(veiculo);
        return aluguel;
    }

    public void devolverVeiculo(long aluguelId) throws SQLException {
        Aluguel aluguel = aluguelDAO.readById(aluguelId);
        if (aluguel == null) {
            throw new IllegalArgumentException("Aluguel não encontrado.");
        }
        aluguel.devolver();
        aluguelDAO.update(aluguel);
        veiculoDAO.update(aluguel.getVeiculo());
    }

    public List<Cliente> getClientes() throws SQLException {
        return clienteDAO.readAll();
    }

    public List<Veiculo> getVeiculos() throws SQLException {
        return veiculoDAO.readAll();
    }

    public List<Aluguel> getAlugueis() throws SQLException {
        return aluguelDAO.readAll();
    }
}
