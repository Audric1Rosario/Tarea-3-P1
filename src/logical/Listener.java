package logical;

import java.io.IOException;

import javax.swing.JOptionPane;

import visual.Principal;

public class Listener extends Thread {
	
	public Listener() {
		super();
	}
	
	@Override
	public void run() {
		boolean cerroServidor = false;
		while (!Thread.currentThread().isInterrupted() && !cerroServidor) {
			try {
				String linea = Principal.getEntradaSocket().readUTF();
				if (linea != "") {
					JOptionPane.showMessageDialog(null, "El servidor principal ha cerrado.", linea, JOptionPane.INFORMATION_MESSAGE);
					cerroServidor = true;
					System.exit(1);
				}
			}
			catch(IOException ioe)
			{
				System.exit(1);
			} 
		}
	}
}
