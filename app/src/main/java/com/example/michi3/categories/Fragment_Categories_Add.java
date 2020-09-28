package com.example.michi3.categories;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.michi3.Database.MyViewModel;
import com.example.michi3.Spinners.SpinnerIconAdapter;
import com.example.michi3.Spinners.SpinnerIconItem;
import com.example.michi3.R;
import com.example.michi3.Utilities.ListCreator;
import com.example.michi3.accounts.Account;

import java.util.ArrayList;

public class Fragment_Categories_Add extends Fragment {

    private EditText categoryNameEditText;
    private int icon;
    private ArrayList<SpinnerIconItem> listIcon = new ListCreator().getCategoryIcons();
    private SpinnerIconAdapter spinnerAdapter;
    private AppCompatImageButton fab;
    MyViewModel myViewModel;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories_add, container, false);
        //get the viewModel
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        final Activity activity = getActivity();
        fab = view.findViewById(R.id.imageButtonCategoriesSave);
        onClickFab(fab, activity);
        categoryNameEditText = view.findViewById(R.id.categoryNameEditText);
        Spinner accountIconSpinner = view.findViewById(R.id.categorySpinner);
        spinnerAdapter = new SpinnerIconAdapter(getContext(), listIcon);
        accountIconSpinner.setAdapter(spinnerAdapter);
        accountIconSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerIconItem clickedIcon = (SpinnerIconItem) parent.getItemAtPosition(position);
                icon = clickedIcon.getIcon();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //
        return view;
    };

    public void onClickFab(final ImageButton fab, final Activity activity){
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //CAMBIO COLORE
                fab.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_save_outline_24dp)); //placeholder icon
                fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                        fab.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_save_24dp));
                        fab.setBackgroundTintList(null);
                        fab.setBackgroundResource(R.drawable.rounded_tl);
                    }
                }, 50);
                //FUNZIONAMENTO EFFETTIVO
                if(!categoryNameEditText.getText().toString().equals("")){
                    Category category = new Category(categoryNameEditText.getText().toString(), icon);
                    myViewModel.addCategory(category);
                    getActivity().finish();
                } else {
                    Toast.makeText(getContext(), "please fill the name field", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}