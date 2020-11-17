package gaol.practice.class4;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@SpringBootApplication
public class Week5Application implements CommandLineRunner {

    @Resource
    private DataSource datasource;

    private static final Logger logger = LoggerFactory.getLogger(Week5Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Week5Application.class, args);
    }

    @Override
    public void run(String... args) {
        logger.info("running");
        try(Connection conn = datasource.getConnection()) {
            logger.info(conn.toString());
            query(conn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void query(Connection conn) throws SQLException {
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            // 1.通过数据库的连接操作数据库，实现增删改查（使用Statement类）
            String name = "张三";
            // 2.预编译
            String sql = "select * from userinfo where name=?";
            statement = conn.prepareStatement(sql);
            statement.setString(1, name);
            // 3.查询
            rs = statement.executeQuery();
            // 4.处理数据库的返回结果(使用ResultSet类)
            while (rs.next()) {
                logger.info(rs.getString("name") + " " + rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 5.关闭资源
            if (rs != null) {
                rs.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
    }
}
