package com.example.michi3.accounts;

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
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.michi3.Activity_Main;
import com.example.michi3.Activity_add;
import com.example.michi3.Activity_modify;
import com.example.michi3.Spinners.SpinnerIconAdapter;
import com.example.michi3.Spinners.SpinnerIconItem;
import com.example.michi3.R;
import com.example.michi3.Utilities.ListCreator;
import com.example.michi3.Database.MyViewModel;
import com.example.michi3.categories.Category;

import java.util.ArrayList;

public class Fragment_Accounts_Modify extends Fragment {
    private EditText accountNameEditText;
    private EditText accountBalanceEditText;
    private int icon;
    private int id;
    private ArrayList<SpinnerIconItem> listIcon = new ListCreator().getAccountIcons();
    private SpinnerIconAdapter spinnerAdapter;
    private AppCompatImageButton save;
    private AppCompatImageButton delete;
    MyViewModel myViewModel;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_accounts_modify, container, false);
        //get the viewModel
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        Toast.makeText(getContext(), "VAAAAAI", Toast.LENGTH_SHORT);
        accountNameEditText = view.findViewById(R.id.accountNameEditTextModify);
        accountBalanceEditText = view.findViewById(R.id.accountBalanceEditTextModify);
        Activity activity = getActivity();
        id = ((Activity_modify)activity).getAccountID();
        Account account = myViewModel.findAccountByID(id);
        accountNameEditText.setText(account.getAccountName());
        accountBalanceEditText.setText(String.valueOf(account.getAccountBalance()));
        icon = account.getIcon();
        save = view.findViewById(R.id.imageButtonAccountsSaveModify);
        delete = view.findViewById(R.id.imageButtonAccountsDelete);
        onClickSave(save, activity, account);
        onClickDelete(delete, activity, account);
        Spinner accountIconSpinner = view.findViewById(R.id.accountSpinnerModify);
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

    public void onClickSave(final ImageButton fab, final Activity activity, final Account account){
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Account newAccount = new Account(accountNameEditText.getText().toString(), Double.parseDouble(accountBalanceEditText.getText().toString()), icon);
                myViewModel.removeAccount(account);
                myViewModel.addAccount(newAccount);
                Intent saveIntent = new Intent(activity, Activity_Main.class);
                startActivity(saveIntent);
            }
        });
    }

    public void onClickDelete(final ImageButton delete, final Activity activity, final Account account){
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               openDeleteDialog(account, activity);
            }
        });
    }

    public void openDeleteDialog(final Account account, final Activity activity){
        AlertDialog.Builder myBuilder = new AlertDialog.Builder(getActivity());
        View myView = getLayoutInflater().inflate(R.layout.z_delete_dialog, null);
        ((TextView)myView.findViewById(R.id.deleteObject)).setText(account.getAccountName());
        myBuilder.setView(myView);
        final AlertDialog dialog = myBuilder.create();
        ((Button)myView.findViewById(R.id.deleteButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewModel.removeAccount(account);
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
