package es.jcorralejo.android.coac2012.activities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

import es.jcorralejo.android.R;
import es.jcorralejo.android.coac2012.CoacApplication;
import es.jcorralejo.android.coac2012.entidades.Agrupacion;
import es.jcorralejo.android.coac2012.utils.Constantes;

public class ActuacionActivity extends ListActivity{

	private List<Agrupacion> agrupaciones;
	private LayoutInflater miInflater;
	private boolean online;
	private AdView adView1;
	
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actuaciones_list);
		
		cargarAnuncios();
		
		miInflater = LayoutInflater.from(this);
		String textoDia = "";
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
			agrupaciones = (List<Agrupacion>) extras.get(Constantes.PARAMETRO_AGRUPACIONES);
			textoDia = extras.getString(Constantes.PARAMETRO_TEXTO_DIA);
			online = extras.getBoolean(Constantes.PARAMETRO_ONLINE);
		}
		
		TextView dia = (TextView) findViewById(R.id.actDia);
		dia.setText(textoDia);
		
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
	
	public void configurarAdapter() {
		setListAdapter(new ArrayAdapter<Agrupacion>(this, R.layout.actuaciones_item, agrupaciones) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View row;
				if (null == convertView) {
					row = miInflater.inflate(R.layout.actuaciones_item, null);
				} else {
					row = convertView;
				}
		 
				Agrupacion item = getItem(position);
				TextView nombre = (TextView) row.findViewById(R.id.agrNombre);
				nombre.setText(item.getNombre());
				if(Constantes.TEXTO_DESCANSO.equals(item.getNombre()) && convertView!=null){
					nombre.setBackgroundColor(Color.GRAY);
					nombre.setTextColor(Color.BLACK);
				}
				
				
				ImageView fav = (ImageView) row.findViewById(R.id.agrFav);
				fav.setImageResource(R.drawable.ic_fav);
				CoacApplication app = (CoacApplication) getApplication();
				fav.setVisibility(item.isCabezaSerie() || app.getFavoritas().contains(item.getId()) ? View.VISIBLE : View.GONE);
				TextView modLoc = (TextView) row.findViewById(R.id.agrModLoc);
				if(item.getModalidad()!=null && !item.getModalidad().equals("")
				&& item.getLocalidad()!=null && !item.getLocalidad().equals("")){
					modLoc.setText(item.getModalidad()+" ("+item.getLocalidad()+")");
					modLoc.setVisibility(View.VISIBLE);
				}else{
					modLoc.setText(null);
					modLoc.setVisibility(View.GONE);
				}
				TextView infoExtra = (TextView) row.findViewById(R.id.agrDatosExtras);
				if(item.getInfo()!=null && !item.getInfo().equals("")){
					infoExtra.setText(item.getInfo());
					infoExtra.setVisibility(View.VISIBLE);
				}else{
					infoExtra.setText(null);
					infoExtra.setVisibility(View.GONE);
				}
		 
				return row;
			}
			
			@Override
			public int getCount() {
				return agrupaciones!=null ? agrupaciones.size() : 0;
			}
			
		});
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Agrupacion agr = (Agrupacion)l.getItemAtPosition(position);
		if(agr.getId()>0){
			Intent i = new Intent();
			i.setClass(getApplicationContext(), AgrupacionActivity.class);
			i.putExtra(Constantes.PARAMETRO_AGRUPACION, agr);
			startActivity(i);
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if(online){
			MenuInflater menuInflater = getMenuInflater();
			menuInflater.inflate(R.menu.actuaciones, menu);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i = null;
		switch (item.getItemId()) {
			case R.id.actVer:
				if(((CoacApplication)getApplication()).isPreeliminar()){
					try{
						i = new Intent(Intent.ACTION_VIEW);
						i.addCategory(Intent.CATEGORY_LAUNCHER);
						i.setClassName("com.opera.browser", "com.opera.Opera");
						i.setData(Uri.parse(Constantes.URL_ONDA_CADIZ));
						startActivity(i);
					}catch (Exception e) {
						showDialog(Constantes.DIALOG_INSTALAR_VIDEO);
					}
				}else if (((CoacApplication)getApplication()).isCuartos()){
					try{
						i = new Intent(Intent.ACTION_VIEW);
						i.addCategory(Intent.CATEGORY_LAUNCHER);
						i.setClassName("com.opera.browser", "com.opera.Opera");
						i.setData(Uri.parse(Constantes.URL_CANAL_ANDALUCIA));
						startActivity(i);
					}catch (Exception e) {
						showDialog(Constantes.DIALOG_INSTALAR_VIDEO);
					}
				}else
					Toast.makeText(getApplicationContext(), "Lo sentimos, pero esta fase no la emite ninguna TV online", Toast.LENGTH_LONG).show();
				return true;
			case R.id.actOir:
				try{
					i = new Intent(Intent.ACTION_VIEW);
					i.setComponent(ComponentName.unflattenFromString("tunein.player/.Activity"));
					startActivity(i);
					Toast.makeText(getApplicationContext(), "Pulse 'Radio Local' y busque en el listado 'Radio Andalucía Información', el sonido comenzará " +
							"a reproducirse pasados unos segundos. Si no la cuentra pruebe a usar el buscador", Toast.LENGTH_LONG).show();
				}catch (Exception e) {
					showDialog(Constantes.DIALOG_INSTALAR_RADIO);
				}

				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	
	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch (id) {
			case Constantes.DIALOG_INSTALAR_RADIO:
				builder.setMessage("Le recomendamos instalar 'TuneIn Radio' con la que podrá escuchar Radio Andalucía Información" +
						" a través de Internet desde cualquier lugar del mundo, además de miles de cadenas. Una vez instalado pulse " +
						"'Radio Local' y busque en la lista 'Radio Andalucía Información', el sonido comenzará a reproducirse pasados unos segundos. " +
						"Si no la cuentra pruebe a usar el buscador" +
						"\n\nSi su dispositivo dispone de aplicación de Radio puede ejecutarla y buscar el dial de Radio Andalucía Información:" +
						"\nAlmeria ---- 90.5" +
						"\nCampo de Dalias ---- 88.5" +
						"\nCádiz ---- 99.4" +
						"\nCampo de Gibraltar ---- 106.4" +
						"\nSierra de Cádiz ---- 97.7" +
						"\nUbrique ---- 93.5" +
						"\nVejer de la Frontera ---- 98.6" +
						"\nCórdoba ---- 99.4" +
						"\nSubbética-Cabra ---- 106.1" +
						"\nValle de los Pedroches ---- 89.6" +
						"\nGranada ---- 89.8" +
						"\nBaza ---- 91.3" +
						"\nGuadix ---- 102.7" +
						"\nLoja ---- 101.3" +
						"\nMotril-Calahonda	---- 99.3" +
						"\nHuelva ---- 97.3" +
						"\nRociana-El Condado ---- 92.9" +
						"\nJaén ---- 91.6" +
						"\nMálaga ---- 94.9" +
						"\nMarbella ---- 104.3" +
						"\nArchidona ---- 94.7" +
						"\nSevilla ---- 94.3" +
						"\nSierra Norte ---- 104.6" +
						"\nEstepa ---- 99.2");
				builder.setPositiveButton("Instalar TuneIn Radio",
										  new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog, int which) {
												Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(Constantes.URL_TUNEIN));
												startActivity(i);
									  	  }});
				builder.setNegativeButton("Volver", null);
				return builder.create();

			case Constantes.DIALOG_INSTALAR_VIDEO:
				builder.setMessage("Le recomendamos que instale 'Opera Mobile' para poder disfrutar de la emisión online de 'Onda Cádiz' y 'Canal Sur'");
				builder.setPositiveButton("Instalar Opera Mobile",
						  new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(Constantes.URL_OPERA));
								startActivity(i);
					  	  }});
				builder.setNegativeButton("Volver",null);
				return builder.create();
			default:
				return null;
		}
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
