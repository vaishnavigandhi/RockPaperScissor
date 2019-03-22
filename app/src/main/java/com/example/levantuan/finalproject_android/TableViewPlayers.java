package com.example.levantuan.finalproject_android;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TableViewPlayers extends AppCompatActivity implements MyAdapter.ItemClickListener{
    FirebaseDatabase database;
    DatabaseReference root;
    private MyAdapter mAdapter;
    private ChildEventListener mChildEventListener ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_view_players);

        // setup the firebase variables
        database = FirebaseDatabase.getInstance();
        root = database.getReference();




        // data to populate the RecyclerView with
        final ArrayList<String> Give = new ArrayList<>();

        //Firebase Fetch
        if (mChildEventListener == null) {

            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    ShowUsers users = dataSnapshot.getValue(ShowUsers.class);
                    String name = users.getName();
                    String txt = "Points:" + " " + users.getPoint() + "\n "+ "Lat:"  + " " + users.getLg() + "\n "+ "Long:"  + " " + users.getLat();
                    Give.add(name);
                    Give.add(txt);

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };
            root.child("Users").addChildEventListener(mChildEventListener);
        }


        // Add a divider bewteen each row



        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        LinearLayoutManager mlm = (LinearLayoutManager) recyclerView.getLayoutManager();


        // add a "line" between each row
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                mlm.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);


        mAdapter = new MyAdapter(this, Give);
        mAdapter.setClickListener(this);
        recyclerView.setAdapter(mAdapter);

    }

    // Click handler for when person clicks on a row
    @Override
    public void onItemClick(View view, final int position) {
        Log.d("tuan","You clicked " + mAdapter.getItem(position) + " on row number " + position);
        Toast.makeText(this, "You clicked " + mAdapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();

        Intent myIntent = new Intent(TableViewPlayers.this, ChallegePlayer.class);
        myIntent.putExtra("KeyName", mAdapter.getItem(position));

//        final String getname = getIntent().getExtras().getString("getName");
//        myIntent.putExtra("getName1",getname);
//
//        startActivity(myIntent);



        // databas check if child exist then do something
        DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        DatabaseReference users = root.child("Users");
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (snapshot.hasChild(mAdapter.getItem(position))) {

                    Log.d("Tuan","KO dc dau");
//                    Toast.makeText(TableViewPlayers.this, "Child existed",Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(TableViewPlayers.this, ChallegePlayer.class);
                    myIntent.putExtra("KeyName", mAdapter.getItem(position));

//                    final String getname = getIntent().getExtras().getString("getName");
//                    myIntent.putExtra("getName1",getname);

                    startActivity(myIntent);


                }// end if go to else
                else{
                    Log.d("Tuan","KO dc dau");
                    Toast.makeText(TableViewPlayers.this, "Please click to Player's Name to see Location",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

}
