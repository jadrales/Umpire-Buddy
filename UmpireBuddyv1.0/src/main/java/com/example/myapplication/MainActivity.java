package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;

public class MainActivity extends AppCompatActivity {
    private Button strike;
    private Button ball;
    private TextView lbl1;
    private TextView lbl2;
    private static int strikes = 0;
    private static int balls = 0;
    private String string;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        strike = (Button) findViewById(R.id.button1);
        ball = (Button) findViewById(R.id.button2);
        lbl1 = (TextView) findViewById(R.id.textView1);
        lbl2 = (TextView) findViewById(R.id.textView2);
        dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialog = dialogBuilder.create();



        strike.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                strikes++;
                string = Integer.toString(strikes);
                lbl1.setText(string);
                if(strikes >= 3) {
                    alertDialog.setMessage("Out!");
                    alertDialog.show();
                    strikes = 0;
                    balls = 0;
                    lbl1.setText("0");
                    lbl2.setText("0");
                }
            }
        });

        ball.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                balls++;
                string = Integer.toString(balls);
                lbl2.setText(string);
                if(balls >= 4) {
                    alertDialog.setMessage("Walk!");
                    alertDialog.show();
                    strikes = 0;
                    balls = 0;
                    lbl1.setText("0");
                    lbl2.setText("0");
                }
            }
        });
    }

}
