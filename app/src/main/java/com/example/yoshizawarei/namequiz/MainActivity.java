package com.example.yoshizawarei.namequiz;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "MainActivity";
    private HashMap<String, String> namesMap;
    private TextView mTextView;
    private String[] fullname;
    private MediaPlayer mediaPlayer;
    private int length;
    private int current_score;
    private int highest_score;

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "onPause method got called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onResume method got called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(TAG, "onResume method got called");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG, "onStart method got called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy method got called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v(TAG, "onResume method got called");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        current_score = 0;
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        prefs.getInt("highest", 0);

//        length = 0;

        // Intent : put data from menu
        Intent intent = getIntent(); //returns intent
        String passed = intent.getStringExtra("secret"); // when input was String
//        intent.getIntExtra()
        toast(passed);

        namesMap = new HashMap<>();
        readContentsFromFile();
        readContentsFromFile_new();
        getQuestion();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // store instance state before gets destroyed
        TextView textView = findViewById(R.id.firstName_tv);
        ListView listView = findViewById(R.id.lastName_lv);

        // get all names in ListView
        ListAdapter listAdapter = listView.getAdapter();
        ArrayList<String> currentOptions = new ArrayList<>();

        for (int i = 0; i < listView.getChildCount(); i++) {
            currentOptions.add(listAdapter.getItem(i).toString()); // view
        }

        outState.putInt("current_score", current_score);
        outState.putString("firstname", textView.getText().toString()); // store current first name
        outState.putStringArrayList("options", currentOptions); // store current last names

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // getting instance state after getting created
        String savedName = savedInstanceState.getString("firstname", "Mark");
        ArrayList<String> savedOptions = savedInstanceState.getStringArrayList("options");
        current_score = savedInstanceState.getInt("current_score", 0);

        TextView textView = findViewById(R.id.firstName_tv);
        textView.setText(savedName);

        ListView lv = findViewById(R.id.lastName_lv);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, savedOptions);
        lv.setAdapter(adapter);
    }

    private void getQuestion() {
        ArrayList<String> firstnames = new ArrayList<>(namesMap.keySet());
        ArrayList<String> lastnames = new ArrayList<>(namesMap.values());
        int rand = (int) (Math.random() * firstnames.size());
        String fn = firstnames.get(rand);
        String ln = namesMap.get(fn);

        lastnames.remove(ln);
        Collections.shuffle(lastnames);

        ArrayList<String> options = new ArrayList<>(lastnames.subList(0, 4));
        options.add(ln);

        Collections.shuffle(options);

        mTextView = findViewById(R.id.firstName_tv);
        mTextView.setText(fn);
        ListView lv = findViewById(R.id.lastName_lv);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, options);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // check answer
        String question = mTextView.getText().toString();
        String answer = namesMap.get(question);
        String selected = parent.getItemAtPosition(position).toString();

        Log.d(TAG, "onItemClick: " + selected);
        if (answer.equals(selected)) {
            current_score++;
            if (current_score > highest_score) {
                highest_score = current_score;
                // save this highest score permanently
                // 1. write to a textfile (too much work for one int)
                // 2. use database (sqlite -> too much work for one int)
                // ==> Use Preference!
                SharedPreferences prefs = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = prefs.edit();

                prefsEditor.putInt("highest", highest_score);
                prefsEditor.apply();

            }
            toast("Right! Score: " + current_score + " Highest: " + highest_score);
            getQuestion();
        } else {
            current_score--;
            toast("Wrong! Score: " + current_score + " Highest: " + highest_score);
        }
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void readContentsFromFile() {
        Scanner scan = new Scanner(getResources().openRawResource(R.raw.names));
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            fullname = line.split("\t");
            namesMap.put(fullname[0], fullname[1]);
        }
        scan.close();
    }

    private void readContentsFromFile_new() {

        Scanner scan = null;
        try {
            scan = new Scanner(openFileInput("new_names.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            fullname = line.split("\t");
            namesMap.put(fullname[0], fullname[1]);
        }
        scan.close();

    }

}