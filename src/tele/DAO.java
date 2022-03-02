package tele;
import java.util.List;

public interface DAO{
    //增加
    void add(Person person);
    //删除
    void delete(int id);
    //删除全部
    void deleteAll();
    //修改
    void update(Person person);
    //获取
    Person get(int id);
    //查询
    List<Person> list();
    //分页查询
    List<Person> list(int start, int count);
}
