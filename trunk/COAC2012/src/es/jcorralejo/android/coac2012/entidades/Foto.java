package es.jcorralejo.android.coac2012.entidades;

import java.io.Serializable;

public class Foto implements Serializable{
	private static final long serialVersionUID = 6561609868525623543L;
	private String descripcion;
	private String url;
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Foto(String descripcion, String url) {
		super();
		this.descripcion = descripcion;
		this.url = url;
	}
}
