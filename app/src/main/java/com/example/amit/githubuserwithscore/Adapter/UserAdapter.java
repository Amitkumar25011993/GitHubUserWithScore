package com.example.amit.githubuserwithscore.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amit.githubuserwithscore.Model.User;
import com.example.amit.githubuserwithscore.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amit on 2/14/2018.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder>{
        private List<User.ItemsBean > itemsEntities;




    public UserAdapter(List<User.ItemsBean > itemsEntities) {
        this.itemsEntities = itemsEntities;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserAdapter.MyViewHolder holder, int position) {

        final UserAdapter.MyViewHolder holder1 = holder;

        holder1.name.setText(itemsEntities.get(position).getLogin());
        holder1.score.setText(itemsEntities.get(position).getScore());
    }

    @Override
    public int getItemCount() {
        return itemsEntities.size();
    }



    public void setfilter(List<User.ItemsBean> listitem)
    {
        itemsEntities =new ArrayList<>();
        itemsEntities.addAll(listitem);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, score;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tv_name);
            score = (TextView) view.findViewById(R.id.tv_score);
        }
    }

}
