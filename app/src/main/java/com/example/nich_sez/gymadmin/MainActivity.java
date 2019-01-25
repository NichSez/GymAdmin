package com.example.nich_sez.gymadmin;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button createAthlete, editOrDeleteAthlete;
    SwipeRefreshLayout swipeRefreshLayout;



    //Defining list object
    RecyclerView usersRecyclerView;
    UserListAdapter usersListAdapter;
    List<User> userList;

    //Event auto called by event bus because of type of object
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserReqGetComplete(JSONObject response) {

        //TODO:Make an Order BY query
        userList = new ArrayList<>();
        try {
            //For each history we will create a subscription object
            JSONArray subsJsonArray = response.getJSONArray("Users");
            for (int i = 0; i < subsJsonArray.length(); i++) {
                JSONObject subsJson = subsJsonArray.getJSONObject(i);

                String name = subsJson.getString("Name");
                String family = subsJson.getString("Family");
                String address = subsJson.getString("Address");
                String id = subsJson.getString("Athlete_ID");
                String lockerCode = subsJson.getString("Locker_Code");
                String nationalCode = subsJson.getString("National_Code");
                String tel = subsJson.getString("Tel");
                String image = subsJson.getString("Image");
                //Adding new user to users list
                userList.add(new User(name, family, address,id,lockerCode,nationalCode,tel));
                //Setting if this program is last program
            }
            //FINALLY SHOW THE LIST
            usersListAdapter = new UserListAdapter(this, userList);
            usersRecyclerView.setAdapter(usersListAdapter);
            usersListAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    //On start is not necessary but for event handling by Event Bus you need to register them here.
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createAthlete = (Button) findViewById(R.id.BtnAdd);
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



        userList = new ArrayList<>();

        usersRecyclerView = (RecyclerView) findViewById(R.id.userRecycler);
        usersRecyclerView.setHasFixedSize(true);
        //Setting list horizontal and what is in recycler view (HINT :‌ There is a vertical linear layout in recycler view and another horizontal linear layout in that linear layout) :((((
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        usersListAdapter = new UserListAdapter(this, userList);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRecycler);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                System.out.println("OK");
                refreshRecycle();
            }
        });


        //Finally let's go for the list
        refreshList(this);

    }

    private void refreshRecycle(){
        refreshList(this);
    }

    public void refreshList(Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
        //declaring userList here to interact with it in Volley

        String baseUrl="https://sayehparsaei.com/GymAPI/";
        String file="user";
        String uri = baseUrl + file;
        JsonObjectRequest objReq = new JsonObjectRequest(Request.Method.GET, uri, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                if (response.length() > 0) {
                    // The user does have repos, so let's loop through them all.
                    try {
                        if(response.getInt("Error_Code")==200) {
                            EventBus.getDefault().post(response);
                        }
                        //String lastUpdated = jsonObj.get("updated_at").toString();
                        //addToRepoList(repoName, lastUpdated);
                    } catch (JSONException e) {
                        // If there is an error then output this to the logs.
                        //Log.e("Volley", "Invalid JSON Object.");
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
                //Passing tokens
        {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("TOKEN_VALUE", Admin.token);
                return headers;
            }
        };

        queue.add(objReq);






    }
}
