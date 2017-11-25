package com.hussain.passengerconnect;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    TextView rfid;
    EditText itemName;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef,ref1;
    ChildEventListener childEventListener;
    DataModel data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rfid = (TextView) findViewById(R.id.rfid);
        itemName = (EditText) findViewById(R.id.item_name);

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("Item");
        myRef.keepSynced(true);

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                data = dataSnapshot.getValue(DataModel.class);
                rfid.setText(data.getid());
                ref1 = dataSnapshot.getRef();
                NotificationManagerCompat myManager = NotificationManagerCompat.from(getApplicationContext());
                NotificationCompat.Builder myNoti = new NotificationCompat.Builder(getApplicationContext());
                myNoti.setContentTitle("Tag & Find");
                myNoti.setContentText("Item has been inserted");
                myNoti.setSmallIcon(android.R.drawable.ic_input_add);

                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                PendingIntent pd = PendingIntent.getActivity(getApplicationContext(),1,i,0);
                myNoti.setContentIntent(pd);
                myNoti.setAutoCancel(true);
                myManager.notify(1,myNoti.build());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                NotificationManagerCompat myManager = NotificationManagerCompat.from(getApplicationContext());
                NotificationCompat.Builder myNoti = new NotificationCompat.Builder(getApplicationContext());
                myNoti.setContentTitle("Tag & Find");
                myNoti.setContentText("Item has been removed");
                myNoti.setSmallIcon(android.R.drawable.ic_input_add);

                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                PendingIntent pd = PendingIntent.getActivity(getApplicationContext(),1,i,0);
                myNoti.setContentIntent(pd);
                myNoti.setAutoCancel(true);

                myManager.notify(1,myNoti.build());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        };
        myRef.addChildEventListener(childEventListener);
    }

    public void store_name(View view) {
        if( itemName.getText().toString().trim().equals(""))
        {
            itemName.setError( "Item name is required!" );

        } else {
            Toast.makeText(getApplicationContext(), "Item Stored Successfully", Toast.LENGTH_LONG).show();
            String iname = itemName.getText().toString();
            ref1.child("item_name").setValue(iname);
        }
    }

    public void view_items(View view) {
        Intent  intent  =   new Intent(this,ViewItems.class);
        startActivity(intent);
    }

    public void rfid1(View view) {
        Uri uri = Uri.parse("http://passengerconnect.000webhostapp.com/firebaseTest.php?arduino_data=00000100");
        Intent intent = new Intent(Intent.ACTION_DEFAULT, uri);
        // startActivity(intent);


    }
}

