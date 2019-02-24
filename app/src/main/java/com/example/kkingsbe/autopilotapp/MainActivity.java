package com.example.kkingsbe.autopilotapp;

import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
private SeekBar angleSelector;
private TextView angleText;
private Switch expModeSwitch;
private Socket socket;
private BufferedWriter writer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //The following is a bad idea and should never be done:
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_main);

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
                if(expModeSwitch.isChecked()) send(Integer.toString(angle));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                angleText.setText(angle + "°");
                if(!expModeSwitch.isChecked()) send(Integer.toString(angle));
            }
        });

        try {
            startTcpConnection(); //Instatiates the TCP connection between the app and the EC2 server
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void startTcpConnection() throws IOException {
        String dns = "ec2-3-80-152-204.compute-1.amazonaws.com"; //DNS of EC2 Server
        int port = 5005;
        String handshake = "client";

        socket = new Socket(dns, port);
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        //Handshake to let server know that this is the non UAV client
        send(handshake);
    }
    private void send(final String msg) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    writer.write(msg, 0, msg.length());
                    writer.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
}
