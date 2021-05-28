package com.example.bgvoc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;

public class VerbsSounds {

    public Hashtable<String, String> VerbsSounds = new Hashtable<>();

    public VerbsSounds(InputStream inputStream) throws IOException {
        loadCsvFile(inputStream);
    }

    private void loadCsvFile(InputStream inputStream) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        String headerLine = br.readLine();

        while ((line = br.readLine()) != null) {
            String str[] = line.split(",");
            if (!str[0].equals("")) {
                String arr[] = str;
                VerbsSounds.put(arr[0].toLowerCase(), arr[1]);
            }
        }
    }
}
