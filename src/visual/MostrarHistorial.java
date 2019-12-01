package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logical.Cilindro;
import logical.CilindroHueco;
import logical.Complejo;
import logical.Esfera;
import logical.Queso;

import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.ListSelectionModel;

public class MostrarHistorial extends JDialog {
	private final JPanel contentPanel = new JPanel();
	private JTextField txtEsfera;
	private JTextField txtCilindro;
	private JTextField txtHueco;
	
	// Table
	private DefaultTableModel model;
	private Object row[];
	private JTable table;

	// Formateador
	DecimalFormat formateador;

	public MostrarHistorial() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MostrarHistorial.class.getResource("/images/cheese.png")));
		formateador = new DecimalFormat("####.##");
		setTitle("Historial de quesos");
		setResizable(false);
		setBounds(100, 100, 502, 470);
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
			panel_1.setBorder(new TitledBorder(null, "Inventario actual:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_1.setBounds(10, 11, 466, 82);
			panel.add(panel_1);
			panel_1.setLayout(null);
			
			txtEsfera = new JTextField();
			txtEsfera.setHorizontalAlignment(SwingConstants.CENTER);
			txtEsfera.setEditable(false);
			txtEsfera.setBounds(22, 48, 125, 20);
			panel_1.add(txtEsfera);
			txtEsfera.setColumns(10);
			
			txtCilindro = new JTextField();
			txtCilindro.setHorizontalAlignment(SwingConstants.CENTER);
			txtCilindro.setEditable(false);
			txtCilindro.setBounds(169, 48, 125, 20);
			panel_1.add(txtCilindro);
			txtCilindro.setColumns(10);
			
			txtHueco = new JTextField();
			txtHueco.setHorizontalAlignment(SwingConstants.CENTER);
			txtHueco.setEditable(false);
			txtHueco.setBounds(316, 48, 125, 20);
			panel_1.add(txtHueco);
			txtHueco.setColumns(10);
			
			JLabel lblEsfrico = new JLabel("Esf\u00E9rico");
			lblEsfrico.setHorizontalAlignment(SwingConstants.CENTER);
			lblEsfrico.setBounds(22, 23, 125, 14);
			panel_1.add(lblEsfrico);
			
			JLabel lblCilndrico = new JLabel("Cil\u00EDndrico");
			lblCilndrico.setHorizontalAlignment(SwingConstants.CENTER);
			lblCilndrico.setBounds(169, 23, 125, 14);
			panel_1.add(lblCilndrico);
			
			JLabel lblCilndricoHueco = new JLabel("Cil\u00EDndrico Hueco");
			lblCilndricoHueco.setHorizontalAlignment(SwingConstants.CENTER);
			lblCilndricoHueco.setBounds(316, 23, 125, 14);
			panel_1.add(lblCilndricoHueco);
			
			JPanel panel_2 = new JPanel();
			panel_2.setBorder(new TitledBorder(null, "Historial de quesos vendidos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_2.setBounds(10, 104, 466, 278);
			panel.add(panel_2);
			panel_2.setLayout(new BorderLayout(0, 0));
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			panel_2.add(scrollPane, BorderLayout.CENTER);
			
			model = new DefaultTableModel();
			String[] headers = { "ID-Factura", "ID-Cliente", "Cliente", "Tipo", "Vol (cm^3)", "Costo"};
			model.setColumnIdentifiers(headers);	
			table = new JTable();
			table.setRowSelectionAllowed(false);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.setModel(model);
			scrollPane.setViewportView(table);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnCerrar = new JButton("Cerrar");
				btnCerrar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btnCerrar.setActionCommand("Cancel");
				buttonPane.add(btnCerrar);
			}
		}
		loadQuesos();
	}

	private void loadQuesos() {
		model.setRowCount(0);
		row = new Object[model.getColumnCount()];
		for (int i = 0; i < Complejo.getInstance().getMisFacturaciones().size(); i++) {
			for (int j = 0; j < Complejo.getInstance().getMisFacturaciones().get(i).getMisQuesos().size(); j++) {
				row[0] = Complejo.getInstance().getMisFacturaciones().get(i).getIdFactura();
				row[1] = Complejo.getInstance().getMisFacturaciones().get(i).getMiCliente().getIdCliente();
				row[2] = Complejo.getInstance().getMisFacturaciones().get(i).getMiCliente().getNombre();
				Queso queso = Complejo.getInstance().getMisFacturaciones().get(i).getMisQuesos().get(j);
				if (queso instanceof Esfera)
					row[3] = "Esférico";
				else if (queso instanceof CilindroHueco)
					row[3] = "Hueco";
				else if (queso instanceof Cilindro)
					row[3] = "Cilíndrico";
				row[4] = formateador.format(queso.volumen());
				row[5] = formateador.format(queso.costo()) + "$";
				model.addRow(row);
			}
		}
		
		int data[] = Complejo.getInstance().obtenerQuesos();
		txtEsfera.setText(Integer.valueOf(data[0]).toString());
		txtHueco.setText(Integer.valueOf(data[1]).toString());
		txtCilindro.setText(Integer.valueOf(data[2]).toString());
		return;
	}
}
