package com.example.isai.estadisticaslldm.ui;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import com.example.isai.estadisticaslldm.R;
import com.example.isai.estadisticaslldm.modelo.ContratoAsistencia;
import com.example.isai.estadisticaslldm.modelo.ProveedorAsistencia;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnListAsistenciaInteractionListener}
 * interface.
 */
public class FragmentAsistencia extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private static final String F = "";
    private OnListAsistenciaInteractionListener mListener;
    private OnListaCheckBoxInteractionListener mListenerCheck;
    AdaptadorAsistencia adaptadorAsistencia;
    FloatingActionButton fab;
    RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FragmentAsistencia() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static FragmentAsistencia newInstance(int columnCount) {
        FragmentAsistencia fragment = new FragmentAsistencia();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_asistencia, container, false);
        fab = (FloatingActionButton) view.findViewById(R.id.fab_asitencia);
        // Set the adapter
        recyclerView = (RecyclerView) view.findViewById(R.id.lista_a);
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), mColumnCount));
        }
        adaptadorAsistencia = new AdaptadorAsistencia(getActivity(), null, mListener,mListenerCheck);
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
    }

    public void guardarAsistencia(String paseListaId) {
        CheckBox checkBox;
        Cursor cursor=getActivity().getContentResolver().query(ContratoAsistencia.CONTENT_URI_MIEMBRO,null,null,null,null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String _id=cursor.getString(cursor.getColumnIndex(ContratoAsistencia.ColumnasMiembro._ID));
            Log.e("FragmentAsistencia","idMiembro : "+_id);
            if (adaptadorAsistencia.itemsList.contains(_id)) {
                ContentValues values = new ContentValues();
                values.put(ContratoAsistencia.ColumnasAsistencia.ASISTIO, "SI");
                values.put(ContratoAsistencia.ColumnasAsistencia.ID_PASE_LISTA, paseListaId + "");
                values.put(ContratoAsistencia.ColumnasAsistencia.ID_MIEMBRO, _id);
                getActivity().getContentResolver().insert(ContratoAsistencia.CONTENT_URI_ASISTENCIA, values);
            } else {
                ContentValues values = new ContentValues();
                values.put(ContratoAsistencia.ColumnasAsistencia.ASISTIO, "NO");
                values.put(ContratoAsistencia.ColumnasAsistencia.ID_PASE_LISTA, paseListaId + "");
                values.put(ContratoAsistencia.ColumnasAsistencia.ID_MIEMBRO, _id);
                getActivity().getContentResolver().insert(ContratoAsistencia.CONTENT_URI_ASISTENCIA, values);
            }
        }
    }

    public void lanzarAlerta() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Elija el Tipo");

        dialog.setItems(ContratoAsistencia.tipos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Contar Asistencias

                //Setear la Hora
                Calendar c = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("E MMM d yyyy");
                String fecha = format.format(c.getTime());

                //Guardar Pase de Lista
                ContentValues values = new ContentValues();
                values.put(ContratoAsistencia.ColumnasPaseLista.FECHA, fecha);
                values.put(ContratoAsistencia.ColumnasPaseLista.TIPO, ContratoAsistencia.tipos[which].toString());
                values.put(ContratoAsistencia.ColumnasPaseLista.NO_ASISTENCIAS, adaptadorAsistencia.itemsList.size());
                getActivity().getContentResolver().insert(ContratoAsistencia.CONTENT_URI_PASE_LISTA, values);

                //Leer id
                String col[] = new String[]{"MAX(" + ContratoAsistencia.ColumnasPaseLista._ID + ")"};
                Cursor cursor = getActivity().getContentResolver().query(ContratoAsistencia.CONTENT_URI_PASE_LISTA, col, null, null, null);
                if (!cursor.moveToFirst())
                    return;
                String idPaseLista = cursor.getString(0);
                guardarAsistencia(idPaseLista);

                //Actualizar Reporte
                //"Estudio Lunes","Estudio Miercoles","Estudio Sabado",
                //"Servicio Domingo","Servicio Jueves","Dominical","Consagracion 04:30","Horcion 06:30"
                int promedio=0;
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListAsistenciaInteractionListener) {
            mListener = (OnListAsistenciaInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
        if (context instanceof OnListaCheckBoxInteractionListener) {
            mListenerCheck = (OnListaCheckBoxInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListaCheckBoxInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mListenerCheck=null;
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
    public interface OnListAsistenciaInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(AdaptadorAsistencia.ViewHolder item);
    }
    public interface OnListaCheckBoxInteractionListener {
        // TODO: Update argument type and name
        void OnListaCheckBoxInteractionListener(AdaptadorAsistencia.ViewHolder item);
    }

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
}
