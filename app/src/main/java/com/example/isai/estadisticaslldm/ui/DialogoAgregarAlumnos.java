package com.example.isai.estadisticaslldm.ui;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
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
public class DialogoAgregarAlumnos extends DialogFragment {

    EditText nombre, aPeterno, aMaterno;
    Spinner grupo;
    private long _ID;
    public DialogoAgregarAlumnos() {
    }


    public interface OnDialogoAlumnosListener {
        void onButtonClickGuardar();
    }

    OnDialogoAlumnosListener listener;

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
        grupo = (Spinner) v.findViewById(R.id.spn_grupos);
        guadar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Loguear...
                        saveData();
                        listener.onButtonClickGuardar();
                        dismiss();
                    }
                }
        );

        return builder.create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (OnDialogoAlumnosListener) context;

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
        values.put(ContratoAsistencia.ColumnasMiembro.GRUPO, grupo.getSelectedItem().toString());
        values.put(ContratoAsistencia.ColumnasMiembro.ID_LISTA, "1");
        getActivity().getContentResolver().insert(ContratoAsistencia.CONTENT_URI_MIEMBRO, values);
    }
}

