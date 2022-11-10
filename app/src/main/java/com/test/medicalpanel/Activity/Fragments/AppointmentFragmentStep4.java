package com.test.medicalpanel.Activity.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.medicalpanel.R;


public class AppointmentFragmentStep4 extends Fragment {

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_appointment_step4, container, false);
    }
}