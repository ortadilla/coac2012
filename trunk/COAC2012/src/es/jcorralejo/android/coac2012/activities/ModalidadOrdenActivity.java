package es.jcorralejo.android.coac2012.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import es.jcorralejo.android.R;
import es.jcorralejo.android.coac2012.CoacApplication;
import es.jcorralejo.android.coac2012.entidades.Agrupacion;
import es.jcorralejo.android.coac2012.utils.Constantes;

public class ModalidadOrdenActivity extends TabActivity{

	static private TabHost mTabHost;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fase);
		mTabHost = getTabHost();       

		CoacApplication app = (CoacApplication) getApplication();
		addTab("Comparsas", Constantes.MODALIDAD_COMPARSA, app.getModalidades().get(Constantes.MODALIDAD_COMPARSA));
		addTab("Chirigotas", Constantes.MODALIDAD_CHIRIGOTA, app.getModalidades().get(Constantes.MODALIDAD_CHIRIGOTA));
		addTab("Coros", Constantes.MODALIDAD_CORO, app.getModalidades().get(Constantes.MODALIDAD_CORO));
		addTab("Cuartetos", Constantes.MODALIDAD_CUARTETO, app.getModalidades().get(Constantes.MODALIDAD_CUARTETO));

		mTabHost.setCurrentTab(0);   
	}	
	private void addTab(String dia, String textoDia, List<Agrupacion> agrupaciones) {
		Intent intent = new Intent(this, OrdenActivity.class);
		intent.putExtra(Constantes.PARAMETRO_AGRUPACIONES, (ArrayList<Agrupacion>)agrupaciones);
		TabSpec spec = mTabHost.newTabSpec(dia);
		spec.setIndicator(dia);
		spec.setContent(intent);
		mTabHost.addTab(spec);
	}
}
