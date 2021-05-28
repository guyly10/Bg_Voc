package com.example.bgvoc;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;

public class Vocabulary {

    public Hashtable<String, String> BulgarianToEnglish = new Hashtable<>();
    public Hashtable<String, String> EnglishToBulgarian = new Hashtable<>();
    String userWordsFileName = "userAddedVocs.csv";

    public Vocabulary(InputStream inputStream) throws IOException {

        loadWordsToPractice(inputStream);
    }

    public void loadWordsToPractice(InputStream inputStream) throws IOException {
        BulgarianToEnglish.clear();
        EnglishToBulgarian.clear();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        String headerLine = br.readLine();

        while ((line = br.readLine()) != null) {
            String str[] = line.split(",");
            if (!str[0].equals("")) {
                String arr[] = str;
                BulgarianToEnglish.put(arr[0].toLowerCase(), arr[1]);
                EnglishToBulgarian.put(arr[1].toLowerCase(), arr[0]);
            }
        }
    }

    public void loadUserWords() throws IOException {
        BulgarianToEnglish.clear();
        EnglishToBulgarian.clear();

        File root = Environment.getExternalStorageDirectory();
        File userWords = new File(root, userWordsFileName);

        FileReader fr = new FileReader(userWords);
        BufferedReader br = new BufferedReader(fr);
        String line;

        while ((line = br.readLine()) != null) {
            String str[] = line.split(",");
            if (!str[0].equals("")) {
                String arr[] = str;
                BulgarianToEnglish.put(arr[0].toLowerCase(), arr[1]);
                EnglishToBulgarian.put(arr[1].toLowerCase(), arr[0]);
            }
        }
    }
}
