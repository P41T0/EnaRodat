package com.uvic.ad32023.pTort.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;



import com.uvic.ad32023.pTort.Entities.Monument;
import com.uvic.ad32023.pTort.R;

import java.util.ArrayList;

public class Adapter_MonumentsNoVis extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private int resourceId;
    private ArrayList<Monument> model;


    public Adapter_MonumentsNoVis(ArrayList<Monument> model) {
        this.model = model;
        this.resourceId = R.layout.mostra_mons_vis;
    }
    public Adapter_MonumentsNoVis(Context context, int resourceId, ArrayList<Monument> model) {
        this.context = context;
        this.resourceId = resourceId;
        this.model = model;

    }

    @NonNull

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(this.resourceId, parent, false);
        Adapter_MonumentsNoVis.ViewHolder vh = new Adapter_MonumentsNoVis.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Adapter_MonumentsNoVis.ViewHolder vh = (Adapter_MonumentsNoVis.ViewHolder) holder;

        int visitedPosition = 0;
        for (int i = 0; i < this.model.size(); i++) {
            Monument item = this.model.get(i);
            if (!item.isVisitat()) {
                if (visitedPosition == position) {


                    int maxCarNom = 35;
                    if (item.getNom().length()>maxCarNom){
                        String textRetallat = item.getNom().substring(0, maxCarNom);
                        vh.tv_nom.setText(textRetallat+"...");
                    }
                    else {
                        vh.tv_nom.setText(item.getNom());
                    }
                    if(item.getUriImg()==null) {
                        Drawable drawable = context.getDrawable(item.getImatge());
                        vh.iv_image.setImageDrawable(drawable);
                    }else {
                        Uri uriImg = Uri.parse(item.getUriImg());
                        vh.iv_image.setImageURI(uriImg);
                    }

                    vh.layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(item.getUbicacio()));
                            intent.setPackage("com.google.android.apps.maps");
                            context.startActivity(intent);


                            Toast.makeText(context, "Ubicacio copiada al portaretalls", Toast.LENGTH_SHORT).show();
                        }
                    });

                    break;
                }
                visitedPosition++;
            }
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        for (Monument monument : this.model) {
            if (!monument.isVisitat()) {
                count++;
            }
        }
        return count;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tv_nom;

        final ImageView iv_image;
        final ConstraintLayout layout;

        public ViewHolder(View view) {
            super(view);
            this.tv_nom = (TextView)view.findViewById(R.id.nom_mon_novis);

            this.iv_image = (ImageView)view.findViewById(R.id.imatge);
            this.layout = (ConstraintLayout) view.findViewById(R.id.layout);
        }
    }
}
