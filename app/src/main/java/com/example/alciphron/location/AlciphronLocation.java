package com.example.alciphron.location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.alciphron.MainActivity;
import com.example.alciphron.R;
import com.example.alciphron.database.AlciphronDatabase;
import com.example.alciphron.modelalciphron.AlciphronModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AlciphronLocation extends AppCompatActivity implements OnMapReadyCallback {

    private ImageButton buttonBack;
    private ImageButton buttonDelete;
    private MapView mapView;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    private AlciphronModel alciphronObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alciphron_location);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.banerlogo);
        actionBar.setTitle("");

        ColorDrawable cd = new ColorDrawable(Color.parseColor("#ffffff"));
        actionBar.setBackgroundDrawable(cd);

        Bundle mapViewBundle = null;

        if(savedInstanceState!=null){
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(mapViewBundle);

        mapView.getMapAsync(this);


        alciphronObject = new AlciphronModel();

        alciphronObject = (AlciphronModel) getIntent().getSerializableExtra("alciphronObject");

        Log.d("s", String.valueOf(alciphronObject));
        buttonBack = findViewById(R.id.buttonBack);
        buttonDelete = findViewById(R.id.buttonDelete);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlciphronDatabase ad = Room.databaseBuilder(AlciphronLocation.this,AlciphronDatabase.class,"alciphron").allowMainThreadQueries().build();
                ad.alciphronDAO().deleteAlciphron(alciphronObject.getId());

                Toast.makeText(getApplicationContext(),"Uspesno obrisano !",Toast.LENGTH_SHORT).show();

                startActivity(new Intent(AlciphronLocation.this, MainActivity.class));
            }
        });

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        LatLng g = null;
        try{
            g = new LatLng(Double.parseDouble(alciphronObject.getLatitude().trim()),Double.parseDouble(alciphronObject.getLongitude().trim()));

            googleMap.addMarker(new MarkerOptions().position(g).title(alciphronObject.getName()));

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(g,15));
            googleMap.animateCamera(CameraUpdateFactory.zoomIn());
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
        }catch (Exception e){
            g = new LatLng(45.42345,58.5483);

            googleMap.addMarker(new MarkerOptions().position(g).title("Greska !"));

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(g,15));
            googleMap.animateCamera(CameraUpdateFactory.zoomIn());
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
        }


    }
    @Override
    public void onStart(){
        super.onStart();
        mapView.onStart();
    }
    @Override
    public void onPause(){
        super.onPause();
        mapView.onPause();
    }
    @Override
    public void onStop(){
        super.onStop();
        mapView.onStop();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory(){
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if(mapViewBundle==null){
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY,mapViewBundle);
        }
        mapView.onSaveInstanceState(mapViewBundle);

    }
}