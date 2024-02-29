package com.example.quanlychitieu.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.quanlychitieu.models.MoneyData;

import java.util.List;

@Dao
public interface MoneyDataDao {
    @Query("select * from moneydata")
    List<MoneyData> all();
    @Query("select * from moneydata where id = :id")
    MoneyData get(int id);
    @Query("select * from moneydata where dateId = :id")
    List<MoneyData> getByDate(int id);
    @Insert
    void insert(MoneyData moneyData);
    @Update
    void update(MoneyData moneyData);
    @Delete
    void delete(MoneyData moneyData);
}
