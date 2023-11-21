package gui.datos;

import controlador.Constantes;
import controlador.Coordinador;
import modelo.Tramo;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

/**
 * Clase TramoList que representa la interfaz gráfica para la lista de tramos.
 */
public class TramoList extends JDialog {
    
    private Coordinador coordinador;
    private ResourceBundle resourceBundle;
    private JButton btnInsertar, btnSalir;
    private JTable table;
    private Tramo tramo;
    private JScrollPane scrollPane;
    private int accion;

    /**
     * Constructor de la clase TramoList.
     */
    public TramoList() {
    }

    /**
     * Inicializa la interfaz gráfica y configura los componentes necesarios.
     */
    public void init() {
        resourceBundle = coordinador.getResourceBundle();
        setBounds(100, 100, 635, 360);

        setTitle(resourceBundle.getString("TramoList_title_window"));
        getContentPane().setLayout(null);

        JLabel titulo = new JLabel(resourceBundle.getString("TramoList_title"));
        titulo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        titulo.setBounds(90, 11, 421, 14);
        getContentPane().add(titulo);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 30, 600, 250);
        add(scrollPane);

        table = new JTable();
        table.setRowSelectionAllowed(false);
        table.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        resourceBundle.getString("TramoList_stop_start"),
                        resourceBundle.getString("TramoList_stop_end"),
                        resourceBundle.getString("TramoList_time"),
                        resourceBundle.getString("TramoList_type"),
                        resourceBundle.getString("TramoList_update"),
                        resourceBundle.getString("TramoList_delete")
                }));
        table.getColumnModel().getColumn(0).setPreferredWidth(250);
        table.getColumnModel().getColumn(1).setPreferredWidth(250);
        table.setBorder(new LineBorder(new Color(0, 0, 0)));
        table.setBounds(10, 30, 464, 250);
        getContentPane().add(table);

        btnInsertar = new JButton(resourceBundle.getString("TramoList_insert"));
        btnInsertar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnInsertar.setBounds(10, 291, 89, 23);
        btnInsertar.setFocusable(false);
        getContentPane().add(btnInsertar);

        btnSalir = new JButton(resourceBundle.getString("TramoList_exit"));
        btnSalir.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnSalir.setBounds(109, 291, 89, 23);
        btnSalir.setFocusable(false);
        getContentPane().add(btnSalir);
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coordinador.salirTramoList();
            }
        });

        table.getColumn(resourceBundle.getString("TramoList_update")).setCellRenderer(new ButtonRenderer());
        table.getColumn(resourceBundle.getString("TramoList_update")).setCellEditor(new ButtonEditor(new JCheckBox()));
        table.getColumn(resourceBundle.getString("TramoList_delete")).setCellRenderer(new ButtonRenderer());
        table.getColumn(resourceBundle.getString("TramoList_delete")).setCellEditor(new ButtonEditor(new JCheckBox()));
        scrollPane.setViewportView(table);

        Handler handler = new Handler();
        btnInsertar.addActionListener(handler);

        setModal(true);
    }

    /**
     * Clase interna Handler que implementa ActionListener para manejar eventos de
     * los botones.
     */
    private class Handler implements ActionListener {

        /**
         * Maneja los eventos de los botones.
         *
         * @param event Evento de acción.
         */
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == btnInsertar)
                coordinador.insertarTramoForm();
        }
    }

    /**
     * Carga los datos en la tabla.
     */
    public void loadTable() {
        // Eliminar todas las filas
        ((DefaultTableModel) table.getModel()).setRowCount(0);
        for (Tramo tramo : coordinador.listarTramos())
            if (tramo instanceof Tramo)
                addRow((Tramo) tramo);
    }

    /**
     * Agrega una fila a la tabla con los datos del tramo.
     *
     * @param tramo Tramo a agregar.
     */
    public void addRow(Tramo tramo) {
        Object[] row = new Object[table.getModel().getColumnCount()];
        row[0] = tramo.getInicio().getCodigo() + " - " + tramo.getInicio().getDireccion();
        row[1] = tramo.getFin().getCodigo() + " - " + tramo.getFin().getDireccion();
        row[2] = tramo.getTiempo();
        row[3] = tramo.getTipo();
        row[4] = "edit";
        row[5] = "drop";
        ((DefaultTableModel) table.getModel()).addRow(row);
    }

    /**
     * Actualiza una fila en la tabla con los datos del tramo.
     *
     * @param row Índice de la fila a actualizar.
     */
    private void updateRow(int row) {
        table.setValueAt(tramo.getInicio(), row, 0);
        table.setValueAt(tramo.getFin(), row, 1);
        table.setValueAt(tramo.getTiempo(), row, 2);
        table.setValueAt(tramo.getTipo(), row, 3);
    }

    /**
     * Clase interna ButtonRenderer que implementa TableCellRenderer para renderizar
     * botones en las celdas de la tabla.
     */
    class ButtonRenderer extends JButton implements TableCellRenderer {

        /**
         * Constructor de la clase ButtonRenderer.
         * Configura el renderizador de celdas para mostrar botones en una tabla.
         */
        public ButtonRenderer() {
            setOpaque(true);
        }

        /**
         * Obtiene el componente de renderizado de celdas para mostrar el botón en la
         * tabla.
         *
         * @param table      Tabla a la que pertenece el renderizador.
         * @param value      Valor de la celda.
         * @param isSelected Indica si la celda está seleccionada.
         * @param hasFocus   Indica si la celda tiene el foco.
         * @param row        Índice de la fila de la celda.
         * @param column     Índice de la columna de la celda.
         * @return Componente de renderizado de celdas.
         */
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
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

    /**
     * Clase interna ButtonEditor que extiende DefaultCellEditor para manejar la
     * edición de botones en las celdas de la tabla.
     */
    class ButtonEditor extends DefaultCellEditor {

        protected JButton button;
        private String label;
        private boolean isPushed;
        private JTable table;
        private boolean isDeleteRow = false;
        private boolean isUpdateRow = false;

        /**
         * Constructor de la clase ButtonEditor.
         *
         * @param checkBox Componente de casilla de verificación asociado al editor.
         */
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

        /**
         * Obtiene el componente de editor de celdas para mostrar el botón en la tabla.
         *
         * @param table      Tabla a la que pertenece el editor.
         * @param value      Valor de la celda.
         * @param isSelected Indica si la celda está seleccionada.
         * @param row        Índice de la fila de la celda.
         * @param column     Índice de la columna de la celda.
         * @return Componente de editor de celdas.
         */
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

        /**
         * Obtiene el valor de la celda después de que se ha producido la edición.
         *
         * @return Valor de la celda después de la edición.
         */
        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                int pI = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString().split(" - ")[0]);
                int pF = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 1).toString().split(" - ")[0]);
                int tipo = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 3).toString());
                Tramo t = (Tramo) coordinador.buscarTramo(
                        new Tramo(coordinador.listarParadas().get(pI), coordinador.listarParadas().get(pF), 0, tipo));
                if (label.equals("edit"))
                    coordinador.modificarTramoForm(t);
                else
                    coordinador.borrarTramoForm(t);
            }
            if (accion == Constantes.BORRAR)
                isDeleteRow = true;
            if (accion == Constantes.MODIFICAR)
                isUpdateRow = true;
            isPushed = false;
            return new String(label);
        }

        /**
         * Detiene la edición de la celda y maneja eventos cuando se detiene la edición.
         *
         * @return true si se detiene la edición, false en caso contrario.
         */
        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        /**
         * Maneja eventos cuando se detiene la edición y actualiza la tabla según sea
         * necesario.
         */
        protected void fireEditingStopped() {
            super.fireEditingStopped();
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            if (isDeleteRow)
                tableModel.removeRow(table.getSelectedRow());
            if (isUpdateRow)
                updateRow(table.getSelectedRow());
        }
    }

    /**
     * Establece el coordinador para la clase TramoList.
     *
     * @param coordinador Coordinador a establecer.
     */
    public void setCoordinador(Coordinador coordinador) {
        this.coordinador = coordinador;
    }

    /**
     * Establece la acción para la clase TramoList.
     *
     * @param accion Acción a establecer.
     */
    public void setAccion(int accion) {
        this.accion = accion;
    }

    /**
     * Establece el tramo para la clase TramoList.
     *
     * @param tramo Tramo a establecer.
     */
    public void setTramo(Tramo tramo) {
        this.tramo = tramo;
    }
}
