package com.example.nich_sez.gymadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button creatAthlete, editOrDeleteAthlete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        creatAthlete = (Button) findViewById(R.id.BtnAdd);
        editOrDeleteAthlete = (Button) findViewById(R.id.BtnEditDelete);

        findViewById(R.id.BtnAdd).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddingAthlete.class));
            }
        });

        findViewById(R.id.BtnEditDelete).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EditOrDeleteAthlete.class));
            }
        });








    }
}
