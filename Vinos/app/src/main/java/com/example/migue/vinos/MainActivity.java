package com.example.migue.vinos;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    Button btnAñadir;
    Vinos[] vinos;
    ListView lstVinos;
    VinosListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAñadir = (Button) findViewById(R.id.btnAnadir);
        //btnAñadir.setOnClickListener(this);

        MostrarLista();

        lstVinos.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list, View view, int pos, long id) {
                Vinos v = null;
                // v = new Vinos(2L," "," ", true," ", " " , 2, " ");


                onVinoSeleccionado(v);
            }
        });
    }




    public void onVinoSeleccionado(Vinos v){        //Si dejo este método sin argumentos, consigo que se abra la actividad, luego el método funciona, pero no conigo pasar un objeto como argumento
        Intent i = new Intent(this, activity_anadir.class);  //rectifico, puedo mandarle un bjeto como argumento, pero no el objeto que está seleccionado en la lista

        startActivity(i);
    }
    @Override
    protected void onResume() {
        super.onResume();
        MostrarLista();
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public interface VinosListener {
        void onVinoSeleccionado(Vinos v);
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
             lstVinos = (ListView)findViewById(R.id.lstVinos);
            final Adaptador_Vinos adaptador_contactos =
                    new Adaptador_Vinos(this, vinos);
            lstVinos.setAdapter(adaptador_contactos);

        }
        dbInterface.cierra();
    }
}
