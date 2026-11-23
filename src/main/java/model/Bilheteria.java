package model;

import dao.EventoDAO;
import dao.VendaDAO;
import java.util.ArrayList;
import java.util.List;

public class Bilheteria {

    private final EventoDAO eventoDAO = new EventoDAO();
    private final VendaDAO vendaDAO = new VendaDAO();

    private List<Evento> eventos = new ArrayList<>();
    private List<Venda> vendas = new ArrayList<>();

    public Bilheteria() {
        recarregar();
    }

    public final void recarregar() {
        eventos = eventoDAO.listar();
        vendas = vendaDAO.listar(eventos);
    }

    public void cadastrarEvento(String nome, double preco, int vagas) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Informe o nome do evento.");
        }
        if (preco < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo.");
        }
        if (vagas <= 0) {
            throw new IllegalArgumentException("O número de vagas deve ser maior que zero.");
        }
        eventoDAO.inserir(nome.trim(), preco, vagas);
        recarregar();
    }

    public void removerEvento(int indice) {
        Evento evento = eventos.get(indice);
        for (Venda v : vendas) {
            if (v.getEvento().getId() == evento.getId()) {
                throw new IllegalArgumentException(
                        "Este evento tem vendas registradas. Cancele as vendas antes de excluí-lo.");
            }
        }
        eventoDAO.excluir(evento.getId());
        recarregar();
    }

    public void vender(int indiceEvento, String comprador, int quantidade) {
        if (comprador == null || comprador.trim().isEmpty()) {
            throw new IllegalArgumentException("Informe o nome do comprador.");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }
        Evento evento = eventos.get(indiceEvento);
        if (quantidade > evento.getDisponiveis()) {
            throw new IllegalArgumentException(
                    "Restam apenas " + evento.getDisponiveis() + " ingresso(s) para este evento.");
        }
        vendaDAO.inserir(evento.getId(), comprador.trim(), quantidade);
        recarregar();
    }

    public void cancelarVenda(int indiceVenda) {
        vendaDAO.excluir(vendas.get(indiceVenda).getId());
        recarregar();
    }

    public double getTotalArrecadado() {
        double total = 0;
        for (Venda v : vendas) {
            total += v.getTotal();
        }
        return total;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public List<Venda> getVendas() {
        return vendas;
    }
}
