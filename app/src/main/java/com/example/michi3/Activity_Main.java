package com.example.michi3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.michi3.accounts.Fragment_Accounts;
import com.example.michi3.accounts.Fragment_Accounts_Modify;
import com.example.michi3.categories.Fragment_Categories;
import com.example.michi3.categories.Fragment_Categories_Modify;
import com.example.michi3.transactions.Fragment_Transactions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class Activity_Main extends AppCompatActivity {
    private static final String TAG = Activity_Main.class.getSimpleName();
    FragmentManager fManager = getSupportFragmentManager();
    Fragment fragment = new Fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bnView = findViewById(R.id.bottomNavigationView);
        bnView.setOnNavigationItemSelectedListener(bnListener);
        fManager.beginTransaction().replace(R.id.fragment_container, new Fragment_Accounts()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bnListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch(item.getItemId()){
                case R.id.accounts:
                    fragment = new Fragment_Accounts();
                    break;
                case R.id.categories:
                    fragment = new Fragment_Categories();
                    break;
                case R.id.transactions:
                    fManager = getSupportFragmentManager();
                    fragment = new Fragment_Transactions();
                    break;
            }

            if(fragment != null){
                fManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            } else {
                Log.e(TAG, "error in creating fragment");
            }

            return true;
        }
    };

}

