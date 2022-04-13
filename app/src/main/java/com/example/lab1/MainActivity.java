package com.example.lab1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Locale local = new Locale("ru", "RU");

    int i = 0;
    private ImageView cat;
    private TextView count;
    private Button main_btn;
    private ImageView reset;
    private Animation animation;
    Date currentDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Feed the cat");

        cat = findViewById(R.id.cat);
        count = findViewById(R.id.count);
        main_btn = findViewById(R.id.main_button);
        reset = findViewById(R.id.reset);
        //reset = findViewById(R.id.reset);

        count.setText(count.getText().toString().concat(Integer.toString(i)));

        main_btn.setTextColor(Color.parseColor("#FFFFFF"));

        main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                count.setText("Satiety : " + i);
                if ((i != 0) && (i % 15 == 0)) {
                    animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.myrotate);
                    cat.startAnimation(animation);
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateFormat df = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, local);
                currentDate = new Date();
                String time = df.format(currentDate);
                DatabaseHelper myDB = new DatabaseHelper(MainActivity.this);
                myDB.addScore(i, time);
                i = 0;
                count.setText("Satiety : " + i);
                cat.setRotation(0);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void onAbout(MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("about the developer")
                .setMessage("Group : 951006\n" +
                        "Name : Vaneka Dmitry Andreevich\n" +
                        "Lab : number one")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle("ABOUT");
        alert.show();
    }

    public void onShare(MenuItem item) {
        Intent myIntent = new Intent(Intent.ACTION_SEND);
        myIntent.setType("text/plain");
        String shareBody = "Download \\\"Feed the cat\\\"  and start playing with me." +
                " Try to beat my record.";
        String shareSub = "Play with me";
        myIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
        myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(myIntent, "Share"));
    }

    public void onTable(MenuItem item) {
        Intent intent = new Intent(this, Table.class);
        startActivity(intent);
    }

}