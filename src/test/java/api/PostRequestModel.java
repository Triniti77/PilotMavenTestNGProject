package api;

public class PostRequestModel {
    String name;
    String salary;
    String age;
    //


    public PostRequestModel(String name, String salary, String age) {
        this.name = name;
        this.salary = salary;
        this.age = age;
    }

    // это в лекции ToString в виде JSON


    @Override
    public String toString() {
        return "{\"name\":\"" + name + "\",\"salary\":\"" + salary + "\",\"age\":\"" + age + "\"}";
    }
}
