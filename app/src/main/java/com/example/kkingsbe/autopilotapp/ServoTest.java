package com.example.kkingsbe.autopilotapp;

import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class ServoTest extends AppCompatActivity {
private SeekBar angleSelector;
private TextView angleText;
private Switch expModeSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_servotest);

        setup();
    }

    private void setup(){
        angleSelector = findViewById(R.id.angleSelector);
        final TextView angleText = findViewById(R.id.currentAngle);
        expModeSwitch = findViewById(R.id.expModeSwitch);

        angleSelector.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int angle = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                angle = progressValue;
                angleText.setText(angle + "°");
                if(expModeSwitch.isChecked()) TCPSocket.send(Integer.toString(angle));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                angleText.setText(angle + "°");
                if(!expModeSwitch.isChecked()) TCPSocket.send(Integer.toString(angle));
            }
        });
    }

}
