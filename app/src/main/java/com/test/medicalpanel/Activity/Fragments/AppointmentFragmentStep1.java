package com.test.medicalpanel.Activity.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.test.medicalpanel.Activity.Adapter.ClinicAdapter;
import com.test.medicalpanel.Activity.Common.SpacesItemDecoration;
import com.test.medicalpanel.Activity.Interface.IALLSalonLoadListener;
import com.test.medicalpanel.Activity.Interface.IClinicsLoadListener;
import com.test.medicalpanel.Activity.Model.Clinic;
import com.test.medicalpanel.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class AppointmentFragmentStep1 extends Fragment implements IALLSalonLoadListener, IClinicsLoadListener {

    CollectionReference allClinicsRef;
    CollectionReference clinicRef;

    IALLSalonLoadListener iAllSalonLoadListener;
    IClinicsLoadListener iClinicsLoadListener;

    @BindView(R.id.spinner_clinc)
    MaterialSpinner spinnerClinic;
    @BindView(R.id.recycler_clinic)
    RecyclerView recycler_clinic;

    Unbinder unbinder;

    static AppointmentFragmentStep1 instance;

    public static AppointmentFragmentStep1 getInstance() {
        if (instance == null)
            instance = new AppointmentFragmentStep1();
        return instance;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        allClinicsRef = FirebaseFirestore.getInstance().collection("AllClinics");

        iAllSalonLoadListener = this;

        iClinicsLoadListener = this;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_appointment_step1, container, false);

        unbinder = ButterKnife.bind(this, fragmentView);

        initView();
        LoadAllClinics();

        return fragmentView;
    }
//without initView you won't see uploaded date from firebase
    private void initView() {
        recycler_clinic.setHasFixedSize(true);
        recycler_clinic.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recycler_clinic.addItemDecoration(new SpacesItemDecoration(4));
    }

    private void LoadAllClinics() {
        allClinicsRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful())
                        {
                            List<String> list = new ArrayList<>();
                            list.add("Please choose city");
                            for (QueryDocumentSnapshot documentSnapshots:task.getResult())
                                list.add(documentSnapshots.getId());
                            iAllSalonLoadListener.onAllSalonLoadSuccess(list);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iAllSalonLoadListener.onAllSalonLoadFailed(e.getMessage());
            }
        });
    }

    @Override
    public void onAllSalonLoadSuccess(List<String> areaNameList) {
        spinnerClinic.setItems(areaNameList);
        spinnerClinic.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (position > 0)
                {
                    loadClinicOfCity(item.toString());
                } else
                {
                    recycler_clinic.setVisibility(View.GONE);
                }
            }
        });
    }

    private void loadClinicOfCity(String cityName) {

        clinicRef = FirebaseFirestore.getInstance()
                .collection("AllClinics")
                .document(cityName)
                .collection("Clinics");

        clinicRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Clinic> list = new ArrayList<>();
                if (task.isSuccessful())
                {
                    for (QueryDocumentSnapshot documentSnapshot:task.getResult())
                    {
                        Clinic clinic = documentSnapshot.toObject(Clinic.class);
                        clinic.setClinicId(documentSnapshot.getId());
                        list.add(clinic);
                    }
                    iClinicsLoadListener.onClinicsLoadSuccess(list);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iClinicsLoadListener.onClinicsLoadFailed(e.getMessage());
            }
        });
    }

    @Override
    public void onAllSalonLoadFailed(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClinicsLoadSuccess(List<Clinic> clinicList) {
        ClinicAdapter adapter = new ClinicAdapter(getActivity(), clinicList);
        recycler_clinic.setAdapter(adapter);
        recycler_clinic.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClinicsLoadFailed(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}