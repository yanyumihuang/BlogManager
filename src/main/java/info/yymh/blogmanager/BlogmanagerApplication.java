package info.yymh.blogmanager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author sikunliang
 */
@MapperScan(value = "info.yymh.blogmanager.dao")
@SpringBootApplication
@EnableAspectJAutoProxy
public class BlogmanagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogmanagerApplication.class, args);
    }

}
