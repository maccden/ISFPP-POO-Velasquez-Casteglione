package gui.datos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import controlador.Constantes;
import controlador.Coordinador;
import modelo.Linea;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LineaList extends JDialog {
    private Coordinador coordinador;
    private int accion;
    private Linea linea;
    private JPanel aplicacion;
    private JScrollPane scrollPane;
    private JTable tableLinea;
    private JButton btnInsertar, btnSalir;

    public LineaList() {
        setBounds(100, 100, 756, 366);
        setTitle("MVC de Lineas");
        aplicacion = new JPanel();
        aplicacion.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(aplicacion);
        aplicacion.setLayout(null);

        btnInsertar = new JButton("Insertar");
        btnInsertar.setBounds(38, 280, 114, 32);
        btnInsertar.setFocusable(false);
        aplicacion.add(btnInsertar);

        btnSalir = new JButton("Salir");
        btnSalir.setBounds(170, 280, 114, 32);
        btnSalir.setFocusable(false);
        aplicacion.add(btnSalir);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(38, 25, 673, 244);
        aplicacion.add(scrollPane);

        tableLinea = new JTable();
        tableLinea.setModel(
                new DefaultTableModel(new Object[][] {}, new String[] { "Codigo", "Modificar", "Borrar" }) {
                    boolean[] columnEditables = new boolean[] { false, true, true };

                    public boolean isCellEditable(int row, int column) {
                        return columnEditables[column];
                    }
                });

        tableLinea.getColumn("Modificar").setCellRenderer(new ButtonRenderer());
        tableLinea.getColumn("Modificar").setCellEditor(new ButtonEditor(new JCheckBox()));
        tableLinea.getColumn("Borrar").setCellRenderer(new ButtonRenderer());
        tableLinea.getColumn("Borrar").setCellEditor(new ButtonEditor(new JCheckBox()));
        scrollPane.setViewportView(tableLinea);

        btnInsertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coordinador.insertarLineaForm();
            }
        });

        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coordinador.salirLineaList();
            }
        });

        setModal(true);
    }

    public void loadTable() {
        // Eliminar todas las filas
        ((DefaultTableModel) tableLinea.getModel()).setRowCount(0);
        for (Linea linea : coordinador.listarLineas().values())
            if (linea instanceof Linea)
                addRow((Linea) linea);
    }

    public void addRow(Linea linea) {
        Object[] row = new Object[tableLinea.getModel().getColumnCount()];
        row[0] = linea.getCodigo();
        row[1] = "edit";
        row[2] = "drop";
        ((DefaultTableModel) tableLinea.getModel()).addRow(row);
    }

    private void updateRow(int row) {
        tableLinea.setValueAt(linea.getCodigo(), row, 0);
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
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                                                     int column) {

            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
            }

            label = (value == null) ? "" : value.toString();
            // button.setText(label);
            isPushed = true;
            this.table = table;
            isDeleteRow = false;
            isUpdateRow = false;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                String id = tableLinea.getValueAt(tableLinea.getSelectedRow(), 0).toString();
                Linea linea = (Linea) coordinador.buscarLinea(new Linea(id));
                if (label.equals("edit"))
                    coordinador.modificarLineaForm(linea);
                else
                    coordinador.borrarLineaForm(linea);
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
}
