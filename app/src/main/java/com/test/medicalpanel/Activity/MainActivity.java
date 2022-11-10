package com.test.medicalpanel.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.test.medicalpanel.R;


public class MainActivity extends AppCompatActivity {

    Button btnLogOut, btnMakeAnApointment;
    FirebaseAuth mAuth;
    Spinner spinnerSTR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogOut = findViewById(R.id.LogOut);
        mAuth = FirebaseAuth.getInstance();
        spinnerSTR = findViewById(R.id.spinnerSTR);
        populateSpinnerSTR();
        btnMakeAnApointment = findViewById(R.id.btnMakeAppointment);

        btnMakeAnApointment.setOnClickListener(view -> {
            startActivity(new Intent(this, AppointmentActivity.class));
        });

        btnLogOut.setOnClickListener(view -> {
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, StartPage.class));
        });

    }

    private void populateSpinnerSTR() {
        ArrayAdapter<String> STRadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.spinnerSTR));
        STRadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSTR.setAdapter(STRadapter);

        spinnerSTR.setSelection(0, false);

        spinnerSTR.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int position, long row_id) {
                final Intent intent;
                switch (position)
                {
                    case 1:
                        intent = new Intent(MainActivity.this, BloodTestActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(MainActivity.this, RegisterActivity.class);
                        startActivity(intent);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(MainActivity.this, StartPage.class));

        }
    }


}