package com.donglv.watch.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;


import com.donglv.watch.ActivityHome;
import com.donglv.watch.MainActivity;
import com.nam.customlibrary.Libs_System;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 5/5/2016.
 */
public class HMW_System {

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;

    public static boolean getBooleanData(Context context, String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean r = preferences.getBoolean(key, true);
        return r;
    }


    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static File saveBitmapToFile(Context context, Bitmap bitmap, String fileName) {
        File outputFile = null;
        FileOutputStream outStream;
        File outputDir = context.getCacheDir(); // context being the Activity pointer
        try {
            outputFile = File.createTempFile(fileName, "jpg", outputDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outStream = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outStream);
            outStream.flush();
            outStream.close();
        } catch (FileNotFoundException var6) {
            var6.printStackTrace();
        } catch (IOException var7) {
            var7.printStackTrace();
        }

        return outputFile;
    }
    public static Bitmap convertToBitmap(Drawable drawable, int widthPixels, int heightPixels) {
        Bitmap mutableBitmap = Bitmap.createBitmap(widthPixels, heightPixels, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mutableBitmap);
        drawable.setBounds(0, 0, widthPixels, heightPixels);
        drawable.draw(canvas);
        return mutableBitmap;
    }

    public static void checkLogin(final Context context){
        if(Libs_System.getStringData(context, HMW_Constant.TOKEN).equals("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Bạn cần phải đăng nhâp");
            builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    context.startActivity(new Intent(context, MainActivity.class));
                    ((ActivityHome)context).finish();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();

        }
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }


    public static boolean checkLogin2(final Context context){
        if(Libs_System.getStringData(context, HMW_Constant.TOKEN).equals("")){
            return true;
        }
        return false;

    }
}
