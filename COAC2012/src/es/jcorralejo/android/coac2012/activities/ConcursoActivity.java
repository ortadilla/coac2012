package es.jcorralejo.android.coac2012.activities;

import java.util.HashSet;
import java.util.Set;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import es.jcorralejo.android.R;
import es.jcorralejo.android.coac2012.utils.Constantes;

public class ConcursoActivity extends Activity{
	
	private AdView adView1;
	private AdView adView2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.concurso);
		
		cargarAnuncios();
		
		TextView preeliminares = (TextView) findViewById(R.id.preeliminares);
		preeliminares.setOnClickListener(
			new OnClickListener() {
				public void onClick(View v) {
					navegarFase(Constantes.FASE_PREELIMINAR);
				}
			}
		);
		TextView semis = (TextView) findViewById(R.id.semis);
		semis.setOnClickListener(
			new OnClickListener() {
				public void onClick(View v) {
					navegarFase(Constantes.FASE_SEMIS);
				}
			}
		);
		TextView cuartos = (TextView) findViewById(R.id.cuartos);
		cuartos.setOnClickListener(
			new OnClickListener() {
				public void onClick(View v) {
					navegarFase(Constantes.FASE_CUARTOS);
				}
			}
		);
		TextView granFinal = (TextView) findViewById(R.id.granFinal);
		granFinal.setOnClickListener(
			new OnClickListener() {
				public void onClick(View v) {
					navegarFase(Constantes.FASE_FINAL);
				}
			}
		);
	}
	
	private void navegarFase(String fase){
		Intent i = new Intent();
		i.putExtra(Constantes.PARAMETRO_FASE, fase);
		i.setClass(getApplicationContext(), DiasFaseActivity.class);
		startActivity(i);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.concurso, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.concursoClasif:
				Intent i = new Intent();
				i.setClass(getApplicationContext(), ModalidadOrdenActivity.class);
				startActivity(i);
				return true;
			default:
				return super.onOptionsItemSelected(item);
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
