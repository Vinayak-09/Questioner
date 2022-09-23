package com.example.questioner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TopicsAdapter extends RecyclerView.Adapter<TopicsAdapter.MyViewHolderNew> {

    ArrayList<String> topics;
    Context context;
    MyOnClickListener onClickListener;

    public TopicsAdapter(ArrayList<String> topics, Context context, MyOnClickListener onClickListener) {
        this.topics = topics;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MyViewHolderNew onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderNew(LayoutInflater.from(context).inflate(R.layout.item_topic,parent,false));
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderNew holder, int position) {
        holder.topic.setText(topics.get(holder.getAdapterPosition()));
        holder.topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onClick(holder.getAdapterPosition());
            }
        });
    }

    protected static class MyViewHolderNew extends RecyclerView.ViewHolder {

        TextView topic;

        public MyViewHolderNew(@NonNull View itemView) {
            super(itemView);
            topic = itemView.findViewById(R.id.topicTextView);
        }
    }
}
