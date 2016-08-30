package com.example.isai.estadisticaslldm.ui;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import com.example.isai.estadisticaslldm.R;
import com.example.isai.estadisticaslldm.modelo.ContratoAsistencia;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the {@link }
 * interface.
 */
public class FragmentEditarAsistencia extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ID_PASE_LISTA = "id";
    // TODO: Customize parameters
    private int column = 1;
    private long idPaseLista = 0;
    AdaptadorDetallesAsistencia adaptadorAsistencia;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    FragmentAsistencia.OnListAsistenciaInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FragmentEditarAsistencia() {

    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static FragmentEditarAsistencia newInstance(int colum,long idPaseLista) {
        FragmentEditarAsistencia fragment = new FragmentEditarAsistencia();
        Bundle args = new Bundle();
        args.putLong(ID_PASE_LISTA, idPaseLista);
        args.putInt(ARG_COLUMN_COUNT,colum);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idPaseLista = getArguments().getLong(ID_PASE_LISTA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_asistencia, container, false);
        fab = (FloatingActionButton) view.findViewById(R.id.fab_asitencia);

        // Set the adapter

        recyclerView = (RecyclerView) view.findViewById(R.id.lista_a);
        if (column <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), column));
        }
        idPaseLista=getActivity().getIntent().getLongExtra(ContratoAsistencia.ColumnasPaseLista._ID,0);
        adaptadorAsistencia = new AdaptadorDetallesAsistencia(getActivity(), null, mListener,idPaseLista);
        recyclerView.setAdapter(adaptadorAsistencia);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarAlerta();
            }
        });
        Log.e("FragmentEditarAsistemci","id pase lista : "+getActivity().getIntent().getLongExtra(ContratoAsistencia.ColumnasPaseLista._ID,0));
        idPaseLista=getActivity().getIntent().getLongExtra(ContratoAsistencia.ColumnasPaseLista._ID,0);
        fab.setVisibility(View.INVISIBLE);
        setHasOptionsMenu(true);
        Log.e("FragmentEditarAsistemci","idPaseLista : "+idPaseLista);
    }



    public void guardarAsistencia(long paseListaId) {
        Cursor cursor=getActivity().getContentResolver().query(ContratoAsistencia.CONTENT_URI_MIEMBRO,null,null,null,null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String _id=cursor.getString(cursor.getColumnIndex(ContratoAsistencia.ColumnasMiembro._ID));
            Log.e("FragmentAsistencia","idMiembro : "+_id);
            if (adaptadorAsistencia.itemsList.contains(_id)) {
                ContentValues values = new ContentValues();
                values.put(ContratoAsistencia.ColumnasAsistencia.ASISTIO, "SI");
                getActivity().getContentResolver().update(ContratoAsistencia.CONTENT_URI_ASISTENCIA, values,
                        ContratoAsistencia.ColumnasAsistencia.ID_PASE_LISTA+" = "+idPaseLista+" AND "+
                                ContratoAsistencia.ColumnasAsistencia.ID_MIEMBRO+" = "+_id,null);
            } else {
                ContentValues values = new ContentValues();
                values.put(ContratoAsistencia.ColumnasAsistencia.ASISTIO, "NO");
                getActivity().getContentResolver().update(ContratoAsistencia.CONTENT_URI_ASISTENCIA, values,
                        ContratoAsistencia.ColumnasAsistencia.ID_PASE_LISTA+" = "+idPaseLista+" AND "+
                                ContratoAsistencia.ColumnasAsistencia.ID_MIEMBRO+" = "+_id,null);
            }
        }
    }

    public void lanzarAlerta() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Elija el Tipo");
        final CharSequence[] items=ContratoAsistencia.tipos;
        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Leer no Asistencias
                int noasistecias = 0;

                //Setear la Hora
                Calendar c = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("E MMM d yyyy");
                String fecha=format.format(c.getTime());

                //Guardar Pase de Lista
                ContentValues values = new ContentValues();
                values.put(ContratoAsistencia.ColumnasPaseLista.FECHA, fecha);
                values.put(ContratoAsistencia.ColumnasPaseLista.TIPO, items[which].toString());
                values.put(ContratoAsistencia.ColumnasPaseLista.NO_ASISTENCIAS, adaptadorAsistencia.itemsList.size());
                Uri uri=ContentUris.withAppendedId(ContratoAsistencia.CONTENT_URI_PASE_LISTA,idPaseLista);
                getActivity().getContentResolver().update(uri, values,null,null);
                //guardarAsistencia

                switch (ContratoAsistencia.tipos[which].toString()) {

                    case "Estudio Lunes":
                        actualizarPromedio(ContratoAsistencia.ColumnasInforme.PROM_EST_LUNES,ContratoAsistencia.tipos[which].toString());
                        break;

                    case "Estudio Miercoles":
                        actualizarPromedio(ContratoAsistencia.ColumnasInforme.PROM_EST_MIER,ContratoAsistencia.tipos[which].toString());
                        break;

                    case "Estudio Sabado":
                        actualizarPromedio(ContratoAsistencia.ColumnasInforme.PROM_EST_SABADO,ContratoAsistencia.tipos[which].toString());
                        break;
                    case "Servicio Domingo":
                        actualizarPromedio(ContratoAsistencia.ColumnasInforme.PROM_SERV_DOM,ContratoAsistencia.tipos[which].toString());
                        break;
                    case "Servicio Jueves":
                        actualizarPromedio(ContratoAsistencia.ColumnasInforme.PROM_SERV_JUEVES,ContratoAsistencia.tipos[which].toString());
                        break;
                    case "Dominical":
                        actualizarPromedio(ContratoAsistencia.ColumnasInforme.PROM_DOMINICAL,ContratoAsistencia.tipos[which].toString());
                        break;
                    case "Consagracion 04:30":
                        actualizarPromedio(ContratoAsistencia.ColumnasInforme.PROM_0430,ContratoAsistencia.tipos[which].toString());
                        break;
                    case "Horcion 06:00":
                        actualizarPromedio(ContratoAsistencia.ColumnasInforme.PROM_SEIS,ContratoAsistencia.tipos[which].toString());
                        break;
                    default:
                        restartLoader();
                        break;
                }
                guardarAsistencia(idPaseLista);
            }
        }).show();
    }
    public void restartLoader(){
        getLoaderManager().restartLoader(0, null,this);
    }
    public void actualizarPromedio(String hr,String tipo){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("MMMM");
        String mes = format.format(c.getTime()).toUpperCase();
        int promedio=obtenerPromedio(tipo);
        ContentValues values= new ContentValues();
        values.put(hr, promedio);
        getActivity().getContentResolver().update(ContratoAsistencia.CONTENT_URI_INFORME,values,
                ContratoAsistencia.ColumnasInforme.MES+" = '"+mes+"'",null);
    }

    public int obtenerPromedio(String comparacion){
        Cursor cursor=getActivity().getContentResolver().query(ContratoAsistencia.CONTENT_URI_PASE_LISTA,
                new String[]{"AVG("+ContratoAsistencia.ColumnasPaseLista.NO_ASISTENCIAS+")"},
                ContratoAsistencia.ColumnasPaseLista.TIPO+" = '"+comparacion+"'",null,null );
        if(!cursor.moveToFirst())
            return 0;
        Log.i("promdio",cursor.getInt(0)+"");
        return cursor.getInt(0);
    }


    public void animar() {
        fab.setVisibility(View.VISIBLE);
        fab.setScaleX(0);
        fab.setScaleY(0);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            final Interpolator interpolador = AnimationUtils.loadInterpolator(getContext(),
                    android.R.interpolator.fast_out_slow_in);
            fab.animate()
                    .scaleX(1)
                    .scaleY(1)
                    .setInterpolator(interpolador)
                    .setDuration(600)
                    .setStartDelay(200);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),
                ContratoAsistencia.CONTENT_URI_MIEMBRO,
                null, null, null, null);

    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adaptadorAsistencia.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adaptadorAsistencia.swapCursor(null);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public static class AlumnoAsisItem {
        public final long _ID;
        public final String nombre;


        public AlumnoAsisItem(long _ID, String nombre) {
            this._ID = _ID;
            this.nombre = nombre;
        }

        @Override
        public String toString() {
            return nombre;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                getActivity().finish();
                return true;
            case R.id.action_delete:
                lanzarAlertaEliminar();
                return true;
            case R.id.action_edit:
                animar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void lanzarAlertaEliminar() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Eliminar");
        dialog.setMessage("Desea elimimar el pase de lista ?");
        dialog.setCancelable(true);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                eliminarRegistro();
                getActivity().finish();
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }
    private void eliminarRegistro() {
        Uri uri = ContentUris.withAppendedId(ContratoAsistencia.CONTENT_URI_PASE_LISTA, idPaseLista);
        getActivity().getContentResolver().delete(
                uri,
                null,
                null
        );
    }
}
