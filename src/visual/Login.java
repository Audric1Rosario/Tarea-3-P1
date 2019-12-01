package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import logical.Complejo;
import logical.Usuario;

public class Login extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsuario;
	private String screenPath;
	private JPasswordField passPass;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				FileInputStream fabrica;
				FileOutputStream fabrica2;
				ObjectInputStream fabricaRead;
				ObjectOutputStream fabricaWrite;
				
				// Crear directorio
				String path = System.getProperty("user.dir");
				File directorio = new File(path + "/data");
				
				if (!directorio.exists()) {
					if (directorio.mkdirs()) {
						
					} else {
						JOptionPane.showMessageDialog(null, "Error al cargar datos.", "Data.", JOptionPane.WARNING_MESSAGE);
					}
				}
				
				// Cargar la clase controladora
				try {
					fabrica = new FileInputStream ("data/queso.dat");
					fabricaRead = new ObjectInputStream(fabrica);
					Complejo temp = (Complejo)fabricaRead.readObject();
					fabrica.close();
					fabricaRead.close();
				} catch (FileNotFoundException e) {
					try {
						fabrica2 = new  FileOutputStream("data/fabrica.dat");
						fabricaWrite = new ObjectOutputStream(fabrica2);
						//String aux = new Administrador("Administrador", "Admin", "Admin", 1);
						fabrica2.close();
						fabricaWrite.close();
					} catch (FileNotFoundException e1) {
					} catch (IOException e1) {
						// TODO Auto-generated catch block
					}
				} catch (IOException e) {


				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
				// Abrir el frame
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setInit();
		
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/images/cheese.png")));
		setResizable(false);
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 530);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		panel.setForeground(new Color(255, 215, 0));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblLoginImage = new JLabel("");
		screenPath = "/images/userA.png";
		lblLoginImage.setBounds(278, 59, 186, 176);
		lblLoginImage.setIcon(new ImageIcon(((new ImageIcon(Login.class.getResource(screenPath))).getImage()).getScaledInstance(
				lblLoginImage.getWidth(), lblLoginImage.getHeight(), Image.SCALE_SMOOTH)));
		panel.add(lblLoginImage);
		
		JLabel lblLogo = new JLabel("");
		screenPath = "/images/cheese.png";
		lblLogo.setBounds(10, 11, 66, 61);
		lblLogo.setIcon(new ImageIcon(((new ImageIcon(Login.class.getResource(screenPath))).getImage()).getScaledInstance(
				lblLogo.getWidth(), lblLogo.getHeight(), Image.SCALE_SMOOTH)));
		panel.add(lblLogo);
		
		txtUsuario = new JTextField();
		txtUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		txtUsuario.setBounds(255, 285, 233, 27);
		panel.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		JButton btnEnter = new JButton("Entrar");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = txtUsuario.getText();
				String password = String.valueOf(passPass.getPassword());
				boolean ok = false;
				
				Usuario actual = Complejo.getInstance().buscarUsuarioByUsername(username);
				
				if (actual != null)
				{
					if (actual.getPassword().equals(password)) {
						ok = true;
					}
				}
				
				if (ok) {
					Principal ventana = new Principal();				
				    ventana.setVisible(true);
					
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.", "Error de inicio.", JOptionPane.WARNING_MESSAGE);
				}
				
			}
		});
		btnEnter.setBounds(285, 395, 173, 27);
		panel.add(btnEnter);
		
		JLabel lblUsername = new JLabel("Usuario");
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setBounds(334, 261, 74, 14);
		panel.add(lblUsername);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setHorizontalAlignment(SwingConstants.CENTER);
		lblContrasea.setBounds(334, 323, 74, 14);
		panel.add(lblContrasea);
		
		JLabel lblNewLabel = new JLabel("Fabrica de Quesos");
		lblNewLabel.setFont(new Font("Monotype Corsiva", Font.BOLD, 20));
		lblNewLabel.setForeground(Color.ORANGE);
		lblNewLabel.setBounds(89, 26, 214, 36);
		panel.add(lblNewLabel);
		
		passPass = new JPasswordField();
		passPass.setHorizontalAlignment(SwingConstants.CENTER);
		passPass.setBounds(255, 348, 233, 27);
		panel.add(passPass);
	}

	
	private void setInit() {
		// TODO Auto-generated method stub
		//Clinica.getInstance(); // Crear la clase controladora
		
	}
}