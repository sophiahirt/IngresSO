package view;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import model.Evento;

public class TelaEventos extends javax.swing.JFrame {

    private final ModeloTabela modeloEventos
            = new ModeloTabela("Evento", "Preço", "Vagas", "Vendidos", "Disponíveis");

    public TelaEventos() {
        initComponents();

        tblEventos.setModel(modeloEventos);
        tblEventos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        lblTitulo.setFont(lblTitulo.getFont().deriveFont(Font.BOLD, 26f));
        painelTitulo.setBorder(BorderFactory.createEmptyBorder(6, 10, 0, 10));
        painelCadastro.setBorder(BorderFactory.createTitledBorder("Cadastrar evento"));
        scrollEventos.setBorder(BorderFactory.createTitledBorder("Eventos cadastrados"));

        setSize(900, 560);
        setLocationRelativeTo(null);
    }

    // ----- Cadastro -----

    public String getNomeEvento() {
        return txtEvento.getText().trim();
    }

    public String getPrecoEvento() {
        return txtPreco.getText().trim();
    }

    public String getVagasEvento() {
        return txtVagas.getText().trim();
    }

    public void limparCadastro() {
        txtEvento.setText("");
        txtPreco.setText("");
        txtVagas.setText("");
        txtEvento.requestFocus();
    }

    // ----- Tabela -----

    public int getEventoSelecionado() {
        return tblEventos.getSelectedRow();
    }

    public void atualizarEventos(List<Evento> eventos) {
        modeloEventos.setRowCount(0);
        for (Evento e : eventos) {
            modeloEventos.addRow(new Object[]{
                e.getNome(),
                Formato.moeda(e.getPreco()),
                e.getCapacidade(),
                e.getVendidos(),
                e.getDisponiveis()
            });
        }
    }

    // ----- Mensagens -----

    public void mostrarMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }

    public boolean confirmar(String mensagem) {
        return JOptionPane.showConfirmDialog(this, mensagem, "Confirmar",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    // ----- Listeners -----

    public void addCadastrarEventoListener(ActionListener l) {
        btnCadastrarEvento.addActionListener(l);
    }

    public void addExcluirEventoListener(ActionListener l) {
        btnExcluirEvento.addActionListener(l);
    }

    public void addIrParaVendasListener(ActionListener l) {
        btnIrParaVendas.addActionListener(l);
    }

    /**
     * Este método é gerado pelo editor visual (aba Design).
     * Não edite o conteúdo dele manualmente.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelTitulo = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblTela = new javax.swing.JLabel();
        painelCentro = new javax.swing.JPanel();
        painelCadastro = new javax.swing.JPanel();
        lblEvento = new javax.swing.JLabel();
        txtEvento = new javax.swing.JTextField();
        lblPreco = new javax.swing.JLabel();
        txtPreco = new javax.swing.JTextField();
        lblVagas = new javax.swing.JLabel();
        txtVagas = new javax.swing.JTextField();
        btnCadastrarEvento = new javax.swing.JButton();
        scrollEventos = new javax.swing.JScrollPane();
        tblEventos = new javax.swing.JTable();
        painelRodape = new javax.swing.JPanel();
        btnExcluirEvento = new javax.swing.JButton();
        btnIrParaVendas = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("IngresSÔ - Eventos");

        painelTitulo.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblTitulo.setText("IngresSÔ");
        painelTitulo.add(lblTitulo);

        lblTela.setText("Eventos");
        painelTitulo.add(lblTela);

        getContentPane().add(painelTitulo, java.awt.BorderLayout.NORTH);

        painelCentro.setLayout(new java.awt.BorderLayout());

        painelCadastro.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblEvento.setText("Evento:");
        painelCadastro.add(lblEvento);

        txtEvento.setColumns(15);
        painelCadastro.add(txtEvento);

        lblPreco.setText("Preço (R$):");
        painelCadastro.add(lblPreco);

        txtPreco.setColumns(6);
        painelCadastro.add(txtPreco);

        lblVagas.setText("Vagas:");
        painelCadastro.add(lblVagas);

        txtVagas.setColumns(4);
        painelCadastro.add(txtVagas);

        btnCadastrarEvento.setText("Cadastrar evento");
        painelCadastro.add(btnCadastrarEvento);

        painelCentro.add(painelCadastro, java.awt.BorderLayout.NORTH);

        scrollEventos.setViewportView(tblEventos);

        painelCentro.add(scrollEventos, java.awt.BorderLayout.CENTER);

        getContentPane().add(painelCentro, java.awt.BorderLayout.CENTER);

        painelRodape.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnExcluirEvento.setText("Excluir evento");
        painelRodape.add(btnExcluirEvento);

        btnIrParaVendas.setText("Ir para Vendas");
        painelRodape.add(btnIrParaVendas);

        getContentPane().add(painelRodape, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCadastrarEvento;
    private javax.swing.JButton btnExcluirEvento;
    private javax.swing.JButton btnIrParaVendas;
    private javax.swing.JLabel lblEvento;
    private javax.swing.JLabel lblPreco;
    private javax.swing.JLabel lblTela;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblVagas;
    private javax.swing.JPanel painelCadastro;
    private javax.swing.JPanel painelCentro;
    private javax.swing.JPanel painelRodape;
    private javax.swing.JPanel painelTitulo;
    private javax.swing.JScrollPane scrollEventos;
    private javax.swing.JTable tblEventos;
    private javax.swing.JTextField txtEvento;
    private javax.swing.JTextField txtPreco;
    private javax.swing.JTextField txtVagas;
    // End of variables declaration//GEN-END:variables
}
