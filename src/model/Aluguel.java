package model;

public class Aluguel {
    private long id;
    private Cliente cliente;
    private Veiculo veiculo;
    private Administrador administrador;
    private int quantidadeDias;
    private double valorTotal;
    private boolean finalizado;

    public Aluguel(long id, Cliente cliente, Veiculo veiculo, Administrador administrador, int quantidadeDias) {
        if (id < 0) {
            throw new IllegalArgumentException("O ID não pode ser negativo.");
        }
        if (cliente == null || veiculo == null || administrador == null) {
            throw new IllegalArgumentException("Cliente, veículo e administrador são obrigatórios.");
        }

        double valor = veiculo.calcularValorAluguel(quantidadeDias);
        veiculo.alugarVeiculo();

        this.id = id;
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.administrador = administrador;
        this.quantidadeDias = quantidadeDias;
        this.valorTotal = valor;
        this.finalizado = false;
    }

    // Usado pelo AluguelDAO para recriar um aluguel que já está salvo no banco
    public Aluguel(long id, Cliente cliente, Veiculo veiculo, Administrador administrador,
                   int quantidadeDias, double valorTotal, boolean finalizado) {
        this.id = id;
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.administrador = administrador;
        this.quantidadeDias = quantidadeDias;
        this.valorTotal = valorTotal;
        this.finalizado = finalizado;
    }

    public void devolver() {
        if (finalizado) {
            throw new IllegalStateException("Este aluguel já foi finalizado.");
        }
        veiculo.devolverVeiculo();
        finalizado = true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public int getQuantidadeDias() {
        return quantidadeDias;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public boolean isFinalizado() {
        return finalizado;
    }
}
