package com.test.medicalpanel.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.AccountAuthenticatorActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.common.internal.AccountAccessor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.test.medicalpanel.Activity.Common.Common;
import com.test.medicalpanel.R;

import butterknife.BindView;


public class  MainActivity extends AppCompatActivity {

    Button btnLogOut, btnMakeAnApointment, btn_update_information;
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
        btn_update_information = findViewById(R.id.btn_update_information);

        btnMakeAnApointment.setOnClickListener(view -> {
            startActivity(new Intent(this, AppointmentActivity.class));
        });

        btn_update_information.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, UpdateInformationUser.class));
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