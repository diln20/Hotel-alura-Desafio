package Views;

import com.alura.fachada.FachadaHotel;
import com.alura.utils.UserSession;
import com.alura.vo.HuespedVO;
import com.alura.vo.ReservasVO;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Optional;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

    private JPanel contentPane;
    private JTextField txtBuscar;
    private JTable tbHuespedes;
    private JTable tbReservas;
    private DefaultTableModel modelo;
    private DefaultTableModel modeloHuesped;
    private JLabel labelAtras;
    private JLabel labelExit;
    int xMouse, yMouse;
    private JComboBox<String> filterComboBox;
   
    private FachadaHotel fachadHotel = new FachadaHotel();
    private JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);

    /**
     * Launch the application.
     *
     * @param args
     */
    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            try {
                Login loginFrame = new Login();
                loginFrame.setVisible(true);
              
                if (UserSession.getLoggedInUser()!=null) {
                    Busqueda frame = new Busqueda();
                    frame.setVisible(true);
                }
            } catch (Exception e) {
            }
        });
    }

    /**
     * Create the frame.
     */
    public Busqueda() {

        setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 910, 571);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setLocationRelativeTo(null);
        setUndecorated(true);

        txtBuscar = new JTextField();
        txtBuscar.setBounds(536, 127, 193, 31);
        txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        contentPane.add(txtBuscar);
        txtBuscar.setColumns(10);

        txtBuscar.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txtBuscar.setText("");
            }
        });
        txtBuscar.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                realizarBusqueda();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                realizarBusqueda();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                realizarBusqueda();
            }
        });

        JLabel lblNewLabel_4 = new JLabel("SISTEMA DE BÚSQUEDA");
        lblNewLabel_4.setForeground(new Color(12, 138, 199));
        lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 24));
        lblNewLabel_4.setBounds(331, 62, 280, 42);
        contentPane.add(lblNewLabel_4);

        panel.setBackground(new Color(12, 138, 199));
        panel.setFont(new Font("Roboto", Font.PLAIN, 16));
        panel.setBounds(20, 169, 865, 328);
        contentPane.add(panel);
        filterComboBox = new JComboBox<>(new String[]{
            "Nombre", "Apellido", "Fecha Nacimiento", "Nacionalidad", "Telefono", "Numero Reserva"
        });

        filterComboBox.setBounds(350, 127, 150, 31);
        contentPane.add(filterComboBox);

        panel.addChangeListener((ChangeEvent e) -> {
            if (panel.getSelectedIndex() == 1) { // Check if "Huéspedes" tab is selected
                filterComboBox.setModel(new DefaultComboBoxModel<>(new String[]{
                    "Nombre", "Apellido", "Fecha Nacimiento", "Nacionalidad", "Telefono", "Número Reserva"
                }));
            } else if (panel.getSelectedIndex() == 0) { // Check if "Reservas" tab is selected
                filterComboBox.setModel(new DefaultComboBoxModel<>(new String[]{
                    "Numero Reserva", "FechaEntrada", "FechaSalida", "Valor", "FormaPago"
                }));
            }
            txtBuscar.setText("");
            realizarBusqueda();
        });
        filterComboBox.setBounds(350, 127, 150, 31);
        contentPane.add(filterComboBox);

        String searchTerm = txtBuscar.getText();

        filterComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedField = (String) filterComboBox.getSelectedItem();
             //   actualizarEtiquetaBuscar(selectedField);
            }

            private void actualizarEtiquetaBuscar(String selectedField) {
                if ("Fecha Nacimiento".equals(selectedField)) {
                    // Si el criterio es Fecha Nacimiento, cambia la etiqueta del campo de búsqueda a "YYYY-MM-DD"
                    txtBuscar.setText("YYYY-MM-DD");
                } else {
                    // Para otros criterios, deja el campo de búsqueda vacío
                    txtBuscar.setText("");
                }
            }

        });

        tbReservas = new JTable();
        tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
        modelo = (DefaultTableModel) tbReservas.getModel();
        modelo.addColumn("Numero de Reserva");
        modelo.addColumn("Fecha Check In");
        modelo.addColumn("Fecha Check Out");
        modelo.addColumn("Valor");
        modelo.addColumn("Forma de Pago");
        JScrollPane scroll_table = new JScrollPane(tbReservas);
        panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), scroll_table, null);
        scroll_table.setVisible(true);

        tbHuespedes = new JTable();
        tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
        modeloHuesped = (DefaultTableModel) tbHuespedes.getModel();
        modeloHuesped.addColumn("Número de Huesped");
        modeloHuesped.addColumn("Nombre");
        modeloHuesped.addColumn("Apellido");
        modeloHuesped.addColumn("Fecha de Nacimiento");
        modeloHuesped.addColumn("Nacionalidad");
        modeloHuesped.addColumn("Telefono");
        modeloHuesped.addColumn("Número de Reserva");

        cargarDatos();
        JScrollPane scroll_tableHuespedes = new JScrollPane(tbHuespedes);
        panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")), scroll_tableHuespedes, null);
        scroll_tableHuespedes.setVisible(true);

        JLabel lblNewLabel_2 = new JLabel("");
        lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
        lblNewLabel_2.setBounds(56, 51, 104, 107);
        contentPane.add(lblNewLabel_2);

        JPanel header = new JPanel();
        header.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                headerMouseDragged(e);

            }
        });
        header.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                headerMousePressed(e);
            }
        });
        header.setLayout(null);
        header.setBackground(Color.WHITE);
        header.setBounds(0, 0, 910, 36);
        contentPane.add(header);

        JPanel btnAtras = new JPanel();
        btnAtras.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MenuUsuario usuario = new MenuUsuario();
                usuario.setVisible(true);
                dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnAtras.setBackground(new Color(12, 138, 199));
                labelAtras.setForeground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnAtras.setBackground(Color.white);
                labelAtras.setForeground(Color.black);
            }
        });
        btnAtras.setLayout(null);
        btnAtras.setBackground(Color.WHITE);
        btnAtras.setBounds(0, 0, 53, 36);
        header.add(btnAtras);

        labelAtras = new JLabel("<");
        labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
        labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
        labelAtras.setBounds(0, 0, 53, 36);
        btnAtras.add(labelAtras);

        JPanel btnexit = new JPanel();
        btnexit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MenuUsuario usuario = new MenuUsuario();
                usuario.setVisible(true);
                dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) { //Al usuario pasar el mouse por el botón este cambiará de color
                btnexit.setBackground(Color.red);
                labelExit.setForeground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) { //Al usuario quitar el mouse por el botón este volverá al estado original
                btnexit.setBackground(Color.white);
                labelExit.setForeground(Color.black);
            }
        });
        btnexit.setLayout(null);
        btnexit.setBackground(Color.WHITE);
        btnexit.setBounds(857, 0, 53, 36);
        header.add(btnexit);

        labelExit = new JLabel("X");
        labelExit.setHorizontalAlignment(SwingConstants.CENTER);
        labelExit.setForeground(Color.BLACK);
        labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
        labelExit.setBounds(0, 0, 53, 36);
        btnexit.add(labelExit);

        JSeparator separator_1_2 = new JSeparator();
        separator_1_2.setForeground(new Color(12, 138, 199));
        separator_1_2.setBackground(new Color(12, 138, 199));
        separator_1_2.setBounds(539, 159, 193, 2);
        contentPane.add(separator_1_2);

        JPanel btnbuscar = new JPanel();
        btnbuscar.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                String selectedField = (String) filterComboBox.getSelectedItem();

                if (panel.getSelectedIndex() == 1) {
                    List<HuespedVO> listaHuespedes = buscarHuespedes(selectedField, searchTerm);
                    actualizarTablaHuespedes(listaHuespedes);
                    HuespedVO huespedVO = new HuespedVO();
                    switch (selectedField) {
                        case "Nombre" ->
                            huespedVO.setNombre(txtBuscar.getText());
                        case "Apellido" ->
                            huespedVO.setApellido(txtBuscar.getText());
                        case "Nacionalidad" ->
                            huespedVO.setNacionalidad(txtBuscar.getText());
                        case "Telefono" ->
                            huespedVO.setTelefono(txtBuscar.getText());
                        case "Fecha Nacimiento" -> {
                            try {
                                String fechaNacimientoStr = txtBuscar.getText();
                                System.out.println("" + fechaNacimientoStr);
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoStr, formatter);
                                huespedVO.setFechaNacimiento(fechaNacimiento);
                            } catch (DateTimeParseException ex) {
                                // Handle parsing exception
                                System.out.println("Invalid date format: " + ex.getMessage());
                                return;
                            }
                        }
                        default -> {
                        }
                    }
                    realizarBusqueda();

                } else if (panel.getSelectedIndex() == 0) {
                    List<ReservasVO> listaReservas = buscarReservas(selectedField, searchTerm);
                    actualizarTablaReservas(listaReservas);
                    ReservasVO reservasVO = new ReservasVO();

                    switch (selectedField) {
                        case "Numero Reserva" -> {
                            String idText = txtBuscar.getText();
                            if (!idText.isEmpty()) {
                                try {
                                    reservasVO.setId(Integer.valueOf(idText));
                                } catch (NumberFormatException ex) {
                                    System.out.println("Invalid input for Id: " + ex.getMessage());
                                    return; // Exit the method to avoid further processing
                                }
                            } else {
                                System.out.println("Input for Id is empty");
                                return; // Exit the method to avoid further processing
                            }
                        }
                        case "FechaEntrada" -> {
                            try {
                                String fechaEntradaStr = txtBuscar.getText();
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                LocalDate fechaEntrada = LocalDate.parse(fechaEntradaStr, formatter);
                                reservasVO.setFechaEntrada(fechaEntrada);
                            } catch (DateTimeParseException ex) {
                                System.out.println("Invalid date format for FechaEntrada: " + ex.getMessage());
                                return; // Exit the method to avoid further processing
                            }
                        }
                        case "FechaSalida" -> {
                            try {
                                String fechaSalidaStr = txtBuscar.getText();
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                LocalDate fechaSalida = LocalDate.parse(fechaSalidaStr, formatter);
                                reservasVO.setFechaSalida(fechaSalida);
                            } catch (DateTimeParseException ex) {
                                System.out.println("Invalid date format for FechaSalida: " + ex.getMessage());
                                return; // Exit the method to avoid further processing
                            }
                        }
                        case "Valor" -> {
                            String valorText = txtBuscar.getText();
                            if (!valorText.isEmpty()) {
                                try {
                                    reservasVO.setValor(Double.parseDouble(valorText));
                                } catch (NumberFormatException ex) {
                                    System.out.println("Invalid input for Valor: " + ex.getMessage());
                                    return; // Exit the method to avoid further processing
                                }
                            } else {
                                System.out.println("Input for Valor is empty");
                                return; // Exit the method to avoid further processing
                            }
                        }
                        case "FormaPago" ->
                            reservasVO.setFormaPago(txtBuscar.getText());
                        default -> {
                        }
                    }
                    // Handle default case if needed
                    realizarBusqueda();
                }
            }
        });
        btnbuscar.setLayout(null);
        btnbuscar.setBackground(new Color(12, 138, 199));
        btnbuscar.setBounds(748, 125, 122, 35);
        btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        contentPane.add(btnbuscar);

        JLabel lblBuscar = new JLabel("BUSCAR");
        lblBuscar.setBounds(0, 0, 122, 35);
        btnbuscar.add(lblBuscar);
        lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
        lblBuscar.setForeground(Color.WHITE);
        lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));

        JPanel btnEditar = new JPanel();
        btnEditar.setLayout(null);
        btnEditar.setBackground(new Color(12, 138, 199));
        btnEditar.setBounds(635, 508, 122, 35);
        btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        contentPane.add(btnEditar);

        JLabel lblEditar = new JLabel("EDITAR");
        lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
        lblEditar.setForeground(Color.WHITE);
        lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
        lblEditar.setBounds(0, 0, 122, 35);
        btnEditar.add(lblEditar);

        JPanel btnEliminar = new JPanel();
        btnEliminar.setLayout(null);
        btnEliminar.setBackground(new Color(12, 138, 199));
        btnEliminar.setBounds(767, 508, 122, 35);
        btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        contentPane.add(btnEliminar);

        JLabel lblEliminar = new JLabel("ELIMINAR");
        lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
        lblEliminar.setForeground(Color.WHITE);
        lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
        lblEliminar.setBounds(0, 0, 122, 35);
        btnEliminar.add(lblEliminar);
        setResizable(false);

        btnEditar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaReservas = tbReservas.getSelectedRow();
                int filaHuespedes = tbHuespedes.getSelectedRow();

                
                if (UserSession.getLoggedInUser()!=null) {
                    if (filaReservas >= 0) {
                        actualizarReservas();
                        System.out.println("" + filaHuespedes);
                        cargarDatos();
                    }
                } else {
                    mostrarMensajeAutenticacion();
                }
                
                System.out.println("::::>>>>"+UserSession.getLoggedInUser());
                if (UserSession.getLoggedInUser()!=null) {

                    if (filaHuespedes >= 0) {
                        
                        actualizarHuespedes();
                        System.out.println("" + filaHuespedes);
                        cargarDatos();
                    }
                } else {
                    mostrarMensajeAutenticacion();
                }
            }
        });

        btnEliminar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaReservas = tbReservas.getSelectedRow();
                int filaHuespedes = tbHuespedes.getSelectedRow();
              if (UserSession.getLoggedInUser()!=null) {
                    if (filaReservas >= 0) {
                        eliminarReservas();
                        System.out.println("" + filaHuespedes);
                        cargarDatos();
                    }
                } else {
                    mostrarMensajeAutenticacion();
                }
                
                System.out.println("::::>>>>"+UserSession.getLoggedInUser());
                if (UserSession.getLoggedInUser()!=null) {

                    if (filaHuespedes >= 0) {
                        
                        eliminarHuespedes();
                        System.out.println("" + filaHuespedes);
                        cargarDatos();
                    }
                } else {
                    mostrarMensajeAutenticacion();
                }
            }
        }
        );

    }

    private void mostrarMensajeAutenticacion() {
        JOptionPane.showMessageDialog(this, "Por favor, inicia sesión para acceder a esta función.", "Autenticación requerida", JOptionPane.WARNING_MESSAGE);
    }

    private void realizarBusqueda() {

        String selectedField = (String) filterComboBox.getSelectedItem();

        if (panel.getSelectedIndex() == 1) { // Check if "Huéspedes" tab is selected
            modeloHuesped.setRowCount(0); // Limpiar la tabla antes de hacer la búsqueda
            // Código para buscar en la tabla de huéspedes
            List<HuespedVO> listaHuespedes = buscarHuespedes(selectedField, txtBuscar.getText());
            // Actualizar la tabla de huéspedes con los resultados de la búsqueda
            actualizarTablaHuespedes(listaHuespedes);
        } else if (panel.getSelectedIndex() == 0) { // Check if "Reservas" tab is selected
            modelo.setRowCount(0); // Limpiar la tabla de reservas
            // Código para buscar en la tabla de reservas
            List<ReservasVO> listaReservas = buscarReservas(selectedField, txtBuscar.getText());
            // Actualizar la tabla de reservas con los resultados de la búsqueda
            actualizarTablaReservas(listaReservas);
        }
    }

//Código que permite mover la ventana por la pantalla según la posición de "x" y "y"
    private void headerMousePressed(java.awt.event.MouseEvent evt) {
        xMouse = evt.getX();
        yMouse = evt.getY();
    }

    private void headerMouseDragged(java.awt.event.MouseEvent evt) {
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }

    private void actualizarTablaHuespedes(List<HuespedVO> listaHuespedes) {
        modeloHuesped.setRowCount(0); // Limpiar la tabla antes de cargar los datos

        for (HuespedVO huesped : listaHuespedes) {
            modeloHuesped.addRow(new Object[]{
                huesped.getId(),
                huesped.getNombre(),
                huesped.getApellido(),
                huesped.getFechaNacimiento(),
                huesped.getNacionalidad(),
                huesped.getTelefono(),
                huesped.getIdReserva()
            });
        }
    }

    private void actualizarTablaReservas(List<ReservasVO> listaReservas) {
        modelo.setRowCount(0); // Limpiar la tabla antes de cargar los datos

        for (ReservasVO reserva : listaReservas) {
            modelo.addRow(new Object[]{
                reserva.getId(),
                reserva.getFechaEntrada(),
                reserva.getFechaSalida(),
                reserva.getValor(),
                reserva.getFormaPago()
            });
        }
    }

    private List<HuespedVO> buscarHuespedes(String selectedField, String searchTerm) {
        List<HuespedVO> listaHuespedesFiltrados = new ArrayList<>();
        List<HuespedVO> listaHuespedes = fachadHotel.listar(new HuespedVO());

        for (HuespedVO huesped : listaHuespedes) {
            if (cumpleCriterioHuesped(huesped, selectedField, searchTerm)) {
                listaHuespedesFiltrados.add(huesped);
            }
        }

        return listaHuespedesFiltrados;
    }

    private List<ReservasVO> buscarReservas(String selectedField, String searchTerm) {
        List<ReservasVO> listaReservasFiltradas = new ArrayList<>();
        List<ReservasVO> listaReservas = fachadHotel.listarReserva(new ReservasVO());

        for (ReservasVO reserva : listaReservas) {
            if (cumpleCriterioReserva(reserva, selectedField, searchTerm)) {
                listaReservasFiltradas.add(reserva);
            }
        }

        return listaReservasFiltradas;
    }

    private boolean cumpleCriterioHuesped(HuespedVO huesped, String selectedField, String searchTerm) {
        String fieldValue = "";

        switch (selectedField) {
            case "Nombre" ->
                fieldValue = huesped.getNombre();
            case "Apellido" ->
                fieldValue = huesped.getApellido();
            case "Fecha Nacimiento" ->
                fieldValue = huesped.getFechaNacimiento().toString();
            case "Nacionalidad" ->
                fieldValue = huesped.getNacionalidad();
            case "Telefono" ->
                fieldValue = huesped.getTelefono();
            case "Número Reserva" ->
                fieldValue = String.valueOf(huesped.getIdReserva());
        }

        return fieldValue.toLowerCase().contains(searchTerm.toLowerCase());
    }

    private boolean cumpleCriterioReserva(ReservasVO reserva, String selectedField, String searchTerm) {
        String fieldValue = "";

        switch (selectedField) {
            case "Numero Reserva" ->
                fieldValue = String.valueOf(reserva.getId());
            case "FechaEntrada" ->
                fieldValue = reserva.getFechaEntrada().toString();
            case "FechaSalida" ->
                fieldValue = reserva.getFechaSalida().toString();
            case "Valor" ->
                fieldValue = String.valueOf(reserva.getValor());
            case "FormaPago" ->
                fieldValue = reserva.getFormaPago();
        }

        return fieldValue.toLowerCase().contains(searchTerm.toLowerCase());
    }

    private void cargarDatos() {
        modeloHuesped.setRowCount(0); // Limpia la tabla antes de cargar los datos
        modelo.setRowCount(0); // Limpia la tabla antes de cargar los datos

        FachadaHotel fachadaHotel = new FachadaHotel();
        List<HuespedVO> listaHuespedes = fachadaHotel.listar(new HuespedVO());
        List<ReservasVO> listaReservas = fachadaHotel.listarReserva(new ReservasVO());

        for (HuespedVO huesped : listaHuespedes) {
            modeloHuesped.addRow(new Object[]{
                huesped.getId(),
                huesped.getNombre(),
                huesped.getApellido(),
                huesped.getFechaNacimiento(),
                huesped.getNacionalidad(),
                huesped.getTelefono(),
                huesped.getIdReserva()
            });
        }
        
        for (ReservasVO reserva : listaReservas) {
             double valor = reserva.getValor();
            DecimalFormat decimalFormat = new DecimalFormat("#,###,##0.0");
            String valorFormateado = decimalFormat.format(valor);
            modelo.addRow(new Object[]{
                reserva.getId(),
                reserva.getFechaEntrada(),
                reserva.getFechaSalida(),
                valorFormateado,
                reserva.getFormaPago(),});
        }
    }

    private void actualizarHuespedes() {
        Optional.ofNullable(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn()))
                .ifPresentOrElse(filaHuespedes -> {
                    int dialogResult = JOptionPane.showConfirmDialog(this, "¿Estás seguro que deseas actualizar este registro?", "Confirmar Actualización", JOptionPane.YES_NO_OPTION);

                    if (dialogResult == JOptionPane.YES_OPTION) {
                        HuespedVO huespedVO = new HuespedVO();
                        String id_Str = modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 0).toString();
                        Integer id_r = Integer.valueOf(id_Str);
                        String nombre = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 1);
                        String apellido = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 2);
                        LocalDate fechaNacimiento = LocalDate.parse(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 3).toString());
                        String nacionalidad = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 4);
                        String telefono = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 5);
                        String id_reservaStr = modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 6).toString();
                        Integer id_reserva = Integer.valueOf(id_reservaStr);
                        huespedVO.setNombre(nombre);
                        huespedVO.setApellido(apellido);
                        huespedVO.setFechaNacimiento(fechaNacimiento);
                        huespedVO.setNacionalidad(nacionalidad);
                        huespedVO.setTelefono(telefono);
                        huespedVO.setIdReserva(id_reserva);
                        huespedVO.setId(id_r);
                        int result = fachadHotel.actualizar(huespedVO);
                        System.out.println("" + result);

                        if (result > 0) {
                            JOptionPane.showMessageDialog(this, String.format("Registro Modificado con éxito"));
                        } else {
                            JOptionPane.showMessageDialog(this, String.format("Registro no fue Modificado"));
                        }
                    }
                }, () -> JOptionPane.showMessageDialog(this, ""));
    }
    
    
     private void actualizarReservas() {
        Optional.ofNullable(modelo.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn()))
                .ifPresentOrElse(filaHuespedes -> {
                    int dialogResult = JOptionPane.showConfirmDialog(this, "¿Estás seguro que deseas actualizar este registro?", "Confirmar Actualización", JOptionPane.YES_NO_OPTION);

                    if (dialogResult == JOptionPane.YES_OPTION) {
                        ReservasVO reservasVO = new ReservasVO();
                        String id_Str = modelo.getValueAt(tbReservas.getSelectedRow(), 0).toString();
                        Integer id_r = Integer.valueOf(id_Str);
                        LocalDate fechaEntrada = LocalDate.parse(modelo.getValueAt(tbReservas.getSelectedRow(), 1).toString());
                        LocalDate fechaSalida = LocalDate.parse(modelo.getValueAt(tbReservas.getSelectedRow(), 2).toString());
                        String valorConPuntosYComa = (String) modelo.getValueAt(tbReservas.getSelectedRow(), 3);

                        // Elimina los caracteres no numéricos, es decir, el punto y la coma
                        String valorSinPuntosNiComa = valorConPuntosYComa.replaceAll("[.,]", "");
                         double valorDouble = Double.parseDouble(valorSinPuntosNiComa);
                        String formaPago = (String) modelo.getValueAt(tbReservas.getSelectedRow(), 4);
                        reservasVO.setFechaEntrada(fechaEntrada);
                        reservasVO.setFechaSalida(fechaSalida);
                        reservasVO.setValor(valorDouble);
                        reservasVO.setFormaPago(formaPago);
                        reservasVO.setId(id_r);
                        int result = fachadHotel.actualizarReserva(reservasVO);
                        System.out.println("" + result);

                        if (result > 0) {
                            JOptionPane.showMessageDialog(this, String.format("Registro Modificado con éxito"));
                        } else {
                            JOptionPane.showMessageDialog(this, String.format("Registro no fue Modificado"));
                        }
                    }
                }, () -> JOptionPane.showMessageDialog(this, ""));
    }

    private void eliminarHuespedes() {
        Optional.ofNullable(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn()))
                .ifPresentOrElse(filaHuespedes -> {
                    String id_Str = modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 0).toString();
                    Integer id_r = Integer.valueOf(id_Str);

                    int dialogResult = JOptionPane.showConfirmDialog(this, "¿Estás seguro que deseas eliminar este registro?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

                    if (dialogResult == JOptionPane.YES_OPTION) {
                        int result = fachadHotel.eliminar(id_r);
                        System.out.println("" + result);

                        if (result > 0) {
                            JOptionPane.showMessageDialog(this, String.format("Registro eliminado con éxito"));
                        } else {
                            JOptionPane.showMessageDialog(this, String.format("Registro no fue eliminado"));
                        }
                    }
                }, () -> JOptionPane.showMessageDialog(this, ""));
    }

    private void eliminarReservas() {
        Optional.ofNullable(modelo.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn()))
                .ifPresentOrElse(filaHuespedes -> {
                    String id_Str = modelo.getValueAt(tbReservas.getSelectedRow(), 0).toString();
                    Integer id_r = Integer.valueOf(id_Str);

                    int dialogResult = JOptionPane.showConfirmDialog(this, "¿Estás seguro que deseas eliminar este registro?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

                    if (dialogResult == JOptionPane.YES_OPTION) {
                        int result = fachadHotel.eliminarReserva(id_r);
                        System.out.println("" + result);

                        if (result > 0) {
                            JOptionPane.showMessageDialog(this, String.format("Registro eliminado con éxito"));
                        } else {
                            JOptionPane.showMessageDialog(this, String.format("Registro no eliminado "));
                        }
                    }
                }, () -> JOptionPane.showMessageDialog(this, ""));
    }
}
