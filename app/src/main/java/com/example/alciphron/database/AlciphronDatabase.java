package com.example.alciphron.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.alciphron.modelalciphron.AlciphronModel;

@Database(entities = {AlciphronModel.class},version = 1)
public abstract class AlciphronDatabase extends RoomDatabase {
    public abstract AlciphronDAO alciphronDAO();
}
