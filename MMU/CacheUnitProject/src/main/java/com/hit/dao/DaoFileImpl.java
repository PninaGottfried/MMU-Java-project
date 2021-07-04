package main.java.com.hit.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.java.com.hit.dm.DataModel;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DaoFileImpl<T> implements IDao<Long, DataModel<T>> {
    java.lang.String filePath;
    int capacity;

    public DaoFileImpl(String filePath) {
        this.filePath = filePath;
    }

    public DaoFileImpl(String filePath, int capacity) {
        this.filePath = filePath;
        this.capacity = capacity;
    }

    @Override
    public void save(DataModel<T> entity) {
        File file = new File(this.filePath);
        Gson gson = new Gson();
        Type userListType = new TypeToken<ArrayList<DataModel<T>>>() {
        }.getType();
        List<DataModel<T>> fileData;
        try {
            FileReader fileReader = new FileReader(this.filePath);
            fileData = gson.fromJson(fileReader, userListType);
            fileReader.close();
            if (fileData == null) fileData = new ArrayList<DataModel<T>>();
            fileData.add(entity);
            FileWriter fileWriter = new FileWriter(this.filePath);
            gson.toJson(fileData, fileWriter);
            fileWriter.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    @Override
    public void delete(DataModel<T> entity) {
        File file = new File(this.filePath);
        Gson gson = new Gson();
        Type userListType = new TypeToken<ArrayList<DataModel<T>>>() {
        }.getType();
        List<DataModel<T>> fileData = new ArrayList<DataModel<T>>();
        try {
            FileReader fileReader = new FileReader(this.filePath);
            fileData = gson.fromJson(fileReader, userListType);
            fileReader.close();
            if (fileData.contains(entity)) {
                fileData.remove(entity);
            }
            FileWriter fileWriter = new FileWriter(this.filePath);
            gson.toJson(fileData, fileWriter);
            fileWriter.close();
        } catch (Exception e) {
            System.out.println(e);
            return;
        }
    }

    @Override
    public DataModel<T> find(Long aLong) {
        File file = new File(this.filePath);//?
        Gson gson = new Gson();
        Type userListType = new TypeToken<ArrayList<DataModel<T>>>() {
        }.getType();
        List<DataModel<T>> fileData = new ArrayList<DataModel<T>>();
        try {
            FileReader fileReader = new FileReader(this.filePath);
            fileData = gson.fromJson(fileReader, userListType);
            fileReader.close();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        for (DataModel<T> i :
                fileData) {
            if (aLong.equals(i.getId())) {
                return i;
            }
        }
        return null;
    }
}
