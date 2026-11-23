package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import model.Evento;
import model.Venda;

public class TelaVendas extends javax.swing.JFrame {

    private final ModeloTabela modeloEventos
            = new ModeloTabela("Evento", "Preço", "Disponíveis");
    private final ModeloTabela modeloVendas
            = new ModeloTabela("Comprador", "Evento", "Qtd", "Total");

    public TelaVendas() {
        initComponents();

        tblEventos.setModel(modeloEventos);
        tblVendas.setModel(modeloVendas);
        tblEventos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblVendas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        spnQuantidade.setModel(new SpinnerNumberModel(1, 1, 100, 1));

        lblTitulo.setFont(lblTitulo.getFont().deriveFont(Font.BOLD, 26f));
        painelTitulo.setBorder(BorderFactory.createEmptyBorder(6, 10, 0, 10));
        scrollEventos.setBorder(BorderFactory.createTitledBorder("Eventos disponíveis"));
        scrollVendas.setBorder(BorderFactory.createTitledBorder("Vendas realizadas"));
        painelVenda.setBorder(BorderFactory.createTitledBorder("Vender ingressos"));
        scrollVendas.setPreferredSize(new Dimension(100, 170));

        setSize(900, 560);
        setLocationRelativeTo(null);
    }

    public String getComprador() {
        return txtComprador.getText().trim();
    }

    public int getQuantidade() {
        try {
            spnQuantidade.commitEdit();
        } catch (ParseException ex) {
        }
        return (Integer) spnQuantidade.getValue();
    }

    public void limparVenda() {
        txtComprador.setText("");
        spnQuantidade.setValue(1);
        txtComprador.requestFocus();
    }
    
    public int getEventoSelecionado() {
        return tblEventos.getSelectedRow();
    }

    public int getVendaSelecionada() {
        return tblVendas.getSelectedRow();
    }

    public void selecionarEvento(int indice) {
        if (indice >= 0 && indice < tblEventos.getRowCount()) {
            tblEventos.setRowSelectionInterval(indice, indice);
        }
    }

    public void atualizarEventos(List<Evento> eventos) {
        modeloEventos.setRowCount(0);
        for (Evento e : eventos) {
            modeloEventos.addRow(new Object[]{
                e.getNome(),
                Formato.moeda(e.getPreco()),
                e.getDisponiveis()
            });
        }
    }

    public void atualizarVendas(List<Venda> vendas) {
        modeloVendas.setRowCount(0);
        for (Venda v : vendas) {
            modeloVendas.addRow(new Object[]{
                v.getComprador(),
                v.getEvento().getNome(),
                v.getQuantidade(),
                Formato.moeda(v.getTotal())
            });
        }
    }

    public void setTotalArrecadado(double total) {
        lblTotal.setText("Total arrecadado: " + Formato.moeda(total));
    }


    public void mostrarMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }

    public boolean confirmar(String mensagem) {
        return JOptionPane.showConfirmDialog(this, mensagem, "Confirmar",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public void addVenderListener(ActionListener l) {
        btnVender.addActionListener(l);
    }

    public void addCancelarVendaListener(ActionListener l) {
        btnCancelarVenda.addActionListener(l);
    }

    public void addIrParaEventosListener(ActionListener l) {
        btnIrParaEventos.addActionListener(l);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelTitulo = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblTela = new javax.swing.JLabel();
        painelCentro = new javax.swing.JPanel();
        scrollEventos = new javax.swing.JScrollPane();
        tblEventos = new javax.swing.JTable();
        scrollVendas = new javax.swing.JScrollPane();
        tblVendas = new javax.swing.JTable();
        painelSul = new javax.swing.JPanel();
        painelVenda = new javax.swing.JPanel();
        lblComprador = new javax.swing.JLabel();
        txtComprador = new javax.swing.JTextField();
        lblQuantidade = new javax.swing.JLabel();
        spnQuantidade = new javax.swing.JSpinner();
        btnVender = new javax.swing.JButton();
        btnCancelarVenda = new javax.swing.JButton();
        lblTotal = new javax.swing.JLabel();
        painelNavegacao = new javax.swing.JPanel();
        btnIrParaEventos = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("IngresSÔ - Vendas");

        painelTitulo.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblTitulo.setText("IngresSÔ");
        painelTitulo.add(lblTitulo);

        lblTela.setText("Vendas");
        painelTitulo.add(lblTela);

        getContentPane().add(painelTitulo, java.awt.BorderLayout.NORTH);

        painelCentro.setLayout(new java.awt.BorderLayout());

        scrollEventos.setViewportView(tblEventos);

        painelCentro.add(scrollEventos, java.awt.BorderLayout.CENTER);

        scrollVendas.setViewportView(tblVendas);

        painelCentro.add(scrollVendas, java.awt.BorderLayout.SOUTH);

        getContentPane().add(painelCentro, java.awt.BorderLayout.CENTER);

        painelSul.setLayout(new java.awt.BorderLayout());

        painelVenda.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblComprador.setText("Comprador:");
        painelVenda.add(lblComprador);

        txtComprador.setColumns(12);
        painelVenda.add(txtComprador);

        lblQuantidade.setText("Quantidade:");
        painelVenda.add(lblQuantidade);
        painelVenda.add(spnQuantidade);

        btnVender.setText("Vender");
        painelVenda.add(btnVender);

        btnCancelarVenda.setText("Cancelar venda");
        painelVenda.add(btnCancelarVenda);

        lblTotal.setText("Total arrecadado: R$ 0,00");
        painelVenda.add(lblTotal);

        painelSul.add(painelVenda, java.awt.BorderLayout.CENTER);

        painelNavegacao.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnIrParaEventos.setText("Ir para Eventos");
        painelNavegacao.add(btnIrParaEventos);

        painelSul.add(painelNavegacao, java.awt.BorderLayout.SOUTH);

        getContentPane().add(painelSul, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelarVenda;
    private javax.swing.JButton btnIrParaEventos;
    private javax.swing.JButton btnVender;
    private javax.swing.JLabel lblComprador;
    private javax.swing.JLabel lblQuantidade;
    private javax.swing.JLabel lblTela;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JPanel painelCentro;
    private javax.swing.JPanel painelNavegacao;
    private javax.swing.JPanel painelSul;
    private javax.swing.JPanel painelTitulo;
    private javax.swing.JPanel painelVenda;
    private javax.swing.JScrollPane scrollEventos;
    private javax.swing.JScrollPane scrollVendas;
    private javax.swing.JSpinner spnQuantidade;
    private javax.swing.JTable tblEventos;
    private javax.swing.JTable tblVendas;
    private javax.swing.JTextField txtComprador;
    // End of variables declaration//GEN-END:variables
}
