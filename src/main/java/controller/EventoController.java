package controller;

import dao.BancoException;
import model.Bilheteria;
import view.TelaEventos;

public class EventoController {

    private final Bilheteria modelo;
    private final TelaEventos tela;

    public EventoController(Bilheteria modelo, TelaEventos tela) {
        this.modelo = modelo;
        this.tela = tela;

        this.tela.addCadastrarEventoListener(e -> cadastrarEvento());
        this.tela.addExcluirEventoListener(e -> excluirEvento());

        atualizar();
    }

    public void atualizar() {
        tela.atualizarEventos(modelo.getEventos());
    }

    private void cadastrarEvento() {
        try {
            double preco = Double.parseDouble(tela.getPrecoEvento().replace(",", "."));
            int vagas = Integer.parseInt(tela.getVagasEvento());
            modelo.cadastrarEvento(tela.getNomeEvento(), preco, vagas);
            tela.limparCadastro();
            atualizar();
        } catch (NumberFormatException ex) {
            tela.mostrarMensagem("Informe o preço (ex.: 50,00) e as vagas (número inteiro).");
        } catch (IllegalArgumentException | BancoException ex) {
            tela.mostrarMensagem(ex.getMessage());
        }
    }

    private void excluirEvento() {
        int indice = tela.getEventoSelecionado();
        if (indice < 0) {
            tela.mostrarMensagem("Selecione um evento na tabela.");
            return;
        }
        if (!tela.confirmar("Excluir o evento selecionado?")) {
            return;
        }
        try {
            modelo.removerEvento(indice);
            atualizar();
        } catch (IllegalArgumentException | BancoException ex) {
            tela.mostrarMensagem(ex.getMessage());
        }
    }
}
