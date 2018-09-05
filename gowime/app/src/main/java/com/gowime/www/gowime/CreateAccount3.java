package com.gowime.www.gowime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateAccount3 extends utilities {
    private static final String TAG = "CreateAccount3";

    Context context;
    EditText location;
    SeekBar seekBar;
    ImageButton goToSearch;
    FloatingActionButton gtAuth;
    TextView searchRadius;

    //firebase authentication and database stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_3);

        context = getApplicationContext();

        //database stuff
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userId = user.getUid();

        //view specific stuff
        location = findViewById(R.id.location_edit);
        seekBar = findViewById(R.id.seekbar);
        searchRadius = findViewById(R.id.seekbar_value);
        goToSearch = findViewById(R.id.go_to_location_search);
        gtAuth = findViewById(R.id.go_to_auth);

        goToSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: add action to go to the location search view
                showToast(context, TAG + " clicked on location search");
                Intent intent = new Intent(CreateAccount3.this, LocationSearch.class);
                startActivity(intent);
            }
        });

        gtAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: add details to user profile
                //set location
                //get search radius
                String userDistance = Integer.toString(seekBar.getProgress());

                //save search radius
                myRef.child("users").child(userId).child("profile").child("searchRadius").setValue(userDistance);
                Intent intent = new Intent(CreateAccount3.this, CreateAccount4.class);
                startActivity(intent);
            }
        });

        // perform seek bar change listener event used for getting the progress value
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                progressChangedValue = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                searchRadius.setText(String.valueOf(progressChangedValue * 10));

            }
        });

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //TODO: takes these values and populate the UI
                loadUser(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    //use this method to get the user object from the database
    private void loadUser(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            UserInformation uInfo = new UserInformation();

            //sets the uInfo object values from the values in the database
            uInfo.setSearchRadius(ds.child(userId).child("profile").getValue(UserInformation.class).getSearchRadius());
            int progress = Integer.parseInt(uInfo.getSearchRadius());
            String progressString = Integer.toString(progress * 10);
            searchRadius.setText(progressString);
            seekBar.setProgress(progress);
        }
    }
}