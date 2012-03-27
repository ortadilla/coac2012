package es.jcorralejo.android.coac2012.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

import android.content.ContentValues;
import es.jcorralejo.android.coac2012.entidades.Agrupacion;
import es.jcorralejo.android.coac2012.entidades.Comentario;
import es.jcorralejo.android.coac2012.entidades.Componente;
import es.jcorralejo.android.coac2012.entidades.Enlace;
import es.jcorralejo.android.coac2012.entidades.Foto;
import es.jcorralejo.android.coac2012.entidades.Video;

public class RssHandler extends DefaultHandler implements LexicalHandler {
	
	public static final String COAC2012 = "coac2012";
	public static final String AGRUPACIONES = "agrupaciones";
	public static final String CALENDARIO = "calendario";
	public static final String AGRUPACION = "agrupacion";
	public static final String COMENTARIO = "comentario";
	public static final String ID = "id";
	public static final String MODALIDAD = "modalidad";
	public static final String NOMBRE = "nombre";
	public static final String AUTOR = "autor";
	public static final String DIRECTOR = "director";
	public static final String LOCALIDAD = "localidad";
	public static final String COAC2011 = "coac2011";
	public static final String CABEZA_SERIE = "cabeza_serie";
	public static final String URL_CC = "url_cc";
	public static final String INFO = "info";
	public static final String URL_FOTO = "url_foto";
	public static final String URL_VIDEOS = "url_videos";
	public static final String COMPONENTE = "componente";
	public static final String VOZ = "voz";
	public static final String VIDEO = "video";
	public static final String URL = "url";
	public static final String FOTO = "foto";
	public static final String DIA = "dia";
	public static final String FECHA = "fecha";
	public static final String PUESTO = "puesto";
	public static final String NO = "no";
	public static final String DESCRIPCION = "descripcion";
	public static final String ORIGEN = "origen";
	public static final String PUNTOS = "puntos";
	public static final String ENLACE = "enlace";
	public static final String TIPO = "tipo";

	public static final String TITLE = "title";
	public static final String LINK = "link";
	public static final String COMMENTS = "comments";
	public static final String PUB_DATE = "pub_date";
	public static final String CREATOR = "creator";
	public static final String DESCRIPTION = "description";
	
	private List<Agrupacion> agrupaciones;
	private Map<String, List<Agrupacion>> calendario;
	private Map<String,List<Agrupacion>> modalidades;
	private Map<String, List<Enlace>> enlaces;
	
	// Donde iremos guardando los datos del registro a guardar
	ContentValues rssItem;
	
	Agrupacion agrupacionActual;
	private List<Agrupacion> agrupacionesDiaActual;
	private String diaActual;

	// Flags para saber en que nodo estamos
	private boolean in_agrupacion = false;
	private boolean in_componente = false;
	private boolean in_video = false;
	private boolean in_foto = false;
	private boolean in_dia = false;
	private boolean in_puesto = false;
	
	
    public RssHandler(List<Agrupacion> agrupaciones, Map<String, List<Agrupacion>> calendario, 
    				  Map<String,List<Agrupacion>> modalidades, Map<String, List<Enlace>> enlaces) {
		this.agrupaciones = agrupaciones;
		this.calendario = calendario;
		this.modalidades = modalidades;
		this.enlaces = enlaces;
	}

    /** 
     * Se llama cuando se abre un tag:
     * <tag>
     * Puede recibir atributos cuando el xml es del estilo:
     * <tag attribute="attributeValue">
     * 
     **/
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
    	if(localName.equalsIgnoreCase(AGRUPACION)) {
    		in_agrupacion = true;
    		agrupacionActual = new Agrupacion();
    		agrupacionActual.setId(Integer.valueOf(atts.getValue(ID)));
    		agrupacionActual.setModalidad(atts.getValue(MODALIDAD));
    		agrupacionActual.setNombre(atts.getValue(NOMBRE));
    		agrupacionActual.setAutor(atts.getValue(AUTOR));
    		agrupacionActual.setDirector(atts.getValue(DIRECTOR));
    		agrupacionActual.setLocalidad(atts.getValue(LOCALIDAD));
    		agrupacionActual.setCoac2011(atts.getValue(COAC2011));
    		agrupacionActual.setCabezaSerie(Boolean.TRUE.toString().equals(atts.getValue(CABEZA_SERIE)));
    		agrupacionActual.setInfo(atts.getValue(INFO));
    		agrupacionActual.setUrl_cc(atts.getValue(URL_CC));
    		agrupacionActual.setUrl_foto(atts.getValue(URL_FOTO));
    		agrupacionActual.setUrl_videos(atts.getValue(URL_VIDEOS));
    		agrupacionActual.setPuntos(atts.getValue(PUNTOS));
    	} else if(localName.equalsIgnoreCase(COMPONENTE)) {
    		in_componente = true;
    		Componente componente = new Componente();
    		componente.setNombre(atts.getValue(NOMBRE));
    		componente.setVoz(atts.getValue(VOZ));
    		if(agrupacionActual.getComponentes()==null)
    			agrupacionActual.setComponentes(new ArrayList<Componente>());
    		agrupacionActual.getComponentes().add(componente);
    	} else if(localName.equalsIgnoreCase(VIDEO)) {
    		in_video = true;
    		if(agrupacionActual.getVideos()==null)
    			agrupacionActual.setVideos(new ArrayList<Video>());
    		agrupacionActual.getVideos().add(new Video(atts.getValue(DESCRIPCION), atts.getValue(URL)));
    	} else if(localName.equalsIgnoreCase(COMENTARIO)) {
    		if(agrupacionActual.getComentarios()==null)
    			agrupacionActual.setComentarios(new ArrayList<Comentario>());
    		agrupacionActual.getComentarios().add(new Comentario(atts.getValue(ORIGEN), atts.getValue(URL)));
    	} else if(localName.equalsIgnoreCase(FOTO)) {
     		in_foto = true;
     		if(agrupacionActual.getFotos()==null)
     			agrupacionActual.setFotos(new ArrayList<Foto>());
     		agrupacionActual.getFotos().add(new Foto(atts.getValue(DESCRIPCION), atts.getValue(URL)));
     	} else if(localName.equalsIgnoreCase(DIA)) {
     		in_dia = true;
     		agrupacionesDiaActual = new ArrayList<Agrupacion>();
     		diaActual = atts.getValue(FECHA);
     		if(!calendario.containsKey(diaActual))
     			calendario.put(diaActual, new ArrayList<Agrupacion>());
     	} else if(localName.equalsIgnoreCase(PUESTO)) {
     		in_puesto = true;
     		int index = Integer.parseInt(atts.getValue(NO));
     		int agrupacion = -1;
     		try{
     			agrupacion = Integer.parseInt(atts.getValue(AGRUPACION));
     		}catch (Exception e) {
     			agrupacion = Integer.MIN_VALUE;
     		}
     		Agrupacion agr = getAgrupacionPorId(agrupacion);
     		if(agr!=null)
     			agrupacionesDiaActual.add(agr);
     	} else if(localName.equalsIgnoreCase(ENLACE)) {
    		Enlace enlace = new Enlace();
    		enlace.setDescripcion(atts.getValue(DESCRIPCION));
    		String tipo = atts.getValue(TIPO);
			enlace.setTipo(tipo);
    		enlace.setUrl(atts.getValue(URL));
    		
    		if(!enlaces.containsKey(tipo))
    			enlaces.put(tipo, new ArrayList<Enlace>());
    		enlaces.get(tipo).add(enlace);
     	}
    }
    
    private Agrupacion crearAgrupacionDescanso(){
    	Agrupacion descanso = new Agrupacion();
    	descanso.setId(Integer.MIN_VALUE);
    	descanso.setNombre(Constantes.TEXTO_DESCANSO);
    	return descanso;
    }
    
    private Agrupacion getAgrupacionPorId(int id){
    	if(Integer.MIN_VALUE==id)
    		return crearAgrupacionDescanso();
    	for(Agrupacion a : agrupaciones){
    		if(a.getId()==id)
    			return a;
    	}
    	return null;
    }
    
    /**
     * Llamado cuando se cierra el tag:
     * </tag> 
     */
    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
    	
    	if(localName.equalsIgnoreCase(AGRUPACION)) {
    		in_agrupacion = false;
    		agrupaciones.add(agrupacionActual);
    		modalidades.get(agrupacionActual.getModalidad()).add(agrupacionActual);
    		agrupacionActual = null;
    	} else if(localName.equalsIgnoreCase(COMPONENTE)) {
    		in_componente = false;
    	} else if(localName.equalsIgnoreCase(VIDEO)) {
    		in_video = false;
    	} else if(localName.equalsIgnoreCase(FOTO)) {
     		in_foto = false;
     	} else if(localName.equalsIgnoreCase(DIA)) {
     		in_dia = false;
     		calendario.get(diaActual).addAll(new ArrayList<Agrupacion>(agrupacionesDiaActual));
     		diaActual = null;
     		agrupacionesDiaActual.clear();
     	} else if(localName.equalsIgnoreCase(PUESTO)) {
     		in_puesto = false;
     	}
    }
    
    /** 
     * Se llama cuando estamos dentro de un tag:
     * <tag>characters</tag> 
     */
	@Override
	public void characters(char ch[], int start, int length) {
	}



	public void endCDATA() throws SAXException {
//		in_CDATA = false;
	}
	public void startCDATA() throws SAXException {
//		in_CDATA = true;
	}

	public void comment(char[] ch, int start, int length) throws SAXException {}

	public void endDTD() throws SAXException {}

	public void endEntity(String name) throws SAXException {}

	public void startDTD(String name, String publicId, String systemId) throws SAXException {}

	public void startEntity(String name) throws SAXException {}
}
