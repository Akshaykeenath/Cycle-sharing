package com.example.cyclesharing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Viewcyclestation extends AppCompatActivity implements JsonResponse{
    ListView l1;
    SharedPreferences sh;
    String [] sname,no_of_cycle,location,value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcyclestation);
        l1=(ListView) findViewById(R.id.list);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Viewcyclestation.this;
        String q = "/Viewcyclestation?login_id=" + sh.getString("log_id", "") ;
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                sname = new String[ja1.length()];
                location = new String[ja1.length()];
                no_of_cycle = new String[ja1.length()];



                value = new String[ja1.length()];


                String[] value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    sname[i] = ja1.getJSONObject(i).getString("sname");

                    location[i] = ja1.getJSONObject(i).getString("location");
                    no_of_cycle[i] = ja1.getJSONObject(i).getString("no_of_cycle");






                    value[i] = "sname:" + sname[i] + "\nlocation: " + location[i] + "\n no_of_cycle: " + no_of_cycle[i]  ;

                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), R.layout.custtext, value);

                l1.setAdapter(ar);

            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

        }
    }
}