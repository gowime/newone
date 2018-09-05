package com.gowime.www.gowime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {

    private static final String TAG = "UserProfile";

    Context context;

    //TextFields
    TextView userUid, userEmail, userName, aboutMe, location, category, searchRadius, myWork;
    SeekBar seekBar;
    Switch notficationSetting;
    Button logout, editProfile;

    //firebase authentication and database stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        context = getApplicationContext();
        userId = FirebaseAuthentication.getUser();


        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userId = user.getUid();

        logout = findViewById(R.id.logout);
        editProfile = findViewById(R.id.edit_profile);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutOfApp();
            }
        });
        editProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                editProfile();
            }
        });


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //TODO: takes these values and populates the UI
                showData(dataSnapshot);            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }



    private void showData(DataSnapshot dataSnapshot) {

        UserInformation uInfo = new UserInformation();

        for (DataSnapshot ds : dataSnapshot.getChildren()) {

            //sets the uInfo object values from the values in the database
            uInfo.setFirstName(ds.child(userId).child("profile").getValue(UserInformation.class).getFirstName());
            uInfo.setLastName(ds.child(userId).child("profile").getValue(UserInformation.class).getLastName());
            uInfo.setAboutMe(ds.child(userId).child("profile").getValue(UserInformation.class).getAboutMe());
            uInfo.setMyWork(ds.child(userId).child("profile").getValue(UserInformation.class).getMyWork());
            uInfo.setEmail(ds.child(userId).child("profile").getValue(UserInformation.class).getEmail());
            uInfo.setNotificationChoice(ds.child(userId).child("profile").getValue(UserInformation.class).getNotificationChoice());
            uInfo.setSearchRadius(ds.child(userId).child("profile").getValue(UserInformation.class).getSearchRadius());



            //Find the views
            userUid = findViewById(R.id.user_uid_value);
            userEmail = findViewById(R.id.user_email_value);
            userName = findViewById(R.id.user_name_value);
            aboutMe = findViewById(R.id.about_me_value);
            myWork = findViewById(R.id.my_work_value);
            notficationSetting = findViewById(R.id.get_notified);
            seekBar = findViewById(R.id.seekBar);
            searchRadius = findViewById(R.id.search_radius);

            //gets the uInfo values from the UserInformation class object and set the views
            userUid.setText(userId);
            userEmail.setText(uInfo.getEmail());
            userName.setText(uInfo.getFirstName() + " " + uInfo.getLastName());
            aboutMe.setText(uInfo.getAboutMe());
            myWork.setText(uInfo.getMyWork());
            if (uInfo.getNotificationChoice() != null) {
                notficationSetting.setChecked(uInfo.getNotificationChoice());

            } else {
                notficationSetting.setChecked(false);
            }
            int progress = Integer.parseInt(uInfo.getSearchRadius());
            String progressString = Integer.toString(progress * 10);
            searchRadius.setText(progressString);
            seekBar.setProgress(progress);
        }
    }




    private void logoutOfApp() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(UserProfile.this, Start.class);
        startActivity(intent);
    }


    private void editProfile() {
        Intent intent = new Intent(UserProfile.this, CreateAccount1.class);
        startActivity(intent);
    }
}
