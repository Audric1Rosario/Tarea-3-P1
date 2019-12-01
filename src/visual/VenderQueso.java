package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JFormattedTextField;
import javax.swing.border.EmptyBorder;


import logical.Cilindro;
import logical.CilindroHueco;
import logical.Cliente;
import logical.Complejo;
import logical.Esfera;
import logical.Factura;
import logical.Queso;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class VenderQueso extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtDireccion;
	private JTextField txtNombre;
	private JFormattedTextField txtTelefono;
	private JTextField txtIdCliente;
	
	// Botones
	private JButton btnBuscar;
	private JButton btnModificar;
	private JButton btnFacturar;
	private JButton btnReturn;
	private JButton btnAdd;
	private JButton btnCancelar;
	
	// Listas
	private JList<String> lstStock;
	private JList<String> lstCarrito;
	
	// Variable para saber si es posible vender
	private boolean esPosibleVender = true;
	
	// Variables de venta
	private ArrayList<String> stock;
	private ArrayList<String> carritoCompras;
	
	// Decimales
	private DecimalFormat formateador;
	private float total;
	private JTextField txtTotal;
	private JScrollPane scrollPane_1;

	public VenderQueso() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(VenderQueso.class.getResource("/images/cheese.png")));
		stock = new ArrayList<String>();
		carritoCompras = new ArrayList<String>();
		formateador = new DecimalFormat("####.##");
		total = 0f;
		setTitle("Punto de venta y facturaciones.");
		setResizable(false);		
		setBounds(100, 100, 530, 620);
		getContentPane().setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			
			JPanel panel_1 = new JPanel();
			panel_1.setBorder(new TitledBorder(null, "Datos del cliente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_1.setBounds(10, 11, 494, 146);
			panel.add(panel_1);
			panel_1.setLayout(null);
			
			JLabel lblId = new JLabel("ID:");
			lblId.setBounds(10, 18, 75, 14);
			panel_1.add(lblId);
			
			JLabel lblNombre = new JLabel("Nombre:");
			lblNombre.setBounds(10, 50, 75, 14);
			panel_1.add(lblNombre);
			
			JLabel lblDireccin = new JLabel("Direcci\u00F3n:");
			lblDireccin.setBounds(10, 82, 75, 14);
			panel_1.add(lblDireccin);
			
			txtDireccion = new JTextField();
			txtDireccion.setEditable(false);
			txtDireccion.setBounds(95, 79, 389, 20);
			panel_1.add(txtDireccion);
			txtDireccion.setColumns(10);
			
			txtNombre = new JTextField();
			txtNombre.setEditable(false);
			txtNombre.setBounds(95, 47, 389, 20);
			panel_1.add(txtNombre);
			txtNombre.setColumns(10);
			
			JLabel lblTelefno = new JLabel("Tel\u00E9fono:");
			lblTelefno.setBounds(10, 113, 75, 14);
			panel_1.add(lblTelefno);
			
			
			try {
				txtTelefono = new JFormattedTextField(new MaskFormatter("(###) ###-####"));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			txtTelefono.setEditable(false);
			txtTelefono.setBounds(95, 110, 223, 20);
			panel_1.add(txtTelefono);
			txtTelefono.setColumns(10);
			
			txtIdCliente = new JTextField();
			txtIdCliente.setColumns(10);
			txtIdCliente.setBounds(95, 15, 223, 20);
			panel_1.add(txtIdCliente);
			
			btnBuscar = new JButton("Buscar");
			btnBuscar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String idCliente = txtIdCliente.getText();
					
					if (idCliente.length() > 0)
					{
						Cliente cliente = Complejo.getInstance().buscarClienteById(idCliente);
						txtNombre.setEditable(true);
						txtDireccion.setEditable(true);
						txtTelefono.setEditable(true);
						txtIdCliente.setEditable(false);
						
						if (cliente == null) {
							JOptionPane.showMessageDialog(null, "No se encontró el cliente solicitado, por favor registrese aquí.", "Información.", JOptionPane.WARNING_MESSAGE);
							btnModificar.setText("Registrar");
						} else {
							txtNombre.setText(cliente.getNombre());
							txtDireccion.setText(cliente.getDireccion());
							txtTelefono.setText(cliente.getTelefono());
							if (esPosibleVender)
							{							
								btnAdd.setEnabled(true);
								btnReturn.setEnabled(false);
								lstStock.setEnabled(true);
								lstCarrito.setEnabled(true);
							}
							btnCancelar.setText("Volver");
						}
						btnBuscar.setEnabled(false);
						btnModificar.setEnabled(true);
					}
					else {
						JOptionPane.showMessageDialog(null, "Por favor ingrese su identificación.", "Información.", JOptionPane.WARNING_MESSAGE);
					}
	
				}
			});
			btnBuscar.setBounds(328, 14, 89, 23);
			panel_1.add(btnBuscar);
			
			btnModificar = new JButton("Modificar");
			btnModificar.setEnabled(false);
			btnModificar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (btnModificar.getText().equalsIgnoreCase("Registrar")) {
						Cliente nuevo = new Cliente(txtIdCliente.getText(), txtNombre.getText(), txtDireccion.getText(), txtTelefono.getText());
						Complejo.getInstance().getMisClientes().add(nuevo);
						clear(false);
						JOptionPane.showMessageDialog(null, "Cliente registrado exitosamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						Cliente cliente = Complejo.getInstance().buscarClienteById(txtIdCliente.getText());
						cliente.setNombre(txtNombre.getText());
						cliente.setDireccion(txtDireccion.getText());
						cliente.setTelefono(txtTelefono.getText());
						JOptionPane.showMessageDialog(null, "Datos modificados exitosamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
			btnModificar.setBounds(328, 109, 89, 23);
			panel_1.add(btnModificar);
			
			JPanel panel_2 = new JPanel();
			panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Compra y venta de quesos.", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			panel_2.setBounds(10, 168, 494, 364);
			panel.add(panel_2);
			panel_2.setLayout(null);
			
			JPanel panel_3 = new JPanel();
			panel_3.setBorder(new TitledBorder(null, "Quesos Disponibles", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_3.setBounds(10, 25, 202, 328);
			panel_2.add(panel_3);
			panel_3.setLayout(new BorderLayout(0, 0));
			
			JScrollPane scrollPane = new JScrollPane();
			panel_3.add(scrollPane, BorderLayout.CENTER);
			
			lstStock = new JList<String>();
			scrollPane.setViewportView(lstStock);
			lstStock.setEnabled(false);
			lstStock.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			lstStock.setToolTipText("Seleccione el queso que desea comprar");
			lstStock.setModel(new DefaultListModel<String>());
			
			JPanel panel_4 = new JPanel();
			panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Carrito de Compras", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			panel_4.setBounds(282, 25, 202, 328);
			panel_2.add(panel_4);
			panel_4.setLayout(new BorderLayout(0, 0));
			
			JPanel panel_5 = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panel_5.getLayout();
			flowLayout.setHgap(2);
			flowLayout.setVgap(10);
			panel_4.add(panel_5, BorderLayout.SOUTH);
			
			txtTotal = new JTextField();
			txtTotal.setToolTipText("Total");
			panel_5.add(txtTotal);
			txtTotal.setText("0");
			txtTotal.setEditable(false);
			txtTotal.setColumns(10);
			
			scrollPane_1 = new JScrollPane();
			panel_4.add(scrollPane_1, BorderLayout.CENTER);
			
			lstCarrito = new JList<String>();
			scrollPane_1.setViewportView(lstCarrito);
			lstCarrito.setEnabled(false);
			lstCarrito.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			lstCarrito.setModel(new DefaultListModel<String>());
			
			
			// Redimensionar el boton.
			String imagePath;
			ImageIcon adjust;
			Image img, adjusted;
			
			btnReturn = new JButton("");
			btnReturn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int selection = lstCarrito.getSelectedIndex();
					if (selection == -1)
						JOptionPane.showMessageDialog(null, "No hay queso seleccionado, por favor elija uno.", "Información.", JOptionPane.WARNING_MESSAGE);
					else
						actualizarLista(false, selection);
				}
			});
			btnReturn.setEnabled(false);
			btnReturn.setToolTipText("Pulse para dejar un queso");
			btnReturn.setBounds(222, 181, 49, 47);
			imagePath = "/images/left-arrow.png";
			adjust = new ImageIcon(VenderQueso.class.getResource(imagePath));
			img = adjust.getImage();
			adjusted = img.getScaledInstance(btnReturn.getWidth() - 15, btnReturn.getHeight() - 15, Image.SCALE_SMOOTH);
			btnReturn.setIcon(new ImageIcon(adjusted));
			panel_2.add(btnReturn);

			btnAdd = new JButton("");
			btnAdd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int selection = lstStock.getSelectedIndex();
					if (selection == -1)
						JOptionPane.showMessageDialog(null, "No hay queso seleccionado, por favor elija uno.", "Información.", JOptionPane.WARNING_MESSAGE);
					else {
						actualizarLista(true, selection);
						
					}
						
				}
			});
			btnAdd.setEnabled(false);
			btnAdd.setToolTipText("Pulse para agregar un queso");
			btnAdd.setBounds(222, 123, 49, 47);
			imagePath = "/images/arrow-point-to-right.png";
			adjust = new ImageIcon(VenderQueso.class.getResource(imagePath));
			img = adjust.getImage();
			adjusted = img.getScaledInstance(btnAdd.getWidth() - 15, btnAdd.getHeight() - 15, Image.SCALE_SMOOTH);
			btnAdd.setIcon(new ImageIcon(adjusted));
			panel_2.add(btnAdd);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnFacturar = new JButton("Facturar");
				btnFacturar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Cliente cliente = Complejo.getInstance().buscarClienteById(txtIdCliente.getText());
						Factura factura = new Factura(cliente);
						for (String index : carritoCompras) {
							factura.getMisQuesos().add(Complejo.getInstance().getMisQuesos().get(formatItemIndex(index)));
						}
						
						SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy"); 
						String message = "******Fábrica de quesos******\n\n";
						message += "Factura  : " + factura.getIdFactura() + "\n";
						message += "Cuenta   : " + cliente.getIdCliente() + "\n";
						message += "Fecha    : " + formater.format(new Date()) + "\n";
						message += "Dirección: " + cliente.getDireccion() + "\n";
						message += "Teléfono : " + cliente.getTelefono() + "\n\n";
						message += "Quesos comprados:\n";
						message += "Tipo\t Volumen (cm^3)\tCosto\n";
						for (Queso queso : factura.getMisQuesos()) {
							Complejo.getInstance().eliminarQueso(queso);
							if (queso instanceof Esfera)
								message += "Esfera\t ";
							else if (queso instanceof CilindroHueco)
								message += "Hueco\t ";
							else if (queso instanceof Cilindro)
								message += "Cilindro ";
							message += formateador.format(queso.volumen()) + "\t\t";
							message += formateador.format(queso.costo()) + "$\n";
						}
						Complejo.getInstance().addFactura(factura);
						message += "\nMonto a pagar: " + formateador.format(total) + "$.";
						
						JPanel messagePanel = new JPanel();
						messagePanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
						messagePanel.setBounds(10, 10, 250, 520);
						
						JScrollPane messageScroll = new JScrollPane();
						//messageScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
						messagePanel.add(messageScroll, BorderLayout.CENTER);
						
						JTextArea mlabel = new JTextArea(message);
						mlabel.setFont(new Font("Consolas", Font.PLAIN, 12));
						mlabel.setEditable(false);
						messageScroll.setViewportView(mlabel);
						JOptionPane.showMessageDialog(null, messagePanel, "Factura.", JOptionPane.INFORMATION_MESSAGE);
						
						

						// Crear directorio para las facturas.
						String path = System.getProperty("user.dir");
						File directorio = new File(path + "/data/facturas");
						
						if (!directorio.exists()) {
							if (!directorio.mkdirs())  {
								JOptionPane.showMessageDialog(null, "Error al cargar datos.", "Data.", JOptionPane.ERROR_MESSAGE);
							}
						}
						
						// Crear el fichero para guardar el .txt
						File archivo = new File(path + "/data/facturas/" + factura.getIdFactura() + ".txt");
						FileWriter escritor;
						try {
							escritor = new FileWriter(archivo);
							// Escribe el archivo con la informacion
					        for (int i=0; i< message.length(); i++)
					            escritor.write(message.charAt(i));
					        escritor.close();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						
						// Enviar fichero por la red.
						try {
							Principal.getSalidaSocket().writeUTF(message);
							Principal.getSalidaSocket().flush();
						} catch (IOException ioe) {
							JOptionPane.showMessageDialog(null, "Error: "+ioe, "Error", JOptionPane.ERROR_MESSAGE);
						}											
						clear(true);
						
					}
				});
				btnFacturar.setEnabled(false);
				btnFacturar.setActionCommand("OK");
				buttonPane.add(btnFacturar);
				getRootPane().setDefaultButton(btnFacturar);
			}
			{
				btnCancelar = new JButton("Cerrar");
				btnCancelar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (btnCancelar.getText().equalsIgnoreCase("Cerrar")) {
							dispose();
							setModal(false);
						}
						else
							clear(false);
						
					}
				});
				btnCancelar.setActionCommand("Cancel");
				buttonPane.add(btnCancelar);
			}
			reiniciarLista(); // <-Agregar los quesos->
		}
	}
	public void clear(boolean lista) {
		// parte del cliente
		// textos
		txtIdCliente.setText("");
		txtNombre.setText("");
		txtDireccion.setText("");
		txtTelefono.setText("");
		txtTotal.setText("0");
		txtIdCliente.setEditable(true);
		txtNombre.setEditable(false);
		txtDireccion.setEditable(false);
		txtTelefono.setEditable(false);
		
		// listas
		lstStock.setEnabled(false);
		lstCarrito.setEnabled(false);
		// botones
		btnModificar.setText("Modificar");
		btnCancelar.setText("Cerrar");
		btnReturn.setEnabled(false);
		btnAdd.setEnabled(false);
		btnModificar.setEnabled(false);
		btnBuscar.setEnabled(true);
		btnFacturar.setEnabled(false);
		
		total = 0;
		if (lista)
			reiniciarLista();
	}
	
	public void reiniciarLista() {
		String item;
		
		// Borrar datos
		DefaultListModel<String> model = (DefaultListModel<String>) lstStock.getModel();
		model.clear();
		model = (DefaultListModel<String>) lstCarrito.getModel();
		model.clear();
		
		if (Complejo.getInstance().getMisQuesos().size() == 0) {
			JOptionPane.showMessageDialog(null, "No hay existencias de queso", "Información.", JOptionPane.WARNING_MESSAGE);
			esPosibleVender = false;
		} else {
			int aux = 0; 
			stock.clear();
			carritoCompras.clear();
			for (Queso i : Complejo.getInstance().getMisQuesos()) {
				item = aux + ":" + formateador.format(i.costo()) + "$: ";
				aux++;
				if (i instanceof Esfera)
					item += "Esférico ";
				else if (i instanceof CilindroHueco)
					item += "Cilíndrico Hueco ";
				else if (i instanceof Cilindro)
					item += "Cilíndrico ";
				item += formateador.format(i.volumen()) + "cm^3";
				stock.add(item);
			}
			// Llenar stock
			model = (DefaultListModel<String>) lstStock.getModel();
			for (String string : stock) {
				model.addElement(string);
			}
		}
	}
	
	public void actualizarLista(boolean razon, int index) {
		if (razon) {// Comprar
			carritoCompras.add(stock.get(index));
			total += Complejo.getInstance().getMisQuesos().get(formatItemIndex(stock.get(index))).costo();
			stock.remove(index);
			if (stock.size() == 0)
				btnAdd.setEnabled(false);
			if (carritoCompras.size() == 1)
			{
				btnReturn.setEnabled(true);
				btnFacturar.setEnabled(true);
			}
		} else {	// Devolver
			stock.add(carritoCompras.get(index));
			total -= Complejo.getInstance().getMisQuesos().get(formatItemIndex(stock.get(index))).costo();
			carritoCompras.remove(index);
			if (carritoCompras.size() == 0)
			{
				btnReturn.setEnabled(false);
				btnFacturar.setEnabled(false);
			}
			if (stock.size() == 1)
				btnAdd.setEnabled(true);
		}
		txtTotal.setText(formateador.format(total));
		// Borrar datos
		DefaultListModel<String> model = (DefaultListModel<String>) lstStock.getModel();
		model.clear();
		model = (DefaultListModel<String>) lstCarrito.getModel();
		model.clear();

		model = (DefaultListModel<String>) lstStock.getModel();
		for (String string : stock) {
			model.addElement(string);
		}
	
		
		model = (DefaultListModel<String>) lstCarrito.getModel();
		for (String string : carritoCompras) {
			model.addElement(string);
		}
	}
	
	private int formatItemIndex(String item) {
		String ind = ""; int aux = 0;
		while (aux < item.length() && item.charAt(aux) != ':')
			ind += item.charAt(aux++);
		return Integer.valueOf(ind);
	}
}
