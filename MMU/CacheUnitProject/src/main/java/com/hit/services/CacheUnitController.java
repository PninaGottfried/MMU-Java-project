package main.java.com.hit.services;

import main.java.com.hit.dm.DataModel;

public class CacheUnitController<T> {
    public CacheUnitService<T> cacheUnitService;

    public CacheUnitController() {
        cacheUnitService = new CacheUnitService<>();
    }

    public boolean update(DataModel<T>[] dataModels) {
        return cacheUnitService.update(dataModels);
    }

    public boolean delete(DataModel<T>[] dataModels) {
        return cacheUnitService.delete(dataModels);
    }

    public DataModel<T>[] get(DataModel<T>[] dataModels) {
        return cacheUnitService.get(dataModels);
    }

    public String statistic() {
        return cacheUnitService.statistic();
    }

    public void cleanCache() {
        cacheUnitService.cleanCache();
    }
}
