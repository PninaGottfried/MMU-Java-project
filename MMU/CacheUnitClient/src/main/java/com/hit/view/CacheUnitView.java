package main.java.com.hit.view;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

public class CacheUnitView {
    public PropertyChangeSupport support;
    public JFrame jFrame;
    public JLabel label;

    public CacheUnitView() {
        support = new PropertyChangeSupport(this);
    }


    public void start() {

        jFrame = new JFrame("CacheUnitUI");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(400, 300);
        GridLayout layout = new GridLayout(2, 1);
        jFrame.setLayout(layout);


        JButton button1 = new JButton("📊 Show Statistic");
        JButton button2 = new JButton("📂 Load a Request");
        label = new JLabel("Welcome! :)");

        JFileChooser jFileChooser = new JFileChooser("src\\main\\resources");
        jFileChooser.setAcceptAllFileFilterUsed(false);
        jFileChooser.addChoosableFileFilter(new FileNameExtensionFilter(".json file", "json"));
        jFileChooser.addChoosableFileFilter(new FileNameExtensionFilter(".txt file", "txt"));
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                support.firePropertyChange("", null, "{\"headers\": {\"action\": \"STATISTIC\"}}");

            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (0 == jFileChooser.showOpenDialog(null)) {

                    List<String> lines = null;
                    String req;

                    try {
                        lines = Files.readAllLines(Paths.get(String.valueOf(jFileChooser.getSelectedFile())), StandardCharsets.UTF_8);
                        req = String.join("\n", lines);
                        support.firePropertyChange("", null, req);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

        JPanel panel = new JPanel();
        panel.add(button1);
        panel.add(button2);
        jFrame.add(panel);
        jFrame.add(label);

        jFrame.setVisible(true);

    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    public <T> void updateUIData(T t) {

        label.setText((String) t);
    }

}
