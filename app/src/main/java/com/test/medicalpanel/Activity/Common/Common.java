package com.test.medicalpanel.Activity.Common;

import com.test.medicalpanel.Activity.Model.Clinic;
import com.test.medicalpanel.Activity.Model.DataSlot;
import com.test.medicalpanel.Activity.Model.Doctor;
import com.test.medicalpanel.Activity.Model.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Common {

    public static final String KEY_ENABLE_BUTTON_NEXT = "ENABLE_BUTTON_NEXT";
    public static final String KEY_CLINIC_STORE = "CLINIC_SAVE";
    public static final String KEY_DOCTOR_LOAD_DONE = "DOCTOR_LOAD_DONE";
    public static final String KEY_DISPLAY_DATA_SLOT = "DISPLAY_DATA_SLOT";
    public static final String KEY_STEP = "STEP";
    public static final String KEY_DOCTOR_SELECTED = "DOCTOR_SELECTED";
    public static final int DATA_SLOT_TOTAL = 16; //how many terms doctor has in a day
    public static final Object DISABLE_TAG = "DISABLE";
    public static final String KEY_DATA_SLOT = "TIME_SLOT";
    public static final String KEY_CONFIRM_APPOINTMENT = "CONFIRM_APPOINTMENT";
    public static Clinic currentClinic;
    public static int step = 0;
    public static String city = "";
    public static Doctor currentDoctor;
    public static int currentDataSlot = -1;
    public static Calendar currentData = Calendar.getInstance();
    public static User currentUser;
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd_MM_yyyy"); //only in case format key is needed

    public static String convertDataSlotToString(int slot) {
        switch (slot)
        {
            case 0:
                return "9:00 - 9:30";
            case 1:
                return "9:30 - 10:00";
            case 2:
                return "10:00 - 10:30";
            case 3:
                return "10:30 - 11:00";
            case 4:
                return "11:00 - 11:30";
            case 5:
                return "11:30 - 12:00";
            case 6:
                return "12:00 - 12:30";
            case 7:
                return "12:30 - 13:00";
            case 8:
                return "13:00 - 13:30";
            case 9:
                return "13:30 - 14:00";
            case 10:
                return "14:00 - 14:30";
            case 11:
                return "14:30 - 15:00";
            case 12:
                return "15:00 - 15:30";
            case 13:
                return "15:30 - 16:00";
            case 14:
                return "16:00 - 16:30";
            case 15:
                return "16:30 - 17:00";
            default:
                return "Closed";

        }
    }
}
