package com.dipoletech.wacos.adapaters;/**
 * Created by DABBY(3pleMinds) on 21-Jan06.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dipoletech.wacos.R;
import com.dipoletech.wacos.model.HistoryItem;
import com.dipoletech.wacos.util.Constants;

import java.util.List;

/**
 * DABBY(3pleMinds) 21-Jan06 9:30 PM 2016 01
 * 21 21 30 Wacos
 **/
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.Item> {

    private static final int EMPTY_VIEW_TYPE = 101;
    private static final int MAIN_VIEW_TYPE = 100;
    private final Context context;
    private List<HistoryItem> historyItemList;

    public HistoryAdapter(Context context, List<HistoryItem> historyItemList)
    {

        this.context = context;
        this.historyItemList = historyItemList;
//        Toast.makeText(getContext(),getItemCount()+"",Toast.LENGTH_LONG).show();
    }
    @Override
    public Item onCreateViewHolder(ViewGroup parent, int viewType) {
        //get the inflater
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = null;
        if (viewType==EMPTY_VIEW_TYPE)        {
            //get the view
            view = inflater.inflate(R.layout.empty_item, parent, false);
        }else if (viewType==MAIN_VIEW_TYPE){
            //get the view
            view = inflater.inflate(R.layout.history_item, parent, false);
        }
        Item item = new Item(view);

        return item;
    }

    @Override
    public void onBindViewHolder(Item holder, int position) {
        if (historyItemList.isEmpty())
        {
            holder.emptyTv.setText("No Item in your History Record.");

        }else{
            holder.dateTv.setText(Constants.getDateFormat(historyItemList.get(position).getDate()));
            holder.amountTv.setText(String.format(getContext().getString(R.string.format_amount), historyItemList.get(position).getAmount()));
            holder.purposeTv.setText(String.format(getContext().getString(R.string.format_purpose), historyItemList.get(position).getPurpose()));
            holder.myIdTv.setText(String.format(getContext().getString(R.string.format_id), historyItemList.get(position).getMyId()));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(historyItemList.isEmpty())
        {
            return EMPTY_VIEW_TYPE;
        }else {
            return MAIN_VIEW_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return historyItemList.isEmpty() ? 1 : historyItemList.size();
    }

    public Context getContext() {
        return context;
    }

    public void swapData(List<HistoryItem> hItems) {
         historyItemList = hItems;
        notifyDataSetChanged();

    }

    public void addData(HistoryItem historyItem) {
        historyItemList.add(0, historyItem);
        notifyItemInserted(0);
    }


    public void updateItem(HistoryItem historyItem, int i) {
        historyItemList.set(i, historyItem);
        notifyItemChanged(i);
    }
    class Item extends RecyclerView.ViewHolder{
        private TextView dateTv,purposeTv,amountTv,myIdTv,emptyTv;

        public Item(View itemView) {
            super(itemView);

            if (historyItemList.isEmpty())
            {
                emptyTv = (TextView) itemView.findViewById(R.id.no_data_tv);

            }else {

                dateTv = (TextView) itemView.findViewById(R.id.item_date);
                purposeTv = (TextView) itemView.findViewById(R.id.purpose_tv);
                amountTv = (TextView) itemView.findViewById(R.id.amount_tv);
                myIdTv = (TextView) itemView.findViewById(R.id.id_tv);
            }
        }
    }
}

