package com.example.cyclesharing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class Registration extends AppCompatActivity implements JsonResponse{

    EditText e1,e2,e3,e4,e5,e6,e7,e8;
    Button b1;
    String fname,lname,city,hname,street,username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        e1=(EditText) findViewById(R.id.fname);
        e2=(EditText) findViewById(R.id.lname);
        e3=(EditText) findViewById(R.id.city);
        e4=(EditText)findViewById(R.id.hname);
        e5=(EditText) findViewById(R.id.street);
        e6=(EditText) findViewById(R.id.uname);
        e7=(EditText) findViewById(R.id.password);


        b1=(Button) findViewById(R.id.signup);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname=e1.getText().toString();
                lname=e2.getText().toString();

                city=e3.getText().toString();

                hname=e4.getText().toString();
                street=e5.getText().toString();
                username=e6.getText().toString();
                password=e7.getText().toString();

                if(fname.equalsIgnoreCase("")|| !fname.matches("[a-zA-Z ]+"))
                {
                    e1.setError("Enter your firstname");
                    e1.setFocusable(true);
                }
                else if(lname.equalsIgnoreCase("")|| !lname.matches("[a-zA-Z ]+"))
                {
                    e2.setError("Enter your lastname");
                    e2.setFocusable(true);
                }

                else if(city.equalsIgnoreCase("")|| !city.matches("[a-zA-Z ]+"))
                {
                    e3.setError("Enter your city");
                    e3.setFocusable(true);
                }




                else if(username.equalsIgnoreCase(""))
                {
                    e6.setError("Enter your username");
                    e6.setFocusable(true);
                }
                else if(password.equalsIgnoreCase(""))
                {
                    e7.setError("Enter your password");
                    e7.setFocusable(true);
                }else {

                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Registration.this;
                    String q = "/Registration?fname=" + fname + "&lname=" + lname + "&city=" + city + "&hname=" + hname + "&street=" + street + "&username=" + username + "&password=" + password ;
                    q = q.replace(" ", "%20");
                    JR.execute(q);
                }
            }
        });

    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                Toast.makeText(getApplicationContext(), "REGISTRATION SUCCESS", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Login.class));

            } else if (status.equalsIgnoreCase("duplicate")) {
                startActivity(new Intent(getApplicationContext(), Registration.class));
                Toast.makeText(getApplicationContext(), "Username and Password already Exist...", Toast.LENGTH_LONG).show();

            }else if (status.equalsIgnoreCase("already")) {
                Toast.makeText(getApplicationContext(), "Username Or Password ALREADY EXIST", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Registration.class));

            }else {
                startActivity(new Intent(getApplicationContext(), Registration.class));

                Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}