package com.studio.chat.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Ack;
import com.github.nkzawa.socketio.client.Socket;
import com.studio.chat.utility.Constants;
import com.studio.chat.R;
import com.studio.chat.utility.SocketManager;


/**
 * A login screen that offers login via username.
 */
public class LoginActivity extends Activity {

    private final String TAG = LoginActivity.class.getName();
    private EditText mUsernameView;
    private Button mSignButton;
    private String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SocketManager.getInstance().listenOn(Constants.NODE_LOGIN, onLogin);
        SocketManager.getInstance().listenOn(Socket.EVENT_CONNECT, onConnect);
        SocketManager.getInstance().listenOn(Socket.EVENT_CONNECT_ERROR, onConnectError);
        SocketManager.getInstance().listenOn(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);

        // Set up the login form.
        mUsernameView = (EditText) findViewById(R.id.username_input);
        mUsernameView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mSignButton = (Button) findViewById(R.id.sign_in_button);
        mSignButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SocketManager.getInstance().listenOff(Constants.NODE_LOGIN, onLogin);
        SocketManager.getInstance().listenOff(Socket.EVENT_CONNECT, onConnect);
        SocketManager.getInstance().listenOff(Socket.EVENT_CONNECT_ERROR, onConnectError);
        SocketManager.getInstance().listenOff(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
    }

    /**
     * Attempts to sign in the account specified by the login form.
     * If there are form errors (invalid username, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mUsernameView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString().trim();

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            mUsernameView.setError(getString(R.string.error_field_required));
            mUsernameView.requestFocus();
            return;
        }

        mUsername = username;

        // perform the user login attempt.
        SocketManager.getInstance().emitEvent(Constants.NODE_ADD_USER, username);
    }

    private Emitter.Listener onLogin = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String result = (String) args[0];
            Intent intent = new Intent();
            intent.putExtra(UserListActivity.KEY_USER_NAME, mUsername);
            intent.putExtra(UserListActivity.KEY_JSON_USERS, result);
            setResult(RESULT_OK, intent);
            finish();
        }
    };

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mUsernameView.setEnabled(true);
                    mSignButton.setEnabled(true);
                    Toast.makeText(getApplicationContext(), R.string.message_connect, Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mUsernameView.setEnabled(false);
                    mSignButton.setEnabled(false);
                    Toast.makeText(getApplicationContext(), R.string.error_connect, Toast.LENGTH_LONG).show();
                }
            });
        }
    };

}



