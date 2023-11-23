package gui.datos;

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
import java.util.ResourceBundle;

/**
 * Clase ParadaList que representa la interfaz gráfica para la lista de paradas.
 */
public class ParadaList extends JDialog {

    private Coordinador coordinador;
    private ResourceBundle resourceBundle;
    private JButton btnInsertar, btnSalir;
    private JTable table;
    private Parada parada;
    private JScrollPane scrollPane;
    private int accion;

    /**
     * Constructor de la clase ParadaList.
     */
    public ParadaList() {
    }

    /**
     * Inicializa la interfaz gráfica y configura los componentes necesarios.
     */
    public void init() {
        resourceBundle = coordinador.getResourceBundle();
        setBounds(100, 100, 500, 360);

        setTitle(resourceBundle.getString("ParadaList_title_window"));
        getContentPane().setLayout(null);

        JLabel titulo = new JLabel(resourceBundle.getString("ParadaList_title"));
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
                        resourceBundle.getString("ParadaList_code"),
                        resourceBundle.getString("ParadaList_address"),
                        resourceBundle.getString("ParadaList_update"),
                        resourceBundle.getString("ParadaList_delete")
                }));
        table.getColumnModel().getColumn(1).setPreferredWidth(250);
        table.setBorder(new LineBorder(new Color(0, 0, 0)));
        table.setBounds(10, 30, 464, 250);
        getContentPane().add(table);

        btnInsertar = new JButton(resourceBundle.getString("ParadaList_insert"));
        btnInsertar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnInsertar.setBounds(10, 291, 89, 23);
        btnInsertar.setFocusable(false);
        getContentPane().add(btnInsertar);

        btnSalir = new JButton(resourceBundle.getString("ParadaList_exit"));
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

        table.getColumn(resourceBundle.getString("ParadaList_update")).setCellRenderer(new ButtonRenderer());
        table.getColumn(resourceBundle.getString("ParadaList_update")).setCellEditor(new ButtonEditor(new JCheckBox()));
        table.getColumn(resourceBundle.getString("ParadaList_delete")).setCellRenderer(new ButtonRenderer());
        table.getColumn(resourceBundle.getString("ParadaList_delete")).setCellEditor(new ButtonEditor(new JCheckBox()));
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
                coordinador.insertarParadaForm();
        }
    }

    /**
     * Carga los datos de las paradas en la tabla.
     */
    public void loadTable() {
        // Eliminar todas las filas
        ((DefaultTableModel) table.getModel()).setRowCount(0);
        for (Parada parada : coordinador.listarParadas().values())
            if (parada instanceof Parada)
                addRow((Parada) parada);
    }

    /**
     * Agrega una fila a la tabla con los datos de la parada.
     *
     * @param parada Parada a agregar a la tabla.
     */
    public void addRow(Parada parada) {
        Object[] row = new Object[table.getModel().getColumnCount()];
        row[0] = parada.getCodigo();
        row[1] = parada.getDireccion();
        row[2] = "edit";
        row[3] = "drop";
        ((DefaultTableModel) table.getModel()).addRow(row);
    }

    /**
     * Actualiza una fila en la tabla con los datos de la parada.
     *
     * @param row Índice de la fila a actualizar.
     */
    private void updateRow(int row) {
        table.setValueAt(parada.getCodigo(), row, 0);
        table.setValueAt(parada.getDireccion(), row, 1);
    }

    /**
     * Clase interna ButtonRenderer que extiende JButton e implementa
     * TableCellRenderer.
     * Se utiliza para personalizar la apariencia de los botones en la tabla.
     */
    class ButtonRenderer extends JButton implements TableCellRenderer {

        /**
         * Constructor de ButtonRenderer.
         */
        public ButtonRenderer() {
            setOpaque(true);
        }

        /**
         * Configura la apariencia del botón en la celda de la tabla.
         *
         * @param table      Tabla a la que pertenece la celda.
         * @param value      Valor de la celda.
         * @param isSelected Indica si la celda está seleccionada.
         * @param hasFocus   Indica si la celda tiene el foco.
         * @param row        Índice de la fila de la celda.
         * @param column     Índice de la columna de la celda.
         * @return Componente de la celda configurado con la apariencia deseada.
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
     * Clase interna ButtonEditor que extiende DefaultCellEditor.
     * Se utiliza para manejar la edición de celdas con botones en la tabla.
     */
    class ButtonEditor extends DefaultCellEditor {

        protected JButton button;
        private String label;
        private boolean isPushed;
        private JTable table;
        private boolean isDeleteRow = false;
        private boolean isUpdateRow = false;

        /**
         * Constructor de ButtonEditor.
         *
         * @param checkBox Casilla de verificación asociada al editor.
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
         * Configura el componente editor de la celda.
         *
         * @param table      Tabla a la que pertenece la celda.
         * @param value      Valor de la celda.
         * @param isSelected Indica si la celda está seleccionada.
         * @param row        Índice de la fila de la celda.
         * @param column     Índice de la columna de la celda.
         * @return Componente editor configurado.
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
         * Obtiene el valor de la celda editora.
         *
         * @return Valor de la celda editora.
         */
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

        /**
         * Detiene la edición de la celda.
         *
         * @return `true` si la edición debe detenerse, `false` en caso contrario.
         */
        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        /**
         * Dispara el evento de edición detenida.
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
     * Establece el coordinador para la clase ParadaList.
     *
     * @param coordinador Coordinador a establecer.
     */
    public void setCoordinador(Coordinador coordinador) {
        this.coordinador = coordinador;
    }

    /**
     * Establece la acción para la clase ParadaList.
     *
     * @param accion Acción a establecer.
     */
    public void setAccion(int accion) {
        this.accion = accion;
    }

    /**
     * Establece la parada para la clase ParadaList.
     *
     * @param parada Parada a establecer.
     */
    public void setParada(Parada parada) {
        this.parada = parada;
    }
}
