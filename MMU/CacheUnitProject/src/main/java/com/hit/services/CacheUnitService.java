package main.java.com.hit.services;

import main.java.algorithm.IAlgoCache;
import main.java.algorithm.LRUAlgoCacheImpl;
import main.java.algorithm.MFUAlgoCacheImpl;
import main.java.algorithm.Random;
import main.java.com.hit.dao.DaoFileImpl;
import main.java.com.hit.dao.IDao;
import main.java.com.hit.dm.DataModel;
import main.java.com.hit.memory.CacheUnit;

import java.util.Map;

public class CacheUnitService<T> {
    public CacheUnit<T> cache;
    public IDao<Long, DataModel<T>> iDao;
    public int capacity = 2;

    public int numReq;
    public int numDM;
    public int numDMswap;

    public CacheUnitService() {
        cache = new CacheUnit<>(new LRUAlgoCacheImpl<>(capacity));
        iDao = new DaoFileImpl<T>("src/main/resources/datasource.json");
        numReq = 0;
        numDM = 0;
        numDMswap = 0;
    }

    public boolean update(DataModel<T>[] dataModels) {
        numReq++;
        numDM += dataModels.length;
        DataModel<T>[] tempDataModels = cache.putDataModels(dataModels);
        numDMswap += tempDataModels.length;
        for (int i = 0; i < dataModels.length; i++) if (tempDataModels[i] != null) iDao.save(tempDataModels[i]);
        return true;
    }

    public boolean delete(DataModel<T>[] dataModels) {
        numReq++;
        numDM += dataModels.length;

        int i;
        for (i = 0; i < dataModels.length; i++) iDao.delete(dataModels[i]);
        Long[] id = new Long[dataModels.length];
        for (i = 0; i < dataModels.length; i++) id[i] = dataModels[i].getId();
        cache.removeDataModels(id);
        return true;

    }

    public DataModel<T>[] get(DataModel<T>[] dataModels) {
        numReq++;
        numDM += dataModels.length;
        Long[] id = new Long[dataModels.length];
        for (int i = 0; i < dataModels.length; i++) id[i] = dataModels[i].getId();
        DataModel<T>[] tempDataModels = (DataModel<T>[]) cache.getDataModels(id);
        for (int i = 0; i < dataModels.length; i++) if (tempDataModels[i] == null) tempDataModels[i] = iDao.find(id[i]);

        for (int i = 0; i < dataModels.length; i++)
            if (tempDataModels[i] == null) {
                tempDataModels[i] = dataModels[i];
                DataModel[] a = cache.putDataModels(new DataModel[]{dataModels[i]});
                if (a[0] != null) {
                    numDMswap++;
                    iDao.save(a[0]);
                }
            }
        return tempDataModels;

    }

    public String statistic() {
        numReq++;
        return "<html>Capacity: " + capacity +
                "<br/>Algorithm: LRU" +
                "<br/>Total number of requests: " + numReq +
                "<br/>Total number of DataModels (GET/DELETE/UPDATE requestes): " + numDM +
                "<br/>Total number of DataModel swaps (from Cache to Disk): " + numDMswap + "</html>";
    }

    public void cleanCache() {
        Map<Long, DataModel<T>> tempDataModels = cache.getAll();
        for (Long i :
                tempDataModels.keySet()) {
            iDao.save(tempDataModels.get(i));
        }


    }
}
