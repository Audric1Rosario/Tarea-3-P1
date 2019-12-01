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
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.Toolkit;

public class CrearQueso extends JDialog {
	private Complejo vendoQuesos;
	private final JPanel contentPanel = new JPanel();

	// Radio button
	private JRadioButton rdbtnEsfera;
	private JRadioButton rdbtnCilindro;
	private JRadioButton rdbtnCilindroHueco;
	// Paneles
	private JPanel panelEsfera;
	private JPanel panelCilindro;
	private JPanel panelCilindroHueco;
	// Spinner
	// Costo
	private JSpinner spnPrecioBase;
	private JSpinner spnPrecioUnitario;
	// Características
	private JSpinner spnRadio;
	private JSpinner spnRadioCil;
	private JSpinner spnRadioAltura;
	private JSpinner spnRadioCilHue;
	private JSpinner spnAlturaCilHue;
	private JSpinner spnRadioInt;
	
	// Decimales
	private DecimalFormat formateador;

	public CrearQueso(Complejo vendoQuesos) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(CrearQueso.class.getResource("/images/cheese.png")));
		formateador = new DecimalFormat("####.##");
		this.vendoQuesos = vendoQuesos;
		setTitle("Fabricar un queso");
		setResizable(false);
		setBounds(100, 100, 450, 300);
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
			panel_1.setBorder(new TitledBorder(null, "Tipo de queso", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_1.setBounds(10, 11, 414, 66);
			panel.add(panel_1);
			panel_1.setLayout(null);
			{
				rdbtnEsfera = new JRadioButton("Esfera");
				rdbtnEsfera.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {						
						rdbtnCilindro.setSelected(false);
						rdbtnCilindroHueco.setSelected(false);
						panelEsfera.setVisible(true);
						panelCilindro.setVisible(false);
						panelCilindroHueco.setVisible(false);
					}
				});
				rdbtnEsfera.setSelected(true);
				rdbtnEsfera.setBounds(21, 21, 109, 23);
				panel_1.add(rdbtnEsfera);
			}
			{
				rdbtnCilindro = new JRadioButton("Cilindro");
				rdbtnCilindro.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						rdbtnEsfera.setSelected(false);
						rdbtnCilindroHueco.setSelected(false);
						panelEsfera.setVisible(false);
						panelCilindro.setVisible(true);
						panelCilindroHueco.setVisible(false);
					}
				});
				rdbtnCilindro.setBounds(151, 21, 109, 23);
				panel_1.add(rdbtnCilindro);
			}
			{
				rdbtnCilindroHueco = new JRadioButton("Cilindro Hueco");
				rdbtnCilindroHueco.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						rdbtnEsfera.setSelected(false);
						rdbtnCilindro.setSelected(false);
						panelEsfera.setVisible(false);
						panelCilindro.setVisible(false);
						panelCilindroHueco.setVisible(true);
					}
				});
				rdbtnCilindroHueco.setBounds(281, 21, 109, 23);
				panel_1.add(rdbtnCilindroHueco);
			}
			{
				JPanel panel_2 = new JPanel();
				panel_2.setBorder(new TitledBorder(null, "Costo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel_2.setBounds(10, 88, 195, 124);
				panel.add(panel_2);
				panel_2.setLayout(null);
				{
					JLabel lblPrecioBase = new JLabel("Precio Base");
					lblPrecioBase.setBounds(10, 32, 83, 14);
					panel_2.add(lblPrecioBase);
				}
				{
					JLabel lblPrecioUnitario = new JLabel("Precio Unidad");
					lblPrecioUnitario.setBounds(10, 78, 83, 14);
					panel_2.add(lblPrecioUnitario);
				}

				spnPrecioBase = new JSpinner();
				spnPrecioBase.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(1)));
				spnPrecioBase.setBounds(103, 29, 82, 20);
				panel_2.add(spnPrecioBase);
				{
					spnPrecioUnitario = new JSpinner();
					spnPrecioUnitario.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(1)));
					spnPrecioUnitario.setBounds(103, 75, 82, 20);
					panel_2.add(spnPrecioUnitario);
				}
			}
			{
				panelEsfera = new JPanel();
				panelEsfera.setBorder(new TitledBorder(null, "Caracter\u00EDsticas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panelEsfera.setBounds(215, 88, 209, 124);
				panel.add(panelEsfera);
				panelEsfera.setLayout(null);
				panelEsfera.setVisible(true);

				JLabel label = new JLabel("Radio");
				label.setBounds(10, 26, 75, 14);
				panelEsfera.add(label);

				spnRadio = new JSpinner();
				spnRadio.setToolTipText("(cm)");
				spnRadio.setModel(new SpinnerNumberModel(new Float(1), new Float(1), null, new Float(1)));
				spnRadio.setBounds(95, 23, 104, 20);
				panelEsfera.add(spnRadio);
			}
			{
				panelCilindro = new JPanel();
				panelCilindro.setToolTipText("(cm)");
				panelCilindro.setBorder(new TitledBorder(null, "Caracter\u00EDsticas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panelCilindro.setBounds(215, 88, 209, 124);
				panel.add(panelCilindro);
				panelCilindro.setLayout(null);
				panelCilindro.setVisible(false);

				JLabel label = new JLabel("Radio");
				label.setBounds(10, 26, 75, 14);
				panelCilindro.add(label);

				spnRadioCil = new JSpinner();
				spnRadioCil.setToolTipText("(cm)");
				spnRadioCil.setModel(new SpinnerNumberModel(new Float(1), new Float(1), null, new Float(1)));
				spnRadioCil.setBounds(95, 23, 104, 20);
				panelCilindro.add(spnRadioCil);

				JLabel label_1 = new JLabel("Altura");
				label_1.setBounds(10, 54, 75, 14);
				panelCilindro.add(label_1);

				spnRadioAltura = new JSpinner();
				spnRadioAltura.setToolTipText("(cm)");
				spnRadioAltura.setModel(new SpinnerNumberModel(new Float(1), new Float(1), null, new Float(1)));
				spnRadioAltura.setBounds(95, 51, 104, 20);
				panelCilindro.add(spnRadioAltura);
			}
			{
				panelCilindroHueco = new JPanel();
				panelCilindroHueco.setBorder(new TitledBorder(null, "Caracter\u00EDsticas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panelCilindroHueco.setBounds(215, 88, 209, 124);
				panel.add(panelCilindroHueco);
				panelCilindroHueco.setLayout(null);
				panelCilindroHueco.setVisible(false);

				JLabel label = new JLabel("Radio");
				label.setBounds(10, 26, 75, 14);
				panelCilindroHueco.add(label);

				JLabel lblAltura = new JLabel("Altura");
				lblAltura.setBounds(10, 54, 75, 14);
				panelCilindroHueco.add(lblAltura);

				JLabel lblRadioInterior = new JLabel("Rad. interior");
				lblRadioInterior.setBounds(10, 82, 75, 14);
				panelCilindroHueco.add(lblRadioInterior);

				spnRadioCilHue = new JSpinner();
				spnRadioCilHue.setToolTipText("(cm)");
				spnRadioCilHue.setModel(new SpinnerNumberModel(new Float(2), new Float(2), null, new Float(1)));
				spnRadioCilHue.setBounds(95, 23, 104, 20);
				panelCilindroHueco.add(spnRadioCilHue);

				spnAlturaCilHue = new JSpinner();
				spnAlturaCilHue.setToolTipText("(cm)");
				spnAlturaCilHue.setModel(new SpinnerNumberModel(new Float(1), new Float(1), null, new Float(1)));
				spnAlturaCilHue.setBounds(95, 51, 104, 20);
				panelCilindroHueco.add(spnAlturaCilHue);

				spnRadioInt = new JSpinner();
				spnRadioInt.setToolTipText("(cm)");
				spnRadioInt.setModel(new SpinnerNumberModel(new Float(1), new Float(1), null, new Float(1)));
				spnRadioInt.setBounds(95, 79, 104, 20);
				panelCilindroHueco.add(spnRadioInt);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnAceptar = new JButton("Aceptar");
				btnAceptar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						float precioBase = Float.valueOf(spnPrecioBase.getValue().toString());
						float precioUnitario = Float.valueOf(spnPrecioUnitario.getValue().toString());
						float radio = 0, altura = 0, radioInt = 0;
						Queso valor;
						int tipo = 0;
						
						if(rdbtnEsfera.isSelected())
							tipo = 0;
						else if (rdbtnCilindro.isSelected())
							tipo = 1;
						else if (rdbtnCilindroHueco.isSelected())
							tipo = 2;
						
						switch (tipo) {
						case 1:
							altura = Float.valueOf(spnRadioAltura.getValue().toString());
							radio = Float.valueOf(spnRadioCil.getValue().toString());							
							valor =  new Cilindro(precioBase, precioUnitario, radio, altura);
							vendoQuesos.getMisQuesos().add(valor);
							break;
						case 2: 
							altura = Float.valueOf(spnAlturaCilHue.getValue().toString());
							radio = Float.valueOf(spnRadioCilHue.getValue().toString());
							radioInt = Float.valueOf(spnRadioInt.getValue().toString());
							if (radio <= radioInt)
							{
								JOptionPane.showMessageDialog(null,
										"No puede crear un queso hueco con radio interior mayor al radio exterior", 
										"Información", JOptionPane.WARNING_MESSAGE);
								clear();
								return;
							}
							
							valor = new CilindroHueco(precioBase, precioUnitario, radio, altura, radioInt);
							vendoQuesos.getMisQuesos().add(valor);
							break;
						default:
							radio = Float.valueOf(spnRadio.getValue().toString());
							valor = new Esfera(precioBase, precioUnitario, radio);
							vendoQuesos.getMisQuesos().add(valor);
						}
						
						// Construir un mensaje de que está listo
						String message = "Usted ha creado un queso de tipo ";
						if (valor instanceof Esfera)
							message += "Esférico ";
						else if (valor instanceof CilindroHueco)
							message += "Cilíndrico Hueco ";
						else if (valor instanceof Cilindro)
							message += "Cilíndrico ";
						message += "de " + formateador.format(valor.volumen()) + "cm^3 y su costo es de: " 
							+ formateador.format(valor.costo()) + "$.";
						
						JOptionPane.showMessageDialog(null, message, "Notificación", JOptionPane.INFORMATION_MESSAGE);
						clear();
					}
				});
				btnAceptar.setActionCommand("OK");
				buttonPane.add(btnAceptar);
				getRootPane().setDefaultButton(btnAceptar);
			}
			{
				JButton btnCancel = new JButton("Cerrar");
				btnCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btnCancel.setActionCommand("Cancel");
				buttonPane.add(btnCancel);
			}
		}
				
	}
	
	private void clear() {
		spnRadio.setValue(Float.valueOf(1.0f));
		spnRadioCil.setValue(Float.valueOf(1.0f));
		spnRadioCilHue.setValue(Float.valueOf(2.0f));
		spnRadioAltura.setValue(Float.valueOf(1.0f));
		spnAlturaCilHue.setValue(Float.valueOf(1.0f));
		spnRadioInt.setValue(Float.valueOf(1.0f));
		return;
	}
}
