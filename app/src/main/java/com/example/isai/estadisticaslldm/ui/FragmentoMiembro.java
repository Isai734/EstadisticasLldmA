package com.example.isai.estadisticaslldm.ui;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import com.example.isai.estadisticaslldm.R;
import com.example.isai.estadisticaslldm.modelo.ContratoAsistencia;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link //OnListFragmentInteractionListener}
 * interface.
 */
public class FragmentoMiembro extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    AdaptadorAlumno adaptador;
    FloatingActionButton fabBtn;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FragmentoMiembro() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")

    public static FragmentoMiembro newInstance(int columnCount) {
        FragmentoMiembro fragment = new FragmentoMiembro();
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
        View view = inflater.inflate(R.layout.fragmento_alumnos_lista, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.lista_alumnos);
        // Set the adapter
        Context context = view.getContext();
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        adaptador = new AdaptadorAlumno(getActivity(), null, mListener);
        recyclerView.setAdapter(adaptador);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);


        fabBtn = (FloatingActionButton)getActivity().findViewById(R.id.fab);
        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                new DialogoAgregarAlumnos().show(fragmentManager, "DialogoAgregarAlumnos");
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
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
        return new CursorLoader(getContext(), ContratoAsistencia.CONTENT_URI_MIEMBRO,
                null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adaptador.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adaptador.swapCursor(null);
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
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(MiembroItem item);
    }

    public static class MiembroItem {
        public final long _ID;
        public final String NOMBRE;
        public final String GRUPO;
        public final Long IDLISTA;



        public MiembroItem(long _ID, String NOMBRE, String GRUPO, Long IDLISTA) {
            this._ID = _ID;
            this.NOMBRE = NOMBRE;
            this.GRUPO = GRUPO;
            this.IDLISTA=IDLISTA;
        }

        @Override
        public String toString() {
            return NOMBRE;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            getLoaderManager().destroyLoader(0);
            if (adaptador != null) {
                adaptador = null;
            }
        } catch (Throwable localThrowable) {
            // Proyectar la excepción
        }
    }
}
