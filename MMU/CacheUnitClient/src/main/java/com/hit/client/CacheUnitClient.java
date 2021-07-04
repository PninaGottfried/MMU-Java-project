package main.java.com.hit.client;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class CacheUnitClient {
    public Socket socket;

    public CacheUnitClient() {
    }

    public String send(String request) {
        String res = "";
        try {
            socket = new Socket("localhost", 12345);
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            output.writeObject(request);
            output.flush();
            res = (String) input.readObject();

            output.close();
            input.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return res;
    }
}
