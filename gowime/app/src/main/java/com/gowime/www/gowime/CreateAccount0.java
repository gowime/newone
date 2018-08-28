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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import com.gowime.www.gowime.utilities;

public class CreateAccount0 extends utilities {

    private static final String TAG = "CreateAccount0";

    Context context;

    EditText accountEmail;
    EditText accountPwd;
    FloatingActionButton gtAuth;

    //firebase configuration
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_0);

        context = getApplicationContext();
        accountEmail = findViewById(R.id.email_address_edit);
        accountPwd = findViewById(R.id.email_password_edit);
        gtAuth = findViewById(R.id.go_to_auth);

        mAuth = FirebaseAuth.getInstance();

        gtAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get email and password and call firebase to see if this is a new user or existing user
                //go to next create account screen
                //pass user details in intent
                String email = accountEmail.getText().toString();
                String pswd = accountPwd.getText().toString();
                if(!email.equals("") && !pswd.equals("")){
                    createNewAccount(email, pswd);
                }else{
                    showToast(getApplicationContext(), "You must complete all fields");
                }
            }
        });

    }

    private void createNewAccount(final String email, String pswd){
        mAuth.createUserWithEmailAndPassword(email, pswd)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if (!task.isSuccessful())
                                {
                                    try
                                    {
                                        throw task.getException();
                                    }
                                    // if user enters weak email.
                                    catch (FirebaseAuthWeakPasswordException weakPassword)
                                    {
                                        Log.d(TAG, "onComplete: weak_password");
                                        showToast(context, "Provided password is weak, try again");
                                        accountPwd.setText("");
                                    }
                                    // if user enters malformed password.
                                    catch (FirebaseAuthInvalidCredentialsException malformedEmail)
                                    {
                                        Log.d(TAG, "onComplete: malformed_email");
                                        showToast(context, "Email is not formed properly");
                                    }
                                    catch (FirebaseAuthUserCollisionException existEmail)
                                    {
                                        Log.d(TAG, "onComplete: exist_email");
                                        showToast(context, "That account already exists.");
                                        accountEmail.setText("");
                                        accountPwd.setText("");
                                    }
                                    catch (Exception e)
                                    {
                                        Log.d(TAG, "onComplete: " + e.getMessage());
                                        showToast(context, e.getMessage());
                                    }
                                }else{
                                    //for now go to the user details flow
                                    Intent intent = new Intent(CreateAccount0.this, CreateAccount1.class);
                                    startActivity(intent);
                                }
                            }
                        }
                );
    }

}
