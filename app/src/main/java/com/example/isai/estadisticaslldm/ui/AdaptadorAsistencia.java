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
import android.widget.Toast;

import com.example.isai.estadisticaslldm.R;
import com.example.isai.estadisticaslldm.modelo.ContratoAsistencia;
import com.example.isai.estadisticaslldm.ui.FragmentAsistencia.AlumnoAsisItem;
import com.example.isai.estadisticaslldm.ui.FragmentAsistencia.OnListAsistenciaInteractionListener;

import java.util.LinkedList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link AlumnoAsisItem} and makes a call to the
 * specified {@link OnListAsistenciaInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AdaptadorAsistencia extends RecyclerView.Adapter<AdaptadorAsistencia.ViewHolder> {

    Context context;
    Cursor cursor;
    LinkedList <String> itemsList =new LinkedList<>();
    private static String TAG=AdaptadorAsistencia.class.getName();
    OnListAsistenciaInteractionListener listener;
    FragmentAsistencia.OnListaCheckBoxInteractionListener listenerCheck;
    public AdaptadorAsistencia(Activity context, Cursor cursor, OnListAsistenciaInteractionListener listener, FragmentAsistencia.OnListaCheckBoxInteractionListener listenerCheck){
        this.context=context;
        this.cursor=cursor;
        this.listener=listener;
        this.listenerCheck=listenerCheck;
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
                    listener.onListFragmentInteraction(holder);
                }
            }
        });

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

    public boolean existeId(String id)
    {
        for(String s:itemsList){
            if(id.equalsIgnoreCase(s)){
                return true;
            }
        }
        return false;
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
