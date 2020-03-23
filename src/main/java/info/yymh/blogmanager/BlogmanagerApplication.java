package info.yymh.blogmanager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author sikunliang
 */
@MapperScan(value = "info.yymh.blogmanager.dao")
@SpringBootApplication
public class BlogmanagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogmanagerApplication.class, args);
    }

}
