package com.example.muhammed.connect3;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    int activatPlayer=0;
    boolean activateMode=true;
    int [] gameState={2,2,2,2,2,2,2,2,2};
    int [][] winnerPositions={
            {0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}
    };
    public void dropIn(View view){

        ImageView counter=(ImageView)view;
        int tappedCounter=Integer.parseInt(counter.getTag().toString());
       if(gameState[tappedCounter]==2 && activateMode) {

           gameState[tappedCounter] = activatPlayer;
           counter.setTranslationY(-1000f);
           if (activatPlayer == 0) {
               counter.setImageResource(R.drawable.yellow);
               activatPlayer = 1;
           } else {
               counter.setImageResource(R.drawable.red);
               activatPlayer = 0; 
           }

           counter.animate().translationYBy(1000f).rotation(360).setDuration(500);

           for(int [] winnerPosition : winnerPositions){
               if (gameState[winnerPosition[0]]==gameState[winnerPosition[1]]
                   && gameState[winnerPosition[1]]==gameState[winnerPosition[2]]
                   && gameState[winnerPosition[0]]!=2){
                    String winner ="Red";
                   if (gameState[winnerPosition[0]]==0){
                       winner="Yellow";
                   }
                   activateMode=false;
                   LinearLayout linear=(LinearLayout)findViewById(R.id.PlayLayout);
                   TextView msg=(TextView)findViewById(R.id.textView);
                   msg.setText(winner + " has won");
                   linear.animate().alpha(1).setDuration(5000);
                }
               else{
                   boolean gameOver=true;
                   for(int coun :gameState){
                       if (coun==2)
                           gameOver=false;

                   }
                   if (gameOver){
                       LinearLayout linear=(LinearLayout)findViewById(R.id.PlayLayout);
                       TextView msg=(TextView)findViewById(R.id.textView);
                       msg.setText("None has won");
                       linear.animate().alpha(1).setDuration(5000);
                   }
           }


           }
       }
    }

    public void playAgain(View v){
        activateMode=true;
        activatPlayer=0;

        for (int i=0;i<gameState.length;i++){
            gameState[i]=2;

        }
    GridLayout gl=(GridLayout)findViewById(R.id.grid);
        for (int i=0;i<gl.getChildCount();i++){
            ((ImageView)gl.getChildAt(i)).setImageResource(0);
        }
        LinearLayout linear=(LinearLayout)findViewById(R.id.PlayLayout);
        linear.animate().alpha(0).setDuration(1000);
    }

}
