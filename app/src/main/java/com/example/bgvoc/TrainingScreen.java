package com.example.bgvoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Random;

public class TrainingScreen extends AppCompatActivity {

    Vocabulary Vocabulary;
    VerbsSounds VerbsSounds;
    TextView wordToTranslate;
    EditText userInput;
    TextView wrongAnswer;
    TextView answer;
    TextView wordCount;
    int count = 0;
    int wrongAnswerCounter = 0;
    String[] wrongAnswers = {"Wrong, try again!", "Still wrong, try again!"};
    boolean shouldRemoveWord = true;
    public Hashtable<String, String> BulgarianToEnglish = new Hashtable<>();
    public Hashtable<String, String> EnglishToBulgarian = new Hashtable<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_screen);
        wordToTranslate = findViewById(R.id.wordToTranslate);
        userInput = findViewById(R.id.userInput);
        wrongAnswer = findViewById(R.id.wrongAnswer);
        answer = findViewById(R.id.answer);
        wordCount = findViewById(R.id.wordCount);
        initWordsList();
        buildWordsListAccordingToUserSelection();
        wordToTranslate.setText(chooseRandomWord());
        wrongAnswer.setVisibility(View.INVISIBLE);
        answer.setVisibility(View.INVISIBLE);
        updateWordCount();
    }

    private void buildWordsListAccordingToUserSelection() {
        Bundle bundle = getIntent().getExtras();
        String selectedTraining = bundle.getString("selectedTraining");
        BulgarianToEnglish = Vocabulary.getWordsListAccordingToUserSelectionBgToEng(selectedTraining);
        EnglishToBulgarian = Vocabulary.getWordsListAccordingToUserSelectionEngToBg(selectedTraining);
    }

    private void updateWordCount() {
        count = BulgarianToEnglish.size();
        wordCount.setText(count + " words left");
    }

    private String chooseRandomWord() {
        Random rnd = new Random();
        int vocabularySum = BulgarianToEnglish.size();
        int index = rnd.nextInt(vocabularySum);

        return (String) BulgarianToEnglish.values().toArray()[index];
    }

    private void initWordsList() {
        try {
            Vocabulary = new Vocabulary(getResources().openRawResource(R.raw.data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void NextWordClicked(View view) {
        String input = userInput.getText().toString();

        if (BulgarianToEnglish.containsKey(input.toLowerCase())
                && EnglishToBulgarian.get(wordToTranslate.getText().toString().toLowerCase()).equalsIgnoreCase(input)) {

            if (shouldRemoveWord) {
                BulgarianToEnglish.remove(input.toLowerCase());
            }
            wordToTranslate.setText(chooseRandomWord());
            updateWordCount();
            userInput.setText("");
            answer.setVisibility(View.INVISIBLE);
            wrongAnswer.setVisibility(View.INVISIBLE);
            shouldRemoveWord = true;

        } else {
            setWrongAnswer();
        }
    }

    private void setWrongAnswer() {
        if (wrongAnswerCounter == 0) {
            wrongAnswer.setText(wrongAnswers[wrongAnswerCounter]);
            wrongAnswerCounter++;
        } else {
            wrongAnswer.setText(wrongAnswers[wrongAnswerCounter]);
            wrongAnswerCounter = 0;
        }
        shouldRemoveWord = false;
        wrongAnswer.setVisibility(View.VISIBLE);
    }

    public void ShowAnswerClicked(View view) {
        answer.setVisibility(View.VISIBLE);
        answer.setText(EnglishToBulgarian.get(wordToTranslate.getText().toString().toLowerCase()));
        shouldRemoveWord = false;
    }

    public void AllWordsClicked(MenuItem item) throws IOException {
        Vocabulary.loadWordsToPractice(getResources().openRawResource(R.raw.vocabulary));
        wordToTranslate.setText(chooseRandomWord());
        updateWordCount();
        answer.setVisibility(View.INVISIBLE);
        Toast.makeText(this, "All words loaded", Toast.LENGTH_SHORT).show();
    }

    public void VerbsClicked(MenuItem item) throws IOException {
        Vocabulary.loadWordsToPractice(getResources().openRawResource(R.raw.verbs));
        wordToTranslate.setText(chooseRandomWord());
        updateWordCount();
        answer.setVisibility(View.INVISIBLE);
        Toast.makeText(this, "Verbs loaded", Toast.LENGTH_SHORT).show();
    }

    public void AddWordsToCsv(MenuItem item) {
        startActivity(new Intent(this, AddWordToCsv.class));
    }

    public void OnWordToTranslateClicked(View view) throws IOException {
        if (VerbsSounds.VerbsSounds.containsKey(answer.getText().toString().toLowerCase())) {
            AssetFileDescriptor afd = getAssets().openFd("sounds/" + VerbsSounds.VerbsSounds.get(answer.getText().toString().toLowerCase()) + ".mp3");
            MediaPlayer player = new MediaPlayer();
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.prepare();
            player.start();
        }
    }

    public void OnLoadUserWordsClicked(MenuItem item) throws IOException {
        //Vocabulary.loadUserWords(this);
        wordToTranslate.setText(chooseRandomWord());
        updateWordCount();
        answer.setVisibility(View.INVISIBLE);
        Toast.makeText(this, "User words loaded", Toast.LENGTH_SHORT).show();
    }
}