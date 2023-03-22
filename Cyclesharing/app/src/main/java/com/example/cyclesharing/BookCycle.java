package com.example.cyclesharing;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

public class BookCycle extends AppCompatActivity implements JsonResponse, AdapterView.OnItemSelectedListener {
EditText e1,e2,e3;
Button b1;
Spinner s1;
String amount,hours,total;

String [] cycle_id,location,value;
SharedPreferences sh;
public static String cid;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_cycle);
        e1=(EditText) findViewById(R.id.Amonut);
        e2=(EditText) findViewById(R.id.hours);
        e3=(EditText) findViewById(R.id.total);
        b1=(Button) findViewById(R.id.booknow);
        s1=(Spinner) findViewById(R.id.spinner);
        sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        e1.setText("20");

        s1.setOnItemSelectedListener(this);

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) BookCycle.this;
        String q = "/viewspinner";
        q = q.replace(" ", "%20");
        JR.execute(q);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hours=e2.getText().toString();
                total=e3.getText().toString();



//                Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!"+sid, Toast.LENGTH_LONG).show();
                JsonReq JR = new JsonReq();
                JR.json_response = (JsonResponse) BookCycle.this;
                String q = "/BookCycle?login_id=" + sh.getString("log_id", "") +"&hours="  + hours +"&total=" +total +"&cid="+cid;
                q = q.replace(" ", "%20");
                JR.execute(q);


            }
        });


        e2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(e2.getText().toString().equalsIgnoreCase(""))
                {

                }
                else if(e2.getText().toString().equalsIgnoreCase("."))
                {

                }
                else
                {
                    Integer s=Integer.parseInt(e1.getText().toString())*Integer.parseInt(e2.getText().toString());
                    e3.setText(s+"");
                }

            }
        });

    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("Bookcycle")) {

                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "ADDED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Userhome.class));

                } else {

                    Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
                }
            }
            else if(method.equalsIgnoreCase("viewspinner"))
            {
                String status=jo.getString("status");
                Log.d("pearl",status);


                if(status.equalsIgnoreCase("success")){
                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");

                    cycle_id=new String[ja1.length()];

                    location=new String[ja1.length()];
                    value=new String[ja1.length()];


                    for(int i = 0;i<ja1.length();i++)
                    {
                        cycle_id[i]=ja1.getJSONObject(i).getString("c_id");
                        location[i]=ja1.getJSONObject(i).getString("location");





                        value[i]=location[i];


                    }
                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),R.layout.custtext,value);
                    s1.setAdapter(ar);
                }
            }



        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        cid=cycle_id[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}