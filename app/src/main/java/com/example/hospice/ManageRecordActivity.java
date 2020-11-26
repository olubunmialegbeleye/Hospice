package com.example.hospice;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ManageRecordActivity extends AppCompatActivity {

    DBHelper dbHelper = new DBHelper(this);

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_record);
        initializeRecyclerView();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManageRecordActivity.this, NewRecordActivity.class));
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume() {
        super.onResume();
        initializeRecyclerView();
    }

    public void initializeRecyclerView(){
        RecyclerView recyclerPatientList = findViewById(R.id.recycler_view);
        LinearLayoutManager listLayoutManager = new LinearLayoutManager(this);
        recyclerPatientList.setLayoutManager(listLayoutManager);
        List<Patient> patientList =dbHelper.getPatientList(); //from database
        Log.d("ManageRecord", "DB contains " + patientList.size());
        final PatientListRecyclerAdapter patientListRecyclerAdapter = new PatientListRecyclerAdapter(this, patientList);
        recyclerPatientList.setAdapter(patientListRecyclerAdapter);
    }
}