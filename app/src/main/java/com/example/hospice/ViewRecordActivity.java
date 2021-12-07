package com.example.hospice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ViewRecordActivity extends AppCompatActivity {

    Patient p;
    TextView textViewName, textViewDOB, textViewOccupation, textViewGender, textViewMaritalStatus,
            textViewPhone, textViewEmail, textViewAddress, textViewCity, textViewState,
            textViewBG, textViewGenotype, textViewWeight, textViewHeight, textViewBP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record); //DEBUG
        initComponents();
        p = (Patient) getIntent().getSerializableExtra("patient object");
        updatePatientInfo(p);
    }

    public void initComponents(){
        textViewName = findViewById(R.id.patientName);
        textViewDOB = findViewById(R.id.DOBTextView);
        textViewOccupation = findViewById(R.id.occupationTextView);
        textViewGender = findViewById(R.id.genderTextView);
        textViewMaritalStatus = findViewById(R.id.maritalStatusTextView);
        textViewPhone = findViewById(R.id.phoneTextView);
        textViewEmail = findViewById(R.id.emailTextView);
        textViewAddress = findViewById(R.id.addressTextView);
        textViewCity = findViewById(R.id.cityTextView);
        textViewState = findViewById(R.id.stateTextView);
        textViewBG = findViewById(R.id.BGTextView);
        textViewGenotype = findViewById(R.id.genotypeTextView);
        textViewWeight = findViewById(R.id.weightTextView);
        textViewHeight = findViewById(R.id.heightTextView);
        textViewBP = findViewById(R.id.bloodPressureTextView);
    }
    public void updatePatientInfo(Patient p){
        textViewName.setText(p.PATIENT_NAME);
        textViewDOB.setText(p.PATIENT_DOB.toString());
        textViewOccupation.setText(p.PATIENT_OCCUPATION);
        textViewGender.setText(p.PATIENT_GENDER);
        textViewMaritalStatus.setText(p.PATIENT_MARITAL_STATUS);
        textViewPhone.setText(p.PATIENT_PHONE);
        textViewEmail.setText(p.PATIENT_EMAIL);
        textViewAddress.setText(p.PATIENT_ADDRESS);
        textViewCity.setText(p.PATIENT_CITY);
        textViewState.setText(p.PATIENT_STATE);
        textViewBG.setText(p.PATIENT_BG);
        textViewGenotype.setText(p.PATIENT_GENOTYPE);
        textViewWeight.setText(p.PATIENT_WEIGHT);
        textViewHeight.setText(p.PATIENT_HEIGHT);
        textViewBP.setText(p.PATIENT_BP_SYSTOLIC + "/" + p.PATIENT_BP_DIASTOLIC);
    }
}