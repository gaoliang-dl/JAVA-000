package gaol.practice;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnClass(Student.class)
@ConditionalOnProperty(prefix = "autoconfig", name = "enabled", havingValue = "true")
@EnableConfigurationProperties(StudentProperties.class)
public class AutoConfiguration {

    private final StudentProperties studentProperties;

    public AutoConfiguration(StudentProperties studentProperties) {
        this.studentProperties = studentProperties;
    }

    @Bean
    public Student student() {
        Student student = new Student(studentProperties.getId(),studentProperties.getName());
        return student;
    }
}
