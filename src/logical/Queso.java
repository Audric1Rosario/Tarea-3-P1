package logical;

public abstract class Queso {
	protected float precioBase;
	protected float precioUnitario;
	protected float radio;
	
	public Queso(float precioBase, float precioUnitario, float radio) {
		super();
		this.precioBase = precioBase;
		this.precioUnitario = precioUnitario;
		this.radio = radio;
	}

	public float getPrecioBase() {
		return precioBase;
	}

	public float getPrecioUnitario() {
		return precioUnitario;
	}

	public float getRadio() {
		return radio;
	}

	public void setPrecioBase(float precioBase) {
		this.precioBase = precioBase;
	}

	public void setPrecioUnitario(float precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public void setRadio(float radio) {
		this.radio = radio;
	}
	
	public float costo() {
		return precioBase + precioUnitario*volumen();
	}
	
	public abstract float volumen();
}
