package com.example.isai.estadisticaslldm.modelo;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Contract Class entre el provider y las aplicaciones
 */
public class
        ContratoAsistencia {
    /**
     * Autoridad del Content Provider
     */
    public final static String AUTORIDAD = "com.example.isai.estadisticaslldm.modelo.ProveedorAsistencia";
    /**
     * Representaci�n de las tablas a consultar
     */

    public static final String LISTA = "lista";
    public static final String MIEMBRO = "miembro";
    public static final String ASISTENCIA = "asistencia";
    public static final String PASE_LISTA = "pase_lista";
    public final static String INFORME="informe";
    /**
     * Tipo MIME que retorna la consulta de una sola fila
     */

    public final static String SIMPLE_MIME_LISTA =
            "vnd.android.cursor.item/vnd." + AUTORIDAD + LISTA;
    public final static String SIMPLE_MIME_ALUMNO =
            "vnd.android.cursor.item/vnd." + AUTORIDAD + MIEMBRO;
    public final static String SIMPLE_MIME_ASISTENCIA =
            "vnd.android.cursor.item/vnd." + AUTORIDAD + ASISTENCIA;
    public final static String SIMPLE_MIME_INFORME =
            "vnd.android.cursor.item/vnd." + AUTORIDAD + INFORME;
    public final static String SIMPLE_MIME_PASE_LISA =
            "vnd.android.cursor.item/vnd." + AUTORIDAD + PASE_LISTA;
    /**
     * Tipo MIME que retorna la consulta de {@link }
     */
    public final static String MULTIPLE_MIME_INFORME =
            "vnd.android.cursor.dir/vnd." + AUTORIDAD + INFORME;
    public final static String MULTIPLE_MIME_LISTA =
            "vnd.android.cursor.dir/vnd." + AUTORIDAD + LISTA;
    public final static String MULTIPLE_MIME_ALUMNO =
            "vnd.android.cursor.dir/vnd." + AUTORIDAD + MIEMBRO;
    public final static String MULTIPLE_MIME_ASISTENCIA =
            "vnd.android.cursor.dir/vnd." + AUTORIDAD + ASISTENCIA;

    public final static String MULTIPLE_MIME_PASE_LISTA =
            "vnd.android.cursor.dir/vnd." + AUTORIDAD + PASE_LISTA;
    /**
     * URI de contenido principal
     */
    public final static Uri CONTENT_URI_INFORME =
            Uri.parse("content://" + AUTORIDAD + "/" + INFORME);
    public final static Uri CONTENT_URI_LISTA =
            Uri.parse("content://" + AUTORIDAD + "/" + LISTA);
    public final static Uri CONTENT_URI_MIEMBRO =
            Uri.parse("content://" + AUTORIDAD + "/" + MIEMBRO);
    public final static Uri CONTENT_URI_ASISTENCIA =
            Uri.parse("content://" + AUTORIDAD + "/" + ASISTENCIA);

    public final static Uri CONTENT_URI_PASE_LISTA =
            Uri.parse("content://" + AUTORIDAD + "/" + PASE_LISTA);
    /**
     * Comparador de URIs de contenido
     */
    public static final UriMatcher uriMatcher;
    /**
     * C�digo para URIs de multiples registros
     */
    public static final int INFORMES = 1;
    public static final int LISTAS = 2;
    public static final int ALUMNOS = 3;
    public static final int ASISTENCIAS = 4;
    public static final int MIEMBROS_LISTAS = 5;
    public static final int PASE_LISTAS = 11;
    /**
     * C�digo para URIS de un solo registro
     */
    public static final int INFORMES_ID = 6;
    public static final int LISTAS_ID = 7;
    public static final int ALUMNOS_ID = 8;
    public static final int ASISTENCIAS_ID = 9;
    public static final int ALUMONS_LISTAS_ID = 10;
    public static final int PASE_LISTA_ID = 12;
    public static final CharSequence[] tipos={"Estudio Lunes","Estudio Miercoles","Estudio Sabado",
            "Servicio Domingo","Servicio Jueves","Dominical","Consagracion 04:30","Horcion 06:00"};
    // Asignaci�n de URIs
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTORIDAD, INFORME, INFORMES);
        uriMatcher.addURI(AUTORIDAD, INFORME + "/#", INFORMES_ID);
        uriMatcher.addURI(AUTORIDAD, LISTA, LISTAS);
        uriMatcher.addURI(AUTORIDAD, LISTA + "/#", LISTAS_ID);
        uriMatcher.addURI(AUTORIDAD, MIEMBRO, ALUMNOS);
        uriMatcher.addURI(AUTORIDAD, MIEMBRO + "/#", ALUMNOS_ID);
        uriMatcher.addURI(AUTORIDAD, ASISTENCIA, ASISTENCIAS);
        uriMatcher.addURI(AUTORIDAD, ASISTENCIA + "/#", ASISTENCIAS_ID);

        uriMatcher.addURI(AUTORIDAD, PASE_LISTA, PASE_LISTAS);
        uriMatcher.addURI(AUTORIDAD, PASE_LISTA + "/#", PASE_LISTA_ID);
    }

    /**
     * Estructura de la tabla MATERIA
     */
    public static class ColumnasInforme implements BaseColumns {
        private ColumnasInforme() {}
        public final static String MES= "mes";
        public final static String PROM_DOMINICAL="prom_dominical";
        public final static String PROM_SERV_DOM="prom_serv_dom";
        public final static String PROM_SERV_JUEVES="prom_serv_jueves";
        public final static String PROM_0430 ="prom_cuatro";
        public final static String PROM_EST_LUNES ="prom_est_lunes";
        public final static String PROM_SEIS="seis";
        public final static String PROM_EST_MIER = "prom_est_mier";
        public final static String PROM_EST_SABADO = "prom_est_sabado";
    }
    /**
     * Estructura de la tabla LISTA
     */
    public static class ColumnasLista implements BaseColumns {
            private ColumnasLista() {}
        public final static String NO_MIEMBROS = "no_miembros";

    }
    /**
     * Estructura de la tabla MIEMBRO
     */
    public static class ColumnasMiembro implements BaseColumns {
        private ColumnasMiembro() {}
        public final static String NOMBRE = "nombre";
        public final static String A_PATERNO = "a_paterno";
        public final static String A_MATERNO = "a_materno";

        public final static String GRUPO = "grupo";
        public final static String ID_LISTA = "id_lista";
    }
    /**
     * Estructura de la tabla ASISTENCIA
     */
    public static class ColumnasAsistencia implements BaseColumns {
        private ColumnasAsistencia() {}
        public final static String ASISTIO = "asistio";
        public final static String ID_PASE_LISTA = "id_pase_lista";
        public final static String ID_MIEMBRO = "id_miembro";
    }
    /**
     * Estructura de la tabla ALUMNOS_LISTA
     */
    public static class ColumnasPaseLista implements BaseColumns {
        private ColumnasPaseLista() {}
        public final static String FECHA = "fecha";
        public final static String TIPO = "tipo";
        public final static String NO_ASISTENCIAS = "no_asistencias";
        public final static String ID_INFORME="id_informe";
    }



}
