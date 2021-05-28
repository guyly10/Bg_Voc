package com.example.bgvoc;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Hashtable;

public class AddWordToCsv extends AppCompatActivity {

    EditText userAddedWord;
    EditText userAddedTranslation;
    File userWords;
    String fileName = "userAddedVocs.csv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word_to_csv);
        userAddedWord = findViewById(R.id.userAddedWord);
        userAddedTranslation = findViewById(R.id.userAddedTranslation);
        loadCreateUserWords();
    }

    private void loadCreateUserWords() {
        File root = Environment.getExternalStorageDirectory();
        userWords = new File(root, fileName);
        if (!userWords.exists()) {
            try {
                userWords.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void OnWordAdded(View view) throws IOException {
        FileWriter fw = new FileWriter(userWords, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        pw.print(userAddedWord.getText().toString());
        pw.print(',');
        pw.println(userAddedTranslation.getText().toString());
        pw.close();
        bw.close();
        fw.close();

        Hashtable<String, String> BulgarianToEnglish = new Hashtable<>();
        FileReader fr = new FileReader(userWords);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null) {
            String str[] = line.split(",");
            if (!str[0].equals("")) {
                String arr[] = str;
                BulgarianToEnglish.put(arr[0].toLowerCase(), arr[1]);
            }
        }
    }

    public void OnUserDoneAddingWords(View view) {
        finish();
    }
}