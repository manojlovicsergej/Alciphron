package com.example.alciphron.addalciphron;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alciphron.BuildConfig;
import com.example.alciphron.MainActivity;
import com.example.alciphron.R;
import com.example.alciphron.database.AlciphronDatabase;
import com.example.alciphron.database.InitDataSpecies;
import com.example.alciphron.modelalciphron.AlciphronModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class AddActivity extends AppCompatActivity {

    //name of add activity variables
    private TextView alciphronName;
    public static TextView addDate;
    private Button buttonDate;
    private Button buttonAddBack;
    private static final String TAG = MainActivity.class.getSimpleName();


    protected Location mLastLocation;
    private TextView geoLatitude;
    private TextView geoLongitude;
    private TextView geoAltitude;


    private Button getLocationButton;
    private TextView finderName;
    private EditText addDescription;
    private Button buttonDodaj;
    //Location
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationManager locationManager;
    //


    public static final String SHARED_PREFS = "sharedPrefs";


    ArrayList<String> alciphronList;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.banerlogo);
        actionBar.setTitle("");

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        ColorDrawable cd = new ColorDrawable(Color.parseColor("#ffffff"));
        actionBar.setBackgroundDrawable(cd);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        addDate = findViewById(R.id.addDate);
        buttonDate = findViewById(R.id.buttonDate);
        geoLatitude = findViewById(R.id.geoLatitude);
        geoLongitude = findViewById(R.id.geoLongitude);
        geoAltitude = findViewById(R.id.geoAltitude);
        getLocationButton = findViewById(R.id.getLocationButton);
        finderName = findViewById(R.id.finderName);
        addDescription = findViewById(R.id.addDescription);
        buttonDodaj = findViewById(R.id.buttonDodaj);
        addDate.setText(sdf.format(new Date()));
        buttonAddBack = findViewById(R.id.buttonAddBack);
        //Location
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        //


        //Defining database variables

        AlciphronDatabase alciphronDatabase = Room.databaseBuilder(AddActivity.this, AlciphronDatabase.class, "alciphron").allowMainThreadQueries().build();

        //


        alciphronName = findViewById(R.id.alciphronName);
        alciphronList = new ArrayList<>();
        alciphronList = initData();


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        if (ActivityCompat.checkSelfPermission(AddActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            ActivityCompat.requestPermissions(AddActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        alciphronName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Initialize dialog
                dialog = new Dialog(AddActivity.this);
                //Set a custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner);
                //Set custom height and width
                dialog.getWindow().setLayout((int) (width * 0.9), (int) (height * 0.9));
                //Set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //show dialog
                dialog.show();

                //Initialize and assign variables
                EditText editText = dialog.findViewById(R.id.edit_text);
                ListView listView = dialog.findViewById(R.id.list_view);

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AddActivity.this, android.R.layout.simple_list_item_1, alciphronList);
                //set adapter;
                listView.setAdapter(arrayAdapter);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        arrayAdapter.getFilter().filter(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        alciphronName.setText(arrayAdapter.getItem(i));
                        dialog.dismiss();
                    }
                });


            }
        });



        getLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mFusedLocationClient = LocationServices.getFusedLocationProviderClient(AddActivity.this);


                if (ActivityCompat.checkSelfPermission(AddActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    ActivityCompat.requestPermissions(AddActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }


            }
        });

        buttonAddBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buttonDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = alciphronName.getText().toString();
                String date = addDate.getText().toString();
                String description = addDescription.getText().toString();
                String longitude = geoLongitude.getText().toString();
                String latitude = geoLatitude.getText().toString();
                String altitude = geoAltitude.getText().toString();
                String finder = finderName.getText().toString();

                AlciphronModel ad = new AlciphronModel(name, date, description, longitude, latitude,altitude, finder);

                alciphronDatabase.alciphronDAO().InsertAll(ad);

                startActivity(new Intent(AddActivity.this, MainActivity.class));


            }
        });

        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(view);
            }
        });


        loadData();

    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        finderName.setText(sharedPreferences.getString("finder", ""));

    }

    @SuppressLint("MissingPermission")
    public void getLocation() {


        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30, 1, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                geoLatitude.setText(String.valueOf(location.getLatitude()));
                geoLongitude.setText(String.valueOf(location.getLongitude()));
                geoAltitude.setText(String.valueOf(location.getAltitude()));
            }
        });

    }



    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            addDate.setText(day+"/"+(month+1)+"/"+year);
        }
    }

    private ArrayList<String> initData(){
        ArrayList<String> items = new ArrayList<>();
        items = InitDataSpecies.initData();
        ArrayList<String> items1 = new ArrayList<>();
        items1 = InitDataSpecies.initDataRest();

        items.addAll(items1);
        return items;
    }

}