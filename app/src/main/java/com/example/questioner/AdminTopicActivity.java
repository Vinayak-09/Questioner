package com.example.questioner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AdminTopicActivity extends AppCompatActivity {

    EditText topicEditText;
    MyDbHelper dbHelper;
    RecyclerView topicsRecyclerView;
    TopicsAddRemoveAdapter topicsAddRemoveAdapter;
    ArrayList<String> topics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_topic);

        dbHelper = new MyDbHelper(this);

        topics = new ArrayList<>();
        getAllTopics();
        topicsAddRemoveAdapter = new TopicsAddRemoveAdapter(this, topics, new MyOnClickListener() {
            @Override
            public void onClick(int position) {
                deleteTopicFromDatabase(topics.get(position),position);
            }
        });

        topicsRecyclerView = findViewById(R.id.adminTopicRecycler);
        topicsRecyclerView.setAdapter(topicsAddRemoveAdapter);
        topicsRecyclerView.setLayoutManager(new LinearLayoutManager(this));





        topicEditText = findViewById(R.id.adminTopicEditText);

        findViewById(R.id.adminTopicAddBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTopicToDatabase(topicEditText.getText().toString().trim());
            }
        });
    }

    private void getAllTopics() {
        topics.clear();
        topics = dbHelper.getAllTopics();
    }

    private void deleteTopicFromDatabase(String topic,int position) {
        if(dbHelper.deleteTopic(topic) == -1){
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Topic Deleted!", Toast.LENGTH_SHORT).show();
            topics.remove(position);
            topicsAddRemoveAdapter.notifyItemRemoved(position);
        }
    }

    private void addTopicToDatabase(String topicName) {
        long result = dbHelper.createTopic(topicName);
        if(result == -1){
            Toast.makeText(this, "Error Occurred!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Topic Added!", Toast.LENGTH_SHORT).show();
            topicEditText.setText("");
            topics.add(topicName);
            topicsAddRemoveAdapter.notifyItemInserted(topics.size());
        }
    }
}