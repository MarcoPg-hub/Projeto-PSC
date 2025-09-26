package model;

public class cadastroMedico {

    private int id_medico;
    private String CRM;
    private String NOME;
    private String TELEFONE;
    private String ENDERECO;
    private String ESPECIALIZACAO;
    private String DISPONIBILIDADE;
    private String email;

    public cadastroMedico() {}

    public cadastroMedico(String NOME, String CRM, String TELEFONE, String ENDERECO, String ESPECIALIZACAO, String DISPONIBILIDADE, String email) {
        this.CRM = CRM;
        this.NOME = NOME;
        this.TELEFONE = TELEFONE;
        this.ENDERECO = ENDERECO;
        this.ESPECIALIZACAO = ESPECIALIZACAO;
        this.DISPONIBILIDADE = DISPONIBILIDADE;
        this.email = email;
    }

    public int getId_medico() {
        return id_medico;
    }

    public void setId_medico(int id_medico) {
        this.id_medico = id_medico;
    }

    public String getCRM() {
        return CRM;
    }

    public void setCRM(String CRM) {
        this.CRM = CRM;
    }

    public String getNOME() {
        return NOME;
    }

    public void setNOME(String NOME) {
        this.NOME = NOME;
    }

    public String getTELEFONE() {
        return TELEFONE;
    }

    public void setTELEFONE(String TELEFONE) {
        this.TELEFONE = TELEFONE;
    }

    public String getENDERECO() {
        return ENDERECO;
    }

    public void setENDERECO(String ENDERECO) {
        this.ENDERECO = ENDERECO;
    }

    public String getESPECIALIZACAO() {
        return ESPECIALIZACAO;
    }

    public void setESPECIALIZACAO(String ESPECIALIZACAO) {
        this.ESPECIALIZACAO = ESPECIALIZACAO;
    }

    public String getDISPONIBILIDADE() {
        return DISPONIBILIDADE;
    }

    public void setDISPONIBILIDADE(String DISPONIBILIDADE) {
        this.DISPONIBILIDADE = DISPONIBILIDADE;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}