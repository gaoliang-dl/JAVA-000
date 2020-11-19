package test.gaol.practice;


import gaol.practice.AutoConfiguration;
import gaol.practice.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AutoConfiguration.class)
public class StudentTest {

    @Autowired
    private Student student;

    @Test
    public void test() {
        System.out.println(student.toString());
    }

}
