package com.example.nich_sez.gymadmin;

import android.content.Context;
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
import com.android.volley.toolbox.Volley;
import com.example.nich_sez.gymadmin.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

                fillItems(etxtSearch.getText().toString().trim());

                etxtLockerCode.setEnabled(true);
                etxtName.setEnabled(true);
                etxtFamily.setEnabled(true);
                etxtAddress.setEnabled(true);
                etxtTel.setEnabled(true);
                etxtNationalCode.setEnabled(true);

                btnDelete.setEnabled(true);
                btnEdit.setEnabled(true);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     deleteAthlete();

            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editAthlete();
            }
        });


    }

    private void fillItems(String athleteId) {

        RequestQueue queue = Volley.newRequestQueue(this);

        String baseUrl = "https://sayehparsaei.com/GymAPI/";
        String file = "SearchAthlete/";
        String uri = baseUrl + file + athleteId ;
        //It's what fragment push in queue - Get  data and it specified by token and token set when user login
        JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.GET, uri, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.length() > 0) {

                    try {
                        if (response.getInt("Error_Code") == 200) {
                            JSONArray profileContentArr = response.getJSONArray("Profile_Content");
                            for (int i = 0; i < profileContentArr.length(); i++) {

                                JSONObject profileContent = profileContentArr.getJSONObject(i);


                                String lockerCode = profileContent.getString("Locker_Code");
                                etxtLockerCode.setText(lockerCode);

                                String name = profileContent.getString("Name");
                                etxtName.setText(name);


                                String family = profileContent.getString("Family");
                                etxtFamily.setText(family);


                                String tel = profileContent.getString("Tel");
                                etxtTel.setText(tel);

                                String nationalCode = profileContent.getString("National_Code");
                                etxtNationalCode.setText(nationalCode);

                                String address = profileContent.getString("Address");
                                etxtAddress.setText(address);


                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.d("Volley", response.toString());

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", "Error on response." + error.getMessage());
            }
        })

        {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("TOKEN_VALUE", Admin.token);
                return headers;
            }
        };

        queue.add(arrReq);
    }

    private void deleteAthlete(){

        JSONObject newJsonObject = new JSONObject();

        try {
            newJsonObject.put("Athlete_ID",etxtSearch.getText().toString().trim());


        } catch (JSONException e) {
            e.printStackTrace();
        }


        RequestQueue queue = Volley.newRequestQueue(this);

        String baseUrl = "https://sayehparsaei.com/GymAPI/";
        String file = "Delete";
        String uri = baseUrl + file;


        //if everything is fine
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, uri, newJsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {


                            //if no error in response
                            if (response.getInt("Error_Code")==200) {
                                Toast.makeText(getApplicationContext(),"حذف موفقیت آمیز بود!", Toast.LENGTH_SHORT).show();
                                finish();


                            } else {
                                Log.e("errorma",String.valueOf(response.getInt("Error_Code")));
                                Toast.makeText(getApplicationContext(),"حذف موفقیت آمیز نبود!!!!!" , Toast.LENGTH_SHORT).show();
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

    private void editAthlete(){
        JSONObject newJsonObject = new JSONObject();

        try {
            newJsonObject.put("Athlete_ID",etxtSearch.getText().toString().trim());
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
        String file = "user/";
        String uri = baseUrl + file;


        //if everything is fine
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, uri, newJsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {


                            //if no error in response
                            if (response.getInt("Error_Code")==200) {
                                Toast.makeText(getApplicationContext(),"تغییر موفقیت آمیز بود!", Toast.LENGTH_SHORT).show();
                                finish();


                            } else {
                                System.out.println(response.toString());
                                Toast.makeText(getApplicationContext(),"تغییر موفقیت آمیز نبود!!!!!" , Toast.LENGTH_SHORT).show();
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


