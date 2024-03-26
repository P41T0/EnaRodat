package com.example.pTort.Singletone;

import static java.lang.System.in;

import android.content.Context;
import android.util.Log;

import com.example.pTort.Entities.Monument;
import com.example.pTort.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
            this.modelMons.add(new Monument("Pont Vell1", 2019, "Pont Vell de Roda de Ter", "geo:0,0?q=41°58'48.6N, 2°18'28.9E", "pont_vell_roda",true, "01-01-1970", "Pont"));
            this.modelMons.add(new Monument("Pont Vell2", 2019, "Pont Vell de Roda de Ter", "geo:0,0?q=41°58'48.6N, 2°18'28.9E", "pont_vell_roda2",false, "01-01-1970", "Pont"));
            this.modelMons.add(new Monument("Pont Vell3", 2019, "Pont Vell de Roda de Ter", "geo:0,0?q=41°58'48.6N, 2°18'28.9E", "pont_vell_roda3", false, "01-01-1970", "Pont"));
            this.modelMons.add(new Monument("Pont Vell4", 2019, "Pont Vell de Roda de Ter", "geo:0,0?q=41°58'48.6N, 2°18'28.9E", "pont_vell_roda4", false, "01-01-1970", "Pont"));
            this.modelMons.add(new Monument("Pont Vell5", 2019, "Pont Vell de Roda de Ter", "geo:0,0?q=41°58'48.6N, 2°18'28.9E", "pont_vell_roda5", false, "01-01-1970", "Pont"));
            this.modelMons.add(new Monument("Pont Vell6", 2019, "Pont Vell de Roda de Ter", "geo:0,0?q=41°58'48.6N, 2°18'28.9E", "pont_vell_roda6",false, "01-01-1970", "Pont"));
            setFileMonuments(modelMons);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
