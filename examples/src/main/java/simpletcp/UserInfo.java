package simpletcp;

import java.io.Serializable;

/**
 * @author akalxs@gmail.com
 */
public class UserInfo implements Serializable {
    private String id;
    private String name;
    private String password;
    private Integer age;
    private Byte[] headIcon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Byte[] getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(Byte[] headIcon) {
        this.headIcon = headIcon;
    }
}
