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

public class EncodeActivity extends AppCompatActivity {
    private static final String TAG = "EncodeActivity";
    private EditText textView;
    private TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encode);
        textView = findViewById(R.id.textView);
        result = findViewById(R.id.textView3);
        result.setOnLongClickListener(v -> copyText());
    }

    public void encodeText(View v)
    {
        String text = textView.getText().toString();
        String res = "";
        char[] letter = { 'a', 'b', 'c', 'd', 'e', 'f',
                'g', 'h', 'i', 'j', 'k', 'l',
                'm', 'n', 'o', 'p', 'q', 'r',
                's', 't', 'u', 'v', 'w', 'x',
                'y', 'z', '1', '2', '3', '4',
                '5', '6', '7', '8', '9', '0' };
        String[] code = { ".-",   "-...", "-.-.", "-..",  ".",
                "..-.", "--.",  "....", "..",   ".---",
                "-.-",  ".-..", "--",   "-.",   "---",
                ".--.", "--.-", ".-.",  "...",  "-",
                "..-",  "...-", ".--",  "-..-", "-.--",
                "--..", "|" };

        for (int i = 0; i < text.length(); i++) {
            for (int j = 0; j < letter.length; j++) {
                if (text.charAt(i) == letter[j]) {
                    res += code[j] + " ";
                    break;
                }
            }
        }
        result.setText(res);
        textView.setText("");
    }

    private boolean copyText(){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("morsecode", result.getText().toString());
        clipboard.setPrimaryClip(clip);
        return false;
    }
    public void changeViewToMainActivity(View view){
        Intent intent = new Intent(EncodeActivity.this, MainActivity.class);
        startActivity(intent);
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