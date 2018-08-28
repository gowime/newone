package com.gowime.www.gowime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CreateAccountHome extends AppCompatActivity {

    private static final String TAG = "CreateAccountHome";

    private Button btnLoginWithFB, btnSignupEmail, btnLoginEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_home);

        btnLoginWithFB = (Button) findViewById(R.id.login_with_fb);
        btnLoginEmail = (Button) findViewById(R.id.login_with_email);
        btnSignupEmail = (Button) findViewById(R.id.signup_with_email);

        btnLoginEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: call firebase email authentication
                //TODO: if email is a valid account and login is succesful, go to user screen
                //TODO: if email is a valid account and login is not-succesful, give error, show reset password flow
                //TODO: if email is not a valid account, go to Create Account screen

                showToast("You clicked login with email");
                Intent intent = new Intent(CreateAccountHome.this, Login.class);
                startActivity(intent);
            }
        });

        btnSignupEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("You clicked signup with email");
                Intent intent = new Intent(CreateAccountHome.this, CreateAccount0.class);
                startActivity(intent);
            }
        });

        btnLoginWithFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: call firebase fb authentication
                //TODO: if authentication is valid, go to user settings screen
                showToast("You clicked login with facebook");
            }
        });
    }

    private void showToast(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
        toast.show();
    }
}
