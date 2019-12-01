package logical;

public class Usuario {
	private String username;
	private String password;
	private String tipo;
	private String nombre;
	
	public Usuario(String username, String password, String tipo, String nombre) {
		super();
		this.username = username;
		this.password = password;
		this.tipo = tipo;
		this.nombre = nombre;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
