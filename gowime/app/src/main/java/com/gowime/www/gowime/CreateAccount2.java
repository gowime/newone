package com.gowime.www.gowime;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

public class CreateAccount2 extends AppCompatActivity {
    private static final String TAG = "CreateAccount2";

    EditText emailAddy;
    Switch notification;
    FloatingActionButton gtAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_2);

        emailAddy = findViewById(R.id.email_address_edit);
        notification = findViewById(R.id.notification_choice);
        gtAuth = findViewById(R.id.go_to_auth);

        gtAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: add code to get the details and send ro firebase or save is user settings locally
                showToast("clicked go to create account 3");
                Intent intent = new Intent(CreateAccount2.this, CreateAccount3.class);
                startActivity(intent);
            }
        });


    }

    private void showToast(String message){
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
        toast.show();
    }
}