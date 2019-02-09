package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button strike;
    private Button ball;
    private TextView lbl1;
    private TextView lbl2;
    private TextView lbl3;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String SAVED_OUTS = "Total Outs";
    private String totalOuts;
    private ActionMode mActionMode;
    private static int strikes = 0;
    private static int balls = 0;
    private static int outs = 0;
    private String string;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu:
                Toast.makeText(this, "Menu selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.reset:
                dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialog = dialogBuilder.create();
                alertDialog.setMessage("The count has been reset");
                alertDialog.show();
                strikes = 0;
                balls = 0;
                outs = 0;
                lbl1.setText("0");
                lbl2.setText("0");
                lbl3.setText("0");
                return true;
            case R.id.about:
                openAbout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        strike = (Button) findViewById(R.id.strike);
        ball = (Button) findViewById(R.id.ball);
        lbl1 = (TextView) findViewById(R.id.strikeCount);
        lbl2 = (TextView) findViewById(R.id.ballCount);
        lbl3 = (TextView) findViewById(R.id.outCount);
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
                    outs++;
                    string = Integer.toString(outs);
                    lbl3.setText(string);
                }
                saveData();
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
        strike.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(mActionMode != null){
                    return false;
                }
                mActionMode = startSupportActionMode(mActionModeCallback);
                saveData();
                return true;
            }
        });
        loadData();
        updateData();
        if(savedInstanceState != null){
            strikes = savedInstanceState.getInt("strikes");
            balls = savedInstanceState.getInt("balls");
            lbl1.setText(String.valueOf(strikes));
            lbl2.setText(String.valueOf(balls));
        }
    }
    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu context_menu) {
            actionMode.getMenuInflater().inflate(R.menu.context_menu, context_menu);
            actionMode.setTitle("Strike or Ball?");
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu context_menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            switch(menuItem.getItemId()) {
                case R.id.strike:
                    strikes++;
                    string = Integer.toString(strikes);
                    lbl1.setText(string);
                    if (strikes >= 3) {
                        alertDialog.setMessage("Out!");
                        alertDialog.show();
                        strikes = 0;
                        balls = 0;
                        lbl1.setText("0");
                        lbl2.setText("0");
                        outs++;
                        string = Integer.toString(outs);
                        lbl3.setText(string);
                    }
                    actionMode.finish();
                    return true;
                case R.id.ball:
                    balls++;
                    string = Integer.toString(balls);
                    lbl2.setText(string);
                    if (balls >= 4) {
                        alertDialog.setMessage("Walk!");
                        alertDialog.show();
                        strikes = 0;
                        balls = 0;
                        lbl1.setText("0");
                        lbl2.setText("0");
                    }
                    actionMode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            mActionMode = null;
        }
    };
    public void openAbout(){
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }
    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVED_OUTS, lbl3.getText().toString());

        editor.apply();
        //Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        totalOuts = sharedPreferences.getString(SAVED_OUTS, "");
    }

    public void updateData(){
        lbl3.setText(totalOuts);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("strikes", strikes);
        outState.putInt("balls", balls);
    }

}
