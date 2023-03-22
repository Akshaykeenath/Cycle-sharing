package com.example.cyclesharing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Addwallet extends AppCompatActivity implements JsonResponse {
    EditText e1;
    ListView l1;
    Button b1;
    String amount;
    String[] amt ,value;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addwallet);
        e1=(EditText) findViewById(R.id.amount);
        b1=(Button) findViewById(R.id.wallet);
        l1=(ListView) findViewById(R.id.List);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Addwallet.this;
        String q = "/Viewwallet?login_id=" + sh.getString("log_id", "") ;
        q = q.replace(" ", "%20");
        JR.execute(q);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount=e1.getText().toString();

                JsonReq JR = new JsonReq();
                JR.json_response = (JsonResponse) Addwallet.this;
                String q = "/Addwallet?login_id=" + sh.getString("log_id", "") +"&amount="  + amount +"&bid=" +ViewBookings.bid ;
                q = q.replace(" ", "%20");
                JR.execute(q);

            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("Addwallet")) {

                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "ADDED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Addwallet.class));

                } else {

                    Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
                }
            }
            else if(method.equalsIgnoreCase("Viewwallet"))
            {
                String status=jo.getString("status");
                Log.d("pearl",status);


                if(status.equalsIgnoreCase("success")){
                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");

                    amt=new String[ja1.length()];


                    value=new String[ja1.length()];


                    for(int i = 0;i<ja1.length();i++)
                    {

                        amt[i]=ja1.getJSONObject(i).getString("amount");

                        value[i]="Balance Amount: "+ amt[i] ;

                    }
                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),R.layout.custtext,value);
                    l1.setAdapter(ar);
                }
            }

        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}