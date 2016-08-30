package com.example.isai.estadisticaslldm.ui;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.isai.estadisticaslldm.R;
import com.example.isai.estadisticaslldm.modelo.ContratoAsistencia;

public class ActividadEditarPaseLista extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_detalles_pase_lista);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (savedInstanceState == null) {
            FragmentEditarAsistencia fragment = new FragmentEditarAsistencia();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contenedor_detalles_asistencias, fragment)
                    .commit();
        }
        Log.e("Actividad Detalles","id pase lista : "+getIntent().getLongExtra(ContratoAsistencia.ColumnasPaseLista._ID,0));
        agregarToolbar();
    }

    private void agregarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detalles_asistencias);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            //ab.setHomeAsUpIndicator(true);
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

}
