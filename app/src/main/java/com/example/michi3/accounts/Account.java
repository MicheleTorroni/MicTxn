package com.example.michi3.accounts;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "accounts_saved")
public class Account {
    @ColumnInfo(name = "realAccountBalance")
    private double accountBalance;
    private int icon;

    @ColumnInfo(name = "accountName")
    private String accountName;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "accounts_id")
    private int id;

    public Account(String accountName, double accountBalance, int icon){
        this.accountName = accountName;
        this.accountBalance = accountBalance;
        this.icon = icon;
    }

    public String getAccountName(){
        return this.accountName;
    }

    public double getAccountBalance(){
        return this.accountBalance;
    }

    public int getIcon(){
        return this.icon;
    }

    public void decreaseBalance(double num){
        this.accountBalance = this.accountBalance-num;
    }

    public void increaseBalance(double num){
        this.accountBalance = this.accountBalance+num;
    }

    public void setName(String newName){
        this.accountName = newName;
    }

    public void setBalance(double newBalance){
        this.accountBalance = newBalance;
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
