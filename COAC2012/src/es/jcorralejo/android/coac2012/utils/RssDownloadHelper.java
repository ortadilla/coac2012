package es.jcorralejo.android.coac2012.utils;

import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import es.jcorralejo.android.coac2012.entidades.Agrupacion;
import es.jcorralejo.android.coac2012.entidades.Enlace;

public class RssDownloadHelper {

	public static void updateRssData(String rssUrl, List<Agrupacion> agrupaciones, Map<String, List<Agrupacion>> calendario, 
									 Map<String,List<Agrupacion>> modalidades, Map<String, List<Enlace>> enlaces) {
		try {
			URL url = new URL(rssUrl);

			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser saxParser = spf.newSAXParser();
			RssHandler rssHandler = new RssHandler(agrupaciones, calendario, modalidades, enlaces);
			saxParser.setProperty("http://xml.org/sax/properties/lexical-handler", rssHandler);
			XMLReader xr = saxParser.getXMLReader();			
			xr.setContentHandler(rssHandler);
			
			InputSource is = new InputSource(url.openStream());
			is.setEncoding("utf-8");
			
			//Una vez obtenido el archivo y antes de parsear, limpiamos las variables
			agrupaciones.clear();
			calendario.clear();
			modalidades.get(Constantes.MODALIDAD_CHIRIGOTA).clear();
			modalidades.get(Constantes.MODALIDAD_COMPARSA).clear();
			modalidades.get(Constantes.MODALIDAD_CUARTETO).clear();
			modalidades.get(Constantes.MODALIDAD_CORO).clear();
			modalidades.get(Constantes.MODALIDAD_INFANTIL).clear();
			modalidades.get(Constantes.MODALIDAD_JUVENIL).clear();
			modalidades.get(Constantes.MODALIDAD_ROMANCERO).clear();
			modalidades.get(Constantes.MODALIDAD_CALLEJERA).clear();
			enlaces.clear();
			
			xr.parse(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
