package model;

public class Caminhonete extends Veiculo {
    public Caminhonete(long id, String modelo, String placa, double valorDiaria) {
        super(id, modelo, placa, valorDiaria);
    }

    @Override
    public double calcularValorAluguel(int dias) {
        return calcularValorBase(dias) + 80.0;
    }
}
