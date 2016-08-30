package com.example.isai.estadisticaslldm.modelo;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Content Provider personalizado para las actividades
 */
public class ProveedorAsistencia extends ContentProvider {
    /**
     * Nombre de la base de datos
     */
    private static final String DATABASE_NAME = "lista.db";
    /**
     * Versi�n actual de la base de datos
     */
    private static final int DATABASE_VERSION = 1;
    /**
     * Instancia del administrador de BD
     */
    private DatabaseHelper databaseHelper;

    @Override
    public boolean onCreate() {
        // Inicializando gestor BD
        databaseHelper = new DatabaseHelper(
                getContext(),
                DATABASE_NAME,
                null,
                DATABASE_VERSION
        );
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int match = ContratoAsistencia.uriMatcher.match(uri);
        switch (match) {
            // Consultando todos los registros
            case ContratoAsistencia.ALUMNOS:
                return query(ContratoAsistencia.CONTENT_URI_MIEMBRO, ContratoAsistencia.MIEMBRO, projection, selection, selectionArgs, sortOrder);
            case ContratoAsistencia.INFORMES:
                return query(ContratoAsistencia.CONTENT_URI_INFORME, ContratoAsistencia.INFORME, projection, selection, selectionArgs, sortOrder);
            case ContratoAsistencia.LISTAS:
                return query(ContratoAsistencia.CONTENT_URI_LISTA, ContratoAsistencia.LISTA, projection, selection, selectionArgs, sortOrder);
            case ContratoAsistencia.ASISTENCIAS:
                return query(ContratoAsistencia.CONTENT_URI_ASISTENCIA, ContratoAsistencia.ASISTENCIA, projection, selection, selectionArgs, sortOrder);
            case ContratoAsistencia.PASE_LISTAS:
                return query(ContratoAsistencia.CONTENT_URI_PASE_LISTA, ContratoAsistencia.PASE_LISTA, projection, selection, selectionArgs, sortOrder);
            // Consultando registros por el _id
            case ContratoAsistencia.ALUMNOS_ID:
                return idQuery(uri, ContratoAsistencia.CONTENT_URI_MIEMBRO, ContratoAsistencia.MIEMBRO, projection, selectionArgs, sortOrder);
            case ContratoAsistencia.INFORMES_ID:
                return idQuery(uri, ContratoAsistencia.CONTENT_URI_INFORME, ContratoAsistencia.INFORME, projection, selectionArgs, sortOrder);
            case ContratoAsistencia.LISTAS_ID:
                return idQuery(uri, ContratoAsistencia.CONTENT_URI_LISTA, ContratoAsistencia.LISTA, projection, selectionArgs, sortOrder);
            case ContratoAsistencia.ASISTENCIAS_ID:
                return idQuery(uri, ContratoAsistencia.CONTENT_URI_ASISTENCIA, ContratoAsistencia.ASISTENCIA, projection, selectionArgs, sortOrder);
            case ContratoAsistencia.PASE_LISTA_ID:
                return idQuery(uri, ContratoAsistencia.CONTENT_URI_PASE_LISTA, ContratoAsistencia.PASE_LISTA, projection, selectionArgs, sortOrder);

            default:
                throw new IllegalArgumentException("URI no soportada: " + uri);
        }
    }

    public Cursor query(Uri contenUri, String tabla, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor c;
        c = db.query(tabla, projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(
                getContext().getContentResolver(), contenUri
        );
        return c;
    }

    public Cursor idQuery(Uri uri, Uri contenUri, String tabla, String[] projection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor c;
        long videoID = ContentUris.parseId(uri);
        c = db.query(tabla, projection,
                ContratoAsistencia.ColumnasPaseLista._ID + " = " + videoID, selectionArgs, null, null, sortOrder
        );
        c.setNotificationUri(
                getContext().getContentResolver(), contenUri
        );
        return c;
    }

    @Override
    public String getType(Uri uri) {
        switch (ContratoAsistencia.uriMatcher.match(uri)) {
            case ContratoAsistencia.ALUMNOS:
                return ContratoAsistencia.MULTIPLE_MIME_ALUMNO;
            case ContratoAsistencia.INFORMES:
                return ContratoAsistencia.MULTIPLE_MIME_INFORME;
            case ContratoAsistencia.LISTAS:
                return ContratoAsistencia.MULTIPLE_MIME_LISTA;
            case ContratoAsistencia.ASISTENCIAS:
                return ContratoAsistencia.MULTIPLE_MIME_ASISTENCIA;
            case ContratoAsistencia.PASE_LISTAS:
                return ContratoAsistencia.MULTIPLE_MIME_PASE_LISTA;
            //mime por id
             case ContratoAsistencia.ALUMNOS_ID:
                return ContratoAsistencia.SIMPLE_MIME_ALUMNO;
            case ContratoAsistencia.INFORMES_ID:
                return ContratoAsistencia.SIMPLE_MIME_INFORME;
            case ContratoAsistencia.LISTAS_ID:
                return ContratoAsistencia.SIMPLE_MIME_LISTA;
            case ContratoAsistencia.ASISTENCIAS_ID:
                return ContratoAsistencia.SIMPLE_MIME_ASISTENCIA;
           case ContratoAsistencia.PASE_LISTA_ID:
                return ContratoAsistencia.SIMPLE_MIME_PASE_LISA;
            default:
                throw new IllegalArgumentException("Tipo de tabla desconocida: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // Validar la uri
        switch (ContratoAsistencia.uriMatcher.match(uri)) {
            case ContratoAsistencia.ALUMNOS:
                return insert(uri, ContratoAsistencia.MIEMBRO, ContratoAsistencia.CONTENT_URI_MIEMBRO, values);
            case ContratoAsistencia.LISTAS:
                return insert(uri, ContratoAsistencia.LISTA, ContratoAsistencia.CONTENT_URI_LISTA, values);
            case ContratoAsistencia.ASISTENCIAS:
                return insert(uri, ContratoAsistencia.ASISTENCIA, ContratoAsistencia.CONTENT_URI_ASISTENCIA, values);
            case ContratoAsistencia.PASE_LISTAS:
                return insert(uri, ContratoAsistencia.PASE_LISTA, ContratoAsistencia.CONTENT_URI_PASE_LISTA, values);
            case ContratoAsistencia.INFORMES:
                return insert(uri, ContratoAsistencia.INFORME, ContratoAsistencia.CONTENT_URI_INFORME, values);
            default:
                throw new SQLException("Falla al insertar fila en : " + uri);
        }
    }

    private Uri insert(Uri uri, String tabla, Uri contentUri, ContentValues values) {
        ContentValues contentValues;
        if (values != null) {
            contentValues = new ContentValues(values);
        } else {
            contentValues = new ContentValues();
        }
        // Si es necesario, verifica los valores
        // Inserci�n de nueva fila
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        long rowId = db.insert(tabla, null, contentValues);
        if (rowId > 0) {
            Uri uri_actividad = ContentUris.withAppendedId(contentUri, rowId);
            getContext().getContentResolver().notifyChange(uri_actividad, null);
            return uri_actividad;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int match = ContratoAsistencia.uriMatcher.match(uri);
        switch (match) {
             case ContratoAsistencia.ALUMNOS:
                return delete(ContratoAsistencia.MIEMBRO, selection, selectionArgs);
            case ContratoAsistencia.INFORMES:
                return delete(ContratoAsistencia.INFORME, selection, selectionArgs);
            case ContratoAsistencia.LISTAS:
                return delete(ContratoAsistencia.LISTA, selection, selectionArgs);
            case ContratoAsistencia.ASISTENCIAS:
                return delete(ContratoAsistencia.ASISTENCIA, selection, selectionArgs);
            case ContratoAsistencia.PASE_LISTAS:
                return delete(ContratoAsistencia.PASE_LISTA, selection, selectionArgs);
            //eliminar por id
            case ContratoAsistencia.ALUMNOS_ID:
                return idDelete(ContratoAsistencia.MIEMBRO, uri, selection, selectionArgs);
            case ContratoAsistencia.INFORMES_ID:
                return idDelete(ContratoAsistencia.INFORME, uri, selection, selectionArgs);
            case ContratoAsistencia.LISTAS_ID:
                return idDelete(ContratoAsistencia.LISTA, uri, selection, selectionArgs);
            case ContratoAsistencia.ASISTENCIAS_ID:
                return idDelete(ContratoAsistencia.ASISTENCIA, uri, selection, selectionArgs);
            case ContratoAsistencia.PASE_LISTA_ID:
                return idDelete(ContratoAsistencia.PASE_LISTA, uri, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Elemento desconocido: " +
                        uri);
        }
    }

    public int delete(String tabla, String selection, String[] selectionArgs) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int affected = db.delete(tabla, selection, selectionArgs);
        return affected;
    }

    public int idDelete(String tabla, Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        long videoId = ContentUris.parseId(uri);
        int affected = db.delete(tabla, ContratoAsistencia.ColumnasPaseLista._ID + "=" + videoId
                + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
        // Notificar cambio asociado a la uri
        getContext().getContentResolver().notifyChange(uri, null);
        return affected;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int affected;
        switch (ContratoAsistencia.uriMatcher.match(uri)) {
            case ContratoAsistencia.ALUMNOS:
                return update(ContratoAsistencia.MIEMBRO, uri, values, selection, selectionArgs);
            case ContratoAsistencia.INFORMES:
                return update(ContratoAsistencia.INFORME, uri, values, selection, selectionArgs);
            case ContratoAsistencia.LISTAS:
                return update(ContratoAsistencia.LISTA, uri, values, selection, selectionArgs);
            case ContratoAsistencia.ASISTENCIAS:
                return update(ContratoAsistencia.ASISTENCIA, uri, values, selection, selectionArgs);
            case ContratoAsistencia.PASE_LISTAS:
                return update(ContratoAsistencia.PASE_LISTA, uri, values, selection, selectionArgs);
            //eliminar por id
            case ContratoAsistencia.ALUMNOS_ID:
                return idUpdate(ContratoAsistencia.MIEMBRO, uri, values, selection, selectionArgs);
            case ContratoAsistencia.INFORMES_ID:
                return idUpdate(ContratoAsistencia.INFORME, uri, values, selection, selectionArgs);
            case ContratoAsistencia.LISTAS_ID:
                return idUpdate(ContratoAsistencia.LISTA, uri, values, selection, selectionArgs);
            case ContratoAsistencia.ASISTENCIAS_ID:
                return idUpdate(ContratoAsistencia.ASISTENCIA, uri, values, selection, selectionArgs);
            case ContratoAsistencia.PASE_LISTA_ID:
                return idUpdate(ContratoAsistencia.PASE_LISTA, uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("URI desconocida: " + uri);
        }
    }

    public int update(String tabla, Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int affected = db.update(tabla, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return affected;
    }

    public int idUpdate(String tabla, Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int affected;
        String videoId = uri.getPathSegments().get(1);
        affected = db.update(tabla, values, ContratoAsistencia.ColumnasPaseLista._ID + "=" + videoId
                        + (!TextUtils.isEmpty(selection) ?
                        " AND (" + selection + ')' : ""),
                selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return affected;
    }
}

