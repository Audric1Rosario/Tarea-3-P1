package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logical.Cilindro;
import logical.CilindroHueco;
import logical.Cliente;
import logical.Complejo;
import logical.Esfera;
import logical.Factura;
import logical.Queso;

import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class VenderQueso extends JDialog {
	private Complejo vendoQuesos;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtDireccion;
	private JTextField txtNombre;
	private JTextField txtTelefono;
	private JTextField txtIdCliente;
	
	// Botones
	private JButton btnBuscar;
	private JButton btnModificar;
	private JButton btnFacturar;
	private JButton btnReturn;
	private JButton btnAdd;
	private JButton btnCancelar;
	
	// Listas
	private JList lstStock;
	private JList lstCarrito;
	
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

	public VenderQueso(Complejo vendoQuesos) {
		this.vendoQuesos = vendoQuesos;
		stock = new ArrayList<String>();
		carritoCompras = new ArrayList<String>();
		formateador = new DecimalFormat("####.##");
		total = 0f;
		setTitle("Punto de venta y facturaciones.");
		setResizable(false);		
		setBounds(100, 100, 425, 529);
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
			panel_1.setBounds(10, 11, 389, 146);
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
			txtDireccion.setBounds(95, 79, 284, 20);
			panel_1.add(txtDireccion);
			txtDireccion.setColumns(10);
			
			txtNombre = new JTextField();
			txtNombre.setEditable(false);
			txtNombre.setBounds(95, 47, 284, 20);
			panel_1.add(txtNombre);
			txtNombre.setColumns(10);
			
			JLabel lblTelefno = new JLabel("Tel\u00E9fono:");
			lblTelefno.setBounds(10, 113, 75, 14);
			panel_1.add(lblTelefno);
			
			txtTelefono = new JTextField();
			txtTelefono.setEditable(false);
			txtTelefono.setBounds(95, 110, 148, 20);
			panel_1.add(txtTelefono);
			txtTelefono.setColumns(10);
			
			txtIdCliente = new JTextField();
			txtIdCliente.setColumns(10);
			txtIdCliente.setBounds(95, 15, 148, 20);
			panel_1.add(txtIdCliente);
			
			btnBuscar = new JButton("Buscar");
			btnBuscar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String idCliente = txtIdCliente.getText();
					
					if (idCliente.length() > 0)
					{
						Cliente cliente = vendoQuesos.buscarClienteById(idCliente);
						txtNombre.setEditable(true);
						txtDireccion.setEditable(true);
						txtTelefono.setEditable(true);
						txtIdCliente.setEditable(false);
						
						if (cliente == null) {
							JOptionPane.showMessageDialog(null, "No se encontr� el cliente solicitado, por favor registrese aqu�.", "Informaci�n.", JOptionPane.WARNING_MESSAGE);
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
						JOptionPane.showMessageDialog(null, "Por favor ingrese su identificaci�n.", "Informaci�n.", JOptionPane.WARNING_MESSAGE);
					}
	
				}
			});
			btnBuscar.setBounds(253, 14, 89, 23);
			panel_1.add(btnBuscar);
			
			btnModificar = new JButton("Modificar");
			btnModificar.setEnabled(false);
			btnModificar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (btnModificar.getText().equalsIgnoreCase("Registrar")) {
						Cliente nuevo = new Cliente(txtIdCliente.getText(), txtNombre.getText(), txtDireccion.getText(), txtTelefono.getText());
						vendoQuesos.getMisClientes().add(nuevo);
						clear(false);
						JOptionPane.showMessageDialog(null, "Cliente registrado exitosamente.", "Informaci�n", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						Cliente cliente = vendoQuesos.buscarClienteById(txtIdCliente.getText());
						cliente.setNombre(txtNombre.getText());
						cliente.setDireccion(txtDireccion.getText());
						cliente.setTelefono(txtTelefono.getText());
						JOptionPane.showMessageDialog(null, "Datos modificados exitosamente.", "Informaci�n", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
			btnModificar.setBounds(253, 109, 89, 23);
			panel_1.add(btnModificar);
			
			JPanel panel_2 = new JPanel();
			panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Compra y venta de quesos.", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			panel_2.setBounds(10, 168, 389, 273);
			panel.add(panel_2);
			panel_2.setLayout(null);
			
			JPanel panel_3 = new JPanel();
			panel_3.setBorder(new TitledBorder(null, "Quesos Disponibles", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_3.setBounds(10, 25, 149, 237);
			panel_2.add(panel_3);
			panel_3.setLayout(new BorderLayout(0, 0));
			
			JScrollPane scrollPane = new JScrollPane();
			panel_3.add(scrollPane, BorderLayout.CENTER);
			
			lstStock = new JList();
			scrollPane.setViewportView(lstStock);
			lstStock.setEnabled(false);
			lstStock.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			lstStock.setToolTipText("Seleccione el queso que desea comprar");
			lstStock.setModel(new DefaultListModel<String>());
			
			JPanel panel_4 = new JPanel();
			panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Carrito de Compras", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			panel_4.setBounds(230, 25, 149, 237);
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
			
			lstCarrito = new JList();
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
						JOptionPane.showMessageDialog(null, "No hay queso seleccionado, por favor elija uno.", "Informaci�n.", JOptionPane.WARNING_MESSAGE);
					else
						actualizarLista(false, selection);
				}
			});
			btnReturn.setEnabled(false);
			btnReturn.setToolTipText("Pulse para dejar un queso");
			btnReturn.setBounds(169, 147, 49, 47);
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
						JOptionPane.showMessageDialog(null, "No hay queso seleccionado, por favor elija uno.", "Informaci�n.", JOptionPane.WARNING_MESSAGE);
					else {
						actualizarLista(true, selection);
						
					}
						
				}
			});
			btnAdd.setEnabled(false);
			btnAdd.setToolTipText("Pulse para agregar un queso");
			btnAdd.setBounds(169, 89, 49, 47);
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
						Cliente cliente = vendoQuesos.buscarClienteById(txtIdCliente.getText());
						Factura factura = new Factura(cliente);
						for (String index : carritoCompras) {
							factura.getMisQuesos().add(vendoQuesos.getMisQuesos().get(formatItemIndex(index)));
						}
						
						String message = "Usted satisfactoriamente ha hecho la compra de los siguientes quesos:\n";
						for (Queso queso : factura.getMisQuesos()) {
							vendoQuesos.eliminarQueso(queso);
							message += "Tipo: ";
							if (queso instanceof Esfera)
								message += "Esf�rico";
							else if (queso instanceof CilindroHueco)
								message += "Cil�ndrico Hueco";
							else if (queso instanceof Cilindro)
								message += "Cil�ndrico";
							message += "; Volumen: " + formateador.format(queso.volumen()) + 
									"cm^3; Costo: " + formateador.format(queso.costo()) + "$\n";
						}
						vendoQuesos.addFactura(factura);
						message += "\nMonto a pagar: " + formateador.format(total) + "$.";
						JOptionPane.showMessageDialog(null, message, "Factura.", JOptionPane.INFORMATION_MESSAGE);
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
						if (btnCancelar.getText().equalsIgnoreCase("Cerrar"))
							dispose();
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
		
		if (vendoQuesos.getMisQuesos().size() == 0) {
			JOptionPane.showMessageDialog(null, "No hay existencias de queso", "Informaci�n.", JOptionPane.WARNING_MESSAGE);
			esPosibleVender = false;
		} else {
			int aux = 0; 
			stock.clear();
			carritoCompras.clear();
			for (Queso i : vendoQuesos.getMisQuesos()) {
				item = aux + ":" + formateador.format(i.costo()) + "$: ";
				aux++;
				if (i instanceof Esfera)
					item += "Esf�rico ";
				else if (i instanceof CilindroHueco)
					item += "Cil�ndrico Hueco ";
				else if (i instanceof Cilindro)
					item += "Cil�ndrico ";
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
			total += vendoQuesos.getMisQuesos().get(formatItemIndex(stock.get(index))).costo();
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
			total -= vendoQuesos.getMisQuesos().get(formatItemIndex(stock.get(index))).costo();
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