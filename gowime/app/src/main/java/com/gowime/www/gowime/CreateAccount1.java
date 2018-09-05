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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateAccount1 extends utilities {

    private static final String TAG = "CreateAccount1";

    Context context;

    EditText fName, lName, about, work;
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
        setContentView(R.layout.create_account_1);

        context = getApplicationContext();

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userId = user.getUid();

        fName = findViewById(R.id.first_name_edit);
        lName = findViewById(R.id.last_name_edit);
        about = findViewById(R.id.about_me_edit);
        work = findViewById(R.id.current_work_edit);
        gtAuth = findViewById(R.id.go_to_auth);

        //set onclick listener for Authentication
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    showToast(context, "user is logged in: " + user.getEmail());
                }else {
                    showToast(context, "user is not logged in.");
                }
            }
        };

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            FirebaseUser user = mAuth.getCurrentUser();
            String userID = user.getUid();
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //TODO: takes these values and populated the UI
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        //set onclick listener for FAB
        gtAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: get the details and send to user profile in firebase
                Log.d(TAG, "onClick: gtca2");
                showToast(context, "clicked on go create account 2");
                //get the details the user provided
                //add them to the database
                String firstName = fName.getText().toString();
                String lastName = lName.getText().toString();
                String aboutMe = about.getText().toString();
                String myWork = work.getText().toString();

                if(!firstName.equals("") && !lastName.equals("")){
                    FirebaseUser user = mAuth.getCurrentUser();
                    String userID = user.getUid();
                    myRef.child("users").child(userID).child("profile").child("firstName").setValue(firstName);
                    myRef.child("users").child(userID).child("profile").child("lastName").setValue(lastName);
                    myRef.child("users").child(userID).child("profile").child("aboutMe").setValue(aboutMe);
                    myRef.child("users").child(userID).child("profile").child("myWork").setValue(myWork);
                    myRef.child("users").child(userID).child("profile").child("email").setValue(user.getEmail());

                    showToast(context, "Profile updated");
                    //go to the next screen
                    Intent intent = new Intent(CreateAccount1.this, CreateAccount2.class);
                    startActivity(intent);
                }else{
                    showToast(context, "You must provide a first and last name");

                }
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

            //Find the views
            fName.setText(uInfo.getFirstName());
            lName.setText(uInfo.getLastName());
            about.setText(uInfo.getAboutMe());
            work.setText(uInfo.getMyWork());
        }
    }
}
