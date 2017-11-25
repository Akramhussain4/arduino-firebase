package com.hussain.passengerconnect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;



public class ViewItems extends AppCompatActivity {

    public ListView listview;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_items);
        listview = (ListView) findViewById(R.id.listview);

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference().child("Item");
        myRef.keepSynced(true);

        final ArrayList names = new ArrayList<>();
        final ArrayList rfidNumber = new ArrayList<>();

        ((ListView) findViewById(R.id.listview)).setAdapter(new ItemAdapter(names,rfidNumber,this));

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                while ((iterator.hasNext())) {
                    DataModel value = iterator.next().getValue(DataModel.class);
                    names.add(value.getItem_name());
                    rfidNumber.add(value.getid());
                    ((ItemAdapter) (((ListView) findViewById(R.id.listview)).getAdapter())).notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }

}
