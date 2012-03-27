package es.jcorralejo.android.coac2012.entidades;

import java.io.Serializable;

public class Comentario implements Serializable{
	
	private static final long serialVersionUID = -4812512377695460270L;
	private String origen;
	private String url;
	
	public Comentario(String origen, String url) {
		super();
		this.origen = origen;
		this.url = url;
	}
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	

}
