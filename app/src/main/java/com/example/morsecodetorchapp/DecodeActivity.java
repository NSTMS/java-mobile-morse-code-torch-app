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
import android.widget.ImageButton;
import android.widget.TextView;

public class DecodeActivity extends AppCompatActivity {
    private static final String TAG = "DecodeActivity";
    private EditText textView;
    private TextView result;
    private ImageButton imageButton;
    private ClipboardManager clipboardManager;
    private ImageButton pointButton;
    private ImageButton lineButton;
    private ImageButton spaceButton;

    private ImageButton cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decode);
        textView = findViewById(R.id.textView);
        result = findViewById(R.id.textView3);
        result.setOnLongClickListener(v -> copyText());

        imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(v -> pasteFromClipboard());

        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        pointButton = findViewById(R.id.pointButton);
        pointButton.setOnClickListener(v -> addToDecodeText("."));

        lineButton = findViewById(R.id.lineButton);
        lineButton.setOnClickListener(v -> addToDecodeText("-"));

        spaceButton = findViewById(R.id.spaceButton);
        spaceButton.setOnClickListener(v -> addToDecodeText(" "));

        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(v -> resetTextView());

    }
    private void resetTextView()
    {
        textView.setText("");
    }
    private void addToDecodeText(String t)
    {
        String text = textView.getText().toString();
        textView.setText(text + t);
        textView.setSelection(text.length()+2);

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
    private boolean pasteFromClipboard(){
        ClipData clipData = clipboardManager.getPrimaryClip();
        if (clipData != null && clipData.getItemCount() > 0) {
            ClipData.Item item = clipData.getItemAt(0);
            CharSequence text = item.getText();
            if (text != null) {
                textView.setText(text.toString());
            } else {
                textView.setText("");
            }
        }
        return true;
    }

    public void decodeText(View v){
        String[] morse = textView.getText().toString().split(" ");
        String res = "";
        String[] code = { ".-",   "-...", "-.-.", "-..",  ".",
                "..-.", "--.",  "....", "..",   ".---",
                "-.-",  ".-..", "--",   "-.",   "---",
                ".--.", "--.-", ".-.",  "...",  "-",
                "..-",  "...-", ".--",  "-..-", "-.--",
                "--..", "/" };

        for (int i = 0; i < morse.length; i++) {
            for (int j = 0; j < code.length; j++) {
                if (morse[i].compareTo(code[j]) == 0) {
                    if(morse[i].compareTo("/") == 0){
                        res += " ";
                    }
                    else{
                        res += (char)(j + 'a');
                    }
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