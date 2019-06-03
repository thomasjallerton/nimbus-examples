package models;

import java.util.Objects;

public class ResponseModel {

    private String username = "";

    public ResponseModel(String username, String fullName, boolean success) {
        this.username = username;
        this.fullName = fullName;
        this.success = success;
    }

    private String fullName = "";

    public ResponseModel(boolean success) {
        this.success = success;
    }

    private boolean success = false;

    public ResponseModel() {}

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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
        ResponseModel model = (ResponseModel) o;
        return success == model.success &&
                Objects.equals(username, model.username) &&
                Objects.equals(fullName, model.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, fullName, success);
    }
}
