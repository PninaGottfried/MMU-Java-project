package main.java.com.hit.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.java.com.hit.dm.DataModel;
import main.java.com.hit.services.CacheUnitController;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.Scanner;

public class HandleRequest<T> implements java.lang.Runnable {
    public Socket socket;
    public CacheUnitController<T> cacheUnitController;

    public HandleRequest(java.net.Socket s, CacheUnitController<T> controller) {
        socket = s;
        cacheUnitController = controller;

    }

    @Override
    public void run() {
        ObjectOutputStream output = null;
        ObjectInputStream input = null;
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

            String req = (String) input.readObject();
            Type ref = new TypeToken<Request<DataModel<T>[]>>() {
            }.getType();
            Request<DataModel<T>[]> request = new Gson().fromJson(req, ref);

            switch (request.getHeaders().get("action")) {

                case "GET":
                    output.writeObject("<html>" + (new Gson().toJson(cacheUnitController.get(request.getBody()))).replaceAll("},", "},<br/>") + "</html>");
                    break;
                case "DELETE":
                    output.writeObject((cacheUnitController.delete(request.getBody())) ? "Succeeded" : "Failed");
                    break;
                case "UPDATE":
                    output.writeObject((cacheUnitController.update(request.getBody())) ? "Succeeded" : "Failed");
                    break;
                case "STATISTIC":
                    output.writeObject(cacheUnitController.statistic());
                    break;
                default:
                    break;
            }

            output.flush();
            output.close();
            input.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
