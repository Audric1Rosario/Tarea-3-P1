package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logical.Complejo;
import logical.Listener;
import logical.Usuario;

import java.awt.Panel;
import java.awt.FlowLayout;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Principal extends JFrame {
	private JPanel contentPane;
	private String screenPath;
	
	// Variables lógicas.
	private Usuario actual;
	private static Socket socketActual;
	private static DataInputStream EntradaSocket;
	private static DataOutputStream SalidaSocket;
	private Listener escuchando;
/*
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
	}*/

	public Principal(Usuario actual) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				// Guardar datos
				FileOutputStream fabricaGuardar;
				ObjectOutputStream fabricaWrite;
				
				try {
					fabricaGuardar = new FileOutputStream("data/fabrica.dat");
					fabricaWrite = new ObjectOutputStream(fabricaGuardar);
					fabricaWrite.writeObject(Complejo.getInstance());
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		// Intentar conectarse al servidor
		try {
			socketActual = new Socket("127.0.0.1",15000);
			EntradaSocket = new DataInputStream(new BufferedInputStream(socketActual.getInputStream()));
			SalidaSocket = new DataOutputStream(new BufferedOutputStream(socketActual.getOutputStream()));
		}
		catch (UnknownHostException uhe) {
			JOptionPane.showMessageDialog(null, "No se puede acceder al servidor.", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		catch (IOException ioe) {
			JOptionPane.showMessageDialog(null, "Comunicación rechazada.", "Error de conexión al servidor.", JOptionPane.ERROR_MESSAGE);					
			System.exit(1);
		}
		// Continuar con la ventana.
		this.actual = actual;
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/images/cheese.png")));
		setResizable(false);
		setTitle("F\u00E1brica de Quesos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1080, 720);
		setLocationRelativeTo(null);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFabricar = new JMenu("Fabricar");
		menuBar.add(mnFabricar);
		
		JMenuItem mntmCrearQueso = new JMenuItem("Crear un queso");
		mntmCrearQueso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CrearQueso ventana = new CrearQueso();
				ventana.setModal(true);
				ventana.setVisible(true);
			}
		});
		mnFabricar.add(mntmCrearQueso);
		
		JMenuItem mntmMostrarHist = new JMenuItem("Mostrar historial");
		mntmMostrarHist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MostrarHistorial ventana = new MostrarHistorial();
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
				VenderQueso ventana = new VenderQueso();				
				ventana.setModal(true);
				ventana.setVisible(true);								
			}
		});
		mnQuesos.add(mntmPuntoDeVenta);
		
		JMenuItem mntmClientes = new JMenuItem("Clientes");
		mntmClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListadoClientes ventana = new ListadoClientes();
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
		lblImage.setBounds(232, 122, 593, 350);
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
		lblBienvenidosA.setBounds(372, 27, 313, 67);
		panelScreen.add(lblBienvenidosA);
		
		JLabel lblLaFabricaDe = new JLabel("la f\u00E1brica de quesos");
		lblLaFabricaDe.setForeground(new Color(255, 165, 0));
		lblLaFabricaDe.setFont(new Font("Tahoma", Font.PLAIN, 48));
		lblLaFabricaDe.setBounds(304, 500, 448, 97);
		panelScreen.add(lblLaFabricaDe);
		
		escuchando = new Listener();
		escuchando.start();
	}

	public static Socket getSocketActual() {
		return socketActual;
	}

	public static void setSocketActual(Socket socketActual) {
		Principal.socketActual = socketActual;
	}

	public static DataInputStream getEntradaSocket() {
		return EntradaSocket;
	}

	public static void setEntradaSocket(DataInputStream entradaSocket) {
		EntradaSocket = entradaSocket;
	}

	public static DataOutputStream getSalidaSocket() {
		return SalidaSocket;
	}

	public static void setSalidaSocket(DataOutputStream salidaSocket) {
		SalidaSocket = salidaSocket;
	}
}
