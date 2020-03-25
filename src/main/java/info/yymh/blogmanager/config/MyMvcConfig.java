package info.yymh.blogmanager.config;

import info.yymh.blogmanager.interceptor.PersipemissionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

/**
 * @author sikunliang
 * @Package oinfo.yymh.blogmanager.cnfig
 * @ClassName MyMvcConfig
 * @date 2020/3/13
 * @Description 扩展springmvc功能
 */
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PersipemissionInterceptor()).addPathPatterns("/**")
                .excludePathPatterns(Arrays.asList("/bootstrap/**", "/css/**","/html/**", "/images/**","/jquery/**", "/js/**","/layui/**","/generictoken","/error","/favicon.ico"));;
    }
}
