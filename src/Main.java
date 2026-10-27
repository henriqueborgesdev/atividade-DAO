import java.sql.SQLException;

import bd.AdministradorDAO;
import bd.AluguelDAO;
import bd.ClienteDAO;
import bd.VeiculoDAO;
import model.Administrador;
import model.Alugavel;
import model.Aluguel;
import model.Caminhonete;
import model.Carro;
import model.Cliente;
import model.Moto;
import model.Veiculo;

public class Main {
    public static void main(String[] args) {
        Locadora locadora = new Locadora();

        // ID 0 = ainda não salvo; o banco gera o ID no cadastro
        Cliente cliente = new Cliente(0, "Cliente Exemplo", "11122233344", "62999990000",
                "cliente@example.com", "Rua Exemplo, 100", "12345678901");
        Administrador administrador = new Administrador(0, "Administrador Exemplo", "99988877766",
                "62988880000", "admin@example.com", "Sede da locadora", "admin");

        Carro carro = new Carro(0, "Carro exemplo", "ABC-1234", 100.0);
        Moto moto = new Moto(0, "Moto exemplo", "DEF-5678", 100.0);
        Caminhonete caminhonete = new Caminhonete(0, "Caminhonete exemplo", "GHI-9012", 100.0);

        try {
            // 1. CREATE
            System.out.println("--- 1. Cadastrando no banco ---");
            locadora.cadastrarCliente(cliente);
            locadora.cadastrarAdministrador(administrador);
            locadora.cadastrarVeiculo(carro);
            locadora.cadastrarVeiculo(moto);
            locadora.cadastrarVeiculo(caminhonete);
            System.out.println("Cliente criado com ID: " + cliente.getId());
            System.out.println("Administrador criado com ID: " + administrador.getId());

            // 2. READ
            System.out.println("\n--- 2. Veículos cadastrados (valores para sete dias) ---");
            for (Veiculo veiculo : locadora.getVeiculos()) {
                System.out.printf("[%d] %s: R$ %.2f%n", veiculo.getId(), veiculo.getModelo(),
                        veiculo.calcularValorAluguel(7));
            }

            Alugavel alugavel = moto;
            System.out.printf("Moto calculada pela interface: R$ %.2f%n", alugavel.calcularValorAluguel(7));

            // 3. UPDATE (aluguel e devolução atualizam o veículo e o aluguel no banco)
            System.out.println("\n--- 3. Alugando e devolvendo ---");
            Aluguel aluguel = locadora.alugarVeiculo(cliente.getId(), carro.getId(), 3, administrador);
            System.out.println("Aluguel registrado por: " + aluguel.getAdministrador().getNome());
            System.out.printf("Valor total: R$ %.2f%n", aluguel.getValorTotal());
            System.out.println("Carro disponível: " + locadora.buscarVeiculo(carro.getId()).getDisponivel());

            try {
                locadora.alugarVeiculo(cliente.getId(), carro.getId(), 2, administrador);
            } catch (IllegalStateException e) {
                System.out.println("Não foi possível alugar: " + e.getMessage());
            }

            locadora.devolverVeiculo(aluguel.getId());
            System.out.println("Carro disponível após devolver: " + locadora.buscarVeiculo(carro.getId()).getDisponivel());
            System.out.println("Contratos no histórico: " + locadora.getAlugueis().size());

            // 4. DELETE (limpa os dados de exemplo para o programa poder rodar de novo)
            System.out.println("\n--- 4. Removendo os dados de exemplo ---");
            new AluguelDAO().delete(aluguel.getId());
            new VeiculoDAO().delete(carro.getId());
            new VeiculoDAO().delete(moto.getId());
            new VeiculoDAO().delete(caminhonete.getId());
            new ClienteDAO().delete(cliente.getId());
            new AdministradorDAO().delete(administrador.getId());
            System.out.println("Veículos restantes: " + locadora.getVeiculos().size());
        } catch (SQLException e) {
            System.err.println("Ocorreu um erro de SQL:");
            e.printStackTrace();
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
