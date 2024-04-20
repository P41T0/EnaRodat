package com.uvic.ad32023.pTort;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;



import com.uvic.ad32023.pTort.Entities.Monument;
import com.uvic.ad32023.pTort.Singletone.singletone_monuments;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DetallMonument extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_WRITE_STORAGE = 3;
    private TextView nomMonument;
    private ImageView imatge;
    private TextView descripcio;
    private TextView dataVisita;
    private TextView tipusMon;
    private TextView anyConstr;
    private int numMon;
    private String temp_img;
    private Context context;
    private static final int RESULT_CAPTURE_IMAGE = 1000;
    private static final int PERMISSION_REQUEST_CAMERA = 1;
    private static final int PERMISSION_REQUEST_READ_STORAGE = 2;
    private static final int RESULT_LOAD_GALLERY_IMAGE = 5;
    private int userOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Monument monument;
        nomMonument = findViewById(R.id.nom);
        imatge = findViewById(R.id.fotoM);
        descripcio = findViewById(R.id.desc);
        dataVisita = findViewById(R.id.data);
        tipusMon = findViewById(R.id.tipMon);
        anyConstr = findViewById(R.id.anyConst);
        Intent i = getIntent();
        if (i.hasExtra("monument")) {
            monument = (Monument) i.getSerializableExtra("monument");
            numMon = (int) i.getSerializableExtra("numMon");
            nomMonument.setText(monument.getNom());
            if (monument.getUriImg()==null) {
                Drawable drawable = getDrawable(monument.getImatge());
                imatge.setImageDrawable(drawable);
            }
            else{
                Uri imgUri = Uri.parse(monument.getUriImg());
                imatge.setImageURI(imgUri);
            }
            descripcio.setText(monument.getDescripcio());
            dataVisita.setText("Visitat el dia: " + monument.getDataVisita());
            tipusMon.setText("Tipus de monument: " + monument.getTipusMonument());
            anyConstr.setText("Construït l'any "+ monument.getAnyConstruccio() + "");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_visitats, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.editButton) {
            mostraNot();
            return true;
        } else if (id == R.id.deleteButton) {
            elimUbicacio();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void elimUbicacio() {
        AlertDialog.Builder deleter = new AlertDialog.Builder(this);
        deleter.setMessage("No podràs accedir a l'element fins que no tornis a escanejar el codi QR!");
        deleter.setTitle("Segur que vols eliminar la ubicació?");
        deleter.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("ProjF", "No s'ha eliminat la ubicació");
            }
        });
        deleter.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ArrayList<Monument> mon = new ArrayList<Monument>();
                mon = singletone_monuments.getInstance().getModelM();

                Log.i("ProjF", nomMonument.getText().toString());
                Log.i("ProjF", mon.get(numMon).getNom());
                if (nomMonument.getText().toString().equals(mon.get(numMon).getNom())) {
                    mon.get(numMon).setVisitat(false);
                    Log.i("ProjF", "He entrat!");
                    singletone_monuments.getInstance().setArrayMons(mon);
                    finish();
                }
            }

        });
        AlertDialog alert = deleter.create();
        alert.show();
    }

    private void mostraNot() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Vols canviar la foto de la ubicació o la descripció");
        builder.setTitle("Canviar elements?");
        builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("ProjF", "Modificacio cancelada");
            }
        });
        builder.setPositiveButton("Modificar foto", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("ProjF", "Nova foto");
                alertFoto();
            }
        });
        builder.setNegativeButton("Modificar text", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("ProjF", "Modificar text");
                Intent modifText = new Intent(DetallMonument.this, CanviDescs.class);

                modifText.putExtra("NumeroMonument", numMon);
                Log.i("ProjF", descripcio.getText().toString());
                modifText.putExtra("TextDesc", descripcio.getText().toString());
                modifText.putExtra("NomMon", nomMonument.getText().toString());
                modifText.putExtra("numMonument", numMon);
                startActivity(modifText);
            }


        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    public void onResume() {
        Monument monument;
        ArrayList<Monument> monuments = new ArrayList<Monument>();
        monuments = singletone_monuments.getInstance().getModelM();
        monument = monuments.get(numMon);


        nomMonument.setText(monument.getNom());
        if (monument.getUriImg()==null) {
            Drawable drawable = getDrawable(monument.getImatge());
            imatge.setImageDrawable(drawable);
        }
        else{
            Uri imgUri = Uri.parse(monument.getUriImg());
            imatge.setImageURI(imgUri);
        }
        descripcio.setText(monument.getDescripcio());
        dataVisita.setText("Visitat el dia: " + monument.getDataVisita());
        tipusMon.setText("Tipus de monument: " + monument.getTipusMonument());
        anyConstr.setText("Construït l'any "+ monument.getAnyConstruccio() + "");

        super.onResume();
    }

    public void obreMapa(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Monument monument;
        ArrayList<Monument> monuments = new ArrayList<Monument>();
        monuments = singletone_monuments.getInstance().getModelM();
        monument = monuments.get(numMon);
        intent.setData(Uri.parse(monument.getUbicacio()));
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);


        Toast.makeText(this, "Ubicacio copiada al portaretalls", Toast.LENGTH_SHORT).show();
    }
    public File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(getFilesDir().getPath()+"/images/");
        File directori = new File(getFilesDir().getPath(), "/images/");

        if (!directori.exists())
            directori.mkdir();
        File image = File.createTempFile(imageFileName, ".jpg", storageDir );
        return image;
    }
    private void alertFoto() {
        AlertDialog.Builder builds = new AlertDialog.Builder(this);
        builds.setMessage("Vols pujar la foto de la galeria o de la camera?");
        builds.setTitle("Galeria o càmera");
        builds.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("ProjF", "Cancelar foto");
            }
        });
        builds.setPositiveButton("Càmera", new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("ProjF", "Foto de la càmera");

                if ((checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)) {
                    Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                        temp_img = "";
                        if (photoFile != null) {
                            temp_img = photoFile.getName();
                            Uri photoURI = FileProvider.getUriForFile(DetallMonument.this,"com.mydomain.fileprovider", photoFile);
                            i.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                            startActivityForResult(i, RESULT_CAPTURE_IMAGE);
                        }
                    } catch (IOException ex) {
                        Log.e("AD_Media", ex.getMessage());
                    }

                    userOption = 0;

                } else {
                    requestPermissions(new String[]{android.Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);

                }
            }
        });
        builds.setNegativeButton("Galeria", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("ProjF", "Galeria");
                userOption = 1;
                if (Build.VERSION.SDK_INT <= 33) {//Permís per a accedir a la galeria versions anteriors a Android 13
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, RESULT_LOAD_GALLERY_IMAGE);

                    } else {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_READ_STORAGE);
                    }
                }else if (Build.VERSION.SDK_INT > 33){//Permís per a accedir a les fotos de la galeria Android 13
                    if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                        startActivityForResult(i, RESULT_LOAD_GALLERY_IMAGE);
                    } else {
                        requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_READ_STORAGE);
                    }
                }
            }
        });
        AlertDialog dialogs = builds.create();
        dialogs.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (userOption == 0) {


            if (resultCode == RESULT_OK) {
                if (!this.temp_img.equals("")) {
                    Uri uriImatge =Uri.fromFile(new File(getFilesDir().getPath()+"/images/"+this.temp_img));
                    Log.i("ProjF",uriImatge.toString());
                    imatge.setImageURI(uriImatge);
                    this.temp_img = "";

                    ArrayList<Monument> monuments = singletone_monuments.getInstance().getModelM();
                    Monument monument = monuments.get(numMon);
                    monument.setUriImg(uriImatge.toString());
                    singletone_monuments.getInstance().setArrayMons(monuments);

                }






            } else if (resultCode == RESULT_CANCELED) {

            }
        } else if (userOption == 1) {
            if (resultCode == RESULT_OK) {

                if (requestCode == RESULT_LOAD_GALLERY_IMAGE) {
                    if (resultCode == RESULT_OK) {


                        try {
                            Uri imageUri = data.getData();
                            Bitmap photo = (Bitmap) MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                            imatge.setImageBitmap(photo);




                            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                            String nomArxiuImg = "JPEG_" + timeStamp + "_.jpg";
                            File storageDir = new File(getFilesDir().getPath()+"/images/");
                            File carpetaImatges = new File(getFilesDir().getPath(), "/images/");


                                if (!carpetaImatges.exists()) {

                                    carpetaImatges.mkdirs();
                                }


                                FileOutputStream outputStream = new FileOutputStream(new File(carpetaImatges, nomArxiuImg));
                                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                                byte[] bytes = new byte[1024];
                                int len;
                                while ((len = inputStream.read(bytes)) != -1) {
                                    outputStream.write(bytes, 0, len);
                                }


                                inputStream.close();
                                outputStream.close();
                            File savedImage = new File(carpetaImatges, nomArxiuImg);


                            Uri savedImageUri = Uri.fromFile(savedImage);
                            ArrayList<Monument> monuments = singletone_monuments.getInstance().getModelM();
                            Monument monument = monuments.get(numMon);
                            monument.setUriImg(savedImageUri.toString());
                            singletone_monuments.getInstance().setArrayMons(monuments);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (resultCode == RESULT_CANCELED) {

                    } else {

                    }
                }

            }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                alertFoto();
            } else {

            }
        }else if (requestCode == PERMISSION_REQUEST_WRITE_STORAGE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }
        }

       if (requestCode == PERMISSION_REQUEST_READ_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }

        }else{

        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}