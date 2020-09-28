package com.example.michi3;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.michi3.accounts.Fragment_Accounts_Modify;
import com.example.michi3.categories.Fragment_Categories_Modify;

import java.util.Objects;

public class Activity_modify extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        Fragment replaceFragment = new Fragment(); //placeholder

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container_modify) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            //firstFragment.setArguments(getIntent().getExtras());
            switch(Objects.requireNonNull(getIntent().getStringExtra("fragment"))){
                case "modifyAccounts":
                    replaceFragment = new Fragment_Accounts_Modify();
                    break;
                case "modifyCategories":
                    replaceFragment = new Fragment_Categories_Modify();
                    break;
                default:
                    Toast toast2 = Toast.makeText(this,"c'è stato un errore", Toast.LENGTH_SHORT);
                    toast2.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
                    toast2.show();
                    break;
            }

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_modify, replaceFragment).commit();
        }
    }

    public int getAccountID(){
        return getIntent().getIntExtra("accountID", 0);
    }

    public int getCategoryID(){
        return getIntent().getIntExtra("categoryID", 0);
    }

}
