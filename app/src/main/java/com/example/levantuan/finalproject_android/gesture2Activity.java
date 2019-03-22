package com.example.levantuan.finalproject_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.seismic.ShakeDetector;

import java.util.Random;

public class gesture2Activity extends AppCompatActivity implements ShakeDetector.Listener{

    ImageView iv2;
    int timer =3;
    String currentChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture2);

        iv2 = (ImageView)findViewById(R.id.iv2);
        //Toast.makeText(this, "second gesture class", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hearShake() {

        try {

            if (timer == 3) {
                timer = timer - 1;

                //Toast.makeText(this, "Shaking 1 time", Toast.LENGTH_SHORT).show();


            } else if (timer == 2) {
                timer = timer - 1;
                //Toast.makeText(this, "Shaking 2 times", Toast.LENGTH_SHORT).show();


            } else if (timer == 1) {
                timer = timer - 1;

            } else if (timer == 0) {
                timer = timer - 1;

                Random r = new Random();
                int randomchoice = r.nextInt(3);


                //first cpu choice....
                if (randomchoice == 0) {
                    currentChoice = "rock";
                    iv2.setImageResource(R.drawable.rock);

                } else if (randomchoice == 1) {
                    currentChoice = "paper";
                    iv2.setImageResource(R.drawable.paper);

                } else if (randomchoice == 2) {
                    currentChoice = "scissor";
                    iv2.setImageResource(R.drawable.scissor);
                }
            }
        }catch(Exception e){

        }

    }
}

