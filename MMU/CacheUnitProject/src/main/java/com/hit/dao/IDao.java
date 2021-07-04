package main.java.com.hit.dao;

public interface IDao<ID extends java.io.Serializable, T> {
    public void save(T entity);

    public void delete(T entity);

    public T find(ID id);
}
