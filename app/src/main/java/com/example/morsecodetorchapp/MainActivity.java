package com.example.morsecodetorchapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Switch torchSwitch;
    private CameraManager cameraManager;
    private String getCameraID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        torchSwitch = (Switch) findViewById(R.id.torchSwitch);
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            getCameraID = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        torchSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try {
                    cameraManager.setTorchMode(getCameraID, isChecked);
                } catch (CameraAccessException e) {
                    throw new RuntimeException(e);
                }
            }

        });
    }
    public void changeViewToEncodeView(View v){
        Intent intent = new Intent(MainActivity.this, EncodeActivity.class);
        startActivity(intent);
    }
    public void changeViewToDecodeView(View v){
        Intent intent = new Intent(MainActivity.this, DecodeActivity.class);
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