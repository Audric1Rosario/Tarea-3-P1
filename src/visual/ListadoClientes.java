package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

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
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;

public class ListadoClientes extends JDialog {
	private Complejo vendoQuesos;
	private final JPanel contentPanel = new JPanel();

	// Table
	private DefaultTableModel model;
	private Object row[];
	private JTable table;

	// Decimal Formateador
	private DecimalFormat formateador;

	public ListadoClientes(Complejo vendoQuesos) {
		formateador = new DecimalFormat("####.##");
		setTitle("Listado de clientes");
		setResizable(false);
		this.vendoQuesos = vendoQuesos;
		setBounds(100, 100, 502, 470);
		getContentPane().setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "Adquisiciones de los clientes por factura", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel.setBounds(10, 11, 476, 381);
			contentPanel.add(panel);
			panel.setLayout(new BorderLayout(0, 0));
			{
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				panel.add(scrollPane, BorderLayout.CENTER);
				model = new DefaultTableModel();
				String[] headers = { "ID-Factura", "ID-Cliente", "Cliente", "Mayor Esfera", "Cant. Quesos", "Total"};
				model.setColumnIdentifiers(headers);	
				table = new JTable();
				table.setModel(model);
				scrollPane.setViewportView(table);
			}
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
		for (int i = 0; i < vendoQuesos.getMisFacturaciones().size(); i++) {
			row[0] = vendoQuesos.getMisFacturaciones().get(i).getIdFactura();
			row[1] = vendoQuesos.getMisFacturaciones().get(i).getMiCliente().getIdCliente();
			row[2] = vendoQuesos.getMisFacturaciones().get(i).getMiCliente().getNombre();
			row[3] = formateador.format(vendoQuesos.mayorQuesoEsferico(row[0].toString())) + "$";
			row[4] = vendoQuesos.getMisFacturaciones().get(i).getMisQuesos().size();
			row[5] = formateador.format(vendoQuesos.calcularPrecio(row[0].toString())) + "$";
			model.addRow(row);
		}
		return;
	}
}
