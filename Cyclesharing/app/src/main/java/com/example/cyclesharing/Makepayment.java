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

public class Makepayment extends AppCompatActivity implements JsonResponse {
    EditText e1,e2,e3;
    Button b1;
    String card,cvv,amount;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makepayment);
        e1=(EditText)findViewById(R.id.card);
        e2=(EditText) findViewById(R.id.cvv);
        e3=(EditText)findViewById(R.id.amount) ;
        b1=(Button)findViewById(R.id.payment);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        e3.setText(ViewBookings.amt);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card=e1.getText().toString();
                cvv=e2.getText().toString();

                if(card.equalsIgnoreCase("")|| card.length()!=16)
                {
                    e1.setError("Enter your 16 digits card number");
                    e1.setFocusable(true);
                }

                else if(cvv.equalsIgnoreCase("")|| cvv.length()!=3)
                {
                    e2.setError("Enter your 3 digits C V V ");
                    e2.setFocusable(true);
                }

                else {


                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Makepayment.this;
                    String q = "/Makepayment?login_id=" + sh.getString("log_id", "")+ "&amount=" + ViewBookings.amt +"&bid=" +ViewBookings.bid;
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
                Toast.makeText(getApplicationContext(), " SUCCESS", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Userhome.class));

            }

            else if (status.equalsIgnoreCase("already")) {
                Toast.makeText(getApplicationContext(), "NO AMOUNT IN WALLET", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Userhome.class));

            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}