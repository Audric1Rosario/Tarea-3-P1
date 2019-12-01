package logical;

import java.io.Serializable;
import java.util.ArrayList;



public class Complejo implements Serializable {
	private static final long serialVersionUID = 1L;
	// Clase controladora ~~
	private ArrayList<Queso> misQuesos;
	private ArrayList<Cliente> misClientes;
	private ArrayList<Factura> misFacturaciones;
	private ArrayList<Usuario> misUsuarios;
	private static Complejo instancia = null;
	//private Usuario admin = new Usuario("admin", "1234", "admin");

	// Constructor
	private Complejo() {
		super();
		this.misQuesos = new ArrayList<Queso>();
		this.misClientes = new ArrayList<Cliente>();
		this.misFacturaciones = new ArrayList<Factura>();
		this.misUsuarios = new ArrayList<Usuario>();
		//misUsuarios.add(admin);
	}

	// 0. Crear una sola clase controladora
	// Patrón singleton
	private synchronized static void createInstance() {
		if (instancia == null) {
			instancia = new Complejo();
		}
	}

	public static Complejo getInstance() {
		if (instancia == null) 
			createInstance();
		return instancia;
	}

	public static void setInstance(Complejo data) {
		if (data != null)
			instancia = data;
	}


	// Getters and Setters
	public ArrayList<Queso> getMisQuesos() {
		return misQuesos;
	}

	public ArrayList<Cliente> getMisClientes() {
		return misClientes;
	}

	public ArrayList<Factura> getMisFacturaciones() {
		return misFacturaciones;
	}

	public void setMisQuesos(ArrayList<Queso> misQuesos) {
		this.misQuesos = misQuesos;
	}

	public void setMisClientes(ArrayList<Cliente> misClientes) {
		this.misClientes = misClientes;
	}

	public void setMisFacturaciones(ArrayList<Factura> misFacturaciones) {
		this.misFacturaciones = misFacturaciones;
	}

	// Agregar y eliminar

	public ArrayList<Usuario> getMisUsuarios() {
		return misUsuarios;
	}

	public void setMisUsuarios(ArrayList<Usuario> misUsuarios) {
		this.misUsuarios = misUsuarios;
	}

	public void addQueso (Queso nuevo) {
		misQuesos.add(nuevo);
		return;
	}

	public void addCliente (Cliente nuevo) {
		misClientes.add(nuevo);
		return;
	}

	public void addFactura (Factura nuevo) {
		misFacturaciones.add(nuevo);
		return;
	}

	public void addUsuario (Usuario nuevo) {
		misUsuarios.add(nuevo);
		return;
	}
	
	public boolean eliminarQueso (Queso aEliminar) {
		boolean encontrado = false; int aux = 0;
		while (aux < misQuesos.size() && !encontrado) {
			if (misQuesos.get(aux).equals(aEliminar)) {
				encontrado = true;
			}
			aux++;
		}

		if (encontrado) {
			misQuesos.remove(aux - 1);
		}

		return encontrado;
	}

	// Buscadores
	public Factura buscarFacturaById(String idFactura) {
		Factura buscar = null;
		boolean encontrado = false; int aux = 0;

		while (aux < misFacturaciones.size() && !encontrado) {
			if (misFacturaciones.get(aux).getIdFactura().equalsIgnoreCase(idFactura)) {
				buscar = misFacturaciones.get(aux);
				encontrado = true;
			}
			aux++;
		}
		return buscar;
	}

	public Cliente buscarClienteById(String idCliente) {
		Cliente buscar = null;
		boolean encontrado = false; int aux = 0;

		while (aux < misClientes.size() && !encontrado) {
			if (misClientes.get(aux).getIdCliente().equalsIgnoreCase(idCliente)) {
				buscar = misClientes.get(aux);
				encontrado = true;
			}
			aux++;
		}
		return buscar;
	}

	// Usuario
	public Usuario buscarUsuarioByUsername(String username) {
		Usuario buscar = null;
		boolean encontrado = false; int aux = 0;

		while (aux < misUsuarios.size() && !encontrado) {
			if (misUsuarios.get(aux).getUsername().equals(username)) {
				buscar = misUsuarios.get(aux);
				encontrado = true;
			}
			aux++;
		}
		return buscar;
	}


	// Tareas requeridas por el cliente de este software
	public float calcularPrecio(String idFactura) {
		float precioTotal = 0;
		Factura pedido = buscarFacturaById(idFactura);

		precioTotal = pedido.total();

		return precioTotal;
	}

	public int[] obtenerQuesos() {
		int tipoQueso[] = new int[3];

		for (Queso i : misQuesos) {
			if (i instanceof Esfera)
				tipoQueso[0]++;
			else if (i instanceof CilindroHueco)
				tipoQueso[1]++;
			else if (i instanceof Cilindro) 
				tipoQueso[2]++;
		}

		return tipoQueso;
	}

	public float mayorQuesoEsferico (String idFactura) {
		Esfera mayor = null;
		float precio = 0f;
		Factura pedido = buscarFacturaById(idFactura);

		if (pedido != null)
		{
			mayor = pedido.esferaMayor();
			precio = mayor.costo();
		}

		return precio;

	}
}
