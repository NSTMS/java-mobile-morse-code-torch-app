package com.example.morsecodetorchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

public class EncodeActivity extends AppCompatActivity {
    private static final String TAG = "EncodeActivity";
    private EditText textView;
    private TextView result;
    private ImageButton imageButton;
    private ImageButton cancelButton;
    private Button show;

    private ClipboardManager clipboardManager;

    private CameraManager cameraManager;
    private String getCameraID;
    private ProgressBar progressBar;

    private boolean isRunning;
    private Thread xd = new Thread();
    private String[] code = {".-", "-...", "-.-.", "-..", ".",
            "..-.", "--.", "....", "..", ".---",
            "-.-", ".-..", "--", "-.", "---",
            ".--.", "--.-", ".-.", "...", "-",
            "..-", "...-", ".--", "-..-", "-.--",
            "--..", "/"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encode);
        textView = findViewById(R.id.textView);
        result = findViewById(R.id.textView3);
        result.setOnLongClickListener(v -> copyText());
        imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(v -> pasteFromClipboard());
        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        cancelButton = findViewById(R.id.cancelBtn);
        cancelButton.setOnClickListener(v -> resetTextView());

        show = findViewById(R.id.show);
        show.setOnClickListener(v -> startTorchSeqence());

        progressBar = findViewById(R.id.progressBar);

        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            getCameraID = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
    private void resetTextView()
    {
        textView.setText("");
    }

    public void startTorchSeqence(){
        if(isRunning) return;
        isRunning = true;
        progressBar.setMax(0);
        String m = result.getText().toString();
        String[] morse = m.split(" ");
        getPrgs();

         xd = new Thread(()->{
            for (int i = 0; i < morse.length; i++) {
                for (int j = 0; j < code.length; j++) {
                    if (morse[i].compareTo(code[j]) == 0) {
                        if(morse[i].compareTo("/") == 0)
                        {
                            progressBar.setProgress( progressBar.getProgress() + 700);
                            try {
                                Thread.sleep(700);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        else{
                            playSequence(code[j]);
                            progressBar.setProgress( progressBar.getProgress() + 300);
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        break;
                    }
                }
            }
            isRunning = false;
        });
        xd.start();

    }


    public void getPrgs()
    {
        int prgs = 0;
        String m = result.getText().toString();
        String[] morse = m.split(" ");

        for (int i = 0; i < morse.length; i++) {
            for (int j = 0; j < code.length; j++) {
                if (morse[i].compareTo(code[j]) == 0) {
                    if(morse[i].compareTo("/") == 0) prgs += 700;
                    else{
                        String[] sequence = code[j].split("");
                        for (int x=0;x< sequence.length;x++)
                        {
                                switch (sequence[x]) {
                                    case ".":
                                        prgs += 100;
                                        break;
                                    case "-":
                                        prgs += 300;
                                        break;
                                    default:
                                        break;
                                }
                        }
                        prgs += 300;
                    }
                    break;
                }
            }
        }
        progressBar.setMax(prgs);
    }






    private void playSequence(String code)  {
        String[] sequence = code.split("");

        for (int i=0;i< sequence.length;i++)
        {
            lightTorch(true);
            try {
                switch (sequence[i]) {
                    case ".":
                        Thread.sleep(100);
                        progressBar.setProgress(progressBar.getProgress() + 100);
                        break;
                    case "-":
                        Thread.sleep(300);
                        progressBar.setProgress(progressBar.getProgress() + 300);
                        break;
                    default:
                        break;
                }
            }catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            lightTorch(false);
        }

    }

    private void lightTorch(boolean mode)
    {
        try {
            cameraManager.setTorchMode(getCameraID, mode);
        } catch (CameraAccessException e) {
            throw new RuntimeException(e);
        }
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

    public void encodeText(View v)
    {
        String text = textView.getText().toString();
        String res = "";
        char[] letter = { 'a', 'b', 'c', 'd', 'e', 'f',
                'g', 'h', 'i', 'j', 'k', 'l',
                'm', 'n', 'o', 'p', 'q', 'r',
                's', 't', 'u', 'v', 'w', 'x',
                'y', 'z',' '};
        String[] code = { ".-",   "-...", "-.-.", "-..",  ".",
                "..-.", "--.",  "....", "..",   ".---",
                "-.-",  ".-..", "--",   "-.",   "---",
                ".--.", "--.-", ".-.",  "...",  "-",
                "..-",  "...-", ".--",  "-..-", "-.--",
                "--..","/"};

        for (int i = 0; i < text.length(); i++) {
            for (int j = 0; j < letter.length; j++) {
                if (text.charAt(i) == letter[j]) {
                    res += code[j] + " ";
                    break;
                }
            }
        }
        if(res.length() == 0) res = "nieprawidłowe dane wejściowe";
        result.setText(res);
//        textView.setText("");
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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
        xd.interrupt();
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
        xd.interrupt();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        xd.interrupt();
        Log.i(TAG, "onDestroy");
    }

}