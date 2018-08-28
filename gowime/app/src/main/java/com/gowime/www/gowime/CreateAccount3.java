package com.gowime.www.gowime;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

public class CreateAccount3 extends AppCompatActivity {
    private static final String TAG = "CreateAccount3";
    
    EditText location;
    SeekBar distance;
    ImageButton goToSearch;
    FloatingActionButton gtAuth;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_3);
        
        location = findViewById(R.id.location_edit);
        distance = findViewById(R.id.max_distance_edit);
        goToSearch = findViewById(R.id.go_to_location_search);
        gtAuth = findViewById(R.id.go_to_auth);
        
        goToSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: add action to go to the location search view
                showToast("clicked on location search");
                Intent intent = new Intent(CreateAccount3.this, LocationSearch.class);
                startActivity(intent);
            }
        });

        gtAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: add details to user profile
                showToast("clicked on go to create account 4");
                Intent intent = new Intent(CreateAccount3.this, CreateAccount4.class);
                startActivity(intent);
            }
        });

    }
    private void showToast(String message){
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
        toast.show();
    }
}