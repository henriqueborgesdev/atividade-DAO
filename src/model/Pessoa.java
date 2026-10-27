package model;

public abstract class Pessoa {
    private long id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private String endereco;

    public Pessoa(long id, String nome, String cpf, String telefone, String email, String endereco) {
        if (id < 0) {
            throw new IllegalArgumentException("O ID não pode ser negativo.");
        }
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome não pode ficar vazio.");
        }
        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("O CPF não pode ficar vazio.");
        }
        if (telefone == null || telefone.isBlank()) {
            throw new IllegalArgumentException("O telefone não pode ficar vazio.");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("O e-mail não pode ficar vazio.");
        }
        if (endereco == null || endereco.isBlank()) {
            throw new IllegalArgumentException("O endereço não pode ficar vazio.");
        }

        this.id = id;
        this.nome = nome.trim();
        this.cpf = cpf.trim().replace(".", "").replace("-", "");
        this.telefone = telefone.trim();
        this.email = email.trim();
        this.endereco = endereco.trim();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public String getEndereco() {
        return endereco;
    }
}
