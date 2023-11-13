package gui;

import controlador.Constantes;
import controlador.Coordinador;
import modelo.Parada;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ParadaList extends JDialog {
    private Coordinador coordinador;
    private JButton btnInsertar, btnSalir;
    private JTable table;
    private Parada parada;
    private JScrollPane scrollPane;
    private int accion;
    public ParadaList() {
        setBounds(100, 100, 500, 360);

        setTitle("Lista de paradas");
        getContentPane().setLayout(null);

        JLabel titulo = new JLabel("Seleccione una de las opciones para realizar acciones en las paradas cargadas:");
        titulo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        titulo.setBounds(31, 11, 421, 14);
        getContentPane().add(titulo);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 30, 464, 250);
        add(scrollPane);

        table = new JTable();
        table.setRowSelectionAllowed(false);
        table.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "Codigo", "Direccion", "Modificar", "Eliminar"
                }
        ));
        table.getColumnModel().getColumn(1).setPreferredWidth(250);
        table.setBorder(new LineBorder(new Color(0, 0, 0)));
        table.setBounds(10, 30, 464, 250);
        getContentPane().add(table);

        btnInsertar = new JButton("Insertar");
        btnInsertar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnInsertar.setBounds(10, 291, 89, 23);
        btnInsertar.setFocusable(false);
        getContentPane().add(btnInsertar);

        btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnSalir.setBounds(109, 291, 89, 23);
        btnSalir.setFocusable(false);
        getContentPane().add(btnSalir);
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coordinador.salirParadaList();
            }
        });

        table.getColumn("Modificar").setCellRenderer(new ButtonRenderer());
        table.getColumn("Modificar").setCellEditor(new ButtonEditor(new JCheckBox()));
        table.getColumn("Eliminar").setCellRenderer(new ButtonRenderer());
        table.getColumn("Eliminar").setCellEditor(new ButtonEditor(new JCheckBox()));
        scrollPane.setViewportView(table);

        Handler handler = new Handler();
        btnInsertar.addActionListener(handler);

        setModal(true);
    }

    private class Handler implements ActionListener {
        public void actionPerformed(ActionEvent event) {

            if (event.getSource() == btnInsertar)
                coordinador.insertarParadaForm();
        }
    }

    public void loadTable() {
        // Eliminar todas las filas
        ((DefaultTableModel) table.getModel()).setRowCount(0);
        for (Parada parada : coordinador.listarParadas().values())
            if (parada instanceof Parada)
                addRow((Parada) parada);
    }

    public void addRow(Parada parada) {
        Object[] row = new Object[table.getModel().getColumnCount()];
        row[0] = parada.getCodigo();
        row[1] = parada.getDireccion();
        row[2] = "edit";
        row[3] = "drop";
        ((DefaultTableModel) table.getModel()).addRow(row);
    }

    private void updateRow(int row) {
        table.setValueAt(parada.getCodigo(), row, 0);
        table.setValueAt(parada.getDireccion(), row, 1);
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
                String id = table.getValueAt(table.getSelectedRow(), 0).toString();
                Parada p = (Parada) coordinador.buscarParada(new Parada(Integer.parseInt(id), null));
                if (label.equals("edit"))
                    coordinador.modificarParadaForm(p);
                else
                    coordinador.borrarParadaForm(p);
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

    public void setParada(Parada parada) {
        this.parada = parada;
    }
}
