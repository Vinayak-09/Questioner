package com.example.questioner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {


    EditText userNameEditText;
    EditText passwordEditText;
    MyDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new MyDbHelper(this);
        userNameEditText = findViewById(R.id.loginUsername);
        passwordEditText = findViewById(R.id.loginPassword);

        findViewById(R.id.loginBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser(userNameEditText.getText().toString().trim(),passwordEditText.getText().toString().trim());
            }
        });

        findViewById(R.id.loginCreateAccountLink).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                LoginActivity.this.finish();
            }
        });
    }


    public void loginUser(String username,String password){
        if(username.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Please fill both the fields!", Toast.LENGTH_SHORT).show();
        }else if(username.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")){
            startActivity(new Intent(LoginActivity.this,AdminMainActivity.class));
            LoginActivity.this.finish();
        }else{
            int result = dbHelper.loginUser(username, password);
            if(result == -1){
                Toast.makeText(this, "Login failed!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences = getSharedPreferences("QUESTIONER",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("USER",username);
                editor.apply();
                startActivity(new Intent(LoginActivity.this,TopicActivity.class));
                LoginActivity.this.finish();
            }
        }
    }
}