package es.jcorralejo.android.coac2012.activities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

import es.jcorralejo.android.R;
import es.jcorralejo.android.coac2012.CoacApplication;
import es.jcorralejo.android.coac2012.entidades.Agrupacion;
import es.jcorralejo.android.coac2012.utils.Constantes;

public class MenuActivity extends Activity{
	
	private static final long FRECUENCIA_ACTUALIZACION = 60*60*1000*1; // recarga cada hora 
//	private ActualizarPostAsyncTask tarea;
	private CoacApplication app;
	private AdView adView1;
	private AdView adView2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		app = (CoacApplication)getApplication();

		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		
		cargarAnuncios();
		
		cargarFavoritas();
		
		TextView hoy = (TextView) findViewById(R.id.hoy);
		hoy.setOnClickListener(
			new OnClickListener() {
				public void onClick(View v) {
					if(!app.getActualizando()){
						SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy"); 
						String dia = sdf.format(new Date());
						List<Agrupacion> agrupaciones = app.getCalendario().get(dia);
						if(agrupaciones!=null && !agrupaciones.isEmpty()){
							Intent intent = new Intent();
							intent.setClass(getApplicationContext(), ActuacionActivity.class);
							intent.putExtra(Constantes.PARAMETRO_AGRUPACIONES, (ArrayList<Agrupacion>)agrupaciones);
							intent.putExtra(Constantes.PARAMETRO_TEXTO_DIA, app.getTextoDia(dia));
							intent.putExtra(Constantes.PARAMETRO_ONLINE, true);
							startActivity(intent);
							Toast.makeText(getApplicationContext(), "Los datos de esta sesión se actualizarán pasadas unas 12 horas. Gracias por la paciencia", Toast.LENGTH_LONG).show();
						}else{
							Toast.makeText(getApplicationContext(), "Hoy no hay concurso", Toast.LENGTH_LONG).show();
						}
					}else 
						Toast.makeText(getBaseContext(), "Actualizando datos...Por favor vuelva a intentarlo pasados unos segundos", Toast.LENGTH_LONG).show();
				}
			}
		);
		TextView modalidades = (TextView) findViewById(R.id.modalidades);
		modalidades.setOnClickListener(
			new OnClickListener() {
				public void onClick(View v) {
					if(app.getModalidades()!=null && !app.getModalidades().isEmpty()){
						Intent intent = new Intent();
						intent.setClass(getApplicationContext(), ModalidadesActivity.class);
						startActivity(intent);
					}else{
						showDialog(Constantes.DIALOG_NO_DATOS);
					}
				}
			}
		);
		TextView calendario = (TextView) findViewById(R.id.calendario);
		calendario.setOnClickListener(
			new OnClickListener() {
				public void onClick(View v) {
					if(app.getConcurso()!=null && !app.getConcurso().isEmpty()){
						Intent intent = new Intent();
						intent.setClass(getApplicationContext(), ConcursoActivity.class);
						startActivity(intent);
					}else{
						showDialog(Constantes.DIALOG_NO_DATOS);
					}
				}
			}
		);
		TextView masCarnaval = (TextView) findViewById(R.id.masCarnaval);
		masCarnaval.setOnClickListener(
			new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setClass(getApplicationContext(), MasCarnavalActivity.class);
					startActivity(intent);
				}
			}
		);
		
		if(app.isError())
			finish();
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
	    adView2 = (AdView) findViewById(R.id.ad2);
	    adView2.loadAd(r2);
	}
	
	@Override
	public void onDestroy() {
		adView1.destroy();
		adView2.destroy();
		super.onDestroy();
	}

	private void cargarFavoritas(){
		SharedPreferences prefs = getSharedPreferences(Constantes.PREFERENCES, MODE_PRIVATE);
		String favString = prefs.getString(Constantes.PREFERENCE_FAVORITAS, "");
		if(favString!=null && !favString.equals("")){
			String[] split = favString.split("\\|");
			for(String fav : split)
				if(fav!=null && !fav.equals(""))
					app.getFavoritas().add(Integer.parseInt(fav));
		}

	}
	
	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch (id) {
			case Constantes.DIALOG_NO_DATOS:
				builder.setMessage("Ha ocurrido un error al procesar los datos del Servidor.\n\nPor favor, vuelva a intentarlo pasados unos minutos. Si persiste el problema contacte con el desarrollador");
				builder.setPositiveButton("Volver",
										  new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog, int which) {
//												cargarDatos(true);
											}
									  	  });
				return builder.create();

			case Constantes.DIALOG_ACERCA_DE:
				LayoutInflater li = LayoutInflater.from(this);
				View view = li.inflate(R.layout.acercade, null);
				builder.setIcon(R.drawable.icon)
				       .setTitle(getString(R.string.app_name))
				       .setPositiveButton("Contactar", 
									    	new DialogInterface.OnClickListener() {
												public void onClick(DialogInterface dialog, int which) {
													Intent i = new Intent(android.content.Intent.ACTION_SEND);
													i.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ "coac2012android@gmail.com"});
													i.setType("plain/text");
													startActivity(i);
										  	}})
				       .setNeutralButton("Donar", 
						    				new DialogInterface.OnClickListener() {
												public void onClick(DialogInterface dialog, int which) {
													Intent i = new Intent("android.intent.action.VIEW", Uri.parse("https://market.android.com/details?id=es.jcorralejo.android.coac2012"));
													startActivity(i);
												}})
				       .setNegativeButton("Volver", null)
				       .setView(view);
				return builder.create();

			default:
				return null;
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		SharedPreferences prefs = getSharedPreferences(Constantes.PREFERENCES, MODE_PRIVATE);;
		long ultima = prefs.getLong("ultima_actualizacion", 0);
		if ((System.currentTimeMillis() - ultima) > FRECUENCIA_ACTUALIZACION){ 
			app.cargarDatos(null);
			Editor editor = prefs.edit();
			editor.putLong("ultima_actualizacion", System.currentTimeMillis());
			editor.commit();
		}
		
		cargarAnuncios();
	}
	
	@Override
	protected void onStop() {
//		if (tarea != null && !tarea.getStatus().equals(AsyncTask.Status.FINISHED)) {
//			tarea.cancel(true);
//		}
		super.onStop();
	}
	
//	private void cargarDatos(boolean ignorarTiempoEspera){
//		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
//		long ultima = prefs.getLong("ultima_actualizacion", 0);
//		if (ignorarTiempoEspera || (System.currentTimeMillis() - ultima) > FRECUENCIA_ACTUALIZACION) {
//			//Comprobamos si hay conexión ha internet
//			if(networkAvailable()){
//				final ProgressDialog pd = ProgressDialog.show(this,"Cargando datos","Por favor, espere mientras actualizamos los datos desde el servidor...", true, false);
//				tarea = new ActualizarPostAsyncTask(pd);
//				tarea.execute();
//			}else{
//				Toast.makeText(getApplicationContext(), "COAC2012 necesita una conexión a Internet para funcionar. Por favor, vuelva a intentarlo más tarde", Toast.LENGTH_LONG).show();
//				finish();
//			}
//		}
//	}
//	
//	private boolean networkAvailable() {
//		ConnectivityManager connectMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//		if (connectMgr != null) {
//			NetworkInfo[] netInfo = connectMgr.getAllNetworkInfo();
//			if (netInfo != null) {
//				for (NetworkInfo net : netInfo) {
//					if (net.getState() == NetworkInfo.State.CONNECTED) {
//						return true;
//					}
//				}
//			}
//		} 
//		return false;
//	}
//	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menuAcercaDe:
				showDialog(Constantes.DIALOG_ACERCA_DE);
				return true;
			case R.id.menuActualizar:
				final ProgressDialog pd = ProgressDialog.show(this, "Cargando datos","Por favor, espere mientras actualizamos los datos desde el servidor...", true, false);
				app.cargarDatos(pd);

				SharedPreferences prefs = getSharedPreferences(Constantes.PREFERENCES, MODE_PRIVATE);;
				Editor editor = prefs.edit();
				editor.putLong("ultima_actualizacion", System.currentTimeMillis());
				editor.commit();
				return true;
			case R.id.menuQuit:
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
//	
//	class ActualizarPostAsyncTask extends AsyncTask<Void, Void, Void> {
//		private ProgressDialog pd;
//		
//		public ActualizarPostAsyncTask(ProgressDialog pd) {
//			this.pd = pd;
//		}
//		
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//		}
//
//		@Override
//		protected Void doInBackground(Void... params) {
//			CoacApplication app = (CoacApplication) getApplication();
//			RssDownloadHelper.updateRssData(app.getRssUrl(), app.getAgrupaciones(), app.getCalendario(), app.getModalidades(), app.getEnlaces());
//			return null;
//		}
//
//		@Override
//		protected void onPostExecute(Void result) {
//			SharedPreferences prefs = getPreferences(MODE_PRIVATE);
//			Editor editor = prefs.edit();
//			editor.putLong("ultima_actualizacion", System.currentTimeMillis());
//			editor.commit();
//			ocultarPD();
//		}
//		
//		@Override
//		protected void onCancelled() {
//			ocultarPD();
//			SharedPreferences prefs = getPreferences(MODE_PRIVATE);
//			Editor editor = prefs.edit();
//			editor.putLong("ultima_actualizacion", 0);
//			editor.commit();
//
//			super.onCancelled();
//		}
//		
//		private void ocultarPD(){
//			if(pd.isShowing()){
//				try{
//					pd.dismiss();
//					pd = null;
//				}catch (Exception e) {
//				}
//			}
//
//		}
//	}

}
