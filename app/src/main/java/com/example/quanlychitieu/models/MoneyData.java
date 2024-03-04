package com.example.quanlychitieu.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "moneydata", foreignKeys = {
        @ForeignKey(
                entity = DayMoney.class,
                parentColumns = {"id"},
                childColumns = {"dateId"}
        )
})
public class MoneyData {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "money")
    protected float money = 0f;
    @ColumnInfo(name = "description")
    protected String description = "";
    @ColumnInfo(name = "MoneyType")
    protected MoneyType moneyType;
    @ColumnInfo(name = "dateId")
    protected int dateId;

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MoneyType getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(MoneyType moneyType) {
        this.moneyType = moneyType;
    }

    public int getDateId() {
        return dateId;
    }

    public void setDateId(int dateId) {
        this.dateId = dateId;
    }
}
