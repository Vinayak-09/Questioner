package com.example.questioner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

public class AdminMainActivity extends AppCompatActivity {

    MyDbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        dbHelper = new MyDbHelper(this);


        findViewById(R.id.topic_more).setOnClickListener(view -> showPopUpMenu(view));


        findViewById(R.id.adminMainAddQuestions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminMainActivity.this,AddQuestionActivity.class));
            }
        });

        findViewById(R.id.adminMainAddRemove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminMainActivity.this,AdminTopicActivity.class));
            }
        });


        findViewById(R.id.adminDemo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("QUESTIONER",MODE_PRIVATE);
                if(sharedPreferences.getBoolean("DemoCreated",false)){
                    Toast.makeText(AdminMainActivity.this, "Demo Quiz already created!", Toast.LENGTH_SHORT).show();
                }else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("DemoCreated",true);
                    editor.apply();
                    dbHelper.createDemoQuiz();
                    Toast.makeText(AdminMainActivity.this, "Created!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void showPopUpMenu(View view){
        PopupMenu popupMenu = new PopupMenu(AdminMainActivity.this,view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.topic_screen_menu,popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                startActivity(new Intent(AdminMainActivity.this,LoginActivity.class));
                AdminMainActivity.this.finish();
                return true;
            }
        });
    }
}