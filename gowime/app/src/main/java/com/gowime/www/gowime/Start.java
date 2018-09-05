package com.gowime.www.gowime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.gowime.www.gowime.utilities.*;

import static com.firebase.ui.auth.data.model.User.getUser;

public class Start extends utilities {

    private static final String TAG = "Start";
    Context context;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        context = getApplicationContext();
        mAuth = FirebaseAuth.getInstance();
    }

    //check to see if a user is already logged in
    //if so, go to the Recommendations flow
    //if not, prompt for login
    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null)
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            //TODO: go to recommendations flow
            //temp go to user info placeholder page
            Intent intent = new Intent(Start.this, UserProfile.class);
            startActivity(intent);
        }else{
            //go to sigin flow
            Intent intent = new Intent(Start.this, CreateAccountHome.class);
            startActivity(intent);
        }
    }

}
