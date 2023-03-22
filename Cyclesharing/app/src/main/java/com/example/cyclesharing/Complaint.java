package com.example.cyclesharing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class Complaint extends AppCompatActivity implements JsonResponse{
    EditText e1;
    Button b1;
    String complaint;
    SharedPreferences sh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        e1=(EditText) findViewById(R.id.comp);
        b1=(Button) findViewById(R.id.complaint);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                complaint=e1.getText().toString();


                JsonReq JR = new JsonReq();
                JR.json_response = (JsonResponse) Complaint.this;
                String q = "/Complaint?compliant=" + complaint +"&login_id=" + sh.getString("log_id", "") +"&cid=" + ViewBookings.cid +"&fromdate="+ViewBookings.fd;
                q = q.replace(" ", "%20");
                JR.execute(q);
            }
        });

    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                Toast.makeText(getApplicationContext(), " SUCCESS", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Userhome.class));

            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}