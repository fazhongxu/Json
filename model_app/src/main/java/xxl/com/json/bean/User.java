package xxl.com.json.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by xxl on 2018/1/2.
 */
@Entity
public class User {
    @Id(autoincrement = true)
    private Long id;

    private int age;

    private String name;

    @Generated(hash = 1660313821)
    public User(Long id, int age, String name) {
        this.id = id;
        this.age = age;
        this.name = name;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
