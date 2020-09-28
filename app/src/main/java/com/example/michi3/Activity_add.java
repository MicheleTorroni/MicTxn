package com.example.michi3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.michi3.Utilities.PhotoUtility;
import com.example.michi3.accounts.Fragment_Accounts_Add;
import com.example.michi3.categories.Fragment_Categories_Add;
import com.example.michi3.transactions.Fragment_Transactions_Add;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import static com.example.michi3.Utilities.PhotoUtility.REQUEST_IMAGE_CAPTURE;
import static com.example.michi3.Utilities.PhotoUtility.RESULT_LOAD_IMG;

//DA NON TOCCARE
public class Activity_add extends AppCompatActivity {

    private Uri currentPhotoUri;
    private Bitmap imageBitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Fragment replaceFragment = new Fragment(); //placeholder

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container_add) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            //firstFragment.setArguments(getIntent().getExtras());
            switch (Objects.requireNonNull(getIntent().getStringExtra("fragment"))) {
                case "addAccounts":
                    replaceFragment = new Fragment_Accounts_Add();
                    break;
                case "addCategories":
                    replaceFragment = new Fragment_Categories_Add();
                    break;
                case "addTransactions":
                    replaceFragment = new Fragment_Transactions_Add();
                    break;
                default:
                    Toast toast2 = Toast.makeText(this, "c'è stato un errore", Toast.LENGTH_SHORT);
                    toast2.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast2.show();
                    break;
            }

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_add, replaceFragment).commit();
        }
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (reqCode == RESULT_LOAD_IMG) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                currentPhotoUri = imageUri;
                if (selectedImage != null){
                    ImageView imageView = findViewById(R.id.yourImage);
                    imageView.setImageBitmap(selectedImage);
                }
                Log.d("LAB", String.valueOf(currentPhotoUri));
                // Load a specific media item, and show it in the ImageView
                Bitmap bitmap = PhotoUtility.getImageBitmap(this, currentPhotoUri);
                if (bitmap != null) {
                    ImageView imageView = findViewById(R.id.yourImage);
                    imageView.setImageBitmap(bitmap);
                    try {
                        saveImage(bitmap);
                    } catch (Exception e){
                        Toast.makeText(this, "nun se può", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else if (reqCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                imageBitmap = (Bitmap) extras.get("data");
                try {
                    if (imageBitmap != null) {
                        //method to save the image in the gallery of the device
                        saveImage(imageBitmap);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Log.d("LAB", String.valueOf(currentPhotoUri));
            // Load a specific media item, and show it in the ImageView
            Bitmap bitmap = PhotoUtility.getImageBitmap(this, currentPhotoUri);
            if (bitmap != null) {
                ImageView imageView = findViewById(R.id.yourImage);
                imageView.setImageBitmap(bitmap);
            }
        } else {
            Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

    private void saveImage(Bitmap bitmap) throws IOException {
        // Create an image file name
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ITALY).format(new Date());
        String name = "JPEG_" + timeStamp + "_.png";
        ContentResolver resolver = getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name + ".jpg");
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");
        Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        currentPhotoUri = imageUri;
        OutputStream fos = resolver.openOutputStream(Objects.requireNonNull(imageUri));
        //for the jpeg quality, it goes from 0 to 100
        //for the png one, the quality is ignored
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        if (fos != null) {
            fos.close();
        }
    }

    public Uri getCurrentPhotoUri(){
        return currentPhotoUri;
    }
}