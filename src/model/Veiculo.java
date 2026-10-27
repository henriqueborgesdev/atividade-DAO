package model;

import java.util.Locale;

public abstract class Veiculo implements Alugavel {
    private long id;
    private String modelo;
    private String placa;
    private double valorDiaria;
    private boolean disponivel;

    public Veiculo(long id, String modelo, String placa, double valorDiaria) {
        if (id < 0) {
            throw new IllegalArgumentException("O ID não pode ser negativo.");
        }
        if (modelo == null || modelo.isBlank()) {
            throw new IllegalArgumentException("O modelo não pode ficar vazio.");
        }
        if (placa == null || placa.isBlank()) {
            throw new IllegalArgumentException("A placa não pode ficar vazia.");
        }
        if (!Double.isFinite(valorDiaria) || valorDiaria <= 0) {
            throw new IllegalArgumentException("A diária deve ser um número maior que zero.");
        }

        this.id = id;
        this.modelo = modelo.trim();
        this.placa = placa.trim().replace("-", "").toUpperCase(Locale.ROOT);
        this.valorDiaria = valorDiaria;
        this.disponivel = true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public double getValorDiaria() {
        return valorDiaria;
    }

    public boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    protected double calcularValorBase(int dias) {
        if (dias <= 0) {
            throw new IllegalArgumentException("A quantidade de dias deve ser maior que zero.");
        }
        double total = valorDiaria * dias;
        if (!Double.isFinite(total)) {
            throw new IllegalArgumentException("O valor calculado é muito alto.");
        }
        return total;
    }

    void alugarVeiculo() {
        if (!disponivel) {
            throw new IllegalStateException("O veículo já está alugado.");
        }
        disponivel = false;
    }

    void devolverVeiculo() {
        if (disponivel) {
            throw new IllegalStateException("O veículo já está disponível.");
        }
        disponivel = true;
    }

    @Override
    public abstract double calcularValorAluguel(int dias);
}
