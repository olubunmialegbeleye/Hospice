package com.example.hospice;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewRecordActivity extends AppCompatActivity implements View.OnClickListener {
    DBHelper dbHelper =new DBHelper(this);
    EditText patientName, DOB, occupation, phone, email, address, city, state,
    bpSystolic, bpDiastolic, weight, height;

    Spinner maritalStatus, gender, bloodGroup, genotype;

    TextView patientNameValidation, DOBValidation, occupationValidation, emailValidation, phoneValidation,
            addressValidation, cityValidation, stateValidation, bpSystolicValidation,
            bpDiastolicValidation, weightValidation, heightValidation;

    Button create;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record);
        initComponents();
        setUpOnClickListeners();
        }
        private void initComponents(){
            patientNameValidation = findViewById(R.id.patientNameValidationError);
            DOBValidation = findViewById(R.id.DOBValidationError);
            occupationValidation = findViewById(R.id.occupationValidationError);
            phoneValidation = findViewById(R.id.phoneValidationError);
            emailValidation = findViewById(R.id.emailValidationError);
            addressValidation = findViewById(R.id.addressValidationError);
            cityValidation = findViewById(R.id.cityValidationError);
            stateValidation = findViewById(R.id.stateValidationError);
            //bpSystolicValidation = findViewById(R.id.systolicValidationError);
            //bpDiastolicValidation = findViewById(R.id.diastolicValidationError);
            weightValidation = findViewById(R.id.weightValidationError);
            heightValidation = findViewById(R.id.heightValidationError);

            patientName = findViewById(R.id.patientNameEditText);
            DOB = findViewById(R.id.DOBEditText);
            occupation = findViewById(R.id.occupationEditText);
            phone = findViewById(R.id.phoneEditText);
            email = findViewById(R.id.emailEditText);
            address = findViewById(R.id.addressEditText);
            city = findViewById(R.id.cityEditText);
            state = findViewById(R.id.stateEditText);
            bpSystolic = findViewById(R.id.systolicEditText);
            bpDiastolic = findViewById(R.id.diastolicEditText);
            weight = findViewById(R.id.weightEditText);
            height = findViewById(R.id.heightEditText);

            maritalStatus = findViewById(R.id.maritalStatusSpinner);
            gender = findViewById(R.id.genderSpinner);
            bloodGroup = findViewById(R.id.bloodGroupSpinner);
            genotype = findViewById(R.id.genotypeSpinner);

            ArrayAdapter<CharSequence> spinnerMaritalStatusAdapter =ArrayAdapter.createFromResource((Activity) this, R.array.marital_status_array, android.R.layout.simple_spinner_item);
            spinnerMaritalStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            maritalStatus.setAdapter(spinnerMaritalStatusAdapter);

            ArrayAdapter<CharSequence> spinnerGenderAdapter =ArrayAdapter.createFromResource((Activity) this, R.array.gender_array, android.R.layout.simple_spinner_item);
            spinnerGenderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            gender.setAdapter(spinnerGenderAdapter);

            ArrayAdapter<CharSequence> spinnerBGAdapter =ArrayAdapter.createFromResource((Activity) this, R.array.BG_array, android.R.layout.simple_spinner_item);
            spinnerBGAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            bloodGroup.setAdapter(spinnerBGAdapter);

            ArrayAdapter<CharSequence> spinnerGenotypeAdapter =ArrayAdapter.createFromResource((Activity) this, R.array.genotype_array, android.R.layout.simple_spinner_item);
            spinnerGenotypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            genotype.setAdapter(spinnerGenotypeAdapter);

            create = findViewById(R.id.createButton);
        }
        private void setUpOnClickListeners(){
        create.setOnClickListener(this);
            final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    DOB.setText(year + "-" + String.format("%02d", month+1) + "-" + String.format("%02d", dayOfMonth));
                }
            };
            DOB.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(v.getContext(),
                            date, 1990, 6,6).show();
                }
            });
        }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.createButton:
                createPatientinDB();
                startActivity(new Intent(NewRecordActivity.this, ManageRecordActivity.class));
                break;
        }
    }
    /*private boolean validateInputs(){

    }
    private boolean validatePatientName(){
        String getUsername = patientName.getText().toString();
        Pattern onlyAlphaChars = Pattern.compile("^[a-zA-z][a-zA-Z\\- ]*$", Pattern.CASE_INSENSITIVE);
        Pattern atLeastFourChars = Pattern.compile(".{4,}");
        Matcher m = onlyAlphaChars.matcher(getUsername);
        Matcher n = atLeastFourChars.matcher(getUsername);
        if(!n.find()){
            patientNameValidation.setText("Username is too short.");
            patientNameValidation.setVisibility(View.VISIBLE);
            return false;
        }
        else if(!m.find()){
            patientNameValidation.setText("Username can contain only alphabets.");
            patientNameValidation.setVisibility(View.VISIBLE);
            return false;
        }

        else{
            patientNameValidation.setVisibility(View.GONE);
            return true;
        }
    }
    private boolean validateDOB(){

    }
    private boolean validateOccupation(){

    }
    private boolean validatePhone(){

    }
    private boolean validateAddress(){

    }
    private boolean validateCity(){
    }
    private boolean validateState(){

    }*/
    public void createPatientinDB() {
        Patient p = new Patient(patientName.getText().toString(), occupation.getText().toString(),
                phone.getText().toString(), address.getText().toString(),
                city.getText().toString(), state.getText().toString(),
                email.getText().toString(), LocalDate.parse(DOB.getText()),
                getGenderValue(), getMaritalStatusValue(),
                height.getText().toString(), weight.getText().toString(),
                bpSystolic.getText().toString(), bpDiastolic.getText().toString(),
                getBGValue(), getGenotypeValue());
        dbHelper.createPatient(p);
    }
    public Patient.GENDER getGenderValue(){
        switch (gender.getSelectedItemPosition()){
            case 0:
                return Patient.GENDER.MALE;
            case 1:
                return Patient.GENDER.FEMALE;
            default:
                return null;
        }
    }
    public Patient.MARITAL_STATUS getMaritalStatusValue(){
        switch (maritalStatus.getSelectedItemPosition()){
            case 0:
                return Patient.MARITAL_STATUS.SINGLE;
            case 1:
                return Patient.MARITAL_STATUS.MARRIED;
            case 2:
                return Patient.MARITAL_STATUS.DIVORCED;
            default:
                return null;
        }
    }
    public Patient.BG getBGValue(){
        switch (bloodGroup.getSelectedItemPosition()){
            case 0:
                return Patient.BG.A;
            case 1:
                return Patient.BG.B;
            case 2:
                return Patient.BG.AB;
            case 3:
                return Patient.BG.O;
            default:
                return null;
        }
    }
    public Patient.GENOTYPE getGenotypeValue(){
        switch (genotype.getSelectedItemPosition()){
            case 0:
                return Patient.GENOTYPE.AA;
            case 1:
                return Patient.GENOTYPE.AS;
            case 2:
                return Patient.GENOTYPE.AC;
            case 3:
                return Patient.GENOTYPE.SS;
            default:
                return null;
        }
    }
}