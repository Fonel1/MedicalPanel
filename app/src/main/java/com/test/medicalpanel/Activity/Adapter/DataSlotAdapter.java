package com.test.medicalpanel.Activity.Adapter;

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

import com.test.medicalpanel.Activity.Common.Common;
import com.test.medicalpanel.Activity.Interface.IRecyclerItemSelectedListener;
import com.test.medicalpanel.Activity.Model.DataSlot;
import com.test.medicalpanel.R;

import java.util.ArrayList;
import java.util.List;

public class DataSlotAdapter extends RecyclerView.Adapter<DataSlotAdapter.MyViewHolder> {

    Context context;
    List<DataSlot> dataSlotList;
    List<CardView> cardViewList;
    LocalBroadcastManager localBroadcastManager;

    public DataSlotAdapter(Context context) {
        this.context = context;
        this.dataSlotList = new ArrayList<>();
        this.localBroadcastManager = LocalBroadcastManager.getInstance(context);
        cardViewList = new ArrayList<>();
    }

    public DataSlotAdapter(Context context, List<DataSlot> dataSlotList) {
        this.context = context;
        this.dataSlotList = dataSlotList;
        this.localBroadcastManager = LocalBroadcastManager.getInstance(context);
        cardViewList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_data_slot, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int i) {
        holder.txt_data_slot.setText(new StringBuilder(Common.convertDataSlotToString(i)).toString());
        if (dataSlotList.size() == 0) //if all positions is available - show list
        {
            holder.card_data_slot.setCardBackgroundColor(context.getColor(android.R.color.white));
            holder.txt_data_slot_description.setText("Available");
            holder.txt_data_slot_description.setTextColor(context.getColor(android.R.color.black));
            holder.txt_data_slot.setTextColor(context.getColor(android.R.color.black));
        }
        else //if position is already full
        {
            for (DataSlot slotValue:dataSlotList)
            {
                //Loop all dates from server and set different color
                int slot = Integer.parseInt(slotValue.getSlot().toString());
                if (slot == i) //If slot == position
                {
                    holder.card_data_slot.setTag(Common.DISABLE_TAG);
                    holder.card_data_slot.setCardBackgroundColor(context.getColor(android.R.color.darker_gray));
                    holder.txt_data_slot_description.setText("Not available");
                    holder.txt_data_slot_description.setTextColor(context.getColor(android.R.color.white));
                    holder.txt_data_slot.setTextColor(context.getColor(android.R.color.white));
                }
            }
        }

        //add all card to list
        if (!cardViewList.contains(holder.card_data_slot))
            cardViewList.add(holder.card_data_slot);

        //checking is slot available

            holder.setiRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
                @Override
                public void onItemSelectedListener(View view, final int position) {
                    //loop card in List
                    for (CardView cardView:cardViewList)
                    {
                        if (cardView.getTag() == null) //available card will be change
                            cardView.setCardBackgroundColor(context.getColor(android.R.color.white));
                    }
                    //selected card color
                    holder.card_data_slot.setCardBackgroundColor(context.getColor(android.R.color.holo_blue_light));

                    //LocalBroadcast gets info about selected card
                    Intent intent = new Intent(Common.KEY_ENABLE_BUTTON_NEXT);
                    intent.putExtra(Common.KEY_DATA_SLOT, position); //put index of card that is selected
                    intent.putExtra(Common.KEY_STEP, 3); //go to step 3
                    localBroadcastManager.sendBroadcast(intent);
                }
            });
        }


    @Override
    public int getItemCount() {
        return Common.DATA_SLOT_TOTAL;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_data_slot, txt_data_slot_description;
        CardView card_data_slot;

        IRecyclerItemSelectedListener iRecyclerItemSelectedListener;
        public void setiRecyclerItemSelectedListener(IRecyclerItemSelectedListener iRecyclerItemSelectedListener) {
            this.iRecyclerItemSelectedListener = iRecyclerItemSelectedListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            card_data_slot = (CardView) itemView.findViewById(R.id.card_data_slot);
            txt_data_slot = (TextView) itemView.findViewById(R.id.txt_data_slot);
            txt_data_slot_description = (TextView) itemView.findViewById(R.id.txt_data_slot_description);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            iRecyclerItemSelectedListener.onItemSelectedListener(view, getAdapterPosition());
        }
    }
}
