package com.example.hospice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {


    EditText emailEditText, passwordEditText;
    Button logInButton;
    TextView createAccountTextView, emailValidationError, passwordValidationError;

    private FirebaseAuth mAuth;
    String TAG = "SignInActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_layout);
        initViews();
        setUpListeners();
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //go to empty record
        //updateUI(currentUser);
    }


    private void initViews(){
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        logInButton = findViewById(R.id.logInButton);

        emailValidationError = findViewById(R.id. emailValidationError);
        passwordValidationError = findViewById(R.id. passwordValidationError);

        createAccountTextView = findViewById(R.id.createAccount);
    }

    private boolean validateEmail(){
        String getEmail = emailEditText.getText().toString();
        Pattern p = Pattern.compile(Utils.regEx, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(getEmail);
        if(!m.find()){
            emailValidationError.setText("Email address is invalid");
            emailValidationError.setVisibility(View.VISIBLE);
            return false;
        }
        else{
            emailValidationError.setVisibility(View.GONE);
            return true;
        }
    }
    private boolean validatePassword(){
        String getPassword = passwordEditText.getText().toString();
        Pattern atLeastEightChars = Pattern.compile(".{8,}");
        Matcher m = atLeastEightChars.matcher(getPassword);
        if(!m.find()){
            passwordValidationError.setText("Password must be at least 8 characters");
            passwordValidationError.setVisibility(View.VISIBLE);
            return false;
        }
        else{
            passwordValidationError.setVisibility(View.GONE);
            return true;
        }

    }
    private boolean checkValidation(){
        //DEBUG/////////////////////////////////////////////////////////////////
        Log.d(TAG, "Running form validation");
        //Get email and password
        String getEmail = emailEditText.getText().toString();
        String getPassword = passwordEditText.getText().toString();

        // Check patter for email id
        Pattern p = Pattern.compile(Utils.regEx, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(getEmail);

        //Check if email is valid
        if(!validateEmail()){
            return false;
        }
        /*if(!m.find()){
            emailValidationError.setVisibility(View.VISIBLE);
            return false;
        }*/

        //Check if password field is empty
        else if(getPassword.equals("") || getPassword.length() == 0){
            passwordValidationError.setVisibility(View.VISIBLE);
            return false;
        }
        //valid
        else{
            //DEBUG
            return true;
        }
    }


    private void setUpListeners(){
        logInButton.setOnClickListener(this);
        createAccountTextView.setOnClickListener(this);
        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateEmail();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validatePassword();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logInButton:
                //DEBUG/////////////////////////////////////////////////////////////////
                Log.d(TAG, "Login button pressed");
                if(validateEmail() && validatePassword()){
                    logIn(emailEditText.getText().toString(), passwordEditText.getText().toString());
                }
                break;
            case R.id.createAccount:
                //launch signup activity
                Intent intent = new Intent(SignInActivity.this, MySignUpActivity.class);
                startActivity(intent);

        }
    }
    private void logIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                            // ...
                        }

                        // ...
                    }
                });
    }
}