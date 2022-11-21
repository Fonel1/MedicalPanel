package com.test.medicalpanel.Activity.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.test.medicalpanel.Activity.Common.Common;
import com.test.medicalpanel.Activity.Model.AppointmentInformation;
import com.test.medicalpanel.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class AppointmentFragmentStep4 extends Fragment {

    SimpleDateFormat simpleDateFormat;
    LocalBroadcastManager localBroadcastManager;
    Unbinder unbinder;

    @BindView(R.id.txt_appointment_doctor_text)
    TextView txt_appointment_doctor_text;
    @BindView(R.id.txt_appointment_data_text)
    TextView txt_appointment_data_text;
    @BindView(R.id.txt_clinic_address)
    TextView txt_clinic_address;
    @BindView(R.id.txt_clinic_name)
    TextView txt_clinic_name;
    @BindView(R.id.txt_clinic_open_hours)
    TextView txt_clinic_open_hours;
    //@BindView(R.id.txt_clinic_phone)
    //TextView txt_clinic_phone;
    @BindView(R.id.txt_clinic_website)
    TextView txt_clinic_website;

    @OnClick(R.id.btn_confirm)
    void confirmAppointment() {

        //booking information
        AppointmentInformation appointmentInformation = new AppointmentInformation();

        appointmentInformation.setDoctorId(Common.currentDoctor.getDoctorId());
        appointmentInformation.setDoctorName(Common.currentDoctor.getName());
        appointmentInformation.setPatientPhone(Common.currentUser.getPhoneNumber());
        appointmentInformation.setPatientName(Common.currentUser.getName());
        appointmentInformation.setClinicAddress(Common.currentClinic.getAddress());
        appointmentInformation.setClinicId(Common.currentClinic.getClinicId());
        appointmentInformation.setClinicName(Common.currentClinic.getName());
        appointmentInformation.setData(new StringBuilder(Common.convertDataSlotToString(Common.currentDataSlot))
                .append(" at ")
                .append(simpleDateFormat.format(Common.currentData.getTime())).toString());
        appointmentInformation.setSlot(Long.valueOf(Common.currentDataSlot));

        //Submit to doctor
        DocumentReference appointmentDate = FirebaseFirestore.getInstance()
                .collection("AllClinics")
                .document(Common.city)
                .collection("Clinics")
                .document(Common.currentClinic.getClinicId())
                .collection("Doctors")
                .document(Common.currentDoctor.getDoctorId())
                .collection(Common.simpleDateFormat.format(Common.currentData.getTime()))
                .document(String.valueOf(Common.currentDataSlot));

        //Write data
        appointmentDate.set(appointmentInformation)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        resetStaticData();
                        getActivity().finish(); //close activity
                        Toast.makeText(getContext(), "Success!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resetStaticData() {
        Common.step = 0;
        Common.currentDataSlot = -1;
        Common.currentClinic = null;
        Common.currentDoctor = null;
        Common.currentData.add(Calendar.DATE, 0);
    }

    BroadcastReceiver confirmAppointmentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setData();
        }
    };

    private void setData() {
        txt_appointment_doctor_text.setText(Common.currentDoctor.getName());
        txt_appointment_data_text.setText(new StringBuilder(Common.convertDataSlotToString(Common.currentDataSlot))
        .append(" at ")
        .append(simpleDateFormat.format(Common.currentData.getTime())));

        txt_clinic_address.setText(Common.currentClinic.getAddress());
        txt_clinic_name.setText(Common.currentClinic.getName());
        txt_clinic_website.setText(Common.currentClinic.getWebsite());
        txt_clinic_open_hours.setText(Common.currentClinic.getOpenHours());

    }

    static AppointmentFragmentStep4 instance;
    public static AppointmentFragmentStep4 getInstance() {
        if (instance == null)
            instance = new AppointmentFragmentStep4();
        return instance;
    }


    public AppointmentFragmentStep4() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        simpleDateFormat = new SimpleDateFormat("dd_MM_yyyy");

        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        localBroadcastManager.registerReceiver(confirmAppointmentReceiver, new IntentFilter(Common.KEY_CONFIRM_APPOINTMENT));

    }

    @Override
    public void onDestroy() {
        localBroadcastManager.unregisterReceiver(confirmAppointmentReceiver);
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.fragment_appointment_step4, container, false);
        unbinder = ButterKnife.bind(this, itemView);

        return itemView;
    }
}