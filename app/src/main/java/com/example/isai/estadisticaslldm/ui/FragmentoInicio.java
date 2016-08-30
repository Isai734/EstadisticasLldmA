package com.example.isai.estadisticaslldm.ui;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.isai.estadisticaslldm.R;
import com.example.isai.estadisticaslldm.modelo.ContratoAsistencia;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListInicioFragmentInteractionListener}
 * interface.
 */
public class FragmentoInicio extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    AdapatadorInicio adapatadorInicio;
    private OnListInicioFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FragmentoInicio() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static FragmentoInicio newInstance(int columnCount) {
        FragmentoInicio fragment = new FragmentoInicio();
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
        View view = inflater.inflate(R.layout.fragmento_lista_inicio, container, false);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            adapatadorInicio = new AdapatadorInicio(getActivity(), null, mListener);
            recyclerView.setAdapter(adapatadorInicio);
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListInicioFragmentInteractionListener) {
            mListener = (OnListInicioFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(), ContratoAsistencia.CONTENT_URI_INFORME,
                null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapatadorInicio.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapatadorInicio.swapCursor(null);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListInicioFragmentInteractionListener {
        // TODO: Update argument type and name
        void OnListInicioFragmentInteractionListener(ItemReporte item);
    }

    public static class ItemReporte {
        public final long id;
        public final String mes;
        public final int dominical;
        public final int serv_dom;
        public final int ser_jueves;
        public final int cinco;
        public final int nueve;
        public final int seis;


        public ItemReporte(long id, String mes, int dominical, int serv_dom, int ser_jueves, int cinco, int nueve, int seis) {
            this.id = id;
            this.mes = mes;
            this.dominical = dominical;
            this.serv_dom = serv_dom;
            this.ser_jueves = ser_jueves;
            this.cinco = cinco;
            this.nueve = nueve;
            this.seis = seis;
        }

        @Override
        public String toString() {
            return mes;
        }
    }
}
