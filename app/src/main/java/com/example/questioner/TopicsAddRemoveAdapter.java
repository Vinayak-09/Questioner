package com.example.questioner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TopicsAddRemoveAdapter extends RecyclerView.Adapter<TopicsAddRemoveAdapter.MyViewHolder> {

    ArrayList<String> topics;
    Context context;
    MyOnClickListener myOnClickListener;

    public TopicsAddRemoveAdapter(Context context, ArrayList<String> topics,MyOnClickListener myOnClickListener){
        this.topics = topics;
        this.context = context;
        this.myOnClickListener = myOnClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_add_remove_topic, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.topicNameTextView.setText(topics.get(holder.getAdapterPosition()));
        holder.trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myOnClickListener.onClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    protected static class MyViewHolder extends RecyclerView.ViewHolder{


        TextView topicNameTextView;
        ImageView trash;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            topicNameTextView = itemView.findViewById(R.id.item_add_remove_topic);
            trash = itemView.findViewById(R.id.add_remove_trash);
        }
    }
}
