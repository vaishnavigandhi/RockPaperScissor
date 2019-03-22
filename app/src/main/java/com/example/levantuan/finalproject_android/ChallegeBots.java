package com.example.levantuan.finalproject_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class ChallegeBots extends AppCompatActivity {


        Button rock,paper,scissor;
        ImageView iv_cpu1,iv_cpu2,iv_me;
        String myChoice,cpu1Choice,cpu2Choice,result;
        Random r1,r2;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_challege_bots);

            rock = (Button)findViewById(R.id.rock);
            paper = (Button)findViewById(R.id.paper);
            scissor = (Button)findViewById(R.id.scissor);

            iv_cpu1 = (ImageView) findViewById(R.id.iv_cpu1);
            iv_cpu2 = (ImageView) findViewById(R.id.iv_cpu2);
            iv_me = (ImageView)findViewById(R.id.iv_me);

            r1 = new Random();
            r2 = new Random();

            rock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myChoice = "rock";

                    iv_me.setImageResource(R.drawable.rock);
                    calculate();
                }
            });

            paper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myChoice = "paper";
                    iv_me.setImageResource(R.drawable.paper);
                    calculate();
                }
            });

            scissor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myChoice = "scissor";
                    iv_me.setImageResource(R.drawable.scissor);
                    calculate();
                }
            });
        }

        public void calculate(){
            int cpu1 = r1.nextInt(3);
            int cpu2 = r2.nextInt(3);

            //first cpu choice....
            if(cpu1 == 0){
                cpu1Choice = "rock";
                iv_cpu1.setImageResource(R.drawable.rock);

            }else if(cpu1 == 1){
                cpu1Choice = "paper";
                iv_cpu1.setImageResource(R.drawable.paper);

            }else if(cpu1 == 2){
                cpu1Choice = "scissor";
                iv_cpu1.setImageResource(R.drawable.scissor);
            }

            //second cpu choice...
            if(cpu2 == 0){
                cpu2Choice = "rock";
                iv_cpu2.setImageResource(R.drawable.rock);

            }else if(cpu2 == 1){
                cpu2Choice = "paper";
                iv_cpu2.setImageResource(R.drawable.paper);

            }else if(cpu2 == 2){
                cpu2Choice = "scissor";
                iv_cpu2.setImageResource(R.drawable.scissor);
            }



            //Draw conditions for 3 players..........

            if(myChoice.equals("rock") && cpu1Choice.equals("paper") && cpu2Choice.equals("scissor")){
                result = "Draw";
            }else if(myChoice.equals("rock") && cpu1Choice.equals("scissor") && cpu2Choice.equals("paper")){
                result = "Draw";
            }else if(myChoice.equals("paper") && cpu1Choice.equals("rock") && cpu2Choice.equals("scissor")){
                result = "Draw";
            }else if(myChoice.equals("paper") && cpu1Choice.equals("scissor") && cpu2Choice.equals("rock")){
                result = "Draw";
            }else if(myChoice.equals("scissor") && cpu1Choice.equals("paper") && cpu2Choice.equals("rock")){
                result = "Draw";
            }else if(myChoice.equals("scissor") && cpu1Choice.equals("rock") && cpu2Choice.equals("paper")){
                result = "Draw";
            }else if(myChoice.equals("rock") && cpu1Choice.equals("rock") && cpu2Choice.equals("rock")){
                result = "Draw";
            }else if(myChoice.equals("paper") && cpu1Choice.equals("paper") && cpu2Choice.equals("paper")){
                result = "Draw";
            }else if(myChoice.equals("scissor") && cpu1Choice.equals("scissor") && cpu2Choice.equals("scissor")){
                result = "Draw";
            }
            //rock wins conditions........
            else if(myChoice.equals("rock") && cpu1Choice.equals("scissor") && cpu2Choice.equals("scissor")){
                result = "you win";
            }else if(myChoice.equals("scissor") && cpu1Choice.equals("scissor") && cpu2Choice.equals("rock")){
                result = "cpu 2 wins";
            }else if(myChoice.equals("scissor") && cpu1Choice.equals("rock") && cpu2Choice.equals("scissor")){
                result = "cpu 1 win";
            }
            //paper wins conditions........
            else if(myChoice.equals("paper") && cpu1Choice.equals("rock") && cpu2Choice.equals("rock")){
                result = "you win";
            }else if(myChoice.equals("rock") && cpu1Choice.equals("paper") && cpu2Choice.equals("rock")){
                result = "cpu 1 wins";
            }else if(myChoice.equals("rock") && cpu1Choice.equals("rock") && cpu2Choice.equals("paper")){
                result = "cpu 2 win";
            }
            //scissor wins conditions........
            else if(myChoice.equals("scissor") && cpu1Choice.equals("paper") && cpu2Choice.equals("paper")){
                result = "you win";
            }else if(myChoice.equals("paper") && cpu1Choice.equals("scissor") && cpu2Choice.equals("paper")){
                result = "cpu 1 wins";
            }else if(myChoice.equals("paper") && cpu1Choice.equals("paper") && cpu2Choice.equals("scissor")){
                result = "cpu 2 win";
            }
            //rock rematch conditions......

            else if(myChoice.equals("rock") && cpu1Choice.equals("paper") && cpu2Choice.equals("paper")){
                Toast.makeText(ChallegeBots.this,"tie between cpu1 & cpu2",Toast.LENGTH_SHORT).show();
                iv_me.setImageResource(R.drawable.rock);

                //solving paper tie between CPU1 & CPU2.........
                if(cpu1Choice.equals("rock") && cpu2Choice.equals("paper")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "cpu2 win";
                }else if(cpu1Choice.equals("rock") && cpu2Choice.equals("scissor")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "cpu1 win";
                }else if(cpu1Choice.equals("paper") && cpu2Choice.equals("rock")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "cpu1 win";
                }else if(cpu1Choice.equals("paper") && cpu2Choice.equals("scissor")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "cpu2 win";
                }else if(cpu1Choice.equals("scissor") && cpu2Choice.equals("rock")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "cpu2 win";
                }else if(cpu1Choice.equals("scissor") && cpu2Choice.equals("paper")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "cpu1 win";
                }else if(cpu1Choice.equals("scissor") && cpu2Choice.equals("scissor")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "it's draw";
                }else if(cpu1Choice.equals("paper") && cpu2Choice.equals("paper")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "it's draw";
                }else if(cpu1Choice.equals("rock") && cpu2Choice.equals("rock")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "it's draw";
                }

            }else if(myChoice.equals("paper") && cpu1Choice.equals("rock") && cpu2Choice.equals("paper")){
                Toast.makeText(ChallegeBots.this,"tie between me & cpu2",Toast.LENGTH_SHORT).show();
                iv_cpu1.setImageResource(R.drawable.rock);

                //solving paper tie between me & CPU2.........
                if(myChoice.equals("rock") && cpu2Choice.equals("paper")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "cpu2 win";
                }else if(myChoice.equals("rock") && cpu2Choice.equals("scissor")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "me win";
                }else if(myChoice.equals("paper") && cpu2Choice.equals("rock")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "me win";
                }else if(myChoice.equals("paper") && cpu2Choice.equals("scissor")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "cpu2 win";
                }else if(myChoice.equals("scissor") && cpu2Choice.equals("rock")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "cpu2 win";
                }else if(myChoice.equals("scissor") && cpu2Choice.equals("paper")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "me win";
                }else if(myChoice.equals("scissor") && cpu2Choice.equals("scissor")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "it's draw";
                }else if(myChoice.equals("paper") && cpu2Choice.equals("paper")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "it's draw";
                }else if(myChoice.equals("rock") && cpu2Choice.equals("rock")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "it's draw";
                }

            }else if(myChoice.equals("paper") && cpu1Choice.equals("paper") && cpu2Choice.equals("rock")){
                Toast.makeText(ChallegeBots.this,"tie between me & cpu1",Toast.LENGTH_SHORT).show();
                iv_cpu2.setImageResource(R.drawable.rock);

                //solving paper tie between me & CPU1.........
                if(myChoice.equals("rock") && cpu1Choice.equals("paper")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "cpu2 win";
                }else if(myChoice.equals("rock") && cpu1Choice.equals("scissor")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "me win";
                }else if(myChoice.equals("paper") && cpu1Choice.equals("rock")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "me win";
                }else if(myChoice.equals("paper") && cpu1Choice.equals("scissor")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "cpu2 win";
                }else if(myChoice.equals("scissor") && cpu1Choice.equals("rock")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "cpu2 win";
                }else if(myChoice.equals("scissor") && cpu1Choice.equals("paper")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "me win";
                }else if(myChoice.equals("scissor") && cpu1Choice.equals("scissor")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "it's draw";
                }else if(myChoice.equals("paper") && cpu1Choice.equals("paper")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "it's draw";
                }else if(myChoice.equals("rock") && cpu1Choice.equals("rock")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "it's draw";
                }

            }

            //paper conditions.....

            else if(myChoice.equals("paper") && cpu1Choice.equals("scissor") && cpu2Choice.equals("scissor")){
                Toast.makeText(ChallegeBots.this,"tie between cpu1 & cpu2",Toast.LENGTH_SHORT).show();
                iv_me.setImageResource(R.drawable.paper);

                //solving scissor tie between CPU1 & CPU2.........
                if(cpu1Choice.equals("rock") && cpu2Choice.equals("paper")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "cpu2 win";
                }else if(cpu1Choice.equals("rock") && cpu2Choice.equals("scissor")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "cpu1 win";
                }else if(cpu1Choice.equals("paper") && cpu2Choice.equals("rock")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "cpu1 win";
                }else if(cpu1Choice.equals("paper") && cpu2Choice.equals("scissor")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "cpu2 win";
                }else if(cpu1Choice.equals("scissor") && cpu2Choice.equals("rock")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "cpu2 win";
                }else if(cpu1Choice.equals("scissor") && cpu2Choice.equals("paper")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "cpu1 win";
                }else if(cpu1Choice.equals("scissor") && cpu2Choice.equals("scissor")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "it's draw";
                }else if(cpu1Choice.equals("paper") && cpu2Choice.equals("paper")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "it's draw";
                }else if(cpu1Choice.equals("rock") && cpu2Choice.equals("rock")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "it's draw";
                }

            }else if(myChoice.equals("scissor") && cpu1Choice.equals("paper") && cpu2Choice.equals("scissor")){
                Toast.makeText(ChallegeBots.this,"tie between me & cpu2",Toast.LENGTH_SHORT).show();
                iv_cpu1.setImageResource(R.drawable.paper);

                //solving scissor tie between me & CPU2.........
                if(myChoice.equals("rock") && cpu2Choice.equals("paper")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "cpu2 win";
                }else if(myChoice.equals("rock") && cpu2Choice.equals("scissor")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "me win";
                }else if(myChoice.equals("paper") && cpu2Choice.equals("rock")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "me win";
                }else if(myChoice.equals("paper") && cpu2Choice.equals("scissor")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "cpu2 win";
                }else if(myChoice.equals("scissor") && cpu2Choice.equals("rock")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "cpu2 win";
                }else if(myChoice.equals("scissor") && cpu2Choice.equals("paper")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "me win";
                }else if(myChoice.equals("scissor") && cpu2Choice.equals("scissor")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "it's draw";
                }else if(myChoice.equals("paper") && cpu2Choice.equals("paper")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "it's draw";
                }else if(myChoice.equals("rock") && cpu2Choice.equals("rock")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "it's draw";
                }

            }else if(myChoice.equals("scissor") && cpu1Choice.equals("scissor") && cpu2Choice.equals("paper")){
                Toast.makeText(ChallegeBots.this,"tie between me & cpu1",Toast.LENGTH_SHORT).show();
                iv_cpu2.setImageResource(R.drawable.paper);

                //solving scissor tie between CPU1 & CPU2.........
                if(myChoice.equals("rock") && cpu1Choice.equals("paper")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "cpu2 win";
                }else if(myChoice.equals("rock") && cpu1Choice.equals("scissor")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "me win";
                }else if(myChoice.equals("paper") && cpu1Choice.equals("rock")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "me win";
                }else if(myChoice.equals("paper") && cpu1Choice.equals("scissor")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "cpu2 win";
                }else if(myChoice.equals("scissor") && cpu1Choice.equals("rock")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "cpu2 win";
                }else if(myChoice.equals("scissor") && cpu1Choice.equals("paper")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "me win";
                }else if(myChoice.equals("scissor") && cpu1Choice.equals("scissor")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "it's draw";
                }else if(myChoice.equals("paper") && cpu1Choice.equals("paper")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "it's draw";
                }else if(myChoice.equals("rock") && cpu1Choice.equals("rock")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "it's draw";
                }

            }

            //scissor conditions...........

            else if(myChoice.equals("scissor") && cpu1Choice.equals("rock") && cpu2Choice.equals("rock")){
                Toast.makeText(ChallegeBots.this,"tie between cpu1 & cpu2",Toast.LENGTH_SHORT).show();
                iv_me.setImageResource(R.drawable.scissor);

                //solving rock tie between CPU1 & CPU2.........
                if(cpu1Choice.equals("rock") && cpu2Choice.equals("paper")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "cpu2 win";
                }else if(cpu1Choice.equals("rock") && cpu2Choice.equals("scissor")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "cpu1 win";
                }else if(cpu1Choice.equals("paper") && cpu2Choice.equals("rock")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "cpu1 win";
                }else if(cpu1Choice.equals("paper") && cpu2Choice.equals("scissor")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "cpu2 win";
                }else if(cpu1Choice.equals("scissor") && cpu2Choice.equals("rock")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "cpu2 win";
                }else if(cpu1Choice.equals("scissor") && cpu2Choice.equals("paper")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "cpu1 win";
                }else if(cpu1Choice.equals("scissor") && cpu2Choice.equals("scissor")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "it's draw";
                }else if(cpu1Choice.equals("paper") && cpu2Choice.equals("paper")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "it's draw";
                }else if(cpu1Choice.equals("rock") && cpu2Choice.equals("rock")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "it's draw";
                }

            }else if(myChoice.equals("rock") && cpu1Choice.equals("scissor") && cpu2Choice.equals("rock")){
                Toast.makeText(ChallegeBots.this,"tie between me & cpu2",Toast.LENGTH_SHORT).show();
                iv_cpu1.setImageResource(R.drawable.scissor);

                //solving rock tie between me & CPU2.........
                if(myChoice.equals("rock") && cpu2Choice.equals("paper")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "cpu2 win";
                }else if(myChoice.equals("rock") && cpu2Choice.equals("scissor")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "me win";
                }else if(myChoice.equals("paper") && cpu2Choice.equals("rock")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "me win";
                }else if(myChoice.equals("paper") && cpu2Choice.equals("scissor")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "cpu2 win";
                }else if(myChoice.equals("scissor") && cpu2Choice.equals("rock")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "cpu2 win";
                }else if(myChoice.equals("scissor") && cpu2Choice.equals("paper")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "me win";
                }else if(myChoice.equals("scissor") && cpu2Choice.equals("scissor")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "it's draw";
                }else if(myChoice.equals("paper") && cpu2Choice.equals("paper")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "it's draw";
                }else if(myChoice.equals("rock") && cpu2Choice.equals("rock")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "it's draw";
                }

            }else if(myChoice.equals("rock") && cpu1Choice.equals("rock") && cpu2Choice.equals("scissor")){
                Toast.makeText(ChallegeBots.this,"tie between me & cpu1",Toast.LENGTH_SHORT).show();
                iv_cpu2.setImageResource(R.drawable.scissor);

                //solving rock tie between CPU1 & CPU2.........
                if(myChoice.equals("rock") && cpu1Choice.equals("paper")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "cpu2 win";
                }else if(myChoice.equals("rock") && cpu1Choice.equals("scissor")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "me win";
                }else if(myChoice.equals("paper") && cpu1Choice.equals("rock")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "me win";
                }else if(myChoice.equals("paper") && cpu1Choice.equals("scissor")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "cpu2 win";
                }else if(myChoice.equals("scissor") && cpu1Choice.equals("rock")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "cpu2 win";
                }else if(myChoice.equals("scissor") && cpu1Choice.equals("paper")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "me win";
                }else if(myChoice.equals("scissor") && cpu1Choice.equals("scissor")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "it's draw";
                }else if(myChoice.equals("paper") && cpu1Choice.equals("paper")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "it's draw";
                }else if(myChoice.equals("rock") && cpu1Choice.equals("rock")){
                    //iv_cpu2.setImageResource(R.drawable.scissor);
                    result = "it's draw";
                }

            }


            Toast.makeText(ChallegeBots.this,result,Toast.LENGTH_SHORT).show();

        }
    }