package com.anonymous.carchecker.login.view;

import android.os.AsyncTask;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.anonymous.carchecker.MainActivity;
import com.anonymous.carchecker.R;
import com.anonymous.carchecker.common.data.PreferencesUtil;
import com.anonymous.carchecker.common.util.MyProgressDialog;
import com.anonymous.carchecker.common.view.BaseActivity;
import com.anonymous.carchecker.login.model.Account;

/**
 * A login screen that offers login via email/mPassword.
 */
public class LoginActivity extends BaseActivity {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mUserView;
    private EditText mPasswordView;
    private MyProgressDialog myProgressDialog;

    private Account mAccount = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mUserView = (EditText) findViewById(R.id.user);

        mPasswordView = (EditText) findViewById(R.id.password);

        Button mSignInButton = (Button) findViewById(R.id.login_activity_login);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        PreferencesUtil preferencesUtil = PreferencesUtil.newInstance(this);
        mAccount = preferencesUtil.getDataModel(Account.class);
        if (mAccount != null) {
            mUserView.setText(mAccount.mUserName);
            mPasswordView.setText(mAccount.mPassword);
        }
        attemptLogin();
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUserView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUserView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid mPassword, if the mUserName entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            mUserView.setError(getString(R.string.error_field_required));
            focusView = mUserView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the mUserName login attempt.
            myProgressDialog = new MyProgressDialog(this);
            myProgressDialog.show("", getResources().getString(R.string.msg_loging));
            Account account = new Account(username, password);
            mAuthTask = new UserLoginTask(account);
            mAuthTask.execute((Void) null);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the mUserName.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private Account mAccount;

        UserLoginTask(Account account) {
            mAccount = account;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                // Simulate network access.
                Thread.sleep(500);
            } catch (InterruptedException e) {
                return false;
            }

            if (mAccount != null && mAccount.mUserName.equals("hieu")) {
                return true;
            }

            // TODO: register the new account here.
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {
                //Save to preference
                PreferencesUtil preferencesUtil = PreferencesUtil.newInstance(LoginActivity.this);
                preferencesUtil.setDataModel(mAccount, Account.class);

                //Goto MainActivity
                gotoActivity(MainActivity.class);
                myProgressDialog.hide();
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
                myProgressDialog.hide();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            myProgressDialog.hide();
        }
    }
}

