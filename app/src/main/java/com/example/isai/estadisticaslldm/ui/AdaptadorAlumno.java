package com.example.isai.estadisticaslldm.ui;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.isai.estadisticaslldm.R;
import com.example.isai.estadisticaslldm.modelo.ContratoAsistencia;
import com.example.isai.estadisticaslldm.ui.FragmentoMiembro.MiembroItem;
import com.example.isai.estadisticaslldm.ui.FragmentoMiembro.OnListFragmentInteractionListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link MiembroItem} and makes a call to the
 * specified {@link //OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AdaptadorAlumno extends RecyclerView.Adapter<AdaptadorAlumno.ViewHolder> {

    private final OnListFragmentInteractionListener mListener;
    Cursor cursor;
    Context context;

    public AdaptadorAlumno(Activity context, Cursor cursor, OnListFragmentInteractionListener listener) {
        this.context=context;
        this.cursor=cursor;
        this.mListener=listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_miembro, parent, false);
        return new ViewHolder(view);
    }

    public Cursor swapCursor(Cursor cursor) {
        if (this.cursor == cursor) {
            return null;
        }
        Cursor oldCursor = cursor;
        this.cursor = cursor;
        if (cursor != null) {
            this.notifyDataSetChanged();
        }
        return oldCursor;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        cursor.moveToPosition(position);

        String _id = cursor.getString(cursor.getColumnIndex(ContratoAsistencia.ColumnasMiembro._ID));
        String nombre = cursor.getString(cursor.getColumnIndex(ContratoAsistencia.ColumnasMiembro.NOMBRE));
        String a_paterno = cursor.getString(cursor.getColumnIndex(ContratoAsistencia.ColumnasMiembro.A_PATERNO));
        String a_materno = cursor.getString(cursor.getColumnIndex(ContratoAsistencia.ColumnasMiembro.A_MATERNO));
        String grupo = cursor.getString(cursor.getColumnIndex(ContratoAsistencia.ColumnasMiembro.GRUPO));
        String idlista=cursor.getString(cursor.getColumnIndex(ContratoAsistencia.ColumnasMiembro.ID_LISTA));

        String nom= nombre + " " + a_paterno + " " + a_materno;
        Log.i("bind", nom);
        holder.mItem = new MiembroItem(Long.decode(_id), nombre + " " + a_paterno + " "+a_materno, grupo,Long.decode(idlista));
        holder.nombre.setText(nom);
        holder.grupo.setText(grupo);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (cursor == null) ? 0 : cursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView nombre;
        public final TextView grupo;
        public  MiembroItem mItem;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            nombre = (TextView) view.findViewById(R.id.nombre_txv);
            grupo = (TextView) view.findViewById(R.id.grupo_txv);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + nombre.getText() + "'";
        }
    }
}
