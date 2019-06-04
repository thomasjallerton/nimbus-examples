package models;

import com.nimbusframework.nimbuscore.annotation.annotations.document.DocumentStore;
import com.nimbusframework.nimbuscore.annotation.annotations.persistent.Attribute;
import com.nimbusframework.nimbuscore.annotation.annotations.persistent.Key;

import java.util.Objects;

@DocumentStore
public class DataModel {

    @Key
    private String username = "";

    @Attribute
    private String fullName = "";

    public DataModel() {}

    public DataModel(String username, String fullName) {
        this.username = username;
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataModel dataModel = (DataModel) o;
        return Objects.equals(username, dataModel.username) &&
                Objects.equals(fullName, dataModel.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, fullName);
    }
}
