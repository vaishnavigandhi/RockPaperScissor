package com.example.levantuan.finalproject_android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class joinpage extends AppCompatActivity {

    EditText email,random;
    Button startJoinGame;
    FirebaseDatabase database;
    DatabaseReference root;

    TextView Show;
    SharedPreferences prefs;
    public static final String PREFERENCES_NAME = "RockPaperScissor";



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joinpage);
        prefs = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);



        database = FirebaseDatabase.getInstance();
        root = database.getReference();

        email = (EditText) findViewById(R.id.jemail);
        random = (EditText) findViewById(R.id.jrandom);
        startJoinGame = (Button)findViewById(R.id.startJoinGame);
        Show = findViewById(R.id.textView3);

        startJoinGame.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {




                //fetch the value of status...if status is done then add this values to database otherwise

                final DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference();
                DatabaseReference mostafa = ref1.child("status");

                mostafa.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        final String x = email.getText().toString();
                        String r = random.getText().toString();

                        String st = dataSnapshot.getValue(String.class);

                        if(st.equals("done")){
                            //fetch value of random number from firebase
                            final SharedPreferences.Editor prefEditor = prefs.edit();

                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                            DatabaseReference mostafa = ref.child("GeneratedNumber");

                            mostafa.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    String x = email.getText().toString();
                                    prefEditor.putString("email",x);
                                    String r = random.getText().toString();
                                    prefEditor.putString("random","67058");

                                    String rnumber = dataSnapshot.getValue(String.class);
                                    if(rnumber.equals(r)){


                                        User u = new User(x,0);
                                        DatabaseReference dbTemp = root.child("Users").push();
                                        dbTemp.setValue(u);
                                        String userKey = dbTemp.getKey();
                                        prefEditor.putString("UserKey", userKey);

                                        GameCurrent c = new GameCurrent(x,"noChoice","playing");
                                        DatabaseReference dbtemp1 = root.child("CurrentGame").child(r).push();
                                        dbtemp1.setValue(c);
                                        String GameKey = dbtemp1.getKey();
                                        prefEditor.putString("GameKey",GameKey);

                                        prefEditor.commit();




//                                        DatabaseReference zonesRef = FirebaseDatabase.getInstance().getReference("CurrentGame");
//                                        Log.d("ssss",prefs.getString("random",""));
//                                        DatabaseReference zone1Ref = zonesRef.child("67058").child("choice");
//
//                                        zone1Ref.addValueEventListener(new ValueEventListener() {
//                                            @Override
//                                            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                                                String playerChoice = dataSnapshot.getValue(String.class);
//
//
//                                                Log.d("choicesssssss",playerChoice);
//
////                                                if(playerChoice.equals("rock")){
////
////                                                    dataSnapshot.getRef().child("Gstatus").setValue("lose");
////                                                }
////                                                else{
////                                                    Toast.makeText(gestureActivity.this,"failllll",Toast.LENGTH_SHORT).show();
////
////                                                }
//                                            }
//
//                                            @Override
//                                            public void onCancelled(DatabaseError databaseError) {
//
//                                            }
//                                        });
//



                                        Intent intent = new Intent(joinpage.this, gestureActivity.class);
                                        startActivity(intent);

                                    }else{
                                        Toast.makeText(joinpage.this, "Wrong random number!!!",
                                                Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(joinpage.this, joinpage.class);
                                        startActivity(intent);
                                    }


                                    Show.setText(rnumber);


                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }

                            });

                        }else{


                            Toast.makeText(joinpage.this, "Wait 10 seconds for status to be done!!!",
                                    Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(joinpage.this, joinpage.class);
                            startActivity(intent);


                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });


            }
        });


    }
}