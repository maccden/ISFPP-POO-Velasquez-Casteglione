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

/**
 * La clase LineaList representa una ventana de lista de líneas en la interfaz
 * de usuario.
 * Proporciona funcionalidades para mostrar, insertar, modificar y eliminar
 * líneas.
 */
public class LineaList extends JDialog {

    private Coordinador coordinador;
    private ResourceBundle resourceBundle;
    private JButton btnInsertar, btnSalir;
    private JTable table;
    private Linea linea;
    private JScrollPane scrollPane;
    private int accion;

    /**
     * Constructor por defecto para LineaList.
     */
    public LineaList() {
    }

    /**
     * Inicializa la ventana de lista de líneas.
     */
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
                }));
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

    /**
     * Clase interna para manejar eventos de acción en la ventana.
     */
    private class Handler implements ActionListener {

        /**
         * Maneja eventos de acción en la ventana.
         *
         * @param event El evento de acción.
         */
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == btnInsertar)
                coordinador.insertarLineaForm();
        }
    }

    /**
     * Carga datos en la tabla de líneas desde el coordinador.
     */
    public void loadTable() {
        // Eliminar todas las filas
        ((DefaultTableModel) table.getModel()).setRowCount(0);
        for (Linea l : coordinador.listarLineas().values())
            if (l instanceof Linea)
                addRow((Linea) l);
    }

    /**
     * Agrega una fila a la tabla con información de una línea.
     *
     * @param linea La línea a agregar.
     */
    public void addRow(Linea linea) {
        Object[] row = new Object[table.getModel().getColumnCount()];
        row[0] = linea.getCodigo();
        row[1] = Time.toTime(linea.getComienza());
        row[2] = Time.toTime(linea.getFinaliza());
        row[3] = linea.getFrecuencia() + "m";
        row[4] = "";
        for (Parada parada : linea.getParadas())
            if (linea.getParadas().indexOf(parada) == linea.getParadas().size() - 1)
                row[4] += String.valueOf(parada.getCodigo());
            else
                row[4] += parada.getCodigo() + " - ";
        row[5] = "edit";
        row[6] = "drop";
        ((DefaultTableModel) table.getModel()).addRow(row);
    }

    /**
     * Actualiza una fila en la tabla con la información de la línea actual.
     *
     * @param row La fila a actualizar.
     */
    private void updateRow(int row) {
        table.setValueAt(linea.getCodigo(), row, 0);
        table.setValueAt(Time.toTime(linea.getComienza()), row, 1);
        table.setValueAt(Time.toTime(linea.getFinaliza()), row, 2);
        table.setValueAt(linea.getFrecuencia() + "m", row, 3);
        StringBuilder columna = new StringBuilder(new String());
        for (Parada parada : linea.getParadas())
            if (linea.getParadas().indexOf(parada) == linea.getParadas().size() - 1)
                columna.append(parada.getCodigo());
            else
                columna.append(parada.getCodigo()).append(" - ");
        table.setValueAt(columna, row, 4);
    }

    /**
     * Clase ButtonRenderer que actúa como un renderizador de celdas para botones en
     * una tabla.
     */
    class ButtonRenderer extends JButton implements TableCellRenderer {

        /**
         * Constructor de la clase ButtonRenderer.
         * Configura las propiedades iniciales del renderizador.
         */
        public ButtonRenderer() {
            setOpaque(true);
        }

        /**
         * Obtiene el componente de renderizado de celda para un botón en la tabla.
         *
         * @param table      La tabla que contiene la celda.
         * @param value      El valor de la celda.
         * @param isSelected Indica si la celda está seleccionada.
         * @param hasFocus   Indica si la celda tiene el foco.
         * @param row        La fila de la celda.
         * @param column     La columna de la celda.
         * @return El componente de renderizado de celda para el botón.
         */
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            // Configura los colores de fondo y texto según el estado de la celda
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(UIManager.getColor("Button.background"));
            }

            // Configura el ícono del botón según el valor de la celda
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
     * Clase ButtonEditor que actúa como un editor de celdas para botones en una
     * tabla.
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
         * @param checkBox CheckBox que se utilizará como base para el editor.
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
         * Configura el componente de editor de celda para un botón en la tabla.
         *
         * @param table      La tabla que contiene la celda.
         * @param value      El valor de la celda.
         * @param isSelected Indica si la celda está seleccionada.
         * @param row        La fila de la celda.
         * @param column     La columna de la celda.
         * @return El componente de editor de celda para el botón.
         */
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                int column) {

            // Configura los colores de fondo y texto según el estado de la celda
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
         * Obtiene el valor actual de la celda después de la edición.
         *
         * @return Valor actual de la celda.
         */
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

        /**
         * Detiene la edición de la celda y guarda cualquier cambio realizado.
         *
         * @return `true` si se detiene la edición, `false` si se cancela.
         */
        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        /**
         * Realiza las acciones necesarias después de detener la edición.
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
     * Establece el coordinador asociado con esta ventana.
     *
     * @param coordinador El coordinador a establecer.
     */
    public void setCoordinador(Coordinador coordinador) {
        this.coordinador = coordinador;
    }

    /**
     * Establece la acción que se realizará en esta ventana (borrar o modificar).
     *
     * @param accion La acción a establecer.
     */
    public void setAccion(int accion) {
        this.accion = accion;
    }

    /**
     * Establece la línea asociada con esta ventana.
     *
     * @param linea La línea a establecer.
     */
    public void setLinea(Linea linea) {
        this.linea = linea;
    }
}
