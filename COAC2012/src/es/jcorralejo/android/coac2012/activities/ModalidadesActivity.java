package es.jcorralejo.android.coac2012.activities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import es.jcorralejo.android.R;
import es.jcorralejo.android.coac2012.CoacApplication;
import es.jcorralejo.android.coac2012.entidades.Agrupacion;
import es.jcorralejo.android.coac2012.utils.Constantes;

public class ModalidadesActivity extends Activity{
	private CoacApplication app;
	private AdView adView1;
	private AdView adView2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modalidades);
		
		cargarAnuncios();
		
		app = (CoacApplication)getApplication();
		
		TextView comparsas = (TextView) findViewById(R.id.comparsas);
		comparsas.setOnClickListener(
			new OnClickListener() {
				public void onClick(View v) {
					navegarModalidad(Constantes.MODALIDAD_COMPARSA);
				}
			}
		);
		TextView chirigotas = (TextView) findViewById(R.id.chirigotas);
		chirigotas.setOnClickListener(
			new OnClickListener() {
				public void onClick(View v) {
					navegarModalidad(Constantes.MODALIDAD_CHIRIGOTA);
				}
			}
		);
		TextView coros = (TextView) findViewById(R.id.coros);
		coros.setOnClickListener(
			new OnClickListener() {
				public void onClick(View v) {
					navegarModalidad(Constantes.MODALIDAD_CORO);
				}
			}
		);
		TextView cuartetos = (TextView) findViewById(R.id.cuartetos);
		cuartetos.setOnClickListener(
			new OnClickListener() {
				public void onClick(View v) {
					navegarModalidad(Constantes.MODALIDAD_CUARTETO);
				}
			}
		);
	}
	
	private void navegarModalidad(String modalidad){
		Intent i = new Intent();
//		Bundle b = new Bundle();
//		b.putSerializable(Constantes.PARAMETRO_AGRUPACIONES_MODALIDAD, (ArrayList<Agrupacion>)app.getModalidades().get(modalidad));
//		i.putExtras(b);
		i.putExtra(Constantes.PARAMETRO_AGRUPACIONES, (ArrayList<Agrupacion>)app.getModalidades().get(modalidad));
		i.setClass(getApplicationContext(), AgrupacionesActivity.class);
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
	    adView2 = (AdView) findViewById(R.id.ad2);
	    adView2.loadAd(r2);
	}
	
	@Override
	public void onDestroy() {
		adView1.destroy();
		adView2.destroy();
		super.onDestroy();
	}
	

}
