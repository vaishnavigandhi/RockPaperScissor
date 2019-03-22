package com.example.levantuan.finalproject_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.SensorManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.seismic.ShakeDetector;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.Random;

public class gestureActivity extends AppCompatActivity implements ShakeDetector.Listener {
    boolean lightOn = false;
    int timer = 3;
    ImageView iv;
    FirebaseDatabase database;
    DatabaseReference root;
    private ChildEventListener mChildEventListener;
    ArrayList<String> ListOfChoice = new ArrayList<String>();
    SharedPreferences prefs;

    public static final String PREFERENCES_NAME = "RockPaperScissor";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        prefs = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);


        database = FirebaseDatabase.getInstance();
        root = database.getReference();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);
        //1. create a bunch of nonsense variables
        //this  a mandatory funcion
        // it's required bt the ShakeDetector.listener class
        SensorManager manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        ShakeDetector detector = new ShakeDetector(this);
        detector.start(manager);


        iv = (ImageView) findViewById(R.id.iv);


    }


    String currentChoice;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void hearShake() {
        //2. this function AUTOAGICALL gets called
        //every time you shake the phone

        //print out a message when the phone shakes

        try {

            if (timer == 3) {
                timer = timer - 1;

                Toast.makeText(this, "Shaking 1 time", Toast.LENGTH_SHORT).show();

                lightOn = false;
            } else if (timer == 2) {
                timer = timer - 1;
                Toast.makeText(this, "Shaking 2 times", Toast.LENGTH_SHORT).show();

                lightOn = false;
            } else if (timer == 1) {
                timer = timer - 1;

            } else if (timer == 0) {
                timer = timer - 1;

                Random r = new Random();
                int randomchoice = r.nextInt(3);


                //first cpu choice....
                if (randomchoice == 0) {
                    currentChoice = "rock";
                    iv.setImageResource(R.drawable.rock);

                } else if (randomchoice == 1) {
                    currentChoice = "paper";
                    iv.setImageResource(R.drawable.paper);

                } else if (randomchoice == 2) {
                    currentChoice = "scissor";
                    iv.setImageResource(R.drawable.scissor);
                }
                //update current choice to firebase....
                DatabaseReference zonesRef = FirebaseDatabase.getInstance().getReference("CurrentGame");
                DatabaseReference zone1Ref = zonesRef.child(prefs.getString("random", "")).child(prefs.getString("GameKey", "")).child("choice");
                zone1Ref.setValue(currentChoice);
                Log.d("successfully!!!", "upated values!!");


                DatabaseReference Ref = FirebaseDatabase.getInstance().getReference("CurrentGame");
                //Toast.makeText(gestureActivity.this,prefs.getString("random",""),Toast.LENGTH_SHORT).show();
                DatabaseReference Ref1 = Ref.child("51002");

                Ref1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //String playerChoice = dataSnapshot.child("choice").getValue(String.class);

                        for (DataSnapshot zoneSnapshot : dataSnapshot.getChildren()) {
                            String temp = zoneSnapshot.child("choice").getValue(String.class);

                            if (temp.equals("rock")) {
                                //String key = zoneSnapshot.child("choice").push().getKey();
                                dataSnapshot.getRef().setValue("Gstatus", "lose");
                                Log.d("successfulll", "win");

                            }
                            // ListOfChoice.add(zoneSnapshot.child("choice").getValue(String.class));


                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                //fetch value of all choices from firebase........
                DatabaseReference fetchChoice = FirebaseDatabase.getInstance().getReference("CurrentGame");
                DatabaseReference fetchChoice1 = fetchChoice.child(prefs.getString("random", ""));

                fetchChoice1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot zoneSnapshot : dataSnapshot.getChildren()) {
                            Log.d("choice", zoneSnapshot.child("choice").getValue(String.class));
                            ListOfChoice.add(zoneSnapshot.child("choice").getValue(String.class));


                        }

                        String myChoice = ListOfChoice.get(0);
                        String cpu1Choice = ListOfChoice.get(1);
                        String cpu2Choice = ListOfChoice.get(2);
                        String result = null;


                        Log.d("choice1", myChoice);
                        Log.d("choice2", cpu1Choice);
                        Log.d("choice3", cpu2Choice);


//Draw conditions for 3 players..........

                        if (myChoice.equals("rock") && cpu1Choice.equals("paper") && cpu2Choice.equals("scissor")) {
                            result = "Draw";
                        } else if (myChoice.equals("rock") && cpu1Choice.equals("scissor") && cpu2Choice.equals("paper")) {
                            result = "Draw";
                        } else if (myChoice.equals("paper") && cpu1Choice.equals("rock") && cpu2Choice.equals("scissor")) {
                            result = "Draw";
                        } else if (myChoice.equals("paper") && cpu1Choice.equals("scissor") && cpu2Choice.equals("rock")) {
                            result = "Draw";
                        } else if (myChoice.equals("scissor") && cpu1Choice.equals("paper") && cpu2Choice.equals("rock")) {
                            result = "Draw";
                        } else if (myChoice.equals("scissor") && cpu1Choice.equals("rock") && cpu2Choice.equals("paper")) {
                            result = "Draw";
                        } else if (myChoice.equals("rock") && cpu1Choice.equals("rock") && cpu2Choice.equals("rock")) {
                            result = "Draw";
                        } else if (myChoice.equals("paper") && cpu1Choice.equals("paper") && cpu2Choice.equals("paper")) {
                            result = "Draw";
                        } else if (myChoice.equals("scissor") && cpu1Choice.equals("scissor") && cpu2Choice.equals("scissor")) {
                            result = "Draw";
                        }
                        //rock wins conditions........
                        else if (myChoice.equals("rock") && cpu1Choice.equals("scissor") && cpu2Choice.equals("scissor")) {
                            result = "rock win";
                        } else if (myChoice.equals("scissor") && cpu1Choice.equals("scissor") && cpu2Choice.equals("rock")) {
                            result = "rock wins";
                        } else if (myChoice.equals("scissor") && cpu1Choice.equals("rock") && cpu2Choice.equals("scissor")) {
                            result = "rock win";
                        }
                        //paper wins conditions........
                        else if (myChoice.equals("paper") && cpu1Choice.equals("rock") && cpu2Choice.equals("rock")) {
                            result = "paper win";
                        } else if (myChoice.equals("rock") && cpu1Choice.equals("paper") && cpu2Choice.equals("rock")) {
                            result = "paper  wins";
                        } else if (myChoice.equals("rock") && cpu1Choice.equals("rock") && cpu2Choice.equals("paper")) {
                            result = "paper win";
                        }
                        //scissor wins conditions........
                        else if (myChoice.equals("scissor") && cpu1Choice.equals("paper") && cpu2Choice.equals("paper")) {
                            result = "scissor win";
                        } else if (myChoice.equals("paper") && cpu1Choice.equals("scissor") && cpu2Choice.equals("paper")) {
                            result = "scissor wins";
                        } else if (myChoice.equals("paper") && cpu1Choice.equals("paper") && cpu2Choice.equals("scissor")) {
                            result = "scissor wins";
                        }
//rock rematch conditions......

                        else if (myChoice.equals("rock") && cpu1Choice.equals("paper") && cpu2Choice.equals("paper")) {
                            Toast.makeText(gestureActivity.this, "tie between paper", Toast.LENGTH_SHORT).show();
                            Toast.makeText(gestureActivity.this, "Rock Lost", Toast.LENGTH_SHORT).show();


//                                    Intent intent = new Intent(gestureActivity.this, gesture2Activity.class);
//                                    startActivity(intent);


                        } else if (myChoice.equals("paper") && cpu1Choice.equals("rock") && cpu2Choice.equals("paper")) {
                            Toast.makeText(gestureActivity.this, "tie between paper", Toast.LENGTH_SHORT).show();
                            Toast.makeText(gestureActivity.this, "Rock Lost", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(gestureActivity.this, gesture2Activity.class);
                            startActivity(intent);


                        } else if (myChoice.equals("paper") && cpu1Choice.equals("paper") && cpu2Choice.equals("rock")) {
                            Toast.makeText(gestureActivity.this, "tie between paper", Toast.LENGTH_SHORT).show();
                            Toast.makeText(gestureActivity.this, "Rock Lost", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(gestureActivity.this, gesture2Activity.class);
                            startActivity(intent);

                        }

                        //paper conditions.....

                        else if (myChoice.equals("paper") && cpu1Choice.equals("scissor") && cpu2Choice.equals("scissor")) {
                            Toast.makeText(gestureActivity.this, "tie between scissor", Toast.LENGTH_SHORT).show();
                            Toast.makeText(gestureActivity.this, "paper Lost", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(gestureActivity.this, gesture2Activity.class);
                            startActivity(intent);


                        } else if (myChoice.equals("scissor") && cpu1Choice.equals("paper") && cpu2Choice.equals("scissor")) {
                            Toast.makeText(gestureActivity.this, "tie between scissor", Toast.LENGTH_SHORT).show();
                            Toast.makeText(gestureActivity.this, "paper Lost", Toast.LENGTH_SHORT).show();


                            Intent intent = new Intent(gestureActivity.this, gesture2Activity.class);
                            startActivity(intent);

                        } else if (myChoice.equals("scissor") && cpu1Choice.equals("scissor") && cpu2Choice.equals("paper")) {
                            Toast.makeText(gestureActivity.this, "tie between scissor", Toast.LENGTH_SHORT).show();
                            Toast.makeText(gestureActivity.this, "paper Lost", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(gestureActivity.this, gesture2Activity.class);
                            startActivity(intent);

                        }

//scissor conditions...........

                        else if (myChoice.equals("scissor") && cpu1Choice.equals("rock") && cpu2Choice.equals("rock")) {
                            Toast.makeText(gestureActivity.this, "tie between rock", Toast.LENGTH_SHORT).show();
                            Toast.makeText(gestureActivity.this, "scissor Lost", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(gestureActivity.this, gesture2Activity.class);
                            startActivity(intent);


                        } else if (myChoice.equals("rock") && cpu1Choice.equals("scissor") && cpu2Choice.equals("rock")) {
                            Toast.makeText(gestureActivity.this, "tie between rock", Toast.LENGTH_SHORT).show();
                            Toast.makeText(gestureActivity.this, "scissor Lost", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(gestureActivity.this, gesture2Activity.class);
                            startActivity(intent);


                        } else if (myChoice.equals("rock") && cpu1Choice.equals("rock") && cpu2Choice.equals("scissor")) {
                            Toast.makeText(gestureActivity.this, "tie between rock", Toast.LENGTH_SHORT).show();
                            Toast.makeText(gestureActivity.this, "scissor Lost", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(gestureActivity.this, gesture2Activity.class);
                            startActivity(intent);
                        }


                        Toast.makeText(gestureActivity.this, result, Toast.LENGTH_SHORT).show();


                    }

//


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }


        } catch (Exception e) {
            Log.d("sakhi", "Error while turning on camera flash");
            Toast.makeText(this, "Error while turning on camera flash", Toast.LENGTH_SHORT).show();

        }


        //2 turn the flash on
    }
}