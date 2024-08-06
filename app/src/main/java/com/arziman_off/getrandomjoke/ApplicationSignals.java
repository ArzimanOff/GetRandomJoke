package com.arziman_off.getrandomjoke;


import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ApplicationSignals {
    public static void loadingError(Context context){
        Toast toast = Toast.makeText(
                context,
                R.string.internet_error_toast_text,
                Toast.LENGTH_SHORT
        );
        toast.setGravity(
                Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL,
                0,
                700
        );
        toast.show();
    }

}