package model;

public class cadastroPaciente {
    private int id;
    private String nomePaciente;
    private String enderecoPaciente;
    private String cpf;
    private String profissao;
    private String nascimento;
    private String sexo;
    private String telefonePaciente;
    private String celular;
    private String emailPaciente;

    public cadastroPaciente(String nomePaciente, String enderecoPaciente, String cpf, String profissao, String nascimento, String sexo, String telefonePaciente, String celular, String emailPaciente) {
        this.nomePaciente = nomePaciente;
        this.enderecoPaciente = enderecoPaciente;
        this.cpf = cpf;
        this.profissao = profissao;
        this.nascimento = nascimento;
        this.sexo = sexo;
        this.telefonePaciente = telefonePaciente;
        this.celular = celular;
        this.emailPaciente = emailPaciente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public String getEnderecoPaciente() {
        return enderecoPaciente;
    }

    public void setEnderecoPaciente(String enderecoPaciente) {
        this.enderecoPaciente = enderecoPaciente;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTelefonePaciente() {
        return telefonePaciente;
    }

    public void setTelefonePaciente(String telefonePaciente) {
        this.telefonePaciente = telefonePaciente;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmailPaciente() {
        return emailPaciente;
    }

    public void setEmailPaciente(String emailPaciente) {
        this.emailPaciente = emailPaciente;
    }
}