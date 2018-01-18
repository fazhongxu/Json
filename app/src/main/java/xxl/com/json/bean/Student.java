package xxl.com.json.bean;

/**
 * Created by xxl on 2018/1/18.
 */

public class Student {
    private String name;
    private int age;
    private String college;

    public Student(){}

    public Student(String name, int age, String college) {
        this.name = name;
        this.age = age;
        this.college = college;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }
}
