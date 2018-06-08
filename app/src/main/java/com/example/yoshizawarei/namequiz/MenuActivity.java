package com.example.yoshizawarei.namequiz;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {
    private static final int RED_CODE_ADD_NAME = 1111;
    private static final String TAG = "MenuActivity";
    private MediaPlayer mediaPlayer;
    private int length;

    @Override
    protected void onPause() {
        super.onPause();
        length = mediaPlayer.getCurrentPosition();
        mediaPlayer.pause();
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
        setContentView(R.layout.activity_menu);

        mediaPlayer = MediaPlayer.create(this, R.raw.shape_of_you);
        mediaPlayer.start();
    }


    public void PlayButtonClicked(View view){
        // change activity(from, to)
        Intent intent = new Intent(this, MainActivity.class);

        //passing data
        intent.putExtra("secret","yeah"); //id -> data , it takes all type of data
        startActivity(intent);

    }


    public void addNameClicked(View view) {

        // start activity, but come back later when you are done

        Intent intent = new Intent(this, addNewName.class);
        startActivityForResult(intent, RED_CODE_ADD_NAME); // any number

    }

// I deed to overide to use startActivityForResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1111){
            if(resultCode == RESULT_OK){
                String newName = data.getStringExtra("newName");
//                String[] names = newName.split(" ");
                toast(newName + " is added");

            }else{
                toast("result canceled");
            }
        }
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
