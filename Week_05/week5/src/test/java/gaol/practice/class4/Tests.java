package gaol.practice.class4;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

//@SpringBootTest
class Tests {

    @Test
    void testJdbc() {
        try {
            new Jdbc().conn();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void testTx() {
        try {
            new Jdbc().tx();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
