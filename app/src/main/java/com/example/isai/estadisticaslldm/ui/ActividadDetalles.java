package com.example.isai.estadisticaslldm.ui;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.TextView;

import com.example.isai.estadisticaslldm.R;
import com.example.isai.estadisticaslldm.modelo.ContratoAsistencia;

public class ActividadDetalles extends AppCompatActivity implements DialogoEditarAlumno.OnDialogoEditarAlumnosListener {

    TextView nombre, grupo, asistencias;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_detalles);
        setToolbar();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                new DialogoEditarAlumno().show(fragmentManager, "DialogoEditarAlumno");
            }
        });

        nombre = (TextView) findViewById(R.id.nombre_d_txv);
        asistencias = (TextView) findViewById(R.id.asistencias_d_txv);
        grupo = (TextView) findViewById(R.id.grupo_d_txv);
        setData();
        animar();
    }
    void setData() {

        long id = getIntent().getLongExtra(ContratoAsistencia.ColumnasMiembro._ID, 0);
        Uri uri = ContentUris.withAppendedId(ContratoAsistencia.CONTENT_URI_MIEMBRO, id);
        Cursor c = getContentResolver().query(uri, null, null, null, null);
        if (!c.moveToFirst())
            return;
        String nom = c.getString(c.getColumnIndex(ContratoAsistencia.ColumnasMiembro.NOMBRE)) + " " +
                c.getString(c.getColumnIndex(ContratoAsistencia.ColumnasMiembro.A_PATERNO)) + " " +
                c.getString(c.getColumnIndex(ContratoAsistencia.ColumnasMiembro.A_MATERNO));
        String grup=c.getString(c.getColumnIndex(ContratoAsistencia.ColumnasMiembro.GRUPO));
        nombre.setText(nom);
        grupo.setText(grup);
        c.close(); // Liberar memoria del cursor
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detalles);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);

        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle("Activdad Detalles");
    }

    public void lanzarAlerta() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Eliminar");
        dialog.setMessage("Desea elimimar el registro del alumno " + nombre.getText() + "?");
        dialog.setCancelable(true);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                eliminarRegistro();
                finish();
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    public void animar() {
        fab.setScaleX(0);
        fab.setScaleY(0);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            final Interpolator interpolador = AnimationUtils.loadInterpolator(getBaseContext(),
                    android.R.interpolator.fast_out_slow_in);
            fab.animate()
                    .scaleX(1)
                    .scaleY(1)
                    .setInterpolator(interpolador)
                    .setDuration(600)
                    .setStartDelay(700);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_delete:
                lanzarAlerta();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void eliminarRegistro() {
        long id = getIntent().getLongExtra(ContratoAsistencia.ColumnasMiembro._ID, 0);
        Uri uri = ContentUris.withAppendedId(ContratoAsistencia.CONTENT_URI_MIEMBRO, id);
        this.getContentResolver().delete(
                uri,
                null,
                null
        );
    }

    @Override
    public void onButtonClickEditar() {
        setData();
    }

}
