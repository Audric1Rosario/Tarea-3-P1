package logical;

import java.io.Serializable;
import java.util.ArrayList;

public class Factura implements Serializable {
	private static final long serialVersionUID = 1L;
	private String idFactura;
	private Cliente miCliente;
	private ArrayList<Queso> misQuesos;
	private static int codId = 0;
	
	public Factura(Cliente miCliente) {
		super();
		this.miCliente = miCliente;
		misQuesos = new ArrayList<Queso>();
		this.idFactura = "F-" + codId;
		codId++;
	}

	public String getIdFactura() {
		return idFactura;
	}

	public Cliente getMiCliente() {
		return miCliente;
	}

	public ArrayList<Queso> getMisQuesos() {
		return misQuesos;
	}

	public void setMiCliente(Cliente miCliente) {
		this.miCliente = miCliente;
	}

	public void setMisQuesos(ArrayList<Queso> misQuesos) {
		this.misQuesos = misQuesos;
	}

	public float total() {
		float t = 0;
		for (int i = 0; i < misQuesos.size(); i++) {
			t += misQuesos.get(i).costo();
		}
		return t;
	}

	public Esfera esferaMayor() {
		Esfera buscada = null;
		for (Queso queso : misQuesos) {
			if (queso instanceof Esfera) {
				if (buscada != null) {
					if (buscada.volumen() < queso.volumen()) {
						buscada = (Esfera) queso;
					}
				} else
					buscada = (Esfera)queso;
			}
		}
		return buscada;
	}
}
