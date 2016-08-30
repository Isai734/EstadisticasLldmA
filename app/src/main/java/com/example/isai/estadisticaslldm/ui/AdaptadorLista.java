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
import com.example.isai.estadisticaslldm.ui.FragmentListaAsistencia.ListaAsistenciaItem;
import com.example.isai.estadisticaslldm.ui.FragmentListaAsistencia.OnPaseListaFragmentInteractionListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ListaAsistenciaItem} and makes a call to the
 * specified {@link OnPaseListaFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AdaptadorLista extends RecyclerView.Adapter<AdaptadorLista.ViewHolder> {


    private final OnPaseListaFragmentInteractionListener listener;
    Context context;
    Cursor cursor;

    public AdaptadorLista(Activity context, Cursor cursor, OnPaseListaFragmentInteractionListener listener) {
        this.context = context;
        this.cursor = cursor;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pase_lista, parent, false);
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
        String fecha = cursor.getString(cursor.getColumnIndex(ContratoAsistencia.ColumnasPaseLista.FECHA));
        String hora = cursor.getString(cursor.getColumnIndex(ContratoAsistencia.ColumnasPaseLista.TIPO));
        String total = cursor.getString(cursor.getColumnIndex(ContratoAsistencia.ColumnasPaseLista.NO_ASISTENCIAS));
        holder.mItem = (new ListaAsistenciaItem(Long.decode(_id), fecha, hora, Integer.parseInt(total)));
        Log.e("ite", _id+"  "+fecha+"   "+hora+"  "+total);
        holder.fechaTxv.setText(fecha);
        holder.horaTxv.setText(hora);
        holder.totalTxv.setText(total);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    listener.OnPaseListaFragmentInteractionListener(holder.mItem);
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
        public final TextView fechaTxv;
        public final TextView horaTxv;
        public final TextView totalTxv;
        public ListaAsistenciaItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            fechaTxv = (TextView) view.findViewById(R.id.fecha_l_txv);
            horaTxv = (TextView) view.findViewById(R.id.grupo_l_txv);
            totalTxv = (TextView) view.findViewById(R.id.total_asis_txv);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + fechaTxv.getText() + "'";
        }
    }
}
