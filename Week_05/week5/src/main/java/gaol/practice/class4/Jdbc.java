package gaol.practice.class4;

import java.sql.*;

public class Jdbc {

    private final static String PASSWORD = "1qaz#EDC";

    public void conn() throws SQLException {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        //init
        String URL = "jdbc:mysql://10.1.52.34:3306/test?characterEncoding=utf-8&useSSL=false";
        String USER = "root";
        try {
            // 1.加载驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 2.获得数据库链接
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            // 3.通过数据库的连接操作数据库，实现增删改查（使用Statement类）
            String name = "张三";
            // 4.预编译
            String sql = "select * from userinfo where name=?";
            statement = conn.prepareStatement(sql);
            statement.setString(1, name);
            // 5.查询
            rs = statement.executeQuery();
            // 6.处理数据库的返回结果(使用ResultSet类)
            while (rs.next()) {
                System.out.println(rs.getString("name") + " " + rs.getString("password"));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 7.关闭资源
            if (rs != null) {
                rs.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public void tx() throws SQLException {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        //init
        String URL = "jdbc:mysql://10.1.52.34:3306/test?characterEncoding=utf-8&useSSL=false";
        String USER = "root";
        try {
            // 1.加载驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 2.获得数据库链接
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            conn.setAutoCommit(false);  //将自动提交设置为false ！！！
            // 3.通过数据库的连接操作数据库，实现增删改查（使用Statement类）
            String password = "000002";
            // 4.预编译
            String sql = "update userinfo set password = ? where id=2";
            statement = conn.prepareStatement(sql);
            statement.setString(1, password);
            statement.executeUpdate(); // !!!
            sql = "select * from userinfo where id=2";
            statement = conn.prepareStatement(sql);
            // 5.查询
            rs = statement.executeQuery();
//            double db = 1/0; // !!!
            conn.commit();      //当两个操作成功后手动提交 ！！！
            // 6.处理数据库的返回结果(使用ResultSet类)
            while (rs.next()) {
                System.out.println(rs.getString("name") + " " + rs.getString("password"));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            conn.rollback();    //一旦其中一个操作出错都将回滚，使两个操作都不成功 ！！！
        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();    //一旦其中一个操作出错都将回滚，使两个操作都不成功 ！！！
        } finally {
            // 7.关闭资源
            if (rs != null) {
                rs.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
}
