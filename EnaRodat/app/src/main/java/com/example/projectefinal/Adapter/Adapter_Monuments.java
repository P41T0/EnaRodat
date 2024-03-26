package com.example.pTort.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pTort.Entities.Monument;
import com.example.pTort.MainActivity;
import com.example.pTort.MainActivity2;
import com.example.pTort.R;

import java.io.IOException;
import java.util.ArrayList;

public class Adapter_Monuments extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private int resourceId;
    private ArrayList<Monument> model;

    public Adapter_Monuments(ArrayList<Monument> model) {
        this.model = model;
        this.resourceId = R.layout.mostra_mons_vis;
    }

    public Adapter_Monuments(Context context, int resourceId, ArrayList<Monument> model) {
        this.context = context;
        this.resourceId = resourceId;
        this.model = model;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(this.resourceId, parent, false);
        Adapter_Monuments.ViewHolder vh = new Adapter_Monuments.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Adapter_Monuments.ViewHolder vh = (ViewHolder) holder;

        int visitedPosition = 0;
        for (int i = 0; i < this.model.size(); i++) {
            Monument item = this.model.get(i);
            if (item.isVisitat()) {
                if (visitedPosition == position) {
                    // Bind data to views
                    int maxCaracters = 25;
                    if (item.getDescripcio().length()>maxCaracters){
                        String textRetallat = item.getDescripcio().substring(0, maxCaracters);
                        vh.tv_cognom.setText(textRetallat+"...");
                    }else {
                        vh.tv_cognom.setText(item.getDescripcio());
                    }vh.tv_nom.setText(item.getNom());


                    int numMon = i;

                    Uri imageUri= Uri.parse((item.getImatge()));
                    Bitmap photo = null;

                    try {
                        photo = (Bitmap) MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
                        vh.iv_image.setImageBitmap(photo);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    // OnItemClick
                    vh.layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, MainActivity2.class);
                            intent.putExtra("monument",item);
                            intent.putExtra("numMon",numMon);
                            context.startActivity(intent);
                        }
                    });

                    break; // Exit the loop once the visited item is found
                }
                visitedPosition++;
            }
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        for (Monument monument : this.model) {
            if (monument.isVisitat()) {
                count++;
            }
        }
        return count;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tv_nom;
        final TextView tv_cognom;
        final ImageView iv_image;
        final ConstraintLayout layout;

        public ViewHolder(View view) {
            super(view);
            this.tv_nom = (TextView)view.findViewById(R.id.tv_name);
            this.tv_cognom = (TextView)view.findViewById(R.id.tv_lastnames);
            this.iv_image = (ImageView)view.findViewById(R.id.Image);
            this.layout = (ConstraintLayout) view.findViewById(R.id.layout);
        }
    }
}