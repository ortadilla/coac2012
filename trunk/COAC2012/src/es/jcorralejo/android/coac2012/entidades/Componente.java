package es.jcorralejo.android.coac2012.entidades;

import java.io.Serializable;

public class Componente implements Serializable{
	
	private static final long serialVersionUID = 2604329865716459603L;
	private String nombre;
	private String voz;
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getVoz() {
		return voz;
	}
	public void setVoz(String voz) {
		this.voz = voz;
	}

}
