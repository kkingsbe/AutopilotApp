package com.example.kkingsbe.autopilotapp;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

public class Connect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //The following is a bad idea and should never be done:
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_connect);
    }

    public void connect(View v) throws IOException {
        try {
            TCPSocket.startTcpConnection(); //Starts the TCP connection
            Intent intent = new Intent(Connect.this, MainActivity.class);
            startActivity(intent);
        }
        catch(Exception e) { //In case it is unable to connect to the server
            Toast.makeText(Connect.this, "Unable to connect to cloud server", Toast.LENGTH_LONG).show();
        }
    }
}
