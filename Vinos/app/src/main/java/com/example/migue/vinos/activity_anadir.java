package com.example.migue.vinos;

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

    Button btnGrabar;
    EditText nombre;
    EditText denominacion;
    CheckBox probado;
    RadioButton tinto;
    RadioButton blanco;
    RatingBar rating;
    String tipo = "";



    DBInterface dbInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir);
        btnGrabar = (Button) findViewById(R.id.btnGrabar);
        btnGrabar.setOnClickListener(this);
        dbInterface = new DBInterface(this);

        nombre = (EditText)findViewById(R.id.edNombre);
        denominacion = (EditText)findViewById(R.id.etDenominacion);
        probado = (CheckBox) findViewById(R.id.cbProbado);
        tinto = (RadioButton) findViewById(R.id.rbTinto);
        blanco = (RadioButton) findViewById(R.id.rbBlanco);
        rating = (RatingBar) findViewById(R.id.rbRating);

        if (tinto.isChecked()){
            tipo = "Tinto";
        }else if(blanco.isChecked()){
            tipo="Blanco";
        }



        dbInterface.abre();
    }
    public void onClick(View v) {
        int contador = 0;
        try {
            dbInterface.insertarContacto(nombre.getText().toString(), denominacion.getText().toString(), probado.isChecked(), tipo, "Descripcion", rating.getNumStars(), "Imagen");
        }catch(Exception e){
            Toast.makeText(getBaseContext(),"ERROR : en la inserción", Toast.LENGTH_LONG).show();
            contador++;
        }
        if(contador==0){
        Toast.makeText(getBaseContext(),"Se ha insertado correctamente", Toast.LENGTH_LONG).show();}
    }
    @Override
    protected void onStop(){
        super.onStop();
        dbInterface.cierra();

    }

}
