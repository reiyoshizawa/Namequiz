package com.example.yoshizawarei.namequiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.Scanner;

public class addNewName extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_name);
    }

    public void AddNewName(View view) throws FileNotFoundException {
        System.out.println("ssss");

        //1. get names
        EditText first = findViewById(R.id.firstName_et);
        EditText last = findViewById(R.id.lastName_et);

        String fn = first.getText().toString();
        String ln = last.getText().toString();

        //2. valid check(RegEx)
//        if (!(fn.matches("[a-zA-Z]")) || !(ln.matches("[a-zA-Z]"))) {
//            System.out.println("error");
//        }

        //3. add new name to new file, we can not modify exist file
        // create new file(new_names.txt), use 2 files
        // generate questions from two files            (Main)
        try {
             PrintStream printStream = new PrintStream(openFileOutput("new_names.txt", MODE_PRIVATE|MODE_APPEND));
             printStream.println(fn + "\t" + ln);
             printStream.close();

        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        //4. Send new data back to MenuActivity to alert that new names added

        Intent intent = new Intent();
        intent.putExtra("newName",fn+ " " + ln);

         // sends data(intent) with result code
        setResult(RESULT_OK, intent);
        finish();





    }
}
