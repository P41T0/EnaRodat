package com.uvic.ad32023.pTort;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import com.google.zxing.Result;
import com.uvic.ad32023.pTort.Adapter.Adapter_Monuments;
import com.uvic.ad32023.pTort.Adapter.Adapter_MonumentsNoVis;
import com.uvic.ad32023.pTort.Entities.Monument;
import com.uvic.ad32023.pTort.Singletone.singletone_monuments;

import java.util.ArrayList;
import java.util.Calendar;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class PantallaPrincipal extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    public static final int PERMISSION_REQUEST_CAMERA = 1;
    private ZXingScannerView mScannerView;
    private boolean cameraOn;
    private RecyclerView Llista;
private RecyclerView LlistaNoVis;
private ConstraintLayout contentFrame;

    ArrayList<Monument> model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        this.Llista = findViewById(R.id.recyclerViewVis);
        this.LlistaNoVis = findViewById(R.id.recyclerViewNoVis);
        this.contentFrame = findViewById(R.id.constIni);
        this.mScannerView = new ZXingScannerView(this);
        this.cameraOn = false;

        model = singletone_monuments.getInstance().getModelM();
        Adapter_Monuments adapter = new Adapter_Monuments(this, R.layout.mostra_mons_vis, model);
        LinearLayoutManager ll_manager = new LinearLayoutManager(this);
        this.Llista.setLayoutManager(ll_manager);
        this.Llista.setAdapter(adapter);
        Adapter_MonumentsNoVis noAdapter = new Adapter_MonumentsNoVis(this,R.layout.mostra_mons_novis,model);
        LinearLayoutManager ll_managerNoVis = new LinearLayoutManager(this);
        this.LlistaNoVis.setLayoutManager(ll_managerNoVis);
        this.LlistaNoVis.setAdapter(noAdapter);
    }
    public void PantallaPersonal(View view){
        Intent i = new Intent(this, PauTort.class);
        startActivity(i);
    }
    @Override
    public void onResume() {
        model = singletone_monuments.getInstance().getModelM();
        Adapter_Monuments adapter = new Adapter_Monuments(this, R.layout.mostra_mons_vis, model);
        LinearLayoutManager ll_manager = new LinearLayoutManager(this);
        this.Llista.setLayoutManager(ll_manager);
        this.Llista.setAdapter(adapter);
        Adapter_MonumentsNoVis noAdapter = new Adapter_MonumentsNoVis(this,R.layout.mostra_mons_novis,model);
        LinearLayoutManager ll_managerNoVis = new LinearLayoutManager(this);
        this.LlistaNoVis.setLayoutManager(ll_managerNoVis);
        this.LlistaNoVis.setAdapter(noAdapter);
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inicial,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void handleResult(Result rawResult) {
        Log.i("AD_C11", "handleResult");
        if (rawResult!=null) {
            String textQR = rawResult.getText();


            ArrayList<Monument> monument = new ArrayList<Monument>();
            monument = singletone_monuments.getInstance().getModelM();
            for (int i = 0; i<monument.size(); i++ ) {
                Toast.makeText(this, textQR, Toast.LENGTH_SHORT).show();

                if (monument.get(i).getNom().equals(textQR)) {


                    if (!monument.get(i).isVisitat()) {

                        monument.get(i).setVisitat(true);


                        Calendar calendar = Calendar.getInstance();
                        int dia = calendar.get(Calendar.DAY_OF_MONTH);
                        int mes = calendar.get(Calendar.MONTH) + 1;
                        int any = calendar.get(Calendar.YEAR);
                        monument.get(i).setDataVisita(String.format("%d/%d/%d", dia, mes, any));

                        singletone_monuments.getInstance().setArrayMons(monument);

                        model = singletone_monuments.getInstance().getModelM();
                        Adapter_Monuments adapter = new Adapter_Monuments(this, R.layout.mostra_mons_vis, model);
                        LinearLayoutManager ll_manager = new LinearLayoutManager(this);
                        this.Llista.setLayoutManager(ll_manager);
                        this.Llista.setAdapter(adapter);
                        Adapter_MonumentsNoVis noAdapter = new Adapter_MonumentsNoVis(this,R.layout.mostra_mons_novis,model);
                        LinearLayoutManager ll_managerNoVis = new LinearLayoutManager(this);
                        this.LlistaNoVis.setLayoutManager(ll_managerNoVis);
                        this.LlistaNoVis.setAdapter(noAdapter);

                        break;
                    }
                }
            }}
        stopCamera();

    }
    public void startCamera() {
        Log.i("ProjF", "startCamera");
        contentFrame.addView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
        cameraOn = true;
    }

    public void stopCamera() {
        Log.i("ProjF", "stopCamera");
        mScannerView.stopCamera();
        contentFrame.removeView(mScannerView);
        cameraOn = false;

    }
    @Override
    public void onBackPressed() {
        Log.i("ProjF", "onBackPressed");
        if (cameraOn) {
            stopCamera();
        } else {

            super.onBackPressed();
        }
}
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.scanQR) {
            if(!cameraOn){
            if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
               startCamera();
            }else{
                this.requestPermissions(new String[]{android.Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            }
            }else{
                stopCamera();
            }
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PERMISSION_REQUEST_CAMERA){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                startCamera();
            }else{

            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}