package com.gowime.www.gowime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateAccount2 extends utilities {
    private static final String TAG = "CreateAccount2";

    Context context;

    TextView emailAddy;
    Switch notificationBtn;
    Boolean notificationChoice;
    FloatingActionButton gtAuth;

    //firebase authentication and database stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_2);

        context = getApplicationContext();

        //database stuff
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userId = user.getUid();

        //view specific stuff
        emailAddy = findViewById(R.id.email_address_edit);
        notificationBtn = findViewById(R.id.notification_choice);
        gtAuth = findViewById(R.id.go_to_auth);


        gtAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: add code to get the details and send to firebase
                //get the current state of the notfications switch
                notificationChoice = notificationBtn.isChecked();

                //save it to the database
                myRef.child("users").child(userId).child("profile").child("notificationChoice").setValue(notificationChoice);
                Intent intent = new Intent(CreateAccount2.this, CreateAccount3.class);
                startActivity(intent);
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
            uInfo.setFirstName(ds.child(userId).child("profile").getValue(UserInformation.class).getFirstName());
            uInfo.setLastName(ds.child(userId).child("profile").getValue(UserInformation.class).getLastName());
            uInfo.setAboutMe(ds.child(userId).child("profile").getValue(UserInformation.class).getAboutMe());
            uInfo.setMyWork(ds.child(userId).child("profile").getValue(UserInformation.class).getMyWork());
            uInfo.setEmail(ds.child(userId).child("profile").getValue(UserInformation.class).getEmail());
            uInfo.setNotificationChoice(ds.child(userId).child("profile").getValue(UserInformation.class).getNotificationChoice());

            emailAddy.setText(uInfo.getEmail());
            if(uInfo.getNotificationChoice() == null){
                notificationBtn.setChecked(false);
            }else {
                notificationBtn.setChecked(uInfo.getNotificationChoice());
            }
        }
    }
}