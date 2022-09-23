package com.example.questioner;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class QuestionActivity extends AppCompatActivity {

    int backCount = 0, questionCount = 0 , correctAnswers = 0;

    boolean stopResponse = true;

    MyDbHelper myDbHelper;
    ArrayList<Question> questionArrayList;
    CircularProgressIndicator progressIndicator;
    TextView progressText, optionA, optionB, optionC, optionD, questionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        myDbHelper = new MyDbHelper(this);
        questionArrayList = new ArrayList<>();
        questionArrayList = myDbHelper.getQuestions(getIntent().getAction());
        progressIndicator = findViewById(R.id.progressBar);

        questionTextView = findViewById(R.id.questionDisplay);
        optionA = findViewById(R.id.optionA);
        optionB = findViewById(R.id.optionB);
        optionC = findViewById(R.id.optionC);
        optionD = findViewById(R.id.optionD);
        
        progressText = findViewById(R.id.progressTextView);

        if(questionArrayList.isEmpty()){

            questionTextView.setVisibility(View.INVISIBLE);
            optionA.setVisibility(View.INVISIBLE);
            optionB.setVisibility(View.INVISIBLE);
            optionC.setVisibility(View.INVISIBLE);
            optionD.setVisibility(View.INVISIBLE);

            View parentLayout = findViewById(android.R.id.content);
            Snackbar.make(parentLayout, "No Questions Available!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("CLOSE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onBackPressed();
                            onBackPressed();
                        }
                    })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light,getTheme()))
                    .show();
        }else{
            loadQuestion();

            ObjectAnimator animator = (ObjectAnimator) AnimatorInflater.loadAnimator(this,R.animator.flip);
            animator.setDuration(500);

            optionA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(stopResponse){
                        if(questionArrayList.get(questionCount).getCorrectOption().equalsIgnoreCase("A")){
                            correctAnswers++;
                            animator.setTarget(view);
                            animator.start();
                        }else {
                            view.setBackgroundColor(Color.RED);
                        }
                        stopResponse = false;
                    }
                }
            });

            optionB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (stopResponse) {
                        if (questionArrayList.get(questionCount).getCorrectOption().equalsIgnoreCase("B")) {
                            correctAnswers++;
                            animator.setTarget(view);
                            animator.start();
                        } else {
                            view.setBackgroundColor(Color.RED);
                        }
                    }
                    stopResponse = false;
                }
            });

            optionC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (stopResponse) {
                        if (questionArrayList.get(questionCount).getCorrectOption().equalsIgnoreCase("C")) {
                            correctAnswers++;
                            animator.setTarget(view);
                            animator.start();
                        } else {
                            view.setBackgroundColor(Color.RED);
                        }
                    }
                    stopResponse = false;
                }
            });

            optionD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (stopResponse) {
                        if (questionArrayList.get(questionCount).getCorrectOption().equalsIgnoreCase("D")) {
                            correctAnswers++;
                            animator.setTarget(view);
                            animator.start();
                        } else {
                            view.setBackgroundColor(Color.RED);
                        }
                    }
                    stopResponse = false;
                }
            });
        }

       
    }

    protected void loadQuestion(){
        if(questionCount != questionArrayList.size()){
            stopResponse = true;
            startTimer(questionCount);
        }else{
            addResultToDatabase();
            Intent intent = new Intent(QuestionActivity.this,ResultActivity.class);
            intent.setAction(correctAnswers+"/"+questionArrayList.size());
            startActivity(intent);
            QuestionActivity.this.finish();
        }
    }

    private void addResultToDatabase() {
        SharedPreferences sharedPreferences = getSharedPreferences("QUESTIONER",MODE_PRIVATE);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        myDbHelper.addResult(sharedPreferences.getString("USER",""),dateFormat.format(new Date()),correctAnswers+"/"+questionArrayList.size(),getIntent().getAction());
    }

    private void startTimer(int questionNumber) {
        showQuestion(questionNumber);
        progressIndicator.setIndicatorColor(Color.GREEN);
        new CountDownTimer(30000,1000){
            @Override
            public void onTick(long l) {
                progressIndicator.setProgress((int)l / 1000);
                progressText.setText((l / 1000) + "");
                if((l / 1000) < 13 ){
                    progressIndicator.setIndicatorColor(Color.RED);
                }else if((l / 1000) < 20 ){
                    progressIndicator.setIndicatorColor(Color.parseColor("#BA883E"));
                }
                if(!stopResponse){
                    this.cancel();
                    questionCount++;
                    loadQuestion();
                }
            }

            @Override
            public void onFinish() {
                questionCount++;
                loadQuestion();
            }
        }.start();
    }

    private void showQuestion(int questionNumber) {
        Question question = questionArrayList.get(questionNumber);
        questionTextView.setText(question.getQuestion());
        optionA.setText(question.getOptionA());
        optionB.setText(question.getOptionB());
        optionC.setText(question.getOptionC());
        optionD.setText(question.getOptionD());
        optionA.setBackgroundResource(R.drawable.blur_white);
        optionB.setBackgroundResource(R.drawable.blur_white);
        optionC.setBackgroundResource(R.drawable.blur_white);
        optionD.setBackgroundResource(R.drawable.blur_white);
    }


    @Override
    public void onBackPressed() {
        backCount++;
        if(backCount == 2){
            super.onBackPressed();
        }
        new CountDownTimer(1500,1000){

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                backCount = 0;
            }
        }.start();
        if(backCount == 1){
            Toast.makeText(this, "Press back again to exit!", Toast.LENGTH_SHORT).show();
        }
    }



}