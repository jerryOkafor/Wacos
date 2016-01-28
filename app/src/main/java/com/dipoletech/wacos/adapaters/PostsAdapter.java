package com.dipoletech.wacos.adapaters;/**
 * Created by DABBY(3pleMinds) on 28-Jan-16.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dipoletech.wacos.R;

/**
 * DABBY(3pleMinds) 28-Jan-16 11:09 AM 2016 01
 * 28 11 09 Wacos
 **/
public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.Item> {
    private Context context;

    public PostsAdapter(Context context)
    {
        this.context = context;
    }
    @Override
    public Item onCreateViewHolder(ViewGroup parent, int viewType) {
        //get the inflater
        LayoutInflater inflater = LayoutInflater.from(getContext());

        //get the view
        View view = inflater.inflate(R.layout.history_item, parent, false);

        Item item = new Item(view);

        return item;
    }

    @Override
    public void onBindViewHolder(Item holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public Context getContext() {
        return context;
    }

    class Item extends RecyclerView.ViewHolder {

        public Item(View itemView) {
            super(itemView);
        }
    }
}
