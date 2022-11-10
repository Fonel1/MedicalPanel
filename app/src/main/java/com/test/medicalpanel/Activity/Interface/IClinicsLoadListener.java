package com.test.medicalpanel.Activity.Interface;

import com.test.medicalpanel.Activity.Model.Clinic;

import java.util.List;

public interface IClinicsLoadListener {
    void onClinicsLoadSuccess(List<Clinic> clinicsList);
    void onClinicsLoadFailed(String message);
}
