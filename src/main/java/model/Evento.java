package model;

public class Evento {

    private final int id;
    private final String nome;
    private final double preco;
    private final int capacidade;
    private final int vendidos;

    public Evento(int id, String nome, double preco, int capacidade, int vendidos) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.capacidade = capacidade;
        this.vendidos = vendidos;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public int getVendidos() {
        return vendidos;
    }

    public int getDisponiveis() {
        return capacidade - vendidos;
    }
}
