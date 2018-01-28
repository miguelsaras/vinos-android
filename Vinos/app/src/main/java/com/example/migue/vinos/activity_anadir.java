package com.example.migue.vinos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class activity_anadir extends AppCompatActivity implements View.OnClickListener {

    Button btnGrabar;
    DBInterface dbInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir);
        btnGrabar = (Button) findViewById(R.id.btnGrabar);
        btnGrabar.setOnClickListener(this);
        dbInterface = new DBInterface(this);
        dbInterface.abre();
    }
    public void onClick(View v) {
        dbInterface.insertarContacto("Vino1", "Denominacion1", true, "tipo1", "Descripcion1",1, "Imagen1");
    }
    @Override
    protected void onStop(){
        super.onStop();
        dbInterface.cierra();

    }

}
