package com.hussain.passengerconnect;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Hussain on 30-Sep-17.
 */

public class ItemAdapter extends BaseAdapter {

    private ArrayList<String> names;
    private ArrayList<Integer> rfidNumber;
    private Context context;

    public ItemAdapter(ArrayList<String> names,ArrayList<Integer> phoneNumbers,Context context){
        this.names=names;
        this.rfidNumber =phoneNumbers;
        this.context=context;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int i) {
        return names.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, final ViewGroup viewGroup) {

        view =  LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.item_layout,viewGroup,false);

        ((TextView)view.findViewById(R.id.iname)).setText(String.valueOf(names.get(i)));
        ((TextView)view.findViewById(R.id.inumber)).setText(String.valueOf("RFID : "+rfidNumber.get(i)));

        ImageView del = (ImageView) view.findViewById(R.id.delete);

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query query = ref.child("Item").orderByChild("item_name").equalTo(names.get(i));

                names.removeAll(names);
                notifyDataSetChanged();

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("----------", "onCancelled", databaseError.toException());
                    }
                });
            }
        });
        return view;
    }
}
