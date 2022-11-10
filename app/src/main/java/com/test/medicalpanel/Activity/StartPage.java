package com.test.medicalpanel.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.test.medicalpanel.R;

public class StartPage extends AppCompatActivity {
private ConstraintLayout startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startpage);


        startBtn = findViewById(R.id.strBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartPage.this, LoginActivity.class));
            }
        });
    }
}