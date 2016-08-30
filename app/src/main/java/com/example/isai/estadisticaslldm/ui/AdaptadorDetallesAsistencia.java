package com.example.isai.estadisticaslldm.ui;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.isai.estadisticaslldm.R;
import com.example.isai.estadisticaslldm.modelo.ContratoAsistencia;
import com.example.isai.estadisticaslldm.modelo.ProveedorAsistencia;
import com.example.isai.estadisticaslldm.ui.FragmentAsistencia.AlumnoAsisItem;
import com.example.isai.estadisticaslldm.ui.FragmentAsistencia.OnListAsistenciaInteractionListener;

import java.util.LinkedList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link AlumnoAsisItem} and makes a call to the
 * specified {@link OnListAsistenciaInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AdaptadorDetallesAsistencia extends RecyclerView.Adapter<AdaptadorDetallesAsistencia.ViewHolder> {

    Context context;
    Cursor cursor;
    OnListAsistenciaInteractionListener listener;
    private static String TAG="AdaptadorDetallesAsistencia";
    LinkedList<String> itemsList =new LinkedList<>();
    private long idPaseLista;
    public AdaptadorDetallesAsistencia(Activity context, Cursor cursor, OnListAsistenciaInteractionListener listener,long idPaseLista){
        this.context=context;
        this.cursor=cursor;
        this.listener=listener;
        this.idPaseLista=idPaseLista;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_asistencia, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        cursor.moveToPosition(position);
        final String _id = cursor.getString(cursor.getColumnIndex(ContratoAsistencia.ColumnasMiembro._ID));

        String nombre = cursor.getString(cursor.getColumnIndex(ContratoAsistencia.ColumnasMiembro.NOMBRE))+" "+
                cursor.getString(cursor.getColumnIndex(ContratoAsistencia.ColumnasMiembro.A_PATERNO))+" "+
                cursor.getString(cursor.getColumnIndex(ContratoAsistencia.ColumnasMiembro.A_MATERNO));
        holder.mItem = (new AlumnoAsisItem(Long.decode(_id), nombre));
        holder.nombre.setText(nombre);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    //listener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
        //holder.checkBox.setEnabled(false);
        //verificar si asistio
        Cursor cursor = context.getContentResolver().query(ContratoAsistencia.CONTENT_URI_ASISTENCIA,
                new String[]{ContratoAsistencia.ColumnasAsistencia.ASISTIO}
                ,ContratoAsistencia.ColumnasAsistencia.ID_PASE_LISTA+" = "+idPaseLista+" AND "+
                        ContratoAsistencia.ColumnasAsistencia.ID_MIEMBRO+" = "+_id, null, null);
        Log.e(TAG,"idLista : "+idPaseLista+"   IdMiembro : "+_id);
        if (!cursor.moveToFirst())
            return;
        String asistio=cursor.getString(0);
        Log.e(TAG,asistio);
        if(asistio.equalsIgnoreCase("SI")){
            itemsList.add(_id);
            holder.checkBox.setChecked(true);
        }
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    itemsList.add(_id);
                    Log.d(TAG,"add : "+_id);
                }else {
                    boolean r=itemsList.remove(_id);
                    Log.d(TAG,"remove : "+_id+", isRemove : "+r);
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

    public void setIdPaseLista(long idPaseLista) {
        this.idPaseLista = idPaseLista;
    }

    @Override
    public int getItemCount() {
        return (cursor == null) ? 0 : cursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView nombre;
        public final CheckBox checkBox;
        public AlumnoAsisItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            nombre = (TextView) view.findViewById(R.id.nombre_a_txv);
            checkBox = (CheckBox) view.findViewById(R.id.asistio_jbx);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + nombre.getText() + "'";
        }
    }
}
