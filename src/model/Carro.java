package model;

public class Carro extends Veiculo {
    public Carro(long id, String modelo, String placa, double valorDiaria) {
        super(id, modelo, placa, valorDiaria);
    }

    @Override
    public double calcularValorAluguel(int dias) {
        return calcularValorBase(dias);
    }
}
