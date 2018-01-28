package com.example.migue.vinos;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnAñadir;
    Vinos[] vinos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAñadir = (Button) findViewById(R.id.btnAnadir);
        MostrarLista();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MostrarLista();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAnadir:
                startActivity(new Intent(this,activity_anadir.class));
                break;
           /* case R.id.btnObtener:
                startActivity(new Intent(this,Activity_Obtener.class));
                break;
            case R.id.btnObtenerTodos:*/
               // startActivity(new Intent(this,Activity_VerTodos.class));
                //Para visualizar los datos en un Toast
             /*   DBInterface dbInterface = new DBInterface(this);
                dbInterface.abre();
                Cursor c= dbInterface.obtenerContactos();

                // Movemos el cursor en la primera posición
                if (c == null) {
                    Toast.makeText(getBaseContext(), "Tabla vacía", Toast.LENGTH_LONG).show();
                } else {
                    String contactos = "";
                    if (c.moveToFirst()) {
                        do {
                            contactos = contactos + "\n Nombre: " + c.getString(0) + " Email: " + c.getString(1);
                            //  Mientras podamos pasar al siguiente contacto
                        } while (c.moveToNext());
                    }
                    Toast.makeText(getBaseContext(), contactos, Toast.LENGTH_LONG).show();
                }

               dbInterface.cierra();*/
              /*  break;
            case R.id.btnActualizar:
                startActivity(new Intent(this,Activity_Modificar.class));
                break;
            case R.id.btnBorrar:
                startActivity(new Intent(this,Activity_Borrar.class));
                break;*/
        }
    }

    public class Adaptador_Vinos extends ArrayAdapter<Vinos> {

        private Vinos[] datos;

        public Adaptador_Vinos(Context context, Vinos[] datosEnviados) {
            super(context, R.layout.item_vinos,datosEnviados);
            datos=datosEnviados;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.item_vinos, null);


            TextView lblID = (TextView)item.findViewById(R.id.LblId);
            lblID.setText(datos[position].getId().toString());
            TextView lblNombre = (TextView)item.findViewById(R.id.LblNombre);
            lblNombre.setText(datos[position].getNombre());
            TextView lblDenominacion = (TextView)item.findViewById(R.id.LblDenominacion);
            lblDenominacion.setText(datos[position].getDenominacion());

            return(item);
        }
    }

    public void MostrarLista(){
        DBInterface dbInterface = new DBInterface(this);
        dbInterface.abre();
        Cursor c= dbInterface.obtenerContactos();
        // Movemos el cursor en la primera posición
        if (c == null || c.getCount()==0) {
            Toast.makeText(getBaseContext(), "Tabla vacía", Toast.LENGTH_LONG).show();
        } else {
            vinos = new Vinos[c.getCount()];
            int i=0;
            if (c.moveToFirst()) {
                do {

                    vinos[i] = new Vinos(c.getLong(0),c.getString(1),c.getString(2), c.getInt(3)>0, c.getString(4), c.getString(5), c.getInt(6), c.getString(7));

                    //  Mientras podamos pasar al siguiente contacto
                    i++;
                } while (c.moveToNext());
            }
            ListView lstContactos = (ListView)findViewById(R.id.lstVinos);
            final Adaptador_Vinos adaptador_contactos =
                    new Adaptador_Vinos(this, vinos);
            lstContactos.setAdapter(adaptador_contactos);

        }
        dbInterface.cierra();
    }
}
