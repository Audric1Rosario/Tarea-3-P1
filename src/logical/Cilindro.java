package logical;

public class Cilindro extends Queso {
	protected float altura;
	
	public Cilindro(float precioBase, float precioUnitario, float radio, float altura) {
		super(precioBase, precioUnitario, radio);
		this.altura = altura;
	}
	
	public float getAltura() {
		return altura;
	}

	public void setAltura(float altura) {
		this.altura = altura;
	}

	@Override
	public float volumen() {
		float vol = 0;
		vol = (float)Math.PI * altura * ((float)Math.pow(radio, 2));
		return vol;
	}

	

}
