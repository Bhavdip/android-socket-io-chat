package com.studio.chat.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.studio.chat.service.SocketService;

public class BaseActivity extends FragmentActivity {

    private final String TAG = BaseActivity.class.getSimpleName();

    public static int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, String.format("onCreate"));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, String.format("OnStart"));
        count = count + 1;
        if (count == 1) {
            // start service
            startService(new Intent(getApplicationContext(), SocketService.class));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, String.format("onStop"));
        count = count - 1;
        if (count == 0) {
            stopService(new Intent(getApplicationContext(), SocketService.class));
        }
    }
}
