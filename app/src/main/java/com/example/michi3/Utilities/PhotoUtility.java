package com.example.michi3.Utilities;

import android.app.Activity;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.Toast;

import java.io.InputStream;
import java.util.Objects;

public class PhotoUtility {
    public static final int REQUEST_IMAGE_CAPTURE = 2;
    public static final int RESULT_LOAD_IMG = 1;

    public static Bitmap getImageBitmap(Activity activity, Uri currentPhotoUri){
        ContentResolver resolver = activity.getApplicationContext()
                .getContentResolver();
        try {
            InputStream stream = resolver.openInputStream(currentPhotoUri);
            Bitmap bitmap = BitmapFactory.decodeStream(stream);
            Objects.requireNonNull(stream).close();
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
