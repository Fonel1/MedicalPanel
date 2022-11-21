package com.test.medicalpanel.Activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.test.medicalpanel.Activity.Adapter.ViewPagerAdapter;
import com.test.medicalpanel.Activity.Common.Common;
import com.test.medicalpanel.Activity.Model.Doctor;
import com.test.medicalpanel.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AppointmentActivity extends AppCompatActivity {

    LocalBroadcastManager localBroadcastManager;
    CollectionReference doctorRef;

    @BindView(R.id.btn_next_step)
    Button btn_next_step;
    @BindView(R.id.btn_previous_step)
    Button btn_previous_step;

    private ViewPagerAdapter pagerAdapter;
    private String[] tabtitles = new String[] {"Clinic", "Doctor", "Date", "Confirm"};

    @BindView(R.id.view_Pager)
    ViewPager2 viewPager;
    @BindView(R.id.tabL)
    TabLayout tabLayout;

    //Event
    @OnClick(R.id.btn_previous_step)
    void previousStep(){
        if (Common.step == 3 || Common.step > 0)
        {
            Common.step--;
            viewPager.setCurrentItem(Common.step);
            if (Common.step < 3)
            {
                btn_next_step.setEnabled(true);
                setColorButton();
            }
        }
    }

    @OnClick(R.id.btn_next_step)
    void nextClick() {
        if (Common.step < 3 || Common.step == 0)
        {
            Common.step++; //increase
            if (Common.step == 1) //After choose clinic
            {
                if (Common.currentClinic != null)
                    loadDoctorByClinic(Common.currentClinic.getClinicId());
            }
            else if(Common.step == 2) //Pick time slot
            {
                if (Common.currentDoctor != null) //common.currentDoctor to check doctor
                    loadTimeSlotOfDoctor(Common.currentDoctor.getDoctorId());
            }
            else if(Common.step == 3) //Confirm
            {
                if (Common.currentDataSlot != -1)
                    confirmAppointment();
            }
            viewPager.setCurrentItem(Common.step);
        }
    }

    private void confirmAppointment() {
        //sending broadcast to step 4
        Intent intent = new Intent(Common.KEY_CONFIRM_APPOINTMENT);
        localBroadcastManager.sendBroadcast(intent);
    }

    private void loadTimeSlotOfDoctor(String doctorId) {
        //Send Local Broadcast to Fragment step 3
        Intent intent = new Intent(Common.KEY_DISPLAY_DATA_SLOT);
        localBroadcastManager.sendBroadcast(intent);
    }

    private void loadDoctorByClinic(String clinicId) {

        //select all doctor of Clinic
        //   /AllClinics/Krakow/Clinics/0oPxXYXchs7q6JigHmCc/Doctors
        if (!TextUtils.isEmpty(Common.city))
        {
            doctorRef = FirebaseFirestore.getInstance()
                    .collection("AllClinics")
                    .document(Common.city)
                    .collection("Clinics")
                    .document(clinicId)
                    .collection("Doctors");

            doctorRef.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    ArrayList<Doctor> doctors = new ArrayList<>();
                    for (QueryDocumentSnapshot doctorSnapShot:task.getResult())
                    {
                        Doctor doctor = doctorSnapShot.toObject(Doctor.class);
                        doctor.setPassword(""); //Remove password because in client App
                        doctor.setDoctorId(doctorSnapShot.getId()); //getting Id of doctor

                        doctors.add(doctor);
                    }
                    //Send Broadcast to AppointmentStep2 to load Recycler
                    Intent intent = new Intent(Common.KEY_DOCTOR_LOAD_DONE);
                    intent.putParcelableArrayListExtra(Common.KEY_DOCTOR_LOAD_DONE, doctors);
                    localBroadcastManager.sendBroadcast(intent);
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }

    }

    //Broadcast Receiver
    private final BroadcastReceiver buttonNextReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int step = intent.getIntExtra(Common.KEY_STEP, 0);
            if (step == 1)
                Common.currentClinic = intent.getParcelableExtra(Common.KEY_CLINIC_STORE);
            else if (step == 2)
                Common.currentDoctor = intent.getParcelableExtra(Common.KEY_DOCTOR_SELECTED);
            else if (step == 3)
                Common.currentDataSlot = intent.getIntExtra(Common.KEY_DATA_SLOT, -1);

            btn_next_step.setEnabled(true);
            setColorButton();
        }
    };

    @Override
    protected void onDestroy() {
        localBroadcastManager.unregisterReceiver(buttonNextReceiver);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        ButterKnife.bind(AppointmentActivity.this);

        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(buttonNextReceiver, new IntentFilter(Common.KEY_ENABLE_BUTTON_NEXT));

        pagerAdapter = new ViewPagerAdapter(this);

        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(4); // keep state of 4 screen page
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int i) {

                if (i == 0 )
                    btn_previous_step.setEnabled(false);
                else
                    btn_previous_step.setEnabled(true);
                super.onPageSelected(i);

                btn_next_step.setEnabled(false);
                setColorButton();
            }
        });
        //this make swiping impossible
        viewPager.setUserInputEnabled(false);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(tabtitles[position])).attach();

    }

    private void setColorButton() {
        if (btn_next_step.isEnabled())
        {
            btn_next_step.setBackgroundResource(R.color.lightblue);
        }
        else
        {
            btn_next_step.setBackgroundResource(android.R.color.darker_gray);
        }

        if (btn_previous_step.isEnabled())
        {
            btn_previous_step.setBackgroundResource(R.color.lightblue);
        }
        else
        {
            btn_previous_step.setBackgroundResource(android.R.color.darker_gray);
        }
    }
}