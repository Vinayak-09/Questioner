package com.example.questioner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText userNameEditText;
    EditText passwordEditText;
    MyDbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dbHelper = new MyDbHelper(this);
        userNameEditText = findViewById(R.id.registerUsername);
        passwordEditText = findViewById(R.id.registerPassword);
        findViewById(R.id.registerSignUpBtn).setOnClickListener(view -> registerUser(userNameEditText.getText().toString().trim(),passwordEditText.getText().toString().trim()));
    }

    public void registerUser(String username,String password){
        if(username.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Please fill both the fields!", Toast.LENGTH_SHORT).show();
        }else if(username.equalsIgnoreCase("admin")){
            Toast.makeText(this, "Please choose another username!", Toast.LENGTH_SHORT).show();
        }else {
            long result = dbHelper.createUserAccount(username, password);
            if(result == -1){
                Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences = getSharedPreferences("QUESTIONER",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("USER",username);
                editor.apply();
                startActivity(new Intent(RegisterActivity.this,TopicActivity.class));
                RegisterActivity.this.finish();
            }
        }
    }
}