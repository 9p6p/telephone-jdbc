package tele;

/**
 * 实体类
 */
public class Person {
    private int id;
    private String name;
    private String age;
    private String sex;
    private String telNum;
    private String address;

    public Person(String name, String age, String sex, String telNum, String address) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.telNum = telNum;
        this.address = address;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTelNum() {
        return telNum;
    }

    public void setTelNum(String telNum) {
        this.telNum = telNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "序号：" + this.id + "#" + "\t" +
                "姓名：" + this.name + "\t\t" +
                "年龄：" + this.age + "\t\t" +
                "性别：" + this.sex + "\t\t" +
                "电话号码：" + this.telNum + "\t\t" +
                "地址：" + this.address;
    }
}
