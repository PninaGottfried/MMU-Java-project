package main.java.com.hit.memory;

import main.java.algorithm.IAlgoCache;
import main.java.com.hit.dm.DataModel;

import java.util.Map;

public class CacheUnit<T> {
    public IAlgoCache<Long, DataModel<T>> algo;

    public CacheUnit(IAlgoCache<Long, DataModel<T>> IAlgoCache) {
        algo = IAlgoCache;
    }

    public DataModel<T>[] getDataModels(java.lang.Long[] ids) {
        DataModel[] arrayToReturn = new DataModel[ids.length];
        for (int i = 0; i < ids.length; i++) {
            arrayToReturn[i] = algo.getElement(ids[i]);
        }
        return arrayToReturn;
    }

    ;

    public DataModel<T>[] putDataModels(DataModel<T>[] datamodels) {
        DataModel[] arrayToReturn = new DataModel[datamodels.length];
        for (int i = 0; i < datamodels.length; i++) {
            arrayToReturn[i] = algo.putElement(datamodels[i].getId(), datamodels[i]);
        }
        return arrayToReturn;
    }

    public void removeDataModels(java.lang.Long[] ids) {
        for (java.lang.Long i : ids) {
            algo.removeElement(i);
        }
    }

    public Map<Long, DataModel<T>> getAll() {
        return algo.getAll();
    }

}
