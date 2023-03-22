package com.example.cyclesharing;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class ViewBookings extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    SharedPreferences sh;
    String[] f_name, location, hours, total, statu, value, cicle_id, booking_id;
    public static String cid, fd, amt, bid,sta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bookings);
        l1 = (ListView) findViewById(R.id.list);
        l1.setOnItemClickListener(this);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        //                Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!"+sid, Toast.LENGTH_LONG).show();
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) ViewBookings.this;
        String q = "/Viewbookings?login_id=" + sh.getString("log_id", "");
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
                f_name = new String[ja1.length()];
                location = new String[ja1.length()];
                hours = new String[ja1.length()];

                total = new String[ja1.length()];

                statu = new String[ja1.length()];
                cicle_id = new String[ja1.length()];
                booking_id = new String[ja1.length()];

                value = new String[ja1.length()];


                String[] value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    f_name[i] = ja1.getJSONObject(i).getString("f_name");
                    booking_id[i] = ja1.getJSONObject(i).getString("b_id");
                    location[i] = ja1.getJSONObject(i).getString("location");
                    hours[i] = ja1.getJSONObject(i).getString("hours");
                    total[i] = ja1.getJSONObject(i).getString("amount");
                    cicle_id[i] = ja1.getJSONObject(i).getString("c_id");

                    statu[i] = ja1.getJSONObject(i).getString("bstatus");


                    value[i] = "f_name:" + f_name[i] + "\nlocation: " + location[i] + "\n hours: " + hours[i] + "\n total: " + total[i] + "\n statu: " + statu[i];

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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        cid = cicle_id[i];
        fd = hours[i];
        amt = total[i];
        bid = booking_id[i];
        sta=statu[i];

        if (sta.equalsIgnoreCase("Booked")) {


            final CharSequence[] items = {"make payment", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(ViewBookings.this);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (items[item].equals("make payment")) {

                        startActivity(new Intent(getApplicationContext(), Makepayment.class));

                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }

            });
            builder.show();


        }else if (sta.equalsIgnoreCase("Paid")) {

            final CharSequence[] items = {"complaint","Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(ViewBookings.this);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("complaint")) {

                        startActivity(new Intent(getApplicationContext(), Complaint.class));

                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }

                }

            });
            builder.show();
        }
    }
}
