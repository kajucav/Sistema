package Backend;

class clientes{
    int cpf;
    String nome;
    String email;
    int telefone;
    int cep;

    public void setCpf(int cpf){
        this.cpf = cpf;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setTelefone(int telefone){
        this.telefone = telefone;
    }

    public void setCep(int cep){
        this.cep = cep;
    }

    int getCpf(){
        return cpf;
    }

    String getNome(){
        return nome;
    }

    String getEmail(){
        return email;
    }

    int getTelefone(){
        return telefone;
    }

    int getCep(){
        return cep;
    }
}