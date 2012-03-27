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
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import es.jcorralejo.android.R;
import es.jcorralejo.android.coac2012.entidades.Agrupacion;
import es.jcorralejo.android.coac2012.utils.Constantes;

public class OrdenActivity extends ListActivity{

	private List<Agrupacion> agrupaciones;
	private LayoutInflater miInflater;
	private AdView adView1;
	
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agrupaciones_list);
		
		cargarAnuncios();
		
		miInflater = LayoutInflater.from(this);
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
			agrupaciones = (List<Agrupacion>) extras.get(Constantes.PARAMETRO_AGRUPACIONES);
			Collections.sort(agrupaciones, new ComparatorAgrupaciones());
		}
		
		configurarAdapter();
		
		// Registramos la lista de lugares para definir su menú contextual
		ListView listaAgrupaciones = (ListView) findViewById(android.R.id.list);
		registerForContextMenu(listaAgrupaciones);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		configurarAdapter();
	}
	
	private static final class ComparatorAgrupaciones implements Comparator<Agrupacion>{
    	public int compare(Agrupacion obj1, Agrupacion obj2) {
    		String puntos1 = ((Agrupacion)obj1).getPuntos();
			String puntos2 = ((Agrupacion)obj2).getPuntos();
			if(puntos1!=null && !puntos1.equals("") && (puntos2==null || puntos2.equals("")))
				return 1;
			else if (puntos2!=null && !puntos2.equals("") && (puntos1==null || puntos1.equals("")))
				return -1;
			else if((puntos2==null || puntos2.equals("")) && (puntos1==null || puntos1.equals(""))) {
				String n1 = ((Agrupacion)obj1).getNombre();
				String n2 = ((Agrupacion)obj2).getNombre();
				return n1.compareTo(n2);
			}else{
				Integer p1 = new Integer(puntos1);
				Integer p2 = new Integer(puntos2);
				return -p1.compareTo(p2);
			}
    	}
    }; 
	
	public void configurarAdapter() {
		setListAdapter(new ArrayAdapter<Agrupacion>(this, R.layout.orden_item, agrupaciones) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View row;
				if (null == convertView) {
					row = miInflater.inflate(R.layout.orden_item, null);
				} else {
					row = convertView;
				}
		 
				Agrupacion item = getItem(position);
				TextView nombre = (TextView) row.findViewById(R.id.agrNombre);
				nombre.setText(item.getNombre());
				TextView puntos = (TextView) row.findViewById(R.id.agrPuntos);
				String pts = item.getPuntos();
				puntos.setText(pts);
				if(pts!=null && !pts.equals("")){
					puntos.setTextColor(Color.RED);
					nombre.setTextColor(Color.RED);
					nombre.setTextAppearance(getApplicationContext(), android.R.attr.textAppearance);
					puntos.setTextAppearance(getApplicationContext(), android.R.attr.textAppearance);
				}else{
					puntos.setTextColor(Color.GRAY);
					nombre.setTextColor(Color.GRAY);
					nombre.setTextAppearance(getApplicationContext(), android.R.attr.textAppearance);
					puntos.setTextAppearance(getApplicationContext(), android.R.attr.textAppearance);
					nombre.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD), Typeface.BOLD);
					puntos.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD), Typeface.BOLD);
				}
		 
				return row;
			}
			
		});
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent i = new Intent();
		i.setClass(getApplicationContext(), AgrupacionActivity.class);
		i.putExtra(Constantes.PARAMETRO_AGRUPACION, (Agrupacion)l.getItemAtPosition(position));
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
