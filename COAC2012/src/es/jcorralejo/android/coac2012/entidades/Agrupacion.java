package es.jcorralejo.android.coac2012.entidades;

import java.io.Serializable;
import java.util.List;

public class Agrupacion implements Serializable{
	
	private static final long serialVersionUID = -59448126852170073L;
	
	private int id;
	private String modalidad;
	private String nombre;
	private String autor;
	private String director;
	private String localidad;
	private String coac2011;
	private boolean cabezaSerie;
	private String info;
	private String url_cc;
	private String url_foto;
	private String url_videos;
	private String puntos;
	private List<Foto> fotos;
	private List<Video> videos;
	private List<Componente> componentes;
	private List<Comentario> comentarios;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getModalidad() {
		return modalidad;
	}
	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public String getCoac2011() {
		return coac2011;
	}
	public void setCoac2011(String coac2011) {
		this.coac2011 = coac2011;
	}
	public boolean isCabezaSerie() {
		return cabezaSerie;
	}
	public void setCabezaSerie(boolean cabezaSerie) {
		this.cabezaSerie = cabezaSerie;
	}
	public String getUrl_cc() {
		return url_cc;
	}
	public void setUrl_cc(String url_cc) {
		this.url_cc = url_cc;
	}
	public String getUrl_foto() {
		return url_foto;
	}
	public void setUrl_foto(String url_foto) {
		this.url_foto = url_foto;
	}
	public String getUrl_videos() {
		return url_videos;
	}
	public void setUrl_videos(String url_videos) {
		this.url_videos = url_videos;
	}
	public List<Foto> getFotos() {
		return fotos;
	}
	public void setFotos(List<Foto> fotos) {
		this.fotos = fotos;
	}
	public List<Video> getVideos() {
		return videos;
	}
	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}
	public List<Componente> getComponentes() {
		return componentes;
	}
	public void setComponentes(List<Componente> componentes) {
		this.componentes = componentes;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public List<Comentario> getComentarios() {
		return comentarios;
	}
	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}
	public String getPuntos() {
		return puntos;
	}
	public void setPuntos(String puntos) {
		this.puntos = puntos;
	}

}
