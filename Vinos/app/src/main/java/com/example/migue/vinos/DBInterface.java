package com.example.migue.vinos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by migue on 28/01/2018.
 */

public class DBInterface {
    public static final String CAMPO_ID = "_id";
    public static final String CAMPO_NOMBRE = "nombre";
    public static final String CAMPO_DENOMINACION = "denominacion";
    public static final String CAMPO_PROBADO= "probado";
    public static final String CAMPO_TIPO = "tipo";
    public static final String CAMPO_DESCRIPCION = "descripcion";
    public static final String CAMPO_RATING = "rating";
    public static final String CAMPO_IMAGEN = "imagen";
    public static final String TAG = "DBInterface";

    public static final String BD_NOMBRE = "BDVinos";
    public static final String BD_TABLA = "vinos";
    public static final int VERSION =5;

    public static final String BD_CREATE =
            "create table " + BD_TABLA + "(" + CAMPO_ID + " integer primary key autoincrement, "+
                    CAMPO_NOMBRE + " text not null," + CAMPO_DENOMINACION + " text not null," + CAMPO_PROBADO + " integer not null,"+CAMPO_TIPO +" text not null," +CAMPO_DESCRIPCION +" text not null,"
                    + CAMPO_RATING + " integer not null,"+ CAMPO_IMAGEN + " text not null); ";
    private final Context contexto;
    private AyudaDB ayuda;
    private SQLiteDatabase bd;

    public DBInterface (Context con)
    {
        this.contexto = con;
        Log.w(TAG, "creando ayuda" );
        ayuda = new AyudaDB(contexto);
    }

    public DBInterface abre () throws SQLException {
        Log.w(TAG, "abrimos base de datos" );
        bd = ayuda.getWritableDatabase();
        return this;
    }

    // Cierra la BD
    public void cierra () {
        ayuda.close();
    }

    public long insertarContacto(String nombre, String denominacion, boolean probado, String tipo, String descripcion,int rating, String imagen)
    {
        ContentValues initialValues = new ContentValues ();
        initialValues.put(CAMPO_NOMBRE, nombre);
        initialValues.put(CAMPO_DENOMINACION, denominacion);
        initialValues.put(CAMPO_PROBADO, probado);
        initialValues.put(CAMPO_TIPO, tipo);
        initialValues.put(CAMPO_DESCRIPCION, descripcion);
        initialValues.put(CAMPO_RATING, rating);
        initialValues.put(CAMPO_IMAGEN, imagen);


        return bd.insert(BD_TABLA, null, initialValues);
    }
    public long modificaContacto(long id,String nombre, String denominacion, boolean probado, String tipo, String descripcion,int rating, String imagen)
    {
        ContentValues newValues = new ContentValues ();
        newValues.put(CAMPO_NOMBRE, nombre);
        newValues.put(CAMPO_DENOMINACION, denominacion);
        newValues.put(CAMPO_PROBADO, probado);
        newValues.put(CAMPO_TIPO, tipo);
        newValues.put(CAMPO_DESCRIPCION, descripcion);
        newValues.put(CAMPO_RATING, rating);
        newValues.put(CAMPO_IMAGEN, imagen);

        return bd.update(BD_TABLA, newValues, CAMPO_ID + "=" + id, null);
    }

    public long borrarContacto(long id)
    {

        return bd.delete(BD_TABLA, CAMPO_ID + "=" + id, null);
    }

    // Devuelve los datos de un contacto a través de su id
    public Cursor obtenerContacto(Long id){
        // Toast.makeText(this.contexto, "ID:"+id, Toast.LENGTH_LONG).show();
        //       {CAMPO_NOMBRE, CAMPO_EMAIL}, CAMPO_ID + "=?" , new String[] {id.toString()}, null, null, null);*/
        return bd.query(BD_TABLA, new String[]
                {CAMPO_NOMBRE, CAMPO_DENOMINACION,CAMPO_PROBADO, CAMPO_TIPO, CAMPO_DESCRIPCION,CAMPO_RATING, CAMPO_IMAGEN}, CAMPO_ID + "=" + id , null, null, null,null);
    }


    // Devuelve todos los Contactos
    public Cursor obtenerContactos(){
        return bd.query(BD_TABLA, new String []
                { CAMPO_ID,CAMPO_NOMBRE, CAMPO_DENOMINACION, CAMPO_PROBADO, CAMPO_TIPO, CAMPO_DESCRIPCION,CAMPO_RATING, CAMPO_IMAGEN}, null,null, null, null, null);
    }

    public class AyudaDB extends SQLiteOpenHelper {

        public AyudaDB(Context con){
            super (con, BD_NOMBRE, null, VERSION);
            Log.w(TAG, "constructor de ayuda");
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                Log.w(TAG, "creando la base de datos "+BD_CREATE );
                db.execSQL(BD_CREATE);
            } catch (SQLException e) {
                e.printStackTrace ();
            }
        }

        @Override
        public void onUpgrade (SQLiteDatabase db,
                               int VersionAntigua, int VersionNueva) {
            Log.w(TAG, "Actualizando Base de datos de la versión" +
                    VersionAntigua + "A" + VersionNueva + ". Destruirá todos los datos");
            db.execSQL("DROP TABLE IF EXISTS " + BD_TABLA);

            onCreate(db);
        }
    }
}
