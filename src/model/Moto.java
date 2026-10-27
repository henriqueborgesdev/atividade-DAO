package model;

public class Moto extends Veiculo {
    public Moto(long id, String modelo, String placa, double valorDiaria) {
        super(id, modelo, placa, valorDiaria);
    }

    @Override
    public double calcularValorAluguel(int dias) {
        double total = calcularValorBase(dias);

        if (dias >= 7) {
            total = total * 0.90;
        }
        return total;
    }
}
