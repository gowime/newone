package com.gowime.www.gowime;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class CreateAccount4 extends utilities {

    FloatingActionButton gtAuth;
    GridView gridview;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_4);

        gtAuth = (FloatingActionButton)findViewById(R.id.go_to_auth);

        gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(CreateAccount4.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });

        gtAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateAccount4.this, UserProfile.class);
                startActivity(intent);
            }
        });
    }
}
