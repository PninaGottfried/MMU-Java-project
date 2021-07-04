package main.java.com.hit.dm;

import java.util.Objects;

public class DataModel<T> extends java.lang.Object implements java.io.Serializable {
    java.lang.Long dataModelId;
    T content;

    public DataModel(Long id, T content) {
        this.dataModelId = id;
        this.content = content;
    }

    @Override
    public String toString() {
        return "DataModel{" +
                "dataModelId=" + dataModelId +
                ", content=" + content +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataModel<?> dataModel = (DataModel<?>) o;
        return dataModelId.equals(dataModel.dataModelId) && content.equals(dataModel.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataModelId, content);
    }

    public Long getId() {
        return dataModelId;
    }

    public void setId(Long id) {
        this.dataModelId = id;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
