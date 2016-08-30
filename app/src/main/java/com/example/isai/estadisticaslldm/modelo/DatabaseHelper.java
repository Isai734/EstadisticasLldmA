package com.example.isai.estadisticaslldm.modelo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Clase envoltura para el gestor de Bases de datos
 */
class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(Context context,
                          String name,
                          SQLiteDatabase.CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase database) {
        //Crear tabla materia
        String cmd="CREATE TABLE " + ContratoAsistencia.LISTA + " (" +
                ContratoAsistencia.ColumnasLista._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContratoAsistencia.ColumnasLista.NO_MIEMBROS + " INTEGER)";
        database.execSQL(cmd);

        //Crear tabla MIEMBRO
         cmd = "CREATE TABLE " + ContratoAsistencia.MIEMBRO + " (" +
                ContratoAsistencia.ColumnasMiembro._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContratoAsistencia.ColumnasMiembro.NOMBRE + " TEXT," +
                ContratoAsistencia.ColumnasMiembro.A_PATERNO + " TEXT," +
                ContratoAsistencia.ColumnasMiembro.A_MATERNO + " TEXT," +
                ContratoAsistencia.ColumnasMiembro.GRUPO + " TEXT," +
                ContratoAsistencia.ColumnasMiembro.ID_LISTA + " INTEGER, FOREIGN KEY(" +
                ContratoAsistencia.ColumnasMiembro.ID_LISTA + ") REFERENCES " +
                ContratoAsistencia.LISTA + "(" +
                ContratoAsistencia.ColumnasLista._ID + "))";
        database.execSQL(cmd);

        //Crear tabla MIEMBRO
        cmd = "CREATE TABLE " + ContratoAsistencia.ASISTENCIA + " (" +
                ContratoAsistencia.ColumnasAsistencia._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContratoAsistencia.ColumnasAsistencia.ASISTIO + " TEXT, " +
                ContratoAsistencia.ColumnasAsistencia.ID_PASE_LISTA + " INTEGER, " +
                ContratoAsistencia.ColumnasAsistencia.ID_MIEMBRO + " INTEGER, FOREIGN KEY(" +
                ContratoAsistencia.ColumnasAsistencia.ID_MIEMBRO + ") REFERENCES " +
                ContratoAsistencia.MIEMBRO + "(" +
                ContratoAsistencia.ColumnasMiembro._ID + "), FOREIGN KEY(" +
                ContratoAsistencia.ColumnasAsistencia.ID_PASE_LISTA + ") REFERENCES " +
                ContratoAsistencia.PASE_LISTA + "(" +
                ContratoAsistencia.ColumnasPaseLista._ID + "))";
        database.execSQL(cmd);

        cmd = "CREATE TABLE " + ContratoAsistencia.PASE_LISTA + " (" +
                ContratoAsistencia.ColumnasPaseLista._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContratoAsistencia.ColumnasPaseLista.FECHA + " DATE," +
                ContratoAsistencia.ColumnasPaseLista.TIPO + " TEXT," +
                ContratoAsistencia.ColumnasPaseLista.NO_ASISTENCIAS + " INTEGER)";
        database.execSQL(cmd);

        cmd = "CREATE TABLE " + ContratoAsistencia.INFORME + " (" +
                ContratoAsistencia.ColumnasPaseLista._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContratoAsistencia.ColumnasInforme.MES + " TEXT," +
                ContratoAsistencia.ColumnasInforme.PROM_DOMINICAL + " INTEGER," +
                ContratoAsistencia.ColumnasInforme.PROM_SERV_DOM + " INTEGER," +
                ContratoAsistencia.ColumnasInforme.PROM_SERV_JUEVES + " INTEGER," +
                ContratoAsistencia.ColumnasInforme.PROM_SEIS + " INTEGER," +
                ContratoAsistencia.ColumnasInforme.PROM_EST_LUNES + " INTEGER," +
                ContratoAsistencia.ColumnasInforme.PROM_EST_MIER + " INTEGER," +
                ContratoAsistencia.ColumnasInforme.PROM_EST_SABADO + " INTEGER," +
                ContratoAsistencia.ColumnasInforme.PROM_0430 + " INTEGER)";

        database.execSQL(cmd);
        createTable(database);
        Log.i("helper", cmd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Actualizaciones
    }

    /**
     * Crear tabla en la base de datos
     *
     * @param database Instancia de la base de datos
     */
    private void createTable(SQLiteDatabase database) {
        database.execSQL("INSERT INTO LISTA VALUES(NULL,'243')");
        database.execSQL("INSERT INTO MIEMBRO VALUES(null,'Isai','Castro','Alvarado','Coro 10 de Junio','1')");
        database.execSQL("INSERT INTO MIEMBRO VALUES(null,'Kelly','Castro','Alvarado','Coro 10 de Junio','1')");
        database.execSQL("INSERT INTO MIEMBRO VALUES(null,'Jael','Castro','Alvarado','Coro 10 de Junio','1')");
        database.execSQL("INSERT INTO MIEMBRO VALUES(null,'Azael','Castro','Adame','Coro 10 de Junio','1')");
        database.execSQL("INSERT INTO MIEMBRO VALUES(null,'Aviamel','Tolentino','MIllan','Coro 10 de Junio','1')");
        database.execSQL("INSERT INTO MIEMBRO VALUES(null,'Francisco','Rivas','Tolentino','Coro 10 de Junio','1')");
        database.execSQL("INSERT INTO MIEMBRO VALUES(null,'Sabdi Melec','Castro','Gonzales','Coro 10 de Junio','1')");
        database.execSQL("INSERT INTO MIEMBRO VALUES(null,'Sarahi','Giles','Vega','Coro 10 de Junio','1')");
        database.execSQL("INSERT INTO MIEMBRO VALUES(null,'Sulema','Castro','Tolentino','Coro 10 de Junio','1')");
        database.execSQL("INSERT INTO MIEMBRO VALUES(null,'Asmadeli','Castro','Tolentino','Coro 10 de Junio','1')");
        database.execSQL("INSERT INTO MIEMBRO VALUES(null,'Clemencia','Tolentino','Cruz','Coro 10 de Junio','1')");
        database.execSQL("INSERT INTO MIEMBRO VALUES(null,'Jazmin','Marino','Algo','Coro 10 de Junio','1')");
        database.execSQL("INSERT INTO MIEMBRO VALUES(null,'Elizama','Millan','De Jesus','Coro 10 de Junio','1')");
        database.execSQL("INSERT INTO MIEMBRO VALUES(null,'Alma Marleni','Morales','Toolentino','Coro 10 de Junio','1')");
        database.execSQL("INSERT INTO MIEMBRO VALUES(null,'Gloria','Castro','Sanchez','Coro 10 de Junio','1')");
    }

    /**
     * Carga datos de ejemplo en la tabla
     * @param database Instancia de la base de datos
     */

}
