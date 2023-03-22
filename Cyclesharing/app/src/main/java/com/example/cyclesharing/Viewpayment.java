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

public class Viewpayment extends AppCompatActivity implements JsonResponse {
    ListView l1;
    SharedPreferences sh;
    String [] location,hours,amount,value,statu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpayment);
        l1=(ListView) findViewById(R.id.list);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Viewpayment.this;
        String q = "/viewpayment?login_id=" + sh.getString("log_id", "") ;
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

                location = new String[ja1.length()];
                hours = new String[ja1.length()];

                amount=new String[ja1.length()];
                statu=new String[ja1.length()];



                value = new String[ja1.length()];


                String[] value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {

                    location[i] = ja1.getJSONObject(i).getString("location");
                    hours[i] = ja1.getJSONObject(i).getString("hours");
                    amount[i] = ja1.getJSONObject(i).getString("amount");

                    statu[i] = ja1.getJSONObject(i).getString("status");






                    value[i] = "location:" + location[i] + "\nhours: " + hours[i] + "\n amount: " + amount[i] +"\n status:" +statu[i] ;

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