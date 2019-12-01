package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logical.Complejo;

import java.awt.Panel;
import java.awt.FlowLayout;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.awt.event.ActionEvent;

public class Principal extends JFrame {
	private Complejo vendoQuesos;
	private JPanel contentPane;
	private String screenPath;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Principal() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/images/cheese.png")));
		setResizable(false);
		setTitle("F\u00E1brica de Quesos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 530);
		setLocationRelativeTo(null);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFabricar = new JMenu("Fabricar");
		menuBar.add(mnFabricar);
		
		JMenuItem mntmCrearQueso = new JMenuItem("Crear un queso");
		mntmCrearQueso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CrearQueso ventana = new CrearQueso(vendoQuesos);
				ventana.setModal(true);
				ventana.setVisible(true);
			}
		});
		mnFabricar.add(mntmCrearQueso);
		
		JMenuItem mntmMostrarHist = new JMenuItem("Mostrar historial");
		mntmMostrarHist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MostrarHistorial ventana = new MostrarHistorial(vendoQuesos);
				ventana.setModal(true);
				ventana.setVisible(true);
			}
		});
		mnFabricar.add(mntmMostrarHist);
		
		JMenu mnQuesos = new JMenu("Quesos");
		menuBar.add(mnQuesos);
		
		JMenuItem mntmPuntoDeVenta = new JMenuItem("Punto de venta");
		mntmPuntoDeVenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VenderQueso ventana = null;
				try {
					ventana = new VenderQueso(vendoQuesos);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				ventana.setVisible(true);
				ventana.setModal(true);
				
			}
		});
		mnQuesos.add(mntmPuntoDeVenta);
		
		JMenuItem mntmClientes = new JMenuItem("Clientes");
		mntmClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListadoClientes ventana = new ListadoClientes(vendoQuesos);
				ventana.setModal(true);
				ventana.setVisible(true);
			}
		});
		mnQuesos.add(mntmClientes);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 222, 173));
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setVgap(15);
		flowLayout.setHgap(15);
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JPanel panelScreen = new JPanel();
		contentPane.add(panelScreen, BorderLayout.CENTER);
		panelScreen.setLayout(null);
		
		// Para escalar las imagenes: 
		JLabel lblImage = new JLabel("");
		lblImage.setBounds(159, 93, 402, 215);
		//lblImage.setIcon(new ImageIcon(Principal.class.getResource("/images/screenCheese.png")));
		screenPath = "/images/cheeseStand.png";
		ImageIcon screen = new ImageIcon(Principal.class.getResource(screenPath));
		Image img = screen.getImage();
		Image nueva = img.getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_SMOOTH);
		lblImage.setIcon(new ImageIcon(nueva));
		panelScreen.add(lblImage);
		
		JLabel lblBienvenidosA = new JLabel("Bienvenidos a");
		lblBienvenidosA.setFont(new Font("Tahoma", Font.PLAIN, 48));
		lblBienvenidosA.setForeground(new Color(255, 165, 0));
		lblBienvenidosA.setBounds(225, 27, 313, 67);
		panelScreen.add(lblBienvenidosA);
		
		JLabel lblLaFabricaDe = new JLabel("la f\u00E1brica de quesos");
		lblLaFabricaDe.setForeground(new Color(255, 165, 0));
		lblLaFabricaDe.setFont(new Font("Tahoma", Font.PLAIN, 48));
		lblLaFabricaDe.setBounds(156, 309, 448, 97);
		panelScreen.add(lblLaFabricaDe);
		
		// Clase controladora
		vendoQuesos = new Complejo();
	}
}
