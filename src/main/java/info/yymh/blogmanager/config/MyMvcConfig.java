package info.yymh.blogmanager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author sikunliang
 * @Package oinfo.yymh.blogmanager.cnfig
 * @ClassName MyMvcConfig
 * @date 2020/3/13
 * @Description 扩展springmvc功能
 */
@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {
   /* @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PersipemissionInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/index.html");
    }*/
}
