package com.example.michi3.categories;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.michi3.Activity_Main;
import com.example.michi3.Activity_modify;
import com.example.michi3.Database.MyViewModel;
import com.example.michi3.Spinners.SpinnerIconAdapter;
import com.example.michi3.Spinners.SpinnerIconItem;
import com.example.michi3.R;
import com.example.michi3.Utilities.ListCreator;
import com.example.michi3.accounts.Account;

import java.util.ArrayList;

public class Fragment_Categories_Modify extends Fragment {
    private EditText categoryNameEditText;
    private Double oldBalance;
    private int icon;
    private int id;
    private ArrayList<SpinnerIconItem> listIcon = new ListCreator().getCategoryIcons();
    private SpinnerIconAdapter spinnerAdapter;
    private AppCompatImageButton save;
    private AppCompatImageButton delete;

    MyViewModel myViewModel;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories_modify, container, false);
        //get the viewModel
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        categoryNameEditText = view.findViewById(R.id.categoryNameEditTextModify);
        Activity activity = getActivity();
        id = ((Activity_modify)activity).getCategoryID();
        Category category = myViewModel.findCategoryByID(id);
        categoryNameEditText.setText(category.getCategoryName());
        icon = category.getIcon();
        oldBalance = category.getCategoryBalance();
        save = view.findViewById(R.id.imageButtonCategoriesSaveModify);
        delete = view.findViewById(R.id.imageButtonCategoriesDelete);
        onClickSave(save, activity, category);
        onClickDelete(delete, activity, category);
        Spinner accountIconSpinner = view.findViewById(R.id.categorySpinnerModify);
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

        return view;
    }

    public void onClickSave(final ImageButton fab, final Activity activity, final Category category){
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Category newCategory = new Category(categoryNameEditText.getText().toString(), icon);
                newCategory.setCategoryBalance(oldBalance);
                myViewModel.removeCategory(category);
                myViewModel.addCategory(newCategory);
                activity.finish();
            }
        });
    }

    public void onClickDelete(final ImageButton delete, final Activity activity, final Category category){
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openDeleteDialog(category, activity);
            }
        });
    }

    public void openDeleteDialog(final Category category,final Activity activity){
        AlertDialog.Builder myBuilder = new AlertDialog.Builder(getActivity());
        View myView = getLayoutInflater().inflate(R.layout.z_delete_dialog, null);
        ((TextView)myView.findViewById(R.id.deleteObject)).setText(category.getCategoryName());
        myBuilder.setView(myView);
        final AlertDialog dialog = myBuilder.create();
        ((Button)myView.findViewById(R.id.deleteButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewModel.removeCategory(category);
                activity.finish();
                dialog.dismiss();
            }
        });
        ((Button)myView.findViewById(R.id.keepButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
