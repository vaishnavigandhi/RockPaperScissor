package com.example.levantuan.finalproject_android;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference root;
    

    EditText UserName;

    private final List<Users> users = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // firebase
        database = FirebaseDatabase.getInstance();
        root = database.getReference();

        // create variables for the user interface components
        UserName = findViewById(R.id.editName);
    }

    public void signPressed(View view) {
        UserName = findViewById(R.id.editName);

        final String username = UserName.getText().toString();
        if(username.isEmpty()){

            Log.d("Tuan", "Filed Blank");
            Toast.makeText(MainActivity.this, "Please, Field in",Toast.LENGTH_LONG).show();
            return;
        }

        // databas check if child exist then do something
        final DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        DatabaseReference users = root.child("Users");
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (snapshot.hasChild(UserName.getText().toString())) {
//                    Users m = new Users("0", UserName.getText().toString(), "0","0");
//                    root.child("Users").child(UserName.getText().toString()).setValue(m);

                    //make clear after sign clicked

                    // go to next screen
                    Intent myIntent = new Intent(MainActivity.this, Main2Activity.class);
                    myIntent.putExtra("username", username);


                    startActivity(myIntent);


                }// end if go to else
                else{

                    Log.d("Tuan","KO dc dau");
                    Toast.makeText(MainActivity.this, "Sorry, The Name does not existed.",Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//
    }


    public void registerPressed(View view) {
        UserName = findViewById(R.id.editName);

        final String username = UserName.getText().toString();
        if(username.isEmpty()){

            Log.d("Tuan", "Filed Blank");
            Toast.makeText(MainActivity.this, "Please, Field in",Toast.LENGTH_LONG).show();
            return;
        }

        // databas check if child exist then do something
        final DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        DatabaseReference users = root.child("Users");
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (snapshot.hasChild(UserName.getText().toString())) {
                   Log.d("Tuan","KO dc dau");
                    Toast.makeText(MainActivity.this, "Sorry, The Name has been taken.",Toast.LENGTH_SHORT).show();


                }// end if go to else
                else{
                    Users m = new Users("0", UserName.getText().toString(), "0","0");
                    root.child("Users").child(UserName.getText().toString()).setValue(m);

                    Log.d("Tuan","KO dc dau");
                    Toast.makeText(MainActivity.this, "Register succeed.",Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
