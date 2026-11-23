package controller;

import dao.BancoException;
import model.Bilheteria;
import view.TelaVendas;

public class VendaController {

    private final Bilheteria modelo;
    private final TelaVendas tela;

    public VendaController(Bilheteria modelo, TelaVendas tela) {
        this.modelo = modelo;
        this.tela = tela;

        this.tela.addVenderListener(e -> vender());
        this.tela.addCancelarVendaListener(e -> cancelarVenda());

        atualizar();
    }

    public void atualizar() {
        tela.atualizarEventos(modelo.getEventos());
        tela.atualizarVendas(modelo.getVendas());
        tela.setTotalArrecadado(modelo.getTotalArrecadado());
    }

    private void vender() {
        int indice = tela.getEventoSelecionado();
        if (indice < 0) {
            tela.mostrarMensagem("Selecione um evento na tabela.");
            return;
        }
        try {
            modelo.vender(indice, tela.getComprador(), tela.getQuantidade());
            tela.limparVenda();
            atualizar();
            tela.selecionarEvento(indice);
        } catch (IllegalArgumentException | BancoException ex) {
            tela.mostrarMensagem(ex.getMessage());
        }
    }

    private void cancelarVenda() {
        int indice = tela.getVendaSelecionada();
        if (indice < 0) {
            tela.mostrarMensagem("Selecione uma venda na tabela.");
            return;
        }
        if (!tela.confirmar("Cancelar a venda selecionada?")) {
            return;
        }
        try {
            modelo.cancelarVenda(indice);
            atualizar();
        } catch (BancoException ex) {
            tela.mostrarMensagem(ex.getMessage());
        }
    }
}
