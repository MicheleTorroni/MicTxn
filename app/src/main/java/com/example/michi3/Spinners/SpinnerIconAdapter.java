package com.example.michi3.Spinners;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.michi3.R;

import java.util.ArrayList;

public class SpinnerIconAdapter extends ArrayAdapter<SpinnerIconItem> {

    public SpinnerIconAdapter(Context context, ArrayList<SpinnerIconItem> accountIconList){
        super(context, 0, accountIconList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_icon_row, parent, false);
        }
            ImageView icon = convertView.findViewById(R.id.icon_spinner);
            SpinnerIconItem currentItem = getItem(position);

            if(currentItem != null) {
                icon.setImageResource(currentItem.getIcon());
            }
            return convertView;
        }
    }
