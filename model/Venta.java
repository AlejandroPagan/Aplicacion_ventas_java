package model;
/*Esta clase es el modelo venta, el cual se utiliza en VentaDAO para añadir de manera sencilla nuevas ventas
 * */
public class Venta {
	private int comercialID;
	private String mes;
	private int cantidad;
	
	public Venta (int comercialID, String mes, int cantidad) {
		this.comercialID = comercialID;
		this.mes= mes;
		this.cantidad= cantidad;
	}

	public int getComercialID() {
		return comercialID;
	}

	public void setComercialID(int comercialID) {
		this.comercialID = comercialID;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}	
}
