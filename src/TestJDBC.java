import tele.Person;

import java.sql.*;

public class TestJDBC {
    public static void main(String[] args) {

        Connection c = null;
        Statement s = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/telephone?characterEncoding=UTF-8&useSSL=false",
                    "root", "102201");

            String sql = "select * from person where name = ?";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setString(1, "su");
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
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            // 数据库的连接时有限资源，相关操作结束后，养成关闭数据库的好习惯
            // 先关闭Statement
            if (s != null)
                try {
                    s.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            // 后关闭Connection
            if (c != null)
                try {
                    c.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

        }
    }
}