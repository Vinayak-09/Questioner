package com.example.questioner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import java.util.ArrayList;

public class TopicActivity extends AppCompatActivity {

    RecyclerView topicsRecyclerView;
    TopicsAdapter topicsAdapter;
    MyDbHelper dbHelper;
    ArrayList<String> topicsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        topicsRecyclerView = findViewById(R.id.topics_recyclerView);


        dbHelper = new MyDbHelper(this);
        topicsList = new ArrayList<>();
        topicsList = dbHelper.getAllTopics();

        topicsAdapter = new TopicsAdapter(topicsList, this, new MyOnClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(TopicActivity.this,QuestionActivity.class);
                intent.setAction(topicsList.get(position));
                startActivity(intent);
            }
        });


        topicsRecyclerView.setAdapter(topicsAdapter);
        topicsRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        findViewById(R.id.topic_more).setOnClickListener(view -> showPopUpMenu(view));


    }


    private void showPopUpMenu(View view){
        PopupMenu popupMenu = new PopupMenu(TopicActivity.this,view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.topic_screen_menu,popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                SharedPreferences sharedPreferences = getSharedPreferences("QUESTIONER",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("USER","");
                editor.apply();
                startActivity(new Intent(TopicActivity.this,LoginActivity.class));
                TopicActivity.this.finish();
                return true;
            }
        });
    }
}