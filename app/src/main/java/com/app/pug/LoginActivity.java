package com.app.pug;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

public class LoginActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    /**
     * Login User, moves to HomeScreen
     *
     * @param v Clicked View
     */
    public void onLogin(View v) {
        startActivity(new Intent(this, HomeActivity.class));
    }


}
