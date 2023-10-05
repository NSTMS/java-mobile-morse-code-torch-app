package com.example.morsecodetorchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class DecodeActivity extends AppCompatActivity {
    private static final String TAG = "DecodeActivity";
    private EditText textView;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decode);
        textView = findViewById(R.id.textView);
        result = findViewById(R.id.textView3);
        result.setOnLongClickListener(v -> copyText());
    }
    private boolean copyText(){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("morsecode", result.getText().toString());
        clipboard.setPrimaryClip(clip);
        return false;
    }

    public void changeViewToMainActivity(View view){
        Intent intent = new Intent(DecodeActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void decodeText(View v){
        String[] morse = textView.getText().toString().split(" ");
        String res = "";
        String[] code = { ".-",   "-...", "-.-.", "-..",  ".",
                "..-.", "--.",  "....", "..",   ".---",
                "-.-",  ".-..", "--",   "-.",   "---",
                ".--.", "--.-", ".-.",  "...",  "-",
                "..-",  "...-", ".--",  "-..-", "-.--",
                "--..", "|" };

        for (int i = 0; i < morse.length; i++) {
            for (int j = 0; j < code.length; j++) {
                if (morse[i].compareTo(code[j]) == 0) {
                    res += (char)(j + 'a');
                    break;
                }
            }
        }
        result.setText(res);
        textView.setText("");
    }


    @Override
    protected void onPause(){
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i(TAG, "onResume");
    }
    @Override
    protected void onStop(){
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

}