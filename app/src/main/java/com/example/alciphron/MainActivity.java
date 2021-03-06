package com.example.alciphron;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.alciphron.adapter.AlciphronAdapter;
import com.example.alciphron.addalciphron.AddActivity;
import com.example.alciphron.database.AlciphronDatabase;
import com.example.alciphron.location.AlciphronLocation;
import com.example.alciphron.modelalciphron.AlciphronModel;
import com.example.alciphron.settings.SettingsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.opencsv.CSVWriter;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private FloatingActionButton addButton;
    private RecyclerView recyclerView;
    private AlciphronAdapter alciphronAdapter;
    public static List<AlciphronModel> alciphronModelList = new ArrayList<>();
    String geoLatitude = "";
    String geoLongitude ="";
    String geoAltitude="";
    private static final String FILE_NAME = "alciphronBaza.txt";
    private static final String FILE_NAME_CSV = "alciphronBazaCSV.csv";
    private static final String FILE_NAME_XLS = "alciphronBazaXLS.xls";

    private LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},1000);
        }

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.banerlogo);
        actionBar.setTitle("");

        ColorDrawable cd = new ColorDrawable(Color.parseColor("#ffffff"));
        actionBar.setBackgroundDrawable(cd);

        addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddActivity.class));
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //Loading data from database to List

        AlciphronDatabase ad = Room.databaseBuilder(getApplicationContext(),AlciphronDatabase.class,"alciphron").allowMainThreadQueries().build();
        alciphronModelList = ad.alciphronDAO().getAllAlciphron();
        //

        alciphronAdapter = new AlciphronAdapter(alciphronModelList, new AlciphronAdapter.ItemClickListener() {
            @Override
            public void onItemClickListener(AlciphronModel alciphronModel) {
                Intent intent = new Intent(MainActivity.this, AlciphronLocation.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("alciphronObject",alciphronModel);
                intent.putExtras(bundle);

                startActivity(intent);

            }
        });
        recyclerView.setAdapter(alciphronAdapter);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.txtFileItem){
            exportToTxtFile();
        }
        if(id == R.id.csvFileItem){
            exportToCSVFile();
        }
        if(id == R.id.xlsFileItem){
            exportToXLSFile();
        }
        if(id == R.id.obrisiItem){
            deleteAllAlciphron();
        }
        if(id == R.id.settingsItem){
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
        }



        return super.onOptionsItemSelected(item);
    }

    private void deleteAllAlciphron(){


        AlciphronDatabase ad = Room.databaseBuilder(getApplicationContext(),AlciphronDatabase.class,"alciphron").allowMainThreadQueries().build();
        ad.alciphronDAO().deleteAllAlciphron();

        ad.close();

        alciphronModelList.clear();
        alciphronAdapter.notifyDataSetChanged();

        Toast.makeText(getApplicationContext(),"Uspesno obrisano !",Toast.LENGTH_SHORT).show();

    }

    private void exportToCSVFile(){

        File fileDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File file  = new File(fileDir,FILE_NAME_CSV);

        CSVWriter writer;
        FileWriter fileWriter;
        // File exist
        try {
            if (file.exists() && !file.isDirectory()) {
                fileWriter = new FileWriter(file, false);
                writer = new CSVWriter(fileWriter);
            } else {
                writer = new CSVWriter(new FileWriter(file.getPath()));
            }
            String dataFirstRow[] = {"Naziv vrste","Kod","Stadijum","Datum pronalaska","Geo.sirina","Geo.duzina","Nadmorska visina","Pronalazac","Napomena"};
            writer.writeNext(dataFirstRow);

            for(AlciphronModel a : alciphronModelList) {
                String[] data = {a.getName(),a.getCode(),a.getStadijum(),a.getDate(),a.getEasting(),a.getNorthing(),a.getAltitude(),a.getFinder(),a.getDescription()};
                writer.writeNext(data);
            }
            writer.close();
        }catch (IOException e){
            Toast.makeText(getApplicationContext(),"Neuspesno !" , Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(getApplicationContext(),"Uspesno sacuvan CSV file !" , Toast.LENGTH_SHORT).show();
    }

    private void exportToTxtFile(){

        File fileDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File file  = new File(fileDir,FILE_NAME);

        String text = "";

        try {
            FileOutputStream fos = new FileOutputStream(file);
            for(AlciphronModel a : alciphronModelList){
                text = a.getName() + "|" + a.getCode() + "|"+ a.getStadijum() +"|" + a.getDate() + "|" + a.getEasting() + "|" + a.getNorthing() + "|"+ a.getAltitude() +"|"+ a.getFinder() + "|" + a.getDescription() + "\n";
                fos.write(text.getBytes());
            }


            fos.close();
            Toast.makeText(getApplicationContext(),"Uspesno sacuvan TXT file !" , Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void exportToXLSFile(){

        File fileDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File file  = new File(fileDir,FILE_NAME_XLS);

        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet hssfSheet = hssfWorkbook.createSheet("Alciphron");


        HSSFRow hssfRow = hssfSheet.createRow(0);
        HSSFCell hssfCell = hssfRow.createCell(0);

        for(int i = 0 ; i < alciphronModelList.size();i++){
            hssfRow = hssfSheet.createRow(i);
            for(int j = 0 ; j < 9;j++){
                hssfCell = hssfRow.createCell(j);

                if(j == 0){
                    hssfCell.setCellValue(alciphronModelList.get(i).getName());
                }
                if(j == 1){
                    hssfCell.setCellValue(alciphronModelList.get(i).getCode());
                }
                if(j == 2){
                    hssfCell.setCellValue(alciphronModelList.get(i).getStadijum());
                }
                if(j == 3){
                    hssfCell.setCellValue(alciphronModelList.get(i).getDate());
                }
                if(j == 4){
                    hssfCell.setCellValue(alciphronModelList.get(i).getEasting());
                }
                if(j == 5){
                    hssfCell.setCellValue(alciphronModelList.get(i).getNorthing());
                }
                if(j == 6){
                    hssfCell.setCellValue(alciphronModelList.get(i).getAltitude());
                }
                if(j == 7){
                    hssfCell.setCellValue(alciphronModelList.get(i).getFinder());
                }
                if(j == 8){
                    hssfCell.setCellValue(alciphronModelList.get(i).getDescription());
                }


            }
        }

        try {
            if (!file.exists()){
                file.createNewFile();
            }

            FileOutputStream fileOutputStream= new FileOutputStream(file);
            hssfWorkbook.write(fileOutputStream);

            if (fileOutputStream!=null){
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText(getApplicationContext(),"Uspesno sacuvan XLS file !",Toast.LENGTH_LONG).show();


    }


    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case 1000:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(MainActivity.this,"Permission granted !",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this,"Permission not granted !",Toast.LENGTH_SHORT).show();
                }
        }

    }

    @SuppressLint("MissingPermission")
    public void getLocation() {


        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30, 1, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                geoLatitude = String.valueOf(location.getLatitude());
                geoLongitude = String.valueOf(location.getLongitude());
                geoAltitude = String.valueOf((int)location.getAltitude());
            }
        });

    }

}