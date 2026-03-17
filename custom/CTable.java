package custom;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * This class extends JTable, adding useful functionality and pre-setting many
 * parameters to our needs.
 * <p>
 * The class creates its own <code>DefaultTableModel</code> allowing the selection of
 * only one item at a time.
 * <p>
 * It also creates a <code>JScrollPane</code> and automatically puts the table inside it.
 * <p>
 * <strong>KEEP IN MIND:</strong> when adding this class to a <code>JPannel</code> you
 * must add the JScrollPane instead with <code>getScrollPane</code>
 */
public class CTable extends JTable {
    private JScrollPane scrollPane;
    private DefaultTableModel model;

    /**
     * The constructor for the class.
     * @param columnIdentifiers an array of Strings with the labels for each column
     */
    public CTable(String[] columnIdentifiers) {
        //this allows to select only one row at a time
        setRowSelectionAllowed(true);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //create the JScrollPane and put this inside
        scrollPane = new JScrollPane(this, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //this makes the cells of the table not editable
        model = new DefaultTableModel(){
            public boolean isCellEditable(int row, int column) {return false;}
        };

        //sets the model with the column identifiers
        setModel(model);
        model.setColumnIdentifiers(columnIdentifiers);
    }

    /**
     * Returns the <code>JScrollPane</code> that contains this table.
     * @return the <code>JScrollPane</code> for the table
     */
    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    /**
     * Returns an attribute value for the cell at <code>row</code> and <code>column</code>.
     * @param row the row whose value is to be queried
     * @param column the column whose value is to be queried
     * @return the value <code>Object</code> at the specified cell
     */
    public Object getValueAt(int row, int column) {
        return model.getValueAt(row, column);
    }

    /**
     * Adds a row to the end of the table. The new row will contain
     * the data in <code>row</code>.
     * @param row the data of the row being added
     */
    public void addRow(String[] row){
        model.addRow(row);
    }

    /**
     * Returns the data of a specific column on the selected row.
     * @param column the column of the selected row. Starts at index 0.
     * @return the data of the selected row and the specified
     * <code>column</code>. Returns an empty string if no row selected.
     */
    public String getSelectedRowColumn(int column){
        int row = getSelectedRow();
        if (row == -1)
            return "";

        return getModel().getValueAt(row, column).toString();
    }

    /**
     * Removes every row of the table.
     */
    public void removeTableElements(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
    }
}
