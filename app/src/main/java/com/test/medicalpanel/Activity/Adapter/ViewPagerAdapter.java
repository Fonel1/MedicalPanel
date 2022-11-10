package com.test.medicalpanel.Activity.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.test.medicalpanel.Activity.Fragments.AppointmentFragmentStep1;
import com.test.medicalpanel.Activity.Fragments.AppointmentFragmentStep2;
import com.test.medicalpanel.Activity.Fragments.AppointmentFragmentStep3;
import com.test.medicalpanel.Activity.Fragments.AppointmentFragmentStep4;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private String[] tabtitles = new String[] {"Clinc", "Doctor", "Date", "Confirm"};

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {

        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position)
        {
            case 0: return AppointmentFragmentStep1.getInstance();
            case 1: return AppointmentFragmentStep2.getInstance();
            case 2: return AppointmentFragmentStep3.getInstance();
            case 3: return AppointmentFragmentStep4.getInstance();
        }

        return null;
    }

    @Override
    public int getItemCount() {

        return 4;
    }
}
