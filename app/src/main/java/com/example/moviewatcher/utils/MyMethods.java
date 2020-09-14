package com.example.moviewatcher.utils;

import android.content.Context;
import android.widget.Toast;

public class MyMethods {

    public void getToastMessage(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
