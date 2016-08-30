package com.example.isai.estadisticaslldm.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import com.example.isai.estadisticaslldm.R;
import com.example.isai.estadisticaslldm.modelo.ContratoAsistencia;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ActividadPrincipal extends AppCompatActivity implements
        FragmentoMiembro.OnListFragmentInteractionListener,
        DialogoAgregarAlumnos.OnDialogoAlumnosListener,
        FragmentAsistencia.OnListAsistenciaInteractionListener,
        FragmentListaAsistencia.OnPaseListaFragmentInteractionListener,
        FragmentoInicio.OnListInicioFragmentInteractionListener,
        FragmentAsistencia.OnListaCheckBoxInteractionListener{

    private DrawerLayout drawerLayout;
    NavigationView navigationView;
    CoordinatorLayout rootLayout;
    private static String TAG="ActividadPrincipal";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);
        agregarToolbar();
        rootLayout = (CoordinatorLayout) findViewById(R.id.rootLayout);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            prepararDrawer(navigationView);
            seleccionarItem(navigationView.getMenu().getItem(0));
        }
        if(!existeInforme()){
            Calendar c = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("MMMM");
            String mesActual = format.format(c.getTime()).toUpperCase();
            ContentValues values=new ContentValues();
            values.put(ContratoAsistencia.ColumnasInforme.MES,mesActual);
            values.put(ContratoAsistencia.ColumnasInforme.PROM_DOMINICAL,0);
            values.put(ContratoAsistencia.ColumnasInforme.PROM_SERV_DOM,0);
            values.put(ContratoAsistencia.ColumnasInforme.PROM_SERV_JUEVES,0);
            values.put(ContratoAsistencia.ColumnasInforme.PROM_SEIS,0);
            values.put(ContratoAsistencia.ColumnasInforme.PROM_0430,0);
            values.put(ContratoAsistencia.ColumnasInforme.PROM_EST_LUNES,0);
            values.put(ContratoAsistencia.ColumnasInforme.PROM_EST_MIER,0);
            values.put(ContratoAsistencia.ColumnasInforme.PROM_EST_SABADO,0);
            getContentResolver().insert(ContratoAsistencia.CONTENT_URI_INFORME, values);
            Log.i("Principal_mes","se inserto mes");
        }
    }


    public boolean existeInforme() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("MMMM");
        String mesActual = format.format(c.getTime()).toUpperCase();
        Cursor cursor=getContentResolver().query(ContratoAsistencia.CONTENT_URI_INFORME, new String[]{"COUNT(*)"},
                ContratoAsistencia.ColumnasInforme.MES + " = '" + mesActual + "'", null, null);
        if(!cursor.moveToFirst())
            return false;
        int exist=cursor.getInt(0);
        if(exist>0){
            return true;
        }
        return false;
    }


    private void agregarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.drawer_toggle);
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void prepararDrawer(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        seleccionarItem(menuItem);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    private void seleccionarItem(MenuItem itemDrawer) {
        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        //Toast.makeText(this,itemDrawer.getItemId(),Toast.LENGTH_LONG).show();
        switch (itemDrawer.getItemId()) {
            case R.id.item_inicio:
                fragmentoGenerico = new FragmentoPaginado();
                break;
            case R.id.item_reportes:
                fragmentoGenerico = new FragmentoInicio();
                break;
        }
        if (fragmentoGenerico != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.contenedor_principal, fragmentoGenerico)
                    .commit();
        }
        setTitle(itemDrawer.getTitle());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(FragmentoMiembro.MiembroItem item) {
        startActivity(new Intent(this, ActividadDetalles.class).putExtra(ContratoAsistencia.ColumnasMiembro._ID, item._ID));

    }

    @Override
    public void onButtonClickGuardar() {

    }

    @Override
    public void onListFragmentInteraction(AdaptadorAsistencia.ViewHolder item) {

    }

    @Override
    public void OnPaseListaFragmentInteractionListener(FragmentListaAsistencia.ListaAsistenciaItem item) {
        Log.e(TAG,"id pase lista : "+item.id);
        startActivity(new Intent(this, ActividadEditarPaseLista.class).putExtra(ContratoAsistencia.ColumnasPaseLista._ID, item.id));

    }


    @Override
    public void OnListInicioFragmentInteractionListener(FragmentoInicio.ItemReporte item) {

    }

    @Override
    public void OnListaCheckBoxInteractionListener(AdaptadorAsistencia.ViewHolder item) {
        if(item.checkBox.isChecked()){
            int num=this.getIntent().getIntExtra("asistencias",0);
            this.getIntent().putExtra("asistencias",(num+1));
        }
    }
}
