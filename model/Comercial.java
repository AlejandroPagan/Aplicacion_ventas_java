package model;
/*Esta clase es la clase que uso para crear nuevos comerciales de forma sencilla y estructurada
 * se usa principalmente en el desplegable "AñadirComercial"
 * Y en ComercialDAO
 * */
public class Comercial {
	private int id;
	private String nombre;
	private boolean especial;
	
	public Comercial(int id, String nombre, boolean especial) {
		this.id = id;
		this.nombre = nombre;
		this.especial = especial;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isEspecial() {
		return especial;
	}

	public void setEspecial(boolean especial) {
		this.especial = especial;
	}
}

