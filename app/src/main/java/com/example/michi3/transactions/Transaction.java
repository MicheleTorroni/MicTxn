package com.example.michi3.transactions;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.michi3.accounts.Account;
import com.example.michi3.categories.Category;

@Entity(tableName = "transactions_saved")
public class Transaction {
    @ColumnInfo(name = "transactions_title")
    private String title;

    private String account;
    private int accountIcon;
    private String category;
    private int categoryIcon;

    @ColumnInfo(name = "transaction_date")
    private String date;
    private String place;
    private double balance;
    private String imageResource;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transactions_id")
    private int id;

    public Transaction(String title, String account, int accountIcon, String category, int categoryIcon, String date, String place, double balance, String imageResource) {
        this.title = title;
        this.account = account;
        this.accountIcon = accountIcon;
        this.category = category;
        this.categoryIcon = categoryIcon;
        this.date = date;
        this.place = place;
        this.balance = balance;
        this.imageResource = imageResource;
    }

    public double getBalance() {
        return balance;
    }

    public String getImageResource() {
        return imageResource;
    }

    public String getTitle() {
        return title;
    }

    public String getAccount() {
        return account;
    }

    public int getAccountIcon() {
        return accountIcon;
    }

    public String getCategory() {
        return category;
    }

    public int getCategoryIcon() {
        return categoryIcon;
    }

    public String getDate() {
        return date;
    }

    public String getPlace() {
        return place;
    }

    public int getId() {
        return id;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setAccountIcon(int accountIcon) {
        this.accountIcon = accountIcon;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCategoryIcon(int categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setId(int id) {
        this.id = id;
    }
}
