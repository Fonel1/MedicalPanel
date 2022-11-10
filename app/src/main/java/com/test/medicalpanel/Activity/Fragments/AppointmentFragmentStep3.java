package com.test.medicalpanel.Activity.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.medicalpanel.R;


public class AppointmentFragmentStep3 extends Fragment {

    static AppointmentFragmentStep3 instance;

    public static AppointmentFragmentStep3 getInstance() {
        if (instance == null)
            instance = new AppointmentFragmentStep3();
        return instance;
    }



    public AppointmentFragmentStep3() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_appointment_step3, container, false);
    }
}