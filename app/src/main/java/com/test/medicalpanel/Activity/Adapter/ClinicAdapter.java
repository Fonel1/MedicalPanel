package com.test.medicalpanel.Activity.Adapter;

import com.test.medicalpanel.Activity.Common.Common;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.google.android.gms.common.internal.service.Common;
import com.test.medicalpanel.Activity.Interface.IRecyclerItemSelectedListener;
import com.test.medicalpanel.Activity.Model.Clinic;
import com.test.medicalpanel.R;

import java.util.ArrayList;
import java.util.List;

public class ClinicAdapter extends RecyclerView.Adapter<ClinicAdapter.MyViewHolder> {

    Context context;
    List<Clinic> clinicsList;
    List<CardView> cardViewList;
    LocalBroadcastManager localBroadcastManager;

    public ClinicAdapter(Context context, List<Clinic> clinicList) {
        this.context = context;
        this.clinicsList = clinicList;
        cardViewList = new ArrayList<>();
        localBroadcastManager= LocalBroadcastManager.getInstance(context); //by this we give information to app that we already selected clinic
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_clinic, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_clinic_name.setText(clinicsList.get(position).getName());
        holder.txt_clinic_address.setText(clinicsList.get(position).getAddress());

        if (!cardViewList.contains(holder.card_clinic))
            cardViewList.add(holder.card_clinic);

        holder.setiRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
            @Override
            public void onItemSelectedListener(View view, int pos) {
                //set white BG for all card not be selected
                for (CardView cardView:cardViewList)
                    cardView.setCardBackgroundColor(context.getColor(android.R.color.white)); //moze spowodowac blad

                //set selected BG for only selected item
                holder.card_clinic.setCardBackgroundColor(context.getColor(android.R.color.holo_blue_dark));

                //send Broadcast to tell Appointment Activity enable button next
                Intent intent = new Intent(Common.KEY_ENABLE_BUTTON_NEXT);
                intent.putExtra(Common.KEY_CLINIC_STORE, clinicsList.get(pos));
                localBroadcastManager.sendBroadcast(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return clinicsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_clinic_name, txt_clinic_address;
        CardView card_clinic;

        IRecyclerItemSelectedListener iRecyclerItemSelectedListener;

        public void setiRecyclerItemSelectedListener(IRecyclerItemSelectedListener iRecyclerItemSelectedListener) {
            this.iRecyclerItemSelectedListener = iRecyclerItemSelectedListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            card_clinic = (CardView)itemView.findViewById(R.id.card_clinic);
            txt_clinic_address = (TextView) itemView.findViewById(R.id.txt_clinic_address);
            txt_clinic_name = (TextView) itemView.findViewById(R.id.txt_clinic_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            iRecyclerItemSelectedListener.onItemSelectedListener(view, getAdapterPosition());
        }
    }
}
