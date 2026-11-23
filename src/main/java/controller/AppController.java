package controller;

import dao.BancoException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import model.Bilheteria;
import view.TelaEventos;
import view.TelaVendas;

public class AppController {

    private final Bilheteria modelo = new Bilheteria();
    private final TelaEventos telaEventos = new TelaEventos();
    private final TelaVendas telaVendas = new TelaVendas();
    private final EventoController eventos;
    private final VendaController vendas;

    public AppController() {
        eventos = new EventoController(modelo, telaEventos);
        vendas = new VendaController(modelo, telaVendas);

        telaEventos.addIrParaVendasListener(e -> irParaVendas());
        telaVendas.addIrParaEventosListener(e -> irParaEventos());
    }

    private void irParaVendas() {
        vendas.atualizar();
        telaEventos.setVisible(false);
        telaVendas.setVisible(true);
    }

    private void irParaEventos() {
        eventos.atualizar();
        telaVendas.setVisible(false);
        telaEventos.setVisible(true);
    }

    public void iniciar() {
        telaEventos.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new AppController().iniciar();
            } catch (BancoException ex) {
                JOptionPane.showMessageDialog(null,
                        ex.getMessage() + "\n\nVerifique se o MySQL está ligado, se o banco "
                        + "'ingresso' foi criado (db.sql) e se o usuário e a senha em "
                        + "dao/ConexaoDB.java estão corretos.",
                        "IngresSÔ", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }
}
