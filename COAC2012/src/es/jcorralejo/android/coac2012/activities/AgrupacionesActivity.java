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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import es.jcorralejo.android.R;
import es.jcorralejo.android.coac2012.CoacApplication;
import es.jcorralejo.android.coac2012.entidades.Agrupacion;
import es.jcorralejo.android.coac2012.utils.Constantes;

public class AgrupacionesActivity extends ListActivity{

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
			Collections.sort(agrupaciones, new ComparatorArticulos());
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
	
	private static final class ComparatorArticulos implements Comparator<Agrupacion>{
    	public int compare(Agrupacion obj1, Agrupacion obj2) {
    		String nombre1 = ((Agrupacion)obj1).getNombre();
			String nombre2= ((Agrupacion)obj2).getNombre();
    		return nombre1.compareTo(nombre2);
    	}
    }; 
	
	public void configurarAdapter() {
		setListAdapter(new ArrayAdapter<Agrupacion>(this, R.layout.agrupaciones_item, agrupaciones) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View row;
				if (null == convertView) {
					row = miInflater.inflate(R.layout.agrupaciones_item, null);
				} else {
					row = convertView;
				}
		 
				Agrupacion item = getItem(position);
				TextView nombre = (TextView) row.findViewById(R.id.agrNombre);
				nombre.setText(item.getNombre());
				ImageView fav = (ImageView) row.findViewById(R.id.agrFav);
				fav.setImageResource(R.drawable.ic_fav);
				CoacApplication app = (CoacApplication) getApplication();
				fav.setVisibility(item.isCabezaSerie() || app.getFavoritas().contains(item.getId()) ? View.VISIBLE : View.GONE);
				TextView datosExtras = (TextView) row.findViewById(R.id.agrDatosExtras);
				if(item.getInfo()!=null && !item.getInfo().equals("")){
					datosExtras.setText(item.getInfo());
					datosExtras.setVisibility(View.VISIBLE);
				}else{
					datosExtras.setText(null);
					datosExtras.setVisibility(View.GONE);
				}
				TextView coac2011 = (TextView) row.findViewById(R.id.agrCOAC2011);
				coac2011.setText("COAC2011: "+(item.getCoac2011()!=null && !item.getCoac2011().equals("") ? item.getCoac2011() : "No participó"));
		 
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

	    AdRequest r2 = new AdRequest();
	    r1.setKeywords(key);
	}
	
	@Override
	public void onDestroy() {
		adView1.destroy();
		super.onDestroy();
	}
	
	
}
