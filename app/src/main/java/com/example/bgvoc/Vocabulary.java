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

    private Hashtable<String, Hashtable<String, String>> dataAccordingToCategoriesBgToEng = new Hashtable<>();
    private Hashtable<String, Hashtable<String, String>> dataAccordingToCategoriesEngToBg = new Hashtable<>();
    String userWordsFileName = "userAddedVocs.csv";

    public Vocabulary(InputStream inputStream) throws IOException {

        loadWordsToPractice(inputStream);
    }

    public void loadWordsToPractice(InputStream inputStream) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        String headerLine = br.readLine();

        while ((line = br.readLine()) != null) {
            String str[] = line.split(",");
            if (!str[0].equals("")) {
                String arr[] = str;
                if (dataAccordingToCategoriesBgToEng.containsKey(arr[2])) {
                    dataAccordingToCategoriesBgToEng.get(arr[2]).put(arr[0].toLowerCase(), arr[1]);
                    dataAccordingToCategoriesEngToBg.get(arr[2]).put(arr[1].toLowerCase(), arr[0]);
                } else {
                    Hashtable<String, String> newWordsPair = new Hashtable<>();
                    newWordsPair.put(arr[0].toLowerCase(), arr[1]);
                    dataAccordingToCategoriesBgToEng.put(arr[2], (Hashtable<String, String>) newWordsPair.clone());
                    newWordsPair.clear();
                    newWordsPair.put(arr[1].toLowerCase(), arr[0]);
                    dataAccordingToCategoriesEngToBg.put(arr[2], (Hashtable<String, String>) newWordsPair.clone());
                }
            }
        }
    }

    public Hashtable<String, String> getWordsListAccordingToUserSelectionBgToEng(String selectedTraining) {
        return dataAccordingToCategoriesBgToEng.get(selectedTraining);
    }

    public Hashtable<String, String> getWordsListAccordingToUserSelectionEngToBg(String selectedTraining) {
        return dataAccordingToCategoriesEngToBg.get(selectedTraining);
    }
}
