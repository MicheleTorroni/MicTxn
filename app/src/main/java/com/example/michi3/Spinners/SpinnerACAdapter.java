package com.example.michi3.Spinners;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.michi3.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SpinnerACAdapter extends ArrayAdapter<SpinnerACItem> {

    public SpinnerACAdapter(Context context, ArrayList<SpinnerACItem> acList){
        super(context, 0, acList);
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_transaction_row, parent, false);
        }
        ImageView icon = convertView.findViewById(R.id.ac_icon_spinner);
        TextView name = convertView.findViewById(R.id.ac_name_spinner);
        SpinnerACItem currentItem = getItem(position);

        if(currentItem != null) {
            icon.setImageResource(currentItem.getIcon());
            name.setText(currentItem.getName());
        }
        return convertView;
    }
}
