package main.java.com.hit.server;

import main.java.com.hit.services.CacheUnitController;

import java.beans.PropertyChangeEvent;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements java.beans.PropertyChangeListener, java.lang.Runnable {
    public ServerSocket serverSocket;
    public Socket someClient;
    public boolean serverON;
    private final CacheUnitController controller;

    public Server() throws IOException {
        someClient = null;
        serverON = false;
        controller = new CacheUnitController<>();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        String value = (String) evt.getNewValue();

        switch (value) {
            case "start":
                if (serverON) break;
                serverON = true;
                new Thread(this).start();
                break;
            case "stop":
                if (serverON)
                    serverON = false;
                break;
            default:
                break;
        }


    }

    @Override
    public void run() {


            try {
                 serverSocket = new ServerSocket(12345);
            } catch (IOException e) {

            }




        while (serverON) {

            try {
                someClient = serverSocket.accept();
            } catch (IOException e) {
                if (serverON) e.printStackTrace();
            }
            if (!serverON) {
                try {
                    ObjectOutputStream output = new ObjectOutputStream(someClient.getOutputStream());
                    output.writeObject("the server closed the connection");
                    output.flush();
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            }
            new Thread(new HandleRequest<String>(someClient, controller)).start();

        }

        controller.cleanCache();
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
