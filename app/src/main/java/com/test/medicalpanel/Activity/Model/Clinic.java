package com.test.medicalpanel.Activity.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Clinic implements Parcelable {
    private String name, address, clinicId;

    public Clinic() {
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getClinicId() {
        return clinicId;
    }

    public void setClinicId(String clinicId) {
        this.clinicId = clinicId;
    }

    public static Creator<Clinic> getCREATOR() {
        return CREATOR;
    }

    protected Clinic(Parcel in) {
        name = in.readString();
        address = in.readString();
        clinicId = in.readString();
    }

    public static final Creator<Clinic> CREATOR = new Creator<Clinic>() {
        @Override
        public Clinic createFromParcel(Parcel in) {
            return new Clinic(in);
        }

        @Override
        public Clinic[] newArray(int size) {
            return new Clinic[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(address);
        parcel.writeString(clinicId);
    }
}
