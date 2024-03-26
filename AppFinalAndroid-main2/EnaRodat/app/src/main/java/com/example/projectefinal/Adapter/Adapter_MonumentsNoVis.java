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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pTort.Entities.Monument;
import com.example.pTort.R;

import java.io.IOException;
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
                    // Bind data to views
                    vh.tv_nom.setText(item.getNom());
                    String imagePath = item.getImatge();
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
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(item.getUbicacio()));
                            intent.setPackage("com.google.android.apps.maps");
                            context.startActivity(intent);

                            // Notify the user that the text has been copied, you can customize this part
                            Toast.makeText(context, "Ubicacio copiada al portaretalls", Toast.LENGTH_SHORT).show();
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
            this.tv_nom = (TextView)view.findViewById(R.id.tv_name2);

            this.iv_image = (ImageView)view.findViewById(R.id.imatge);
            this.layout = (ConstraintLayout) view.findViewById(R.id.layout);
        }
    }
}
