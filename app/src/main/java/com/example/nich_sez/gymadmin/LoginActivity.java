package com.example.nich_sez.gymadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText UserTxt ;
    Button LoginBtn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        UserTxt = (EditText) findViewById(R.id.editTextNationalCode);
        LoginBtn = (Button) findViewById(R.id.buttonLogin);


        //User login
        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminLogin();
            }
        });
    }

    private void adminLogin() {
        //first getting the values
        final String nationalCode = UserTxt.getText().toString();


        //validating inputs
        if (TextUtils.isEmpty(nationalCode)) {
            UserTxt.setError("لطفا کد ملی خود را وارد کنید:)");
            return;
        }



        RequestQueue queue = Volley.newRequestQueue(this);

        String baseUrl = "https://sayehparsaei.com/GymAPI/";
        String file = "Admin";
        String uri = baseUrl + file;


        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response",String.valueOf(response));
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (obj.getInt("Error_Code")==200) {

                                //creating a new user object
                                Admin admin = new Admin(
                                        obj.getString("User_ID"),
                                        obj.getString("Name"),
                                        obj.getString("Family"),
                                        obj.getInt("Position"),
                                        obj.getString("Token")
                                );

                                //storing the user in shared preferences
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(admin);

                                //starting the profile activity
                                finish();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("National_Code", nationalCode);
                return params;
            }
        };

        queue.add(stringRequest);
    }

}
