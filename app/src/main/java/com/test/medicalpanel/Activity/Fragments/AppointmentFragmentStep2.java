package com.test.medicalpanel.Activity.Fragments;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.medicalpanel.Activity.Adapter.DoctorAdapter;
import com.test.medicalpanel.Activity.Common.Common;
import com.test.medicalpanel.Activity.Common.SpacesItemDecoration;
import com.test.medicalpanel.Activity.Model.Doctor;
import com.test.medicalpanel.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class AppointmentFragmentStep2 extends Fragment {

    Unbinder unbinder;
    LocalBroadcastManager localBroadcastManager;

    RecyclerView recyclerDoctor;

    @BindView(R.id.recycler_doctor)
    RecyclerView recycler_doctor;

    private final BroadcastReceiver doctorDoneReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<Doctor> doctorArrayList = intent.getParcelableArrayListExtra(Common.KEY_DOCTOR_LOAD_DONE);
            DoctorAdapter adapter = new DoctorAdapter(getContext(),doctorArrayList);
            recycler_doctor.setAdapter(adapter);
        }
    };

    static AppointmentFragmentStep2 instance;

    public static AppointmentFragmentStep2 getInstance() {
        if (instance == null)
            instance = new AppointmentFragmentStep2();
        return instance;
    }


    public AppointmentFragmentStep2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        localBroadcastManager.registerReceiver(doctorDoneReceiver, new IntentFilter(Common.KEY_DOCTOR_LOAD_DONE));

        }

    @Override
    public void onDestroy() {
        localBroadcastManager.unregisterReceiver(doctorDoneReceiver);
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_appointment_step2, container, false);

        unbinder = ButterKnife.bind(this, fragmentView);

        initView();

        return fragmentView;
    }

    private void initView() {
        recycler_doctor.setHasFixedSize(true);
        recycler_doctor.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recycler_doctor.addItemDecoration(new SpacesItemDecoration(4));
    }
}