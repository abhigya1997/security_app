package com.example.security;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class securer extends AppCompatActivity {
    ListView lv;
    ArrayAdapter<String> adapter;
    ArrayList<String> listItems = new ArrayList<String>();
    ArrayList<String> listKeys = new ArrayList<String>();

    int count = 0;
    HashMap<String , Integer> mapping;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapping = new HashMap<>();

        setContentView(R.layout.activity_securer);

        lv = findViewById(R.id.helper_list);
//        listItems.addAll(Arrays.asList("India", "China", "australia", "Portugle", "America", "NewZealand", "China", "australia", "Portugle", "America", "NewZealand"));

        adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, R.id.textView, listItems);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String loc = (String) adapterView.getItemAtPosition(position);
//                loc = "-33.8668896,151.1957878";
                Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?daddr=" + loc);
                // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
// Make the Intent explicit by setting the Google Maps package
                mapIntent.setPackage("com.google.android.apps.maps");

// Attempt to start an activity that can handle the Intent
                startActivity(mapIntent);
            }
        });

        DatabaseReference helpDb = FirebaseDatabase.getInstance().getReference();

        // initial data loading
//        helpDb.child("helps").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                HashMap uid = dataSnapshot.getValue(HashMap.class);
//                if (uid != null) {
//                    FirebaseDatabase.getInstance().getReference().child(String.valueOf(uid)).addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            LocationHelper location = dataSnapshot.getValue(LocationHelper.class);
//                            if (location != null){
//                                Log.e("TAG", location.toString());
//                                String msg = String.valueOf(location.getLatitude()) + ", "+String.valueOf(location.getLongitude());
//                                listItems.add(msg);
//                                adapter.notifyDataSetChanged();
//                            }else{
//                               // no data
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        // change in data
        helpDb.child("helps").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String uid = dataSnapshot.getKey().toString();
                mapping.put(uid, count);
                updateList(uid);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.e("TAG", "Child in change");
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.e("TAG", "Child in change" + dataSnapshot.getKey());
                String uid = dataSnapshot.getKey();
                int position = mapping.get(uid);
                listItems.remove(position);
                count--;
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Button btn = findViewById(R.id.logout_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                fAuth.signOut();
                startActivity(new Intent(getApplicationContext(), register.class));
            }
        });
    }

    private void updateList(String uid){
        FirebaseDatabase.getInstance().getReference().child(String.valueOf(uid)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                LocationHelper location = dataSnapshot.getValue(LocationHelper.class);
                if (location != null){
                    Log.e("TAG", location.toString());
                    String msg = String.valueOf(location.getLatitude()) + ", "+String.valueOf(location.getLongitude());
                    listItems.add(msg);
                    count++;
                    adapter.notifyDataSetChanged();
                }else{
                    // no data
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

//    private String[] getHelpData(){
//        DatabaseReference helpDb = FirebaseDatabase.getInstance().getReference();
////        DataSnapshot snapshot = helpDb.child("help");
//        Query query = helpDb.child("help").orderByKey();
////        helpDb.child("help").
//    }
}
