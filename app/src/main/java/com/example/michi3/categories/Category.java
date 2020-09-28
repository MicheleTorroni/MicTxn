package com.example.michi3.categories;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories_saved")
public class Category {
    @ColumnInfo(name = "realCategoryBalance")
    private double categoryBalance = 0;
    private int icon;

    @ColumnInfo(name = "categoryName")
    private String categoryName;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "categories_id")
    private int id;

    public Category(String categoryName, int icon){
        this.categoryName = categoryName;
        this.categoryBalance = 0;
        this.icon = icon;
    }

    public void setCategoryBalance(double num){
        this.categoryBalance =  num;
    }

    public String getCategoryName(){
        return this.categoryName;
    }

    public double getCategoryBalance(){
        return this.categoryBalance;
    }

    public int getIcon(){
        return this.icon;
    }

    public void increaseBalance(double num){
        this.categoryBalance = this.categoryBalance+num;
    }

    public void decreaseBalance(double num){
        this.categoryBalance = this.categoryBalance-num;
    }

    public void setName(String newName){
        this.categoryName = newName;
    }

    public void setIcon(int newIcon){
        this.icon = newIcon;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
