package model;

public class Venda {

    private final int id;
    private final String comprador;
    private final Evento evento;
    private final int quantidade;

    public Venda(int id, String comprador, Evento evento, int quantidade) {
        this.id = id;
        this.comprador = comprador;
        this.evento = evento;
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public String getComprador() {
        return comprador;
    }

    public Evento getEvento() {
        return evento;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getTotal() {
        return evento.getPreco() * quantidade;
    }
}
