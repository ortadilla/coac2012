package es.jcorralejo.android.coac2012.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import es.jcorralejo.android.R;
import es.jcorralejo.android.coac2012.CoacApplication;
import es.jcorralejo.android.coac2012.entidades.Agrupacion;
import es.jcorralejo.android.coac2012.utils.Constantes;

public class DiasFaseActivity extends TabActivity{

	static private TabHost mTabHost;
	private Resources mResources;	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fase);
		mTabHost = getTabHost();       
		mResources = getResources();
		
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
			String fase = extras.getString(Constantes.PARAMETRO_FASE);
			CoacApplication app = (CoacApplication) getApplication();
			List<String> dias = app.getConcurso().get(fase);
			for(String dia : dias){
				addTab(dia, app.getTextoDia(dia), app.getCalendario().get(dia));
			}
		}
		
		
		mTabHost.setCurrentTab(0);   
	}	
	private void addTab(String dia, String textoDia, List<Agrupacion> agrupaciones) {
		Intent intent = new Intent(this, ActuacionActivity.class);
		intent.putExtra(Constantes.PARAMETRO_AGRUPACIONES, (ArrayList<Agrupacion>)agrupaciones);
		intent.putExtra(Constantes.PARAMETRO_TEXTO_DIA, textoDia);
		TabSpec spec = mTabHost.newTabSpec(dia);
		spec.setIndicator(dia);
		spec.setContent(intent);
		mTabHost.addTab(spec);
	}
}
