package com.example.migue.vinos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Toast;

public class activity_anadir extends AppCompatActivity implements View.OnClickListener {
    Button btnEliminar;
    Button btnGrabar;
    EditText nombre;
    EditText denominacion;
    CheckBox probado;
    RadioButton tinto;
    RadioButton blanco;
    RatingBar rating;
    String tipo = "";
    String opcion;
    long id;

    DBInterface dbInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir);
        btnGrabar = (Button) findViewById(R.id.btnGrabar);
        btnGrabar.setOnClickListener(this);
        //btnEliminar.setOnClickListener(this);
        dbInterface = new DBInterface(this);
        btnEliminar= (Button) findViewById(R.id.btnEliminar);
        nombre = (EditText)findViewById(R.id.edNombre);
        denominacion = (EditText)findViewById(R.id.etDenominacion);
        probado = (CheckBox) findViewById(R.id.cbProbado);
        tinto = (RadioButton) findViewById(R.id.rbTinto);
        blanco = (RadioButton) findViewById(R.id.rbBlanco);
        rating = (RatingBar) findViewById(R.id.rbRating);


        Intent i = getIntent();
        opcion = i.getStringExtra("Opcion");
        if (opcion.equalsIgnoreCase("modificar")){
            nombre.setText(i.getStringExtra("Nombre"));
            denominacion.setText(i.getStringExtra("Denominacion"));
            probado.setChecked(i.getBooleanExtra("Probado", true));
            String tipo = i.getStringExtra("Tipo");
            id = i.getLongExtra("Id", 0);
            if(tipo.equalsIgnoreCase("blanco")){
                blanco.setChecked(true);
            }else if(tipo.equalsIgnoreCase("tinto")){tinto.setChecked(true);}
            rating.setNumStars(i.getIntExtra("Rating", 0));

        }

        btnEliminar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
              dbInterface.borrarContacto(id);
                Toast.makeText(getBaseContext(), "Se ha borrado correctamente", Toast.LENGTH_LONG).show();
            }
        });


        if (tinto.isChecked()){
            tipo = "Tinto";
        }else if(blanco.isChecked()){
            tipo="Blanco";
        }



        dbInterface.abre();
    }



    public void onClick(View v) {

        if (opcion.equalsIgnoreCase("crear")){
        int contador = 0;
        try {
            dbInterface.insertarContacto(nombre.getText().toString(), denominacion.getText().toString(), probado.isChecked(), tipo, "Descripcion", rating.getNumStars(), "Imagen");
        }catch(Exception e){
            Toast.makeText(getBaseContext(),"ERROR : en la inserción", Toast.LENGTH_LONG).show();
            contador++;
        }
        if(contador==0){
        Toast.makeText(getBaseContext(),"Se ha insertado correctamente", Toast.LENGTH_LONG).show();}}

     //   public long modificaContacto(long id,String nombre, String denominacion, boolean probado, String tipo, String descripcion,int rating, String imagen)
        if (opcion.equalsIgnoreCase("modificar")){
            try {
                dbInterface.modificaContacto(id, nombre.getText().toString(), denominacion.getText().toString(), probado.isChecked(), tipo, "Descripcion", rating.getNumStars(), "Imagen");
            }catch(Exception e){
                Toast.makeText(getBaseContext(),"ERROR : en la modificación", Toast.LENGTH_LONG).show();
            }
            Toast.makeText(getBaseContext(),"Se ha modificado correctamente", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onStop(){
        super.onStop();
        dbInterface.cierra();

    }

}
