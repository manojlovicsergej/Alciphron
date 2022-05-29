package com.example.alciphron.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.alciphron.modelalciphron.AlciphronModel;

import java.util.List;

@Dao
public interface AlciphronDAO {

    @Query("SELECT * FROM alciphronmodel")
    List<AlciphronModel> getAllAlciphron();

    @Insert
    void InsertAll(AlciphronModel... alciphronModels);

    @Query("DELETE FROM alciphronmodel WHERE id=:idPassed")
    void deleteAlciphron(int idPassed);


}
