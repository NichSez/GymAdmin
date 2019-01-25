package com.example.nich_sez.gymadmin;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nich_sez.gymadmin.R;

public class EditOrDeleteAthlete extends AppCompatActivity {

    Button btnSearch, btnEdit, btnDelete;
    EditText etxtSearch, etxtLockerCode, etxtName, etxtFamily, etxtAddress, etxtTel, etxtNationalCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_or_delete_athlete);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnSearch = (Button) findViewById(R.id.BtnSearch);
        btnEdit = (Button) findViewById(R.id.BtnEdit);
        btnDelete = (Button) findViewById(R.id.BtnDelete);

        etxtSearch = (EditText) findViewById(R.id.EtxtSearch);
        etxtLockerCode = (EditText) findViewById(R.id.EtxtLockerCode);
        etxtName = (EditText) findViewById(R.id.EtxtName);
        etxtFamily = (EditText) findViewById(R.id.EtxtFamily);
        etxtAddress = (EditText) findViewById(R.id.EtxtAddress);
        etxtTel = (EditText) findViewById(R.id.EtxtTel);
        etxtNationalCode = (EditText) findViewById(R.id.EtxtNationalCode);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                etxtLockerCode.setVisibility(View.VISIBLE);
                etxtName.setVisibility(View.VISIBLE);
                etxtFamily.setVisibility(View.VISIBLE);
                etxtAddress.setVisibility(View.VISIBLE);
                etxtTel.setVisibility(View.VISIBLE);
                etxtNationalCode.setVisibility(View.VISIBLE);


            }
        });






    }

}
