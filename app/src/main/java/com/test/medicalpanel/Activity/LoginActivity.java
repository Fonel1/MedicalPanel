package com.test.medicalpanel.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.test.medicalpanel.R;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText Email;
    TextInputEditText Password;
    TextView btnSignUp;
    Button btnLogin;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email = findViewById(R.id.inputEmail);
        Password = findViewById(R.id.inputPassword);
        btnSignUp = findViewById(R.id.SignUp);
        btnLogin = findViewById(R.id.btnlogin);

        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(view -> {
            loginUser();
        });
        btnSignUp.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

    }

    private void loginUser(){
        String email = Email.getText().toString();
        String password = Password.getText().toString();

        if (TextUtils.isEmpty(email)){
            Email.setError("Email cannot be empty");
            Email.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            Password.setError("Password cannot be empty");
            Password.requestFocus();
        }else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }else{
                        Toast.makeText(LoginActivity.this, "Log in Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}