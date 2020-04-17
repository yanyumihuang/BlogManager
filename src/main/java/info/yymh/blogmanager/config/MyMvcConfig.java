package info.yymh.blogmanager.config;

import info.yymh.blogmanager.dao.UserDao;
import info.yymh.blogmanager.interceptor.PersipemissionInterceptor;
import info.yymh.blogmanager.interceptor.TokenInterceptor;
import info.yymh.blogmanager.utils.TokenUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

/**
 *  扩展springmvc功能
 * @author sikunliang
 * @date 2020/3/13
 */
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    private UserDao userDao;
    private TokenUtils tokenUtils;

    public MyMvcConfig(UserDao userDao, TokenUtils tokenUtils) {
        this.userDao = userDao;
        this.tokenUtils = tokenUtils;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInterceptor(tokenUtils)).addPathPatterns("/**")
                .excludePathPatterns(Arrays.asList("/bootstrap/**", "/css/**","/articles/**","/html/**", "/images/**",
                        "/jquery/**", "/editormd/**","/js/**","/layui/**","/favicon.ico","/generictoken",
                        "/error","/regeist","uploadImg"));
        registry.addInterceptor(new PersipemissionInterceptor(userDao, tokenUtils)).addPathPatterns("/**")
                .excludePathPatterns(Arrays.asList("/bootstrap/**", "/css/**","/html/**","/articles/**", "/images/**","/jquery/**",
                        "/editormd/**","/js/**","/layui/**","/favicon.ico","/generictoken","/login","/error","/regeist","uploadImg"));
    }
}
