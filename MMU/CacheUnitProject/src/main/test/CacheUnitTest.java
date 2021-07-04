package main.test;

import main.java.algorithm.LRUAlgoCacheImpl;
import main.java.com.hit.dao.DaoFileImpl;
import main.java.com.hit.dm.DataModel;
import main.java.com.hit.memory.CacheUnit;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

public class CacheUnitTest {
    @Test
    public void testCacheUnit() throws IOException {
        DaoFileImpl<String> dao = new DaoFileImpl<String>("src/main/resources/datasource.json", 3);
        CacheUnit<String> cache = new CacheUnit<String>(new LRUAlgoCacheImpl<Long, DataModel<String>>(3));
        DataModel<String>[] arr = new DataModel[7];
        arr[0] = new DataModel<String>(1L, "ayala");
        arr[1] = new DataModel<String>(2L, "chava");
        arr[2] = new DataModel<String>(3L, "orian");
        arr[3] = new DataModel<String>(4L, "miri");
        arr[4] = new DataModel<String>(5L, "pnina");
        arr[5] = new DataModel<String>(6L, "racheli");
        arr[6] = new DataModel<String>(7L, "tamar");
        DataModel<String>[] removed = cache.putDataModels((DataModel<String>[]) arr);
        for (DataModel<String> data : arr) {
            if (!Arrays.asList(removed)
                    .contains(data)) {
                dao.save(data);
            }
        }
        DataModel<String> data8 = dao.find(5L);
        Assert.assertEquals("find failed", data8, arr[4]);
        dao.delete(data8);
        Long[] ids = {5L};
        cache.removeDataModels(ids);
        data8 = dao.find(5L);
        Assert.assertNull("delete failed", data8);
    }
}
