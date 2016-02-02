package com.dipoletech.wacos.util;/**
 * Created by DABBY(3pleMinds) on 26-Jan-16.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.Log;

import com.dipoletech.wacos.model.Post;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * DABBY(3pleMinds) 26-Jan-16 4:34 PM 2016 01
 * 26 16 34 Wacos
 **/
public class Constants {
    public static final String appUrl="https://wacos.firebaseio.com/";
    private static final String TAG = Constants.class.getSimpleName();
    public static String displaName = "display_name";
    public static String email = "email";
    public static String surety_id = "email";
    public static String isFisrtTime = "isFirstTime";

    public static String getDateFormat(long s)
    {
        return DateUtils.getRelativeTimeSpanString(s).toString();

    }


    public static String getStringImage(Bitmap photo) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, b);
        byte[] imageBytes = b.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        Log.v(TAG, "Encode File/Image: " + encodedImage);
        return encodedImage;
    }

    public static Bitmap getDecodedBitmap(String encodedImage) {
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    public static Bitmap decodeFile(File file) {
        try {
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(file), null, o);

            // Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 512;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(file), null, o2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static boolean isInRange(File file) {

        //file size in byte
        long fileByte = file.length();

        //file size on KB
        long fileKb = fileByte/1024;

        //file size in MB
        long fileMb = fileKb/1024;

        return fileMb < 10 ? true : false;

    }

    public static String getAttachCount(Post post) {
        return String.valueOf(post.getAttachments()== null ?0 : post.getAttachments().size()  );
    }
}
