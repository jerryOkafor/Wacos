package com.dipoletech.wacos.adapaters;/**
 * Created by DABBY(3pleMinds) on 21-Jan-16.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dipoletech.wacos.R;

/**
 * DABBY(3pleMinds) 21-Jan-16 9:30 PM 2016 01
 * 21 21 30 Wacos
 **/
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.Item> {

    private final Context context;

    public HistoryAdapter(Context context)
    {
        this.context = context;
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

    }

    @Override
    public int getItemCount() {
        return 30;
    }

    public Context getContext() {
        return context;
    }

    class Item extends RecyclerView.ViewHolder{

        public Item(View itemView) {
            super(itemView);
        }
    }
}

