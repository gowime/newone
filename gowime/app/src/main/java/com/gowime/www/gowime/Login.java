package com.gowime.www.gowime;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class Login extends utilities {

    private static final String TAG = "Login";

    EditText accountEmail;
    EditText accountPwd;

    TextView forgotPwd;

    FloatingActionButton gtAuth;

    //firebase configuration
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        accountEmail = findViewById(R.id.email_address_edit);
        accountPwd = findViewById(R.id.email_password_edit);
        forgotPwd = findViewById(R.id.forgot_pwd);
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
                    signInUser(email, pswd);
                }else{
                    showToast(getApplicationContext(), "You must complete all fields");
                }
            }
        });

        forgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call forgot password method
                forgotPwd();
            }
        });


    }

    //do auth stuff
    private void signInUser(String email, String pswd){
        //try to sigin, based on error code do something
        mAuth.signInWithEmailAndPassword(email, pswd)
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
                                    // if user enters wrong email.
                                    catch (FirebaseAuthInvalidUserException invalidEmail)
                                    {
                                        Log.d(TAG, "onComplete: invalid_email");
                                        showToast(getApplicationContext(), "invalid email");

                                    }
                                    // if user enters wrong password.
                                    catch (FirebaseAuthInvalidCredentialsException wrongPassword)
                                    {
                                        Log.d(TAG, "onComplete: wrong_password");
                                        showToast(getApplicationContext(), "wrong password");
                                    }
                                    catch (Exception e)
                                    {
                                        Log.d(TAG, "onComplete: " + e.getMessage());
                                        showToast(getApplicationContext(), "onComplete: " + e.getMessage());
                                    }
                                }else{
                                    //all is good so log the user in
                                    //TODO: take user to Recommendations flow
                                    //for now go to the user details flow
                                    Intent intent = new Intent(Login.this, CreateAccount1.class);
                                    startActivity(intent);
                                }
                            }
                        }
                );
    }

    private void forgotPwd(){
        Intent intent = new Intent(Login.this, ForgotPassword.class);
        startActivity(intent);
    }
}