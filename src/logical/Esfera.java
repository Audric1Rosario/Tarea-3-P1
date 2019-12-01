package logical;

public class Esfera extends Queso {

	public Esfera(float precioBase, float precioUnitario, float radio) {
		super(precioBase, precioUnitario, radio);
		// TODO Auto-generated constructor stub
	}

	@Override
	public float volumen() {
		float vol = 0;
		vol = (float)(Math.PI * Math.pow(radio, 3)) * (4.0f / 3.0f);
		return vol;
	}

}
