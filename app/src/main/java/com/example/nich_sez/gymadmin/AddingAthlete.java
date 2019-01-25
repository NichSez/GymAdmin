package com.example.nich_sez.gymadmin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nich_sez.gymadmin.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class AddingAthlete extends AppCompatActivity {

    EditText etxtSearch, etxtLockerCode, etxtName, etxtFamily, etxtAddress, etxtTel, etxtNationalCode;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_athlete);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnAdd = (Button) findViewById(R.id.BtnAdd);

        etxtLockerCode = (EditText) findViewById(R.id.EtxtLockerCode);
        etxtName = (EditText) findViewById(R.id.EtxtName);
        etxtFamily = (EditText) findViewById(R.id.EtxtFamily);
        etxtAddress = (EditText) findViewById(R.id.EtxtAddress);
        etxtTel = (EditText) findViewById(R.id.EtxtTel);
        etxtNationalCode = (EditText) findViewById(R.id.EtxtNationalCode);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();


        }
        });





    }

    private void sendData(){

        JSONObject newJsonObject = new JSONObject();

        try {
            newJsonObject.put("Locker_Code",etxtLockerCode.getText().toString().trim());
            newJsonObject.put("Name",etxtName.getText().toString().trim());
            newJsonObject.put("Family",etxtFamily.getText().toString().trim());
            newJsonObject.put("Address",etxtAddress.getText().toString().trim());
            newJsonObject.put("Tel",etxtTel.getText().toString().trim());
            newJsonObject.put("National_Code",etxtNationalCode.getText().toString().trim());

        } catch (JSONException e) {
            e.printStackTrace();
        }


        RequestQueue queue = Volley.newRequestQueue(this);

        String baseUrl = "https://sayehparsaei.com/GymAPI/";
        String file = "AddAthlete";
        String uri = baseUrl + file;


        //if everything is fine
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, uri, newJsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {


                            //if no error in response
                            if (response.getInt("Error_Code")==200) {
                                Toast.makeText(getApplicationContext(),"ایجاد موفقیت آمیز بود!", Toast.LENGTH_SHORT).show();
                                finish();


                            } else {
                                Toast.makeText(getApplicationContext(),"ایجاد موفقیت آمیز نبود!!!!!" , Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("TOKEN_VALUE", Admin.token);
                return params;
            }
        };

        queue.add(jsonObjectRequest);
    }

}
