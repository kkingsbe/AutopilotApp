package com.example.kkingsbe.autopilotapp;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TCPSocket {
    private static Socket socket;
    private static BufferedWriter writer;

    public static void startTcpConnection() throws IOException {
        String dns = "3.92.241.164"; //DNS of EC2 Server
        int port = 5005;
        String handshake = "client";

        socket = new Socket(dns, port);
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        //Handshake to let server know that this is the non UAV client
        send(handshake);
    }
    public static void send(final String msg) {
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
