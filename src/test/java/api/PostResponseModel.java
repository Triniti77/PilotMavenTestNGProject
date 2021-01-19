package api;

import java.util.Objects;

public class PostResponseModel {
    String status;
    EmployeeData data;
    String message;

    public PostResponseModel(String status, EmployeeData data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostResponseModel that = (PostResponseModel) o;
        return  Objects.equals(status, that.status) &&
                data.equals(that.data) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, data, message);
    }

    // после ошибки добавили toString


    @Override
    public String toString() {
        return "PostResponseModel{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}
