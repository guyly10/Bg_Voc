package com.example.bgvoc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Vocabulary Vocabulary;
    TextView wordToTranslate;
    EditText userInput;
    TextView wrongAnswer;
    TextView answer;
    TextView wordCount;
    int count = 0;
    int wrongAnswerCounter = 0;
    String [] wrongAnswers = {"Wrong, try again!", "Still wrong, try again!"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSupportActionBar().hide();
        wordToTranslate = findViewById(R.id.wordToTranslate);
        userInput = findViewById(R.id.userInput);
        wrongAnswer = findViewById(R.id.wrongAnswer);
        answer = findViewById(R.id.answer);
        wordCount = findViewById(R.id.wordCount);
        initiateVerbs();
        wordToTranslate.setText(chooseRandomWord());
        wrongAnswer.setVisibility(View.INVISIBLE);
        updateWordCount();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.practice_option_menu, menu);
        return true;
    }

    private void updateWordCount() {
        count = Vocabulary.BulgarianToEnglish.size();
        wordCount.setText(count + " words left");
    }

    private String chooseRandomWord() {
        Random rnd = new Random();
        int vocabularySum = Vocabulary.BulgarianToEnglish.size();
        int index = rnd.nextInt(vocabularySum);

        return (String) Vocabulary.BulgarianToEnglish.values().toArray()[index];
    }

    private void initiateVerbs() {
        try {
            Vocabulary = new Vocabulary(getResources().openRawResource(R.raw.vocabulary));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void NextWordClicked(View view) {
        String input = userInput.getText().toString();

        if (Vocabulary.BulgarianToEnglish.containsKey(input.toLowerCase())
                && Vocabulary.EnglishToBulgarian.get(wordToTranslate.getText().toString().toLowerCase()).equalsIgnoreCase(input)) {

            Vocabulary.BulgarianToEnglish.remove(input.toLowerCase());
            wordToTranslate.setText(chooseRandomWord());
            updateWordCount();
            userInput.setText("");
            answer.setText("");
            wrongAnswer.setVisibility(View.INVISIBLE);

        } else {
            setWrongAnswer();
        }
    }

    private void setWrongAnswer() {
        if (wrongAnswerCounter == 0){
            wrongAnswer.setText(wrongAnswers[wrongAnswerCounter]);
            wrongAnswerCounter++;
        }
        else {
            wrongAnswer.setText(wrongAnswers[wrongAnswerCounter]);
            wrongAnswerCounter = 0;
        }
        wrongAnswer.setVisibility(View.VISIBLE);
    }

    public void ShowAnswerClicked(View view) {
        answer.setText(Vocabulary.EnglishToBulgarian.get(wordToTranslate.getText().toString().toLowerCase()));
    }

    public void AllWordsClicked(MenuItem item) throws IOException {
        Vocabulary.loadWordsToPractice(getResources().openRawResource(R.raw.vocabulary));
        wordToTranslate.setText(chooseRandomWord());
        updateWordCount();
        Toast.makeText(this, "All words loaded", Toast.LENGTH_SHORT).show();
    }

    public void VerbsClicked(MenuItem item) throws IOException {
        Vocabulary.loadWordsToPractice(getResources().openRawResource(R.raw.verbs));
        wordToTranslate.setText(chooseRandomWord());
        updateWordCount();
        Toast.makeText(this, "Verbs loaded", Toast.LENGTH_SHORT).show();
    }
}