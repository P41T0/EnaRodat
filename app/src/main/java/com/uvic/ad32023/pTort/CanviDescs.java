package com.uvic.ad32023.pTort;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



import com.uvic.ad32023.pTort.Entities.Monument;
import com.uvic.ad32023.pTort.Singletone.singletone_monuments;

import java.util.ArrayList;

public class CanviDescs extends AppCompatActivity {
    private EditText textDesc;
    private int numMonuments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        textDesc = findViewById(R.id.DescMon);
        TextView nomElement = findViewById(R.id.NomMonument);
        Intent intent = getIntent();
        if (intent.hasExtra("TextDesc")) {

            String text = intent.getStringExtra("TextDesc");
            numMonuments = (int) intent.getIntExtra("numMonument",-1);
            Log.i("ProjF", text);


            textDesc.setText(text);
            nomElement.setText(intent.getStringExtra("NomMon"));
        } else {

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
