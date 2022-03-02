package tele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class PersonDAO implements DAO {
    public PersonDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/telephone?characterEncoding=UTF-8&useSSL=false",
                "root", "102201");
    }
    public int getTotal() {
        int total = 0;
        try (Connection c = getConnection(); Statement s = c.createStatement()) {
            String sql = "select count(*) from person";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    /**
     * 用户添加记录业务逻辑控制
     */
    public void addLogic() {
        Menu menu = new Menu();
        TelNoteRegex telNoteRegex = new TelNoteRegex();
        while (true) {
            menu.addMenu();
            int item = telNoteRegex.menuItemValidate(1, 3);
            switch (item) {
                case 1:
                    this.addOperation();
                    break;
                case 2:
                    this.showAll();
                    break;
                case 3:
                    return;
            }
        }
    }
    public void addOperation() {
        TelNoteRegex telNoteRegex = new TelNoteRegex();
        String name = telNoteRegex.nameValidate();
        String age = telNoteRegex.ageValidate();
        String sex = telNoteRegex.sexValidate();
        String telNum = telNoteRegex.telNumValidate();
        String address = telNoteRegex.addressValidate();
        Person person = new Person(name, age, sex, telNum, address);
        this.add(person);
    }
    @Override
    public void add(Person person) {
        String sql = "insert into person values(null,?,?,?,?,?)";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, person.getName());
            ps.setString(2, person.getAge());
            ps.setString(3, person.getSex());
            ps.setString(4, person.getTelNum());
            ps.setString(5, person.getAddress());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                person.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void showAll() {
        if (this.getTotal() == 0) {
            System.out.println("没有任何记录");
            return;
        }
        for (Person one : list(0, this.getTotal())) {
            System.out.println(one);
        }
    }

    /**
     * 用户查询记录业务逻辑控制
     */
    public void searchLogic() {
        Menu menu = new Menu();
        TelNoteRegex telNoteRegex = new TelNoteRegex();
        while (true) {
            menu.searchMenu();
            int item = telNoteRegex.menuItemValidate(1, 7);
            switch (item) {
                case 1:
                    this.searchByName();
                    break;
                case 2:
                    this.searchByAge();
                    break;
                case 3:
                    this.searchBySex();
                    break;
                case 4:
                    this.searchByTelNum();
                    break;
                case 5:
                    this.searchByAddress();
                    break;
                case 6:
                    this.showAll();
                    break;
                case 7:
                    return;
            }
        }
    }
    public void search(String query, String sql) {
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, query);
            ResultSet rs = ps.executeQuery(sql);
            if (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String age = rs.getString(3);
                String sex = rs.getString(4);
                String telNum = rs.getString(5);
                String address = rs.getString(6);
                Person person = new Person(name, age, sex, telNum, address);
                person.setId(id);
                System.out.println(person);
            }
            else System.out.println("没有此人记录");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void searchByName() {
        TelNoteRegex telNoteRegex = new TelNoteRegex();
        String nameQuery = telNoteRegex.nameValidate();
        String sql = "select * from person where name = ?";
        this.search(nameQuery, sql);
    }
    public void searchByAge() {
        TelNoteRegex telNoteRegex = new TelNoteRegex();
        String ageQuery = telNoteRegex.ageValidate();
        String sql = "select * from person where age = ?";
        this.search(ageQuery, sql);
    }
    public void searchBySex() {
        TelNoteRegex telNoteRegex = new TelNoteRegex();
        String sexQuery = telNoteRegex.sexValidate();
        String sql = "select * from person where sex = ?";
        this.search(sexQuery, sql);
    }
    public void searchByTelNum() {
        TelNoteRegex telNoteRegex = new TelNoteRegex();
        String telNumQuery = telNoteRegex.telNumValidate();
        String sql = "select * from person where telNum = ?";
        this.search(telNumQuery, sql);
    }
    public void searchByAddress() {
        TelNoteRegex telNoteRegex = new TelNoteRegex();
        String addressQuery = telNoteRegex.addressValidate();
        String sql = "select * from person where address = ?";
        this.search(addressQuery, sql);
    }

    /**
     * 修改记录业务逻辑控制
     */
    public void modifyLogic() {
        Menu menu = new Menu();
        TelNoteRegex telNoteRegex = new TelNoteRegex();
        while (true) {
            menu.modifyMenu();
            int item = telNoteRegex.menuItemValidate(1, 3);
            switch (item) {
                case 1:
                    this.showAll();
                    break;
                case 2:
                    this.modifyOperation();
                    break;
                case 3:
                    return;
            }
        }
    }
    public void modifyOperation() {
        TelNoteRegex telNoteRegex = new TelNoteRegex();
        Menu menu = new Menu();
        //对被修改的记录的序号进行验证，可以使用对菜单项验证的方法来完成
        System.out.println("请输入记录的序号：");
        int itemNum = telNoteRegex.menuItemValidate(1, this.getTotal());
        menu.subModifyMenu();
        int menuItem = telNoteRegex.menuItemValidate(1, 6);
        switch (menuItem) {
            case 1:
                modifyByName(itemNum);
                break;
            case 2:
                modifyByAge(itemNum);
                break;
            case 3:
                modifyBySex(itemNum);
                break;
            case 4:
                modifyByTelNum(itemNum);
                break;
            case 5:
                modifyByAddress(itemNum);
                break;
            case 6:
                break;
        }
    }
    public void modify(String value, int id, String sql) {
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, value);
            ps.setInt(2, id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void modifyByName(int id) {
        TelNoteRegex telNoteRegex = new TelNoteRegex();
        String nameModify = telNoteRegex.nameValidate();
        String sql = "update person set name = ? where id = ?";
        this.modify(nameModify, id, sql);
    }
    public void modifyByAge(int id) {
        TelNoteRegex telNoteRegex = new TelNoteRegex();
        String ageModify = telNoteRegex.ageValidate();
        String sql = "update person set age = ? where id = ?";
        this.modify(ageModify, id, sql);
    }
    public void modifyBySex(int id) {
        TelNoteRegex telNoteRegex = new TelNoteRegex();
        String sexModify = telNoteRegex.sexValidate();
        String sql = "update person set sex = ? where id = ?";
        this.modify(sexModify, id, sql);
    }
    public void modifyByTelNum(int id) {
        TelNoteRegex telNoteRegex = new TelNoteRegex();
        String telNumModify = telNoteRegex.telNumValidate();
        String sql = "update person set telNum = ? where id = ?";
        this.modify(telNumModify, id, sql);
    }
    public void modifyByAddress(int id) {
        TelNoteRegex telNoteRegex = new TelNoteRegex();
        String addressModify = telNoteRegex.addressValidate();
        String sql = "update person set address = ? where id = ?";
        this.modify(addressModify, id, sql);
    }
    @Override
    public void update(Person person) {
        String sql = "update person set name = ?, age = ? , sex = ? , telNum = ?, address = ? where id = ?";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, person.getName());
            ps.setString(2, person.getAge());
            ps.setString(3, person.getSex());
            ps.setString(4, person.getTelNum());
            ps.setString(5, person.getAddress());
            ps.setInt(6, person.getId());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除记录业务逻辑控制
     */
    public void deleteLogic() {
        Menu menu = new Menu();
        TelNoteRegex telNoteRegex = new TelNoteRegex();
        while (true) {
            menu.deleteMenu();
            int item = telNoteRegex.menuItemValidate(1, 4);
            switch (item) {
                case 1:
                    this.showAll();
                    break;
                case 2:
                    this.deleteOperation();
                    break;
                case 3:
                    this.deleteAll();
                    break;
                case 4:
                    return;
            }
        }
    }
    public void deleteOperation() {
        TelNoteRegex telNoteRegex = new TelNoteRegex();
        System.out.println("请输入记录序号");
        int itemNum = telNoteRegex.menuItemValidate(1, this.getTotal());
        this.delete(itemNum);
        System.out.println("删除成功！请继续操作！");
    }
    @Override
    public void delete(int id) {
        String sql = "delete from person where id = ?";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void deleteAll() {
        String sql = "truncate person";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("记录已清空，请继续操作！");
    }

    /**
     * 排序记录业务逻辑控制
     */
    public void orderLogic() {
        Menu menu = new Menu();
        TelNoteRegex telNoteRegex = new TelNoteRegex();
        List<Person> persons = this.list(0, getTotal());
        while (true) {
            menu.orderMenu();
            int item = telNoteRegex.menuItemValidate(1, 5);
            switch (item) {
                case 1:
                    this.orderName(persons);
                    break;
                case 2:
                    this.orderAge(persons);
                    break;
                case 3:
                    this.orderSex(persons);
                    break;
                case 4:
                    this.showAll();
                    break;
                case 5:
                    return;
            }
        }
    }
    public void orderName(List<Person> persons) {
        persons.sort(new orderByName());
        for (Person person : persons) {
            System.out.println(person);
        }
    }
    public void orderAge(List<Person> persons) {
        persons.sort(new orderByAge());
        for (Person person : persons) {
            System.out.println(person);
        }
    }
    public void orderSex(List<Person> persons) {
        persons.sort(new orderBySex());
        for (Person person : persons) {
            System.out.println(person);
        }
    }
    static class orderByName implements Comparator<Person> {
        @Override
        public int compare(Person o1, Person o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }
    static class orderByAge implements Comparator<Person> {
        @Override
        public int compare(Person o1, Person o2) {
            return o1.getAge().compareTo(o2.getAge());
        }
    }
    static class orderBySex implements Comparator<Person> {
        @Override
        public int compare(Person o1, Person o2) {
            return o1.getSex().compareTo(o2.getSex());
        }
    }

    @Override
    public Person get(int id) {
        Person person = null;
        try (Connection c = getConnection(); Statement s = c.createStatement()) {
            String sql = "select * from person where id = " + id;
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                String name = rs.getString(2);
                String age = rs.getString(3);
                String sex = rs.getString(4);
                String telNum = rs.getString(5);
                String address = rs.getString(6);
                person = new Person(name, age, sex, telNum, address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    @Override
    public List<Person> list() {
        return list(0, Short.MAX_VALUE);
    }

    @Override
    public List<Person> list(int start, int count) {
        List<Person> persons = new ArrayList<>();
        String sql = "select * from person order by id desc limit ?,? ";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, start);
            ps.setInt(2, count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString(2);
                String age = rs.getString(3);
                String sex = rs.getString(4);
                String telNum = rs.getString(5);
                String address = rs.getString(6);
                Person person = new Person(name, age, sex, telNum, address);
                int id = rs.getInt(1);
                person.setId(id);
                persons.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }
}
