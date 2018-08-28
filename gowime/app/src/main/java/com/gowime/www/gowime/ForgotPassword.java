package com.gowime.www.gowime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends utilities {

    private static final String TAG = "ForgotPassword";

    Context context;
    EditText accountEmail;
    FloatingActionButton gtAuth;

    //firebase configuration
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_pwd);

        context = getApplicationContext();
        accountEmail = findViewById(R.id.email_address_edit);
        gtAuth = findViewById(R.id.go_to_auth);

        mAuth = FirebaseAuth.getInstance();

        gtAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = accountEmail.getText().toString();
                //call firebase to send password reset email
                if(!email.equals("")){
                    sendPwdReset(email);
                }else {
                    showToast(context, "you must specify an email address");
                }
            }
        });
    }

private void sendPwdReset(String email){
    mAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        // do something when mail was sent successfully.
                        showToast(context, "Password reset email has been sent");
                        Intent intent = new Intent(ForgotPassword.this, Login.class);
                        startActivity(intent);
                    } else {
                        // ...
                        showToast(context, "Email not valid");
                    }
                }
            });
}
}
