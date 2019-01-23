package com.kevin.john.json;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    EditText firstname , lastname;
    Button add, show , clear;
    TextView result;
    RequestQueue requestQueue;
    String insertUrl = "";
    String showUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);

        add = findViewById(R.id.add);
        show = findViewById(R.id.show);
        clear = findViewById(R.id.clear);

        result = findViewById(R.id.textView);

        requestQueue = Volley.newRequestQueue(getApplicationContext());


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText("");
            }
        });


            show.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                            showUrl, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray students = response.getJSONArray("students");
                                for (int i = 0; i < students.length(); i++) {
                                    JSONObject student = students.getJSONObject(i);

                                    String firstname = student.getString("firstname");
                                    String lastname = student.getString("lastname");

                                result.append(firstname+" "+lastname+ "\n");

                                }
                                result.append("=========\n");


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }

                });
                    requestQueue.add(jsonObjectRequest);
                }
            });

           add.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
                       @Override
                       public void onResponse(String response) {

                       }
                   }, new Response.ErrorListener() {
                       @Override
                       public void onErrorResponse(VolleyError error) {

                       }
                   }) {
                       @Override
                       protected Map<String, String> getParams() throws AuthFailureError {
                           Map<String, String> parameters = new HashMap<String, String>();
                           parameters.put("firstname", firstname.getText().toString());
                           parameters.put("lastname", lastname.getText().toString());

                           return parameters;

                       }

                   };

                   requestQueue.add(request);

               }
           });

           }
}
