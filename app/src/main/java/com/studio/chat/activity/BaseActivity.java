package com.studio.chat.activity;

import android.app.Activity;
import android.util.Log;

public class BaseActivity extends Activity {
    private final String TAG = BaseActivity.class.getName();

    public static int count = 0;

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "OnStart");
        count = count + 1;
        if (count == 1) {
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
        count = count - 1;
        if (count == 0) {
            //SocketManager.getInstance().disconnect();
        }
    }
}
