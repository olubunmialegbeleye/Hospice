package com.example.hospice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EmptyRecordActivity extends AppCompatActivity {
    DBHelper dbHelper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_record);
        Button fab = findViewById(R.id.createButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmptyRecordActivity.this, NewRecordActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //check db and if it contains something, move on
        if(!dbHelper.getPatientList().isEmpty()){
            startActivity(new Intent(EmptyRecordActivity.this, ManageRecordActivity.class));
        }
    }
}