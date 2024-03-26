package com.example.pTort;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pTort.Entities.Monument;
import com.example.pTort.Singletone.singletone_monuments;

import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity {
    private EditText textDesc;
    private TextView nomElement;
    private int numMonuments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        // Obtén la referencia al EditText por su ID
        textDesc = findViewById(R.id.DescMon);
        nomElement = findViewById(R.id.NomMonument);
        // Verifica si hay datos extras en el intent
        Intent intent = getIntent();
        if (intent.hasExtra("TextDesc")) {
            // Obtiene el texto del intent
            String text = intent.getStringExtra("TextDesc");
            numMonuments = (int) intent.getIntExtra("numMonument",-1);
            // Log para verificar que el texto se ha recibido correctamente
            Log.i("ProjF", text);

            // Establece el texto en el EditText
            textDesc.setText(text);
            nomElement.setText(intent.getStringExtra("NomMon"));
        } else {
            // Si no hay datos extras, establece un texto predeterminado
            textDesc.setText("Bona tarda a tots");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ArrayList<Monument> monuments = new ArrayList<Monument>();
        monuments = singletone_monuments.getInstance().getModelM();
        Log.i("ProjF",numMonuments+"");
        if(numMonuments != -1){
        monuments.get(numMonuments).setDescripcio(textDesc.getText().toString().trim());
        singletone_monuments.getInstance().setArrayMons(monuments);}
    }
}
