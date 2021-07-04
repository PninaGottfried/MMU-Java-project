package main.java.com.hit.util;

import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Observable;
import java.util.Scanner;

public class CLI extends Observable implements java.lang.Runnable {
    public Scanner _in;
    public PrintStream _out;
    public PropertyChangeSupport arr;
    public String news;

    public CLI(java.io.InputStream in, java.io.OutputStream out) {
        _in = new Scanner(in);
        _out = new PrintStream(out);
        arr = new PropertyChangeSupport(this);

    }

    @Override
    public void run() {
        String command;
        while (true) {
            _out.println("Please enter your command:");
            command = _in.next();
            switch (command) {
                case "start":
                    _out.println("Starting server.......\n");
                    write(command);
                    break;
                case "stop":
                    _out.println("Shutdown server\n");
                    write(command);
                    break;
                default:
                    _out.println("Not a valid command\n");

            }
        }


    }

    public void addPropertyChangeListener(java.beans.PropertyChangeListener pcl) {
        arr.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(java.beans.PropertyChangeListener pcl) {
        arr.removePropertyChangeListener(pcl);
    }

    public void write(java.lang.String string) {
        arr.firePropertyChange("write", news, string);
        news = string;
    }

}
