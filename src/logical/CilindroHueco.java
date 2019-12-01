package logical;

public class CilindroHueco extends Cilindro {
	private float radioInt;
	
	public CilindroHueco(float precioBase, float precioUnitario, float radio, float altura, float radioInt) {
		super(precioBase, precioUnitario, radio, altura);
		this.radioInt = radioInt;
	}

	public float getRadioInt() {
		return radioInt;
	}

	public void setRadioInt(float radioInt) {
		this.radioInt = radioInt;
	}

	public float volumen() {
		float vol = 0;
		vol = (float)Math.PI * altura * ((float) (Math.pow(radio, 2) - Math.pow(radioInt, 2)));
		return vol;
	}
}
