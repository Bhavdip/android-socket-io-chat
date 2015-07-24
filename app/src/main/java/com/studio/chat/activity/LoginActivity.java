package com.studio.chat.activity;

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
import com.studio.chat.R;
import com.studio.chat.events.AddUserEvent;
import com.studio.chat.events.UserLoginEvent;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * A login screen that offers login via username.
 */
public class LoginActivity extends BaseActivity {

    private final String TAG = LoginActivity.class.getSimpleName();

    private EventBus mEventBus = EventBus.getDefault();

    private EditText mUsernameView;
    private Button mSignButton;
    private String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEventBus.register(this);
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
        mEventBus.unregister(this);
        super.onDestroy();
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

        emitAddUserEvent();
    }

    /**
     * Login web socket response will handle
     * @param userLoginEvent
     */
    @Subscribe
    public void onEvent(UserLoginEvent userLoginEvent) {
        String result = userLoginEvent.getUserList();
        if (!TextUtils.isEmpty(result)) {
            Log.d(TAG, String.format("User List :%s", result));
            Intent intent = new Intent();
            intent.putExtra(UserListActivity.KEY_USER_NAME, mUsername);
            intent.putExtra(UserListActivity.KEY_JSON_USERS, result);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    public void emitAddUserEvent(){
        // perform the user login attempt.
        if(mEventBus != null){
            mEventBus.post(new AddUserEvent().setUserId(mUsername));
        }
    }
}



