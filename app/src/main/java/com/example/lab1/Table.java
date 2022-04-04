package com.example.lab1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Table extends AppCompatActivity {


    RecyclerView recyclerView;
    ImageView empty_imageView;
    TextView empty_textView;

    DatabaseHelper myDB;
    ArrayList<String> result_id, result_score, result_date;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        setTitle("Results");

        recyclerView = findViewById(R.id.recyclerView);
        empty_imageView = findViewById(R.id.empty_imageView);
        empty_textView = findViewById(R.id.empty_textView);


        myDB = new DatabaseHelper(Table.this);
        result_id = new ArrayList<>();
        result_score = new ArrayList<>();
        result_date = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(Table.this, result_id, result_score, result_date);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Table.this));
    }

    void storeDataInArrays() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            empty_imageView.setVisibility(View.VISIBLE);
            empty_textView.setVisibility(View.VISIBLE);

        } else {
            while (cursor.moveToNext()) {
                result_id.add(cursor.getString(0));
                result_score.add(cursor.getString(1));
                result_date.add(cursor.getString(2));
            }
            empty_imageView.setVisibility(View.GONE);
            empty_textView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_table, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all) {
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog() {
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete All?");
            builder.setMessage("Are you sure you want to delete all Data?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    DatabaseHelper myDB = new DatabaseHelper(Table.this);
                    myDB.deleteAllData();
                    Toast.makeText(Table.this, "Delete", Toast.LENGTH_SHORT).show();
                    //Refresh activity
                    Intent intent = new Intent(Table.this, Table.class);
                    startActivity(intent);
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.create().show();
        }
    }
}