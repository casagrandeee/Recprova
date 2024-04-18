public class Person {
    private int id;
    private String nome;
    private String rua;
    private int cep;

    public Person(String nome, String rua, int cep) {
        this.nome = nome;
        this.rua = rua;
        this.cep = cep;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public int getCep() {
        return cep;
    }

    public void setCep(int cep) {
        this.cep = cep;
    }
}
