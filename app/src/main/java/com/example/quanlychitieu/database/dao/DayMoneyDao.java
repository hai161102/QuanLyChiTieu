package com.example.quanlychitieu.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.quanlychitieu.models.DayMoney;

import java.util.List;

@Dao
public interface DayMoneyDao {
    @Query("select * from daymoney")
    List<DayMoney> all();

    @Query("select * from daymoney where id =:id")
    DayMoney get(int id);

    @Insert
    void insert(DayMoney dayMoney);

    @Update
    void update(DayMoney dayMoney);

    @Delete
    void delete(DayMoney dayMoney);
}
