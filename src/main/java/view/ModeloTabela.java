package view;

import javax.swing.table.DefaultTableModel;

public class ModeloTabela extends DefaultTableModel {

    public ModeloTabela(String... colunas) {
        super(colunas, 0);
    }

    @Override
    public boolean isCellEditable(int linha, int coluna) {
        return false;
    }
}
