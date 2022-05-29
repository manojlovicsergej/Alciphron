package com.example.alciphron.modelalciphron;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity
public class AlciphronModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="name")
    private String name;

    @ColumnInfo(name="date")
    private String date;

    @ColumnInfo(name="description")
    private String description;

    @ColumnInfo(name="longitude")
    private String longitude;

    @ColumnInfo(name="latitude")
    private String latitude;

    @ColumnInfo(name="finder")
    private String finder;


    public AlciphronModel( String name, String date, String description, String longitude, String latitude, String finder) {
        this.name = name;
        this.date = date;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
        this.finder = finder;
    }

    public AlciphronModel(){
        this.name = "";
        this.date ="19/5/2022";
        this.description =  "";
        this.longitude =  "";
        this.latitude =  "";
        this.finder =  "";

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getFinder() {
        return finder;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setFinder(String finder) {
        this.finder = finder;
    }

    @Override
    public String toString() {
        return "AlciphronModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", finder='" + finder + '\'' +
                '}';
    }
}
