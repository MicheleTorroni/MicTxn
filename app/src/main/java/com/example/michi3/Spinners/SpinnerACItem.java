package com.example.michi3.Spinners;

public class SpinnerACItem {
    private String name;
    private int icon;
    private int id;

    public SpinnerACItem(String name, int icon, int id) {
        this.name = name;
        this.icon = icon;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }

    public int getId(){
        return this.id;
    }
}