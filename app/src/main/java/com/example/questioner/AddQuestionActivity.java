package com.example.questioner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddQuestionActivity extends AppCompatActivity {

    MyDbHelper myDbHelper;
    EditText optionAEditText,optionBEditText, optionCEditText, optionDEditText, questionEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        myDbHelper = new MyDbHelper(this);

        questionEditText = findViewById(R.id.addQuestionEditText);
        optionAEditText = findViewById(R.id.addQuestionOptionA);
        optionBEditText = findViewById(R.id.addQuestionOptionB);
        optionCEditText = findViewById(R.id.addQuestionOptionC);
        optionDEditText = findViewById(R.id.addQuestionOptionD);

        Spinner spinner = findViewById(R.id.addQuestionSpinner);
        ArrayAdapter<String> arrayAdapterTopics = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, myDbHelper.getAllTopics());
        spinner.setAdapter(arrayAdapterTopics);

        Spinner spinnerAnswer = findViewById(R.id.addQuestionSpinnerAnswer);
        ArrayAdapter<String> arrayAdapterAnswer = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, new String[]{"A", "B", "C", "D"});
        spinnerAnswer.setAdapter(arrayAdapterAnswer);

        spinner.setSelection(0);
        spinnerAnswer.setSelection(0);


        findViewById(R.id.addQuestionButton).setOnClickListener(view -> {
            if(arrayAdapterTopics.getCount() > 0){
                addQuestionToDb(spinner.getSelectedItem().toString(),
                        questionEditText.getText().toString().trim(),
                        optionAEditText.getText().toString().trim(),
                        optionBEditText.getText().toString().trim(),
                        optionCEditText.getText().toString().trim(),
                        optionDEditText.getText().toString().trim(),
                        spinnerAnswer.getSelectedItem().toString());
            }else {
                Toast.makeText(AddQuestionActivity.this, "Please Add Few Topics First To Proceed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addQuestionToDb(String topic, String question, String optionA, String optionB, String optionC, String optionD, String correctOption) {
        if(question.isEmpty() || optionA.isEmpty() || optionB.isEmpty() || optionC.isEmpty() || optionD.isEmpty()){
            Toast.makeText(this, "Fill all the fields!", Toast.LENGTH_SHORT).show();
        }else{
            long result = myDbHelper.addQuestion(question,optionA,optionB,optionC,optionD,correctOption,topic);
            if(result == -1){
                Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Question Added!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}