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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

public class ChallegePlayer extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference root;
    private ChildEventListener mChildEventListener;
    SharedPreferences prefs;
    public static final String PREFERENCES_NAME = "RockPaperScissor";


    EditText email;
    TextView random;
    Button start;
    TextView textView5,Name;
    ArrayList<String> ListOfEmail= new ArrayList<String>();
    ArrayList<String> ListOfChoice= new ArrayList<String>();
    String result;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        prefs = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);



        database = FirebaseDatabase.getInstance();
        root = database.getReference();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challege_player);


        email = (EditText)findViewById(R.id.email);
        random = (TextView)findViewById(R.id.random);
        start = (Button)findViewById(R.id.startgame);
        Name = (TextView)findViewById(R.id.Player);

        final String getname = getIntent().getExtras().getString("getName");
        final String playergotChallege = getIntent().getExtras().getString("KeyName");

        Name.append(getname);

        textView5 = (TextView)findViewById(R.id.textView5);




        Random r = new Random();
        final int i1 = r.nextInt(45000 - 28) + 28023;

        random.setText(String.valueOf(i1));







        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor prefEditor = prefs.edit();

                DatabaseReference myRef = database.getReference("status");
                myRef.setValue("done");

                DatabaseReference ref = database.getReference("GeneratedNumber");
                ref.setValue(String.valueOf(i1));

//                    final String txtemail = email.getText().toString();

                prefEditor.putString("random", String.valueOf(i1));



                GameCurrent c = new GameCurrent(getname,"noChoice","playing");
                root.child("CurrentGame").child(String.valueOf(i1)).child(getname).setValue(c);

                //send message to players who got challenged
                root.child("Users").child(playergotChallege).child("message").setValue(getname + "Challege you");


                Intent intent = new Intent(ChallegePlayer.this, gestureActivity.class);
                intent.putExtra("getName1",getname);
                startActivity(intent);

                //put condition for checking if email is already exist or not

                mChildEventListener = new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                        User users = dataSnapshot.getValue(User.class);
//                        String txt = users.getEmail();
//
//                        ListOfEmail.add(txt);
//
//
//
//                        Log.d("emailArray", String.valueOf(ListOfEmail));
//                        Log.d("choicearray", String.valueOf(ListOfChoice));


//                        if(!(txtemail.equals(txt))){
//
//                            User u = new User(txtemail,0);
//                            root.child("Users").push().setValue(u);
//
//                            CurrentGame c = new CurrentGame(txtemail,"noChoice");
//                            root.child("CurrentGame").child(String.valueOf(i1)).push().setValue(c);
//
//                        }else{
//
////                            CurrentGame c = new CurrentGame(txtemail,"noChoice");
////                            root.child("CurrentGame").child(String.valueOf(i1)).push().setValue(c);
//
//                            Toast.makeText(createPageActivity.this, "Already existed!!",
//                                    Toast.LENGTH_SHORT).show();
//                        }


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
        });


    }


}
//
