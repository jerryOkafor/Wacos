package com.dipoletech.wacos.adapaters;/**
 * Created by DABBY(3pleMinds) on 28-Jan-16.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dipoletech.wacos.R;
import com.dipoletech.wacos.model.Post;
import com.dipoletech.wacos.util.Constants;

import java.util.List;

/**
 * DABBY(3pleMinds) 28-Jan-16 11:09 AM 2016 01
 * 28 11 09 Wacos
 **/
public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.Item> {
    private static final int VIEW_TYPE_IN = 100;
    private static final int VIEW_TYPE_OUT = 101;
    private static final int EMPTY_VIEW_TYPE = 102;
    private final String uid;
    private List<Post> posts;
    private Context context;

    public PostsAdapter(Context context, List<Post> posts,String uid)
    {
        this.context = context;
        this.posts = posts;
        this.uid = uid;
    }
    @Override
    public Item onCreateViewHolder(ViewGroup parent, int viewType) {
        //get the inflater
        LayoutInflater inflater = LayoutInflater.from(getContext());
        //get the view
        View view = null;
        if (viewType==VIEW_TYPE_IN) {
            view = inflater.inflate(R.layout.view_type_in, parent, false);
        }
        else if (viewType==VIEW_TYPE_OUT){
            view = inflater.inflate(R.layout.view_type_out, parent, false);

        }else if (viewType==EMPTY_VIEW_TYPE)
        {
            view = inflater.inflate(R.layout.empty_item, parent, false);
        }
        Item item = new Item(view);

        return item;
    }

    @Override
    public int getItemViewType(int position) {
        if(posts.isEmpty())
        {
            return  EMPTY_VIEW_TYPE;
        }else {
            return uid.equals(posts.get(position).getUid()) ? VIEW_TYPE_OUT : VIEW_TYPE_IN;
        }
    }

    @Override
    public void onBindViewHolder(Item holder, int position) {
        if (posts.isEmpty()) {
            holder.emptyTv.setText("No Post to display for now.");

        } else {

            holder.dateTv.setText(Constants.getDateFormat(posts.get(position).getDate()));
            holder.whatTv.setText(posts.get(position).getWhat());
            holder.byTv.setText(posts.get(position).getDisplayName());
            holder.attachCountTev.setText(Constants.getAttachCount(posts.get(position)));
            holder.viewsTv.setText(String.valueOf(posts.get(position).getViews() == 0 ? 0 : posts.get(position).getViews()));
        }
    }


    @Override
    public int getItemCount() {
        return posts.isEmpty() ? 1 : posts.size() ;
    }

    public Context getContext() {
        return context;
    }

    public void swapData(List<Post> ps) {
        posts = ps;
        notifyDataSetChanged();

    }


    public void addData(Post post) {
        posts.add(0, post);
        notifyItemInserted(0);
    }


    public void updateItem(Post post, int i) {
        posts.set(i, post);
        notifyItemChanged(i);
    }

    class Item extends RecyclerView.ViewHolder {
        private TextView attachCountTev;
        private TextView dateTv,byTv,whatTv,viewsTv,emptyTv;

        public Item(View itemView) {
            super(itemView);
            if (posts.isEmpty())
            {
                emptyTv = (TextView) itemView.findViewById(R.id.no_data_tv);

            }else {
                dateTv = (TextView) itemView.findViewById(R.id.post_date);
                whatTv = (TextView) itemView.findViewById(R.id.post_text);
                byTv = (TextView) itemView.findViewById(R.id.post_by);
                attachCountTev = (TextView) itemView.findViewById(R.id.attachment_count_tv);
                viewsTv = (TextView) itemView.findViewById(R.id.views_count_tv);
            }


        }
    }
}
