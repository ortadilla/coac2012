package es.jcorralejo.android.coac2012.entidades;

import java.io.Serializable;

public class Enlace implements Serializable{
	
	private static final long serialVersionUID = 7579152533156096563L;
	private String tipo;
	private String descripcion;
	private String url;
	
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
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
	

}
