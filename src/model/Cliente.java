package model;

public class Cliente extends Pessoa {
    private String cnh;

    public Cliente(long id, String nome, String cpf, String telefone, String email, String endereco, String cnh) {
        super(id, nome, cpf, telefone, email, endereco);

        if (cnh == null || cnh.isBlank()) {
            throw new IllegalArgumentException("A CNH não pode ficar vazia.");
        }
        this.cnh = cnh.trim();
    }

    public String getCnh() {
        return cnh;
    }
}
