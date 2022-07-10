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

    @ColumnInfo(name="altitude")
    private String altitude;

    @ColumnInfo(name="finder")
    private String finder;

    @ColumnInfo(name="code")
    private String code;

    @ColumnInfo(name="stadium")
    private String stadijum;


    public AlciphronModel( String name, String date, String description, String longitude, String latitude , String altitude, String finder) {
        this.name = name;
        this.date = date;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
        this.finder = finder;
        this.altitude = altitude;
    }

    public AlciphronModel(){
        this.name = "";
        this.date ="19/5/2022";
        this.description =  "";
        this.longitude =  "0";
        this.latitude =  "0";
        this.finder =  "";
        this.altitude="0";
        this.stadijum = "";
        this.code= " ";
    }

    public String getStadijum() {
        return stadijum;
    }

    public void setStadijum(String stadijum) {
        this.stadijum = stadijum;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
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
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", altitude='" + altitude + '\'' +
                ", finder='" + finder + '\'' +
                ", code='" + code + '\'' +
                ", stadijum='" + stadijum + '\'' +
                '}'+"\n";
    }
}
