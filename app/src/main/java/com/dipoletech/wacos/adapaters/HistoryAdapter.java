package com.dipoletech.wacos.adapaters;/**
 * Created by DABBY(3pleMinds) on 21-Jan-16.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dipoletech.wacos.R;
import com.dipoletech.wacos.model.HistoryItem;

import java.util.List;

/**
 * DABBY(3pleMinds) 21-Jan-16 9:30 PM 2016 01
 * 21 21 30 Wacos
 **/
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.Item> {

    private final Context context;
    private final List<HistoryItem> historyItemList;

    public HistoryAdapter(Context context, List<HistoryItem> historyItemList)
    {

        this.context = context;
        this.historyItemList = historyItemList;
    }
    @Override
    public Item onCreateViewHolder(ViewGroup parent, int viewType) {
        //get the inflater
        LayoutInflater inflater = LayoutInflater.from(getContext());

        //get the view
        View view = inflater.inflate(R.layout.history_item,parent,false);

        Item item = new Item(view);

        return item;
    }

    @Override
    public void onBindViewHolder(Item holder, int position) {
        holder.dateTv.setText(String.valueOf(historyItemList.get(position).getDate()));
        holder.amountTv.setText(historyItemList.get(position).getAmount());
        holder.purposeTv.setText(historyItemList.get(position).getPurpose());
        holder.myIdTv.setText(historyItemList.get(position).getMyId());

    }

    @Override
    public int getItemCount() {
        return historyItemList==null ? 1:historyItemList.size();
    }

    public Context getContext() {
        return context;
    }

    class Item extends RecyclerView.ViewHolder{
        private TextView dateTv,purposeTv,amountTv,myIdTv;

        public Item(View itemView) {
            super(itemView);

            dateTv = (TextView) itemView.findViewById(R.id.item_date);
            purposeTv = (TextView) itemView.findViewById(R.id.pupose_tv);
            amountTv = (TextView) itemView.findViewById(R.id.amount_tv);
            myIdTv = (TextView) itemView.findViewById(R.id.id_tv);
        }
    }
}

