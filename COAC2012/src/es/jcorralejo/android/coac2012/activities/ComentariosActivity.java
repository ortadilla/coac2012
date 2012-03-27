package es.jcorralejo.android.coac2012.activities;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import es.jcorralejo.android.R;
import es.jcorralejo.android.coac2012.entidades.Comentario;
import es.jcorralejo.android.coac2012.utils.Constantes;

public class ComentariosActivity extends ListActivity{

	private List<Comentario> comentarios;
	private LayoutInflater miInflater;
	private AdView adView1;
	
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.videos_list);
		
		cargarAnuncios();
		
		miInflater = LayoutInflater.from(this);
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
			comentarios = (List<Comentario>) extras.get(Constantes.PARAMETRO_COMENTARIOS);
			Collections.sort(comentarios, new ComparatorComentarios());
		}
		
		configurarAdapter();
		
		// Registramos la lista de lugares para definir su menú contextual
		ListView listaVideos = (ListView) findViewById(android.R.id.list);
		registerForContextMenu(listaVideos);
	}
	
	private static final class ComparatorComentarios implements Comparator<Comentario>{
    	public int compare(Comentario obj1, Comentario obj2) {
    		String nombre1 = ((Comentario)obj1).getOrigen();
			String nombre2= ((Comentario)obj2).getOrigen();
    		return nombre1.compareTo(nombre2);
    	}
    }; 
	
	public void configurarAdapter() {
		setListAdapter(new ArrayAdapter<Comentario>(this, R.layout.videos_item, comentarios) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View row;
				if (null == convertView) {
					row = miInflater.inflate(R.layout.comentarios_item, null);
				} else {
					row = convertView;
				}
		 
				Comentario item = getItem(position);
				TextView origen = (TextView) row.findViewById(R.id.comentOrigen);
				origen.setText(item.getOrigen());
		 
				return row;
			}
			
		});
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent i = new Intent("android.intent.action.VIEW", Uri.parse(((Comentario)l.getItemAtPosition(position)).getUrl()));
		startActivity(i);
	}

	private void cargarAnuncios(){
		Set<String> key = new HashSet<String>();
		key.add("Carnaval"); 
		key.add("Cádiz"); 
		key.add("Comparsa"); 
		key.add("Chirigota"); 
		key.add("Coro"); 
		key.add("Cuarteto"); 
		key.add("Febrero"); 

		AdRequest r1 = new AdRequest();
		r1.setKeywords(key);
		adView1 = (AdView) findViewById(R.id.ad1);
	    adView1.loadAd(r1);

	}
	
	@Override
	public void onDestroy() {
		adView1.destroy();
		super.onDestroy();
	}

	
	
}
