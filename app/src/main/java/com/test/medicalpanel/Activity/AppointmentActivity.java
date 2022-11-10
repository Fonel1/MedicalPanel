package com.test.medicalpanel.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.test.medicalpanel.Activity.Adapter.ViewPagerAdapter;
import com.test.medicalpanel.Activity.Common.Common;
import com.test.medicalpanel.R;

import butterknife.OnClick;

public class AppointmentActivity extends AppCompatActivity {

    LocalBroadcastManager localBroadcastManager;

    Button btn_next_step;
    Button btn_previous_step;
    LinearLayout linearLayout;

    private ViewPagerAdapter pagerAdapter;
    private String[] tabtitles = new String[] {"Clinic", "Doctor", "Date", "Confirm"};
    ViewPager2 viewPager;
    TabLayout tabLayout;

    //Event
    @OnClick(R.id.btn_previous_step)
    void previousStep(){
        if (Common.step == 3 || Common.step > 0)
        {
            Common.step--;
            viewPager.setCurrentItem(Common.step);
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
            viewPager.setCurrentItem(Common.step);
        }
    }

    private void loadDoctorByClinic(String clinicId) {

    }

    //Broadcast Receiver
    private BroadcastReceiver buttonNextReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Common.currentClinic = intent.getParcelableExtra(Common.KEY_CLINIC_STORE);
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

        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(buttonNextReceiver, new IntentFilter(Common.KEY_ENABLE_BUTTON_NEXT));


        viewPager = findViewById(R.id.view_Pager);
        tabLayout = findViewById(R.id.tabL);
        btn_previous_step = findViewById(R.id.btn_previous_step);
        btn_next_step = findViewById(R.id.btn_next_step);
        linearLayout = findViewById(R.id.layout_buttonApp);

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