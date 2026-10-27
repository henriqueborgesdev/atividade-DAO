package model;

public class Administrador extends Pessoa {
    private String usuario;

    public Administrador(long id, String nome, String cpf, String telefone, String email,
                         String endereco, String usuario) {
        super(id, nome, cpf, telefone, email, endereco);

        if (usuario == null || usuario.isBlank()) {
            throw new IllegalArgumentException("O usuário não pode ficar vazio.");
        }
        this.usuario = usuario.trim();
    }

    public String getUsuario() {
        return usuario;
    }
}
