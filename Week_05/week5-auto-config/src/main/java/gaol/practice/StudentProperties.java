package gaol.practice;


import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "autoconfig.student")
public class StudentProperties {

    private String id;

    private String name;

}
