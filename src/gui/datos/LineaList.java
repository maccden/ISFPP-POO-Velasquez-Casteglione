package gui.datos;

import controlador.Constantes;
import controlador.Coordinador;
import modelo.Linea;
import modelo.Parada;
import util.Time;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public class LineaList extends JDialog {
    private Coordinador coordinador;
    private ResourceBundle resourceBundle;
    private JButton btnInsertar, btnSalir;
    private JTable table;
    private Linea linea;
    private JScrollPane scrollPane;
    private int accion;
    public LineaList() {

    }

    public void init() {
        resourceBundle = coordinador.getResourceBundle();
        setBounds(100, 100, 825, 360);

        setTitle(resourceBundle.getString("LineaList_title_window"));
        getContentPane().setLayout(null);

        JLabel titulo = new JLabel(resourceBundle.getString("LineaList_title"));
        titulo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        titulo.setBounds(180, 11, 421, 14);
        getContentPane().add(titulo);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 30, 790, 250);
        add(scrollPane);

        table = new JTable();
        table.setRowSelectionAllowed(false);
        table.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        resourceBundle.getString("LineaList_code"),
                        resourceBundle.getString("LineaList_start_time"),
                        resourceBundle.getString("LineaList_end_time"),
                        resourceBundle.getString("LineaList_frecuency"),
                        resourceBundle.getString("LineaList_stops"),
                        resourceBundle.getString("LineaList_update"),
                        resourceBundle.getString("LineaList_delete")
                }
        ));
        table.getColumnModel().getColumn(4).setPreferredWidth(390);
        table.setBorder(new LineBorder(new Color(0, 0, 0)));
        table.setBounds(10, 30, 464, 250);
        getContentPane().add(table);

        btnInsertar = new JButton(resourceBundle.getString("LineaList_insert"));
        btnInsertar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnInsertar.setBounds(10, 291, 89, 23);
        btnInsertar.setFocusable(false);
        getContentPane().add(btnInsertar);

        btnSalir = new JButton(resourceBundle.getString("LineaList_exit"));
        btnSalir.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnSalir.setBounds(109, 291, 89, 23);
        btnSalir.setFocusable(false);
        getContentPane().add(btnSalir);
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coordinador.salirLineaList();
            }
        });

        table.getColumn(resourceBundle.getString("LineaList_update")).setCellRenderer(new ButtonRenderer());
        table.getColumn(resourceBundle.getString("LineaList_update")).setCellEditor(new ButtonEditor(new JCheckBox()));
        table.getColumn(resourceBundle.getString("LineaList_delete")).setCellRenderer(new ButtonRenderer());
        table.getColumn(resourceBundle.getString("LineaList_delete")).setCellEditor(new ButtonEditor(new JCheckBox()));
        scrollPane.setViewportView(table);

        Handler handler = new Handler();
        btnInsertar.addActionListener(handler);

        setModal(true);
    }

    private class Handler implements ActionListener {
        public void actionPerformed(ActionEvent event) {

            if (event.getSource() == btnInsertar)
                coordinador.insertarLineaForm();
        }
    }

    public void loadTable() {
        // Eliminar todas las filas
        ((DefaultTableModel) table.getModel()).setRowCount(0);
        for (Linea l : coordinador.listarLineas().values())
            if (l instanceof Linea)
                addRow((Linea) l);
    }

    public void addRow(Linea linea) {
        Object[] row = new Object[table.getModel().getColumnCount()];
        row[0] = linea.getCodigo();
        row[1] = Time.toTime(linea.getComienza());
        row[2] = Time.toTime(linea.getFinaliza());
        row[3] = linea.getFrecuencia() + "m";
        row[4] = "";
        for (Parada parada: linea.getParadas())
            if (linea.getParadas().indexOf(parada) == linea.getParadas().size() - 1)
                row[4] += String.valueOf(parada.getCodigo());
            else
                row[4] += parada.getCodigo() + " - ";
        row[5] = "edit";
        row[6] = "drop";
        ((DefaultTableModel) table.getModel()).addRow(row);
    }

    private void updateRow(int row) {
        table.setValueAt(linea.getCodigo(), row, 0);
        table.setValueAt(Time.toTime(linea.getComienza()), row, 1);
        table.setValueAt(Time.toTime(linea.getFinaliza()), row, 2);
        table.setValueAt(linea.getFrecuencia() + "m", row, 3);
        StringBuilder columna = new StringBuilder(new String());
        for (Parada parada: linea.getParadas())
            if (linea.getParadas().indexOf(parada) == linea.getParadas().size() - 1)
                columna.append(parada.getCodigo());
            else
                columna.append(parada.getCodigo()).append(" - ");
        table.setValueAt(columna, row, 4);
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(UIManager.getColor("Button.background"));
            }
            // setText((value == null) ? "" : value.toString());
            Icon icon = null;
            if (value.toString().equals("edit"))
                icon = new ImageIcon(getClass().getResource("/imagen/b_edit.png"));
            if (value.toString().equals("drop"))
                icon = new ImageIcon(getClass().getResource("/imagen/b_drop.png"));
            setIcon(icon);
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {

        protected JButton button;
        private String label;
        private boolean isPushed;
        private JTable table;
        private boolean isDeleteRow = false;
        private boolean isUpdateRow = false;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
            }

            label = (value == null) ? "" : value.toString();
            // button.setText(label);
            Icon icon = null;
            if (value.toString().equals("edit"))
                icon = new ImageIcon(getClass().getResource("/imagen/b_edit.png"));
            if (value.toString().equals("drop"))
                icon = new ImageIcon(getClass().getResource("/imagen/b_drop.png"));
            button.setIcon(icon);
            isPushed = true;
            this.table = table;
            isDeleteRow = false;
            isUpdateRow = false;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                String codigo = table.getValueAt(table.getSelectedRow(), 0).toString();
                Linea l = (Linea) coordinador.buscarLinea(new Linea(codigo, 0, 0, 0));
                if (label.equals("edit"))
                    coordinador.modificarLineaForm(l);
                else
                    coordinador.borrarLineaForm(l);
            }
            if (accion == Constantes.BORRAR)
                isDeleteRow = true;
            if (accion == Constantes.MODIFICAR)
                isUpdateRow = true;
            isPushed = false;
            return new String(label);
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();

            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            if (isDeleteRow)
                tableModel.removeRow(table.getSelectedRow());

            if (isUpdateRow) {
                updateRow(table.getSelectedRow());
            }

        }
    }

    public void setCoordinador(Coordinador coordinador) {
        this.coordinador = coordinador;
    }

    public void setAccion(int accion) {
        this.accion = accion;
    }

    public void setLinea(Linea linea) {
        this.linea = linea;
    }
}
