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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MySignUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText emailEditText, usernameEditText, passwordEditText, confirmPasswordEditText;
    Button signUpButton;
    TextView logInTextView,emailValidationError, usernameValidationError,
            passwordValidationError, confirmPasswordValidationError;

    private FirebaseAuth mAuth;
    String TAG = "SignInActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);
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
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);

        signUpButton = findViewById(R.id.signUpButton);

        logInTextView = findViewById(R.id.signIn);
        emailValidationError = findViewById(R.id. emailValidationError);
        usernameValidationError = findViewById(R.id.usernameValidationError);
        passwordValidationError = findViewById(R.id. passwordValidationError);
        confirmPasswordValidationError = findViewById(R.id.confirmPasswordValidationError);
    }

    private void setUpListeners(){
        signUpButton.setOnClickListener(this);
        logInTextView.setOnClickListener(this);
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

        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateUsername();
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
                validateConfirmPassword();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        confirmPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateConfirmPassword();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signUpButton:
                //under construction
                if(validateEmail() && validateUsername() && validatePassword() && validateConfirmPassword()){
                    signUp(emailEditText.getText().toString(), passwordEditText.getText().toString());
                }
                break;
            case R.id.signIn:
                //launch sign in activity
                Intent intent = new Intent(MySignUpActivity.this, SignInActivity.class);
                startActivity(intent);

        }
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

    private boolean validateUsername(){
        String getUsername = usernameEditText.getText().toString();
        Pattern onlyAlphaChars = Pattern.compile("^[a-zA-z][a-zA-Z ]*$", Pattern.CASE_INSENSITIVE);
        Pattern atLeastFourChars = Pattern.compile(".{4,}");
        Matcher m = onlyAlphaChars.matcher(getUsername);
        Matcher n = atLeastFourChars.matcher(getUsername);
        if(!n.find()){
            usernameValidationError.setText("Username is too short.");
            usernameValidationError.setVisibility(View.VISIBLE);
            return false;
        }
        else if(!m.find()){
            usernameValidationError.setText("Username can contain only alphabets.");
            usernameValidationError.setVisibility(View.VISIBLE);
            return false;
        }

        else{
            usernameValidationError.setVisibility(View.GONE);
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

    private boolean validateConfirmPassword(){
        String getPassword = passwordEditText.getText().toString();
        String getConfirmPassword = confirmPasswordEditText.getText().toString();
        if(!getPassword.equals(getConfirmPassword)){
            confirmPasswordValidationError.setText("Passwords do not match");
            confirmPasswordValidationError.setVisibility(View.VISIBLE);
            return false;
        }
        else{
            confirmPasswordValidationError.setVisibility(View.GONE);
            return true;
        }
    }

    private void signUp(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MySignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }
}