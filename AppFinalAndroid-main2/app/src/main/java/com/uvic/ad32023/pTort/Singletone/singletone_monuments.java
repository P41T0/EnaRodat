package com.uvic.ad32023.pTort.Singletone;

import android.content.Context;
import android.util.Log;



import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uvic.ad32023.pTort.Entities.Monument;
import com.uvic.ad32023.pTort.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class singletone_monuments {
    private ArrayList model;
    private ArrayList<Monument>modelMons;
    private Context context;
    private static final String MONS_FILE = "monument.json";



    private static class SingletonInstance {
        private static singletone_monuments INSTANCE = new singletone_monuments();
    }

    public static singletone_monuments getInstance() {
        return SingletonInstance.INSTANCE;
    }


    private singletone_monuments() {
        //Constructor Singleton

 }

    public ArrayList getModel() {
        return model;
    }

    public void setModel(ArrayList model) {
        this.model = model;
    }

    public ArrayList<Monument> getModelM() {
        Log.i("ProjF","He retornat modelmons");

        return modelMons;
    }

    public void setArrayMons(ArrayList<Monument> arrayMons) {
        this.modelMons = arrayMons;
        setFileMonuments(arrayMons);
    }

        public void setContext(Context context) {
            this.context = context;
            getFileMonuments();
        }





    private void setFileMonuments(ArrayList<Monument> list)
    {
        String data = new Gson().toJson(list);
        FileOutputStream fOut = null;
        try {
            fOut = context.openFileOutput(MONS_FILE, Context.MODE_PRIVATE);
            fOut.write(data.getBytes());
            fOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void getFileMonuments() {
        FileInputStream fin = null;
        String receiveString;
        try {
            fin = context.openFileInput(MONS_FILE);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fin, "UTF-8"), 1024);
            StringBuilder stringBuilder = new StringBuilder();
            while ((receiveString = bufferedReader.readLine()) != null) {
                stringBuilder.append(receiveString);
            }
            fin.close();
            String data = new String(String.valueOf(stringBuilder).getBytes(), Charset.forName("UTF-8"));
            // Ja podem tractar el data
            Type typelist = new TypeToken<List<Monument>>(){}.getType();
            Gson gson = new Gson();
            this.modelMons = gson.fromJson(data,typelist);

        } catch (FileNotFoundException e) {
            Log.i("ProjF","No existeix el fitxer");
            this.modelMons = new ArrayList<Monument>();
            this.modelMons.add(new Monument("Pont Vell", 1876, "Pont Vell de Roda de Ter. 'De pobles com Roda, no n'hi ha cap més d'igual, un pont sobre l'altre, i una esglèsia a cada cap'", "geo:0,0?q=41°58'48.6N, 2°18'28.9E", R.drawable.pont_vell_roda,true, "01-01-1970", "Pont"));
            this.modelMons.add(new Monument("Jaciment arqueològic de l'Esquerda", -700, "Jaciment arqueològic ubicat a masies de Roda, en un dels meandres del Riu Ter", "geo:0,0?q=41°58'27.5N, 2°18'44.8E", R.drawable.esquerda,false, "01-01-1970", "Jaciment arqueològic"));
            this.modelMons.add(new Monument("Escola Mare de Déu del Sol del Pont", 1935, "Escola pública de Roda de Ter. En aquesta escola hi va estudia el poata Miquel Martí i Pol durant la 2a República/Guerra Civil Espanyola", "geo:0,0?q=41°58'50.4N 2°18'21.2E", R.drawable.sol_del_pont, false, "01-01-1970", "Escola"));
            this.modelMons.add(new Monument("Fundació Miquel Martí i Pol", 2019, "Casa/Museu de Miquel Martí i Pol. En aquesta casa hi va viure Miquel Martí i Pol quan treballava a La Blava", "geo:0,0?q=41°58'38.4N 2°18'24.9E", R.drawable.marti_i_pol, false, "01-01-1970", "Museu"));
            this.modelMons.add(new Monument("Museu Arqueològic de l'Esquerda", 1977, "Museu on es mostren algunes de les troballes que s'han realitzat al jaciment arqueològic de l'esquerda. Anteriorment era la caserna de la Guardia Civil a Roda de Ter", "geo:0,0?q=41°58'37.1N 2°18'46.0E", R.drawable.museu_esquerda, false, "01-01-1970", "Museu"));
            this.modelMons.add(new Monument("Fàbrica Tecla Sala 'La Blava'", 1925, "Antiga fàbrica, actualment abandonada, ubicada en un dels laterals del riu Ter al seu pas per Roda de Ter", "geo:0,0?q=41°58'39.3N 2°18'28.7E", R.drawable.la_blava,false, "01-01-1970", "Fàbrica tèxtil"));
            setFileMonuments(modelMons);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
