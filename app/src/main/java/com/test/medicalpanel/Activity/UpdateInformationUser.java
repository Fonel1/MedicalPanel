package com.test.medicalpanel.Activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.test.medicalpanel.R;

import java.util.HashMap;
import java.util.Map;


public class UpdateInformationUser extends AppCompatActivity {


    Button btn_update;
    TextInputEditText edt_email, edt_address, edt_phoneNumber, edt_name;

    FirebaseFirestore mStore;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_information_user);

        mStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        btn_update = findViewById(R.id.btn_update);
        edt_email = findViewById(R.id.edt_email);
        edt_address = findViewById(R.id.edt_address);
        edt_phoneNumber = findViewById(R.id.edt_phoneNumber);
        edt_name = findViewById(R.id.edt_name);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = edt_email.getText().toString();
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void uVoid) {
                        DocumentReference documentReference = mStore.collection("Users").document(user.getUid());
                        Map<String, Object> updated = new HashMap<>();
                        updated.put("email", email);
                        updated.put("address", edt_address.getText().toString());
                        updated.put("phoneNumber", edt_phoneNumber.getText().toString());
                        updated.put("name", edt_name.getText().toString());
                        documentReference.update(updated).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(UpdateInformationUser.this, "Profile Updated!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }
                        });
                        Toast.makeText(UpdateInformationUser.this, "Email is changed", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateInformationUser.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}