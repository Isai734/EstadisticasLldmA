package com.example.isai.estadisticaslldm.ui;

import android.app.Dialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.isai.estadisticaslldm.R;
import com.example.isai.estadisticaslldm.modelo.ContratoAsistencia;


/**
 * Fragmento con un diálogo personalizado
 */
public class DialogoEditarAlumno extends DialogFragment {

    EditText nombre, aPeterno, aMaterno;
    Spinner grupos;
    private long _ID;

    public DialogoEditarAlumno() {
    }


    public interface OnDialogoEditarAlumnosListener {
        void onButtonClickEditar();
    }

    OnDialogoEditarAlumnosListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createLoginDialogo();
    }

    /**
     * Crea un diálogo con personalizado para comportarse
     * como formulario de alumno
     *
     * @return Diálogo
     */
    public AlertDialog createLoginDialogo() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialogo_agregar_miembro, null);
        builder.setView(v);

        Button guadar = (Button) v.findViewById(R.id.guardar_btn);
        nombre = (EditText) v.findViewById(R.id.nombre_etx);
        aPeterno = (EditText) v.findViewById(R.id.a_pater_etx);
        aMaterno = (EditText) v.findViewById(R.id.a_mater_etx);
        grupos=(Spinner)v.findViewById(R.id.spn_grupos);
        guadar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Loguear...
                        saveData();
                        listener.onButtonClickEditar();
                        dismiss();
                    }
                }
        );
        setData();
        return builder.create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (OnDialogoEditarAlumnosListener) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() +
                            " no implementó OnDialogoAlumnosListener");
        }
    }

    private void saveData() {
        // Obtención de valores actuales
        ContentValues values = new ContentValues();
        values.put(ContratoAsistencia.ColumnasMiembro.NOMBRE, nombre.getText().toString());
        values.put(ContratoAsistencia.ColumnasMiembro.A_PATERNO, aPeterno.getText().toString());
        values.put(ContratoAsistencia.ColumnasMiembro.A_MATERNO, aMaterno.getText().toString());
        values.put(ContratoAsistencia.ColumnasMiembro.GRUPO,grupos.getSelectedItem().toString());

        Uri uri = ContentUris.withAppendedId(ContratoAsistencia.CONTENT_URI_MIEMBRO, _ID);
        getActivity().getContentResolver().update(uri, values, null, null);
    }

    public void setData() {
        _ID = getActivity().getIntent().getLongExtra(ContratoAsistencia.ColumnasMiembro._ID, 0);
        Uri uri = ContentUris.withAppendedId(ContratoAsistencia.CONTENT_URI_MIEMBRO, _ID);
        Cursor c = getActivity().getContentResolver().query(uri, null, null, null, null);
        if (!c.moveToFirst())
            return;
        String nom = c.getString(c.getColumnIndex(ContratoAsistencia.ColumnasMiembro.NOMBRE));
        String aP = c.getString(c.getColumnIndex(ContratoAsistencia.ColumnasMiembro.A_PATERNO));
        String aM = c.getString(c.getColumnIndex(ContratoAsistencia.ColumnasMiembro.A_MATERNO));
        String grupo = c.getString(c.getColumnIndex(ContratoAsistencia.ColumnasMiembro.GRUPO));
        nombre.setText(nom);
        aPeterno.setText(aP);
        aMaterno.setText(aM);
        switch (grupo){
            case "Casados Grandes":
                grupos.setSelection(0);
                break;
            case "Solos":
                grupos.setSelection(1);
                break;
            case "Casados medianos":
                grupos.setSelection(2);
                break;
            case "Casados Chicos":
                grupos.setSelection(3);
                break;
            case "Casados Jovenes":
                grupos.setSelection(4);
                break;
        }

        c.close(); // Liberar memoria del cursor
    }
}

