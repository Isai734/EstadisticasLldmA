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
import com.example.isai.estadisticaslldm.ui.FragmentoInicio.ItemReporte;
import com.example.isai.estadisticaslldm.ui.FragmentoInicio.OnListInicioFragmentInteractionListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ItemReporte} and makes a call to the
 * specified {@link OnListInicioFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AdapatadorInicio extends RecyclerView.Adapter<AdapatadorInicio.ViewHolder> {

    Cursor cursor;
    Context context;
    private final OnListInicioFragmentInteractionListener mListener;

    public AdapatadorInicio(Activity context, Cursor cursor, OnListInicioFragmentInteractionListener listener) {
        this.context = context;
        this.cursor = cursor;
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inicio, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        cursor.moveToPosition(position);
        long id = Long.valueOf(cursor.getString(cursor.getColumnIndex(ContratoAsistencia.ColumnasInforme._ID)));
        String mes = cursor.getString(cursor.getColumnIndex(ContratoAsistencia.ColumnasInforme.MES));
        int pro_dom = Integer.valueOf(cursor.getString(cursor.getColumnIndex(ContratoAsistencia.ColumnasInforme.PROM_DOMINICAL)));
        int pro_ser_do = Integer.valueOf(cursor.getString(cursor.getColumnIndex(ContratoAsistencia.ColumnasInforme.PROM_SERV_DOM)));
        int pro_ser_jue = Integer.valueOf(cursor.getString(cursor.getColumnIndex(ContratoAsistencia.ColumnasInforme.PROM_SERV_JUEVES)));
        int pro_cinco = Integer.valueOf(cursor.getString(cursor.getColumnIndex(ContratoAsistencia.ColumnasInforme.PROM_0430)));
        int pro_est_lues = Integer.valueOf(cursor.getString(cursor.getColumnIndex(ContratoAsistencia.ColumnasInforme.PROM_EST_LUNES)));
        int pro_est_mier = Integer.valueOf(cursor.getString(cursor.getColumnIndex(ContratoAsistencia.ColumnasInforme.PROM_EST_MIER)));
        int pro_est_sab = Integer.valueOf(cursor.getString(cursor.getColumnIndex(ContratoAsistencia.ColumnasInforme.PROM_EST_SABADO)));
        int pro_seis = Integer.valueOf(cursor.getString(cursor.getColumnIndex(ContratoAsistencia.ColumnasInforme.PROM_SEIS)));
        Log.i("Adapter_inicio_cursor", mes + "  " + pro_dom + "   " + pro_ser_do + "   " + pro_ser_jue + "   " + pro_seis + "   " + "   " + pro_cinco);
        holder.mes.setText(mes);
        holder.promEstLunes.setText(pro_est_lues+"");
        holder.promEstMier.setText(pro_est_mier+"");
        holder.promEstSabad.setText(pro_est_sab+"");
        holder.pSeies.setText(pro_seis+"");
        holder.pAserDomin.setText(pro_ser_do+"");
        holder.pDomini.setText(pro_dom+"");
        holder.pCinco.setText(pro_cinco+"");
        holder.pServJueve.setText(pro_ser_jue+"");
        holder.totalAsis.setText((pro_dom+pro_ser_do+pro_ser_jue+pro_cinco+pro_est_lues+pro_est_mier+pro_est_sab+pro_seis)+"");


        holder.mItem = new ItemReporte(id, mes, pro_dom, pro_ser_do, pro_ser_jue, pro_cinco, pro_est_lues, pro_seis);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.OnListInicioFragmentInteractionListener(holder.mItem);
                }
            }
        });
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
    public int getItemCount() {
        return (cursor == null) ? 0 : cursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView totalAsis;
        public final TextView pDomini, pAserDomin, pServJueve, pCinco, pSeies, mes,promEstLunes,promEstMier,promEstSabad;
        public ItemReporte mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            totalAsis = (TextView) view.findViewById(R.id.txv_asis_total);
            pDomini = (TextView) view.findViewById(R.id.txv_prom_domi);
            pAserDomin = (TextView) view.findViewById(R.id.txv_prom_serv_domi);
            pServJueve = (TextView) view.findViewById(R.id.txv_prom_serv_jueves);
            pCinco = (TextView) view.findViewById(R.id.txv_prom_cinco);
            promEstLunes = (TextView) view.findViewById(R.id.txv_prom_est_lunes);
            promEstMier = (TextView) view.findViewById(R.id.txv_prom_est_miercoles);
            promEstSabad = (TextView) view.findViewById(R.id.txv_prom_est_sabado);
            pSeies = (TextView) view.findViewById(R.id.txv_prom_seis);
            mes = (TextView) view.findViewById(R.id.txv_mes);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mes.getText() + "'";
        }
    }
}
