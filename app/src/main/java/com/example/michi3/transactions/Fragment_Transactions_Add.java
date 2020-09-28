package com.example.michi3.transactions;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.michi3.Activity_add;
import com.example.michi3.Database.MyViewModel;
import com.example.michi3.R;
import com.example.michi3.Spinners.SpinnerACAdapter;
import com.example.michi3.Spinners.SpinnerACItem;
import com.example.michi3.Utilities.ListCreator;
import com.example.michi3.accounts.Account;
import com.example.michi3.categories.Category;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.michi3.Utilities.PhotoUtility.REQUEST_IMAGE_CAPTURE;
import static com.example.michi3.Utilities.PhotoUtility.RESULT_LOAD_IMG;

public class Fragment_Transactions_Add extends Fragment {

    private EditText titleEditText;
    private EditText balanceEditText;
    private String accountName;
    private int accountIcon;
    private int accountID;
    private String categoryName;
    private int categoryIcon;
    private int categoryID;
    private EditText place;
    private String date;
    private ImageView yourImage;
    private Spinner accountSpinner;
    private ArrayList<SpinnerACItem> listAccounts;
    private Spinner categorySpinner;
    private ArrayList<SpinnerACItem> listCategories;
    private SpinnerACAdapter accountSpinnerAdapter;
    private SpinnerACAdapter categorySpinnerAdapter;
    private AppCompatImageButton fab;
    private AppCompatImageButton photoButton;
    private MyViewModel myViewModel;
    private ListCreator listCreator = new ListCreator();
    private ImageButton galleryButton;

    Bitmap bitmap;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transactions_add, container, false);
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        final Activity activity = getActivity();
        //Toast.makeText(getContext(), String.valueOf(Calendar.getInstance().get(Calendar.YEAR)), Toast.LENGTH_SHORT).show();
        bindGraphic(view);
        onClickFab(fab, activity);
        onClickTakePhoto(photoButton, activity);
        onClickPickPhoto(galleryButton, activity);
        updateAccountSpinner();
        updateCategorySpinner();
        accountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerACItem item = (SpinnerACItem) parent.getItemAtPosition(position);
                accountName = item.getName();
                accountIcon = item.getIcon();
                accountID = item.getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerACItem item = (SpinnerACItem) parent.getItemAtPosition(position);
                categoryName = item.getName();
                categoryIcon = item.getIcon();
                categoryID = item.getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
                Uri imageUri = ((Activity_add)activity).getCurrentPhotoUri();
                String imageUriString;
                if (imageUri == null){
                    //if the image was not taken, i save a drawable
                    imageUriString = "ic_launcher_foreground";
                } else {
                    imageUriString = imageUri.toString();
                }
                date = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH))+"/"
                        +String.valueOf(Calendar.getInstance().get(Calendar.MONTH)+1)+"/"
                        +String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

                try {
                    Transaction transaction = new Transaction(titleEditText.getText().toString(),
                                                                accountName,
                                                                accountIcon,
                                                                categoryName,
                                                                categoryIcon,
                                                                date,
                                                                place.getText().toString(),
                                                                Double.valueOf(balanceEditText.getText().toString()),
                                                                imageUriString);


                    myViewModel.decreaseBalanceAccount(accountID, Double.valueOf(balanceEditText.getText().toString()));
                    myViewModel.increaseBalanceCategory(categoryID, Double.valueOf(balanceEditText.getText().toString()));

                    myViewModel.addTransaction(transaction);
                    getActivity().finish();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "please fill all the fields", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void updateAccountSpinner(){
        if(myViewModel.getAccounts().getValue() != null) {
            accountSpinnerAdapter = new SpinnerACAdapter(getContext(), listCreator.getAccounts(myViewModel.getAccounts().getValue()));
            accountSpinner.setAdapter(accountSpinnerAdapter);
        }
        myViewModel.getAccounts().observe(getViewLifecycleOwner(), new Observer<List<Account>>() {
            @Override
            public void onChanged(List<Account> accounts) {
                List<Account> list = new ArrayList<>();
                for (Account account : accounts) {
                    list.add(account);
                }
                accountSpinnerAdapter = new SpinnerACAdapter(getContext(), listCreator.getAccounts(myViewModel.getAccounts().getValue()));
                accountSpinner.setAdapter(accountSpinnerAdapter);
            }
        });
    }

    private void updateCategorySpinner(){
        if(myViewModel.getCategories().getValue() != null) {
            categorySpinnerAdapter = new SpinnerACAdapter(getContext(), listCreator.getCategories(myViewModel.getCategories().getValue()));
            categorySpinner.setAdapter(categorySpinnerAdapter);
        }
        myViewModel.getCategories().observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                List<Category> list = new ArrayList<>();
                for (Category category : categories) {
                    list.add(category);
                }
                categorySpinnerAdapter = new SpinnerACAdapter(getContext(), listCreator.getCategories(myViewModel.getCategories().getValue()));
                categorySpinner.setAdapter(categorySpinnerAdapter);
            }
        });
    }

    private void bindGraphic(View view){
        fab = view.findViewById(R.id.imageButtonTransactionSave);
        titleEditText = view.findViewById(R.id.transactionTitleEditText);
        balanceEditText = view.findViewById(R.id.transactionBalanceEditText);
        place = view.findViewById(R.id.transactionPlaceEditText);
        accountSpinner = view.findViewById(R.id.transactionAccountSpinner);
        categorySpinner = view.findViewById(R.id.transactionCategorySpinner);
        photoButton = view.findViewById(R.id.buttonTakeAPicture);
        yourImage = view.findViewById(R.id.yourImage);
        galleryButton = view.findViewById(R.id.buttonGallery);
    }

    public void onClickTakePhoto(final ImageButton photoButton, final Activity activity) {
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStoragePermissionGranted(activity)) {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        // Ensure that there's a camera activity to handle the intent
                        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
                            activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        }
                    } else {
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                                        activity.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                        != PackageManager.PERMISSION_GRANTED) {
                                    Toast.makeText(activity, "you have to guarantee storage permission to take a photo", Toast.LENGTH_LONG).show();
                                }
                            }
                        }, 200);

                    }

                }
        });
    }

    public void onClickPickPhoto(final ImageButton galleryButton, final Activity activity) {
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStoragePermissionGranted(activity)) {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    activity.startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
                } else {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                                    activity.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                            != PackageManager.PERMISSION_GRANTED) {
                                Toast.makeText(activity, "you have to guarantee storage permission to take a photo", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, 200);

                }

            }
        });
    }

    private  boolean isStoragePermissionGranted(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    /**
     * Method use to set the bitmap that will be displayed on the ImageView
     * @param bitmap bitmap representing the picture taken.
     */
    void setImageView(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
