package main.java.com.hit.client;

import main.java.com.hit.view.CacheUnitView;

import java.beans.PropertyChangeEvent;

public class CacheUnitClientObserver implements java.beans.PropertyChangeListener {
    public CacheUnitClient client;
    public CacheUnitView view;

    public CacheUnitClientObserver() {
        client = new CacheUnitClient();

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String res = client.send((String) evt.getNewValue());
        view = (CacheUnitView) evt.getSource();
        view.updateUIData(res);
    }
}
