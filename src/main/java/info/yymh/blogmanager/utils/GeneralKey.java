package info.yymh.blogmanager.utils;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author sikunliang
 * @Package info.yymh.blogmanager.utils
 * @ClassName:
 * @date 2020/3/24
 * @Description 生成key
 */
@Component
@Aspect
public class GeneralKey {
    private ConfigBean configBean;

    public GeneralKey(ConfigBean configBean) {
        this.configBean = configBean;
    }
    @Pointcut("execution(* info.yymh.blogmanager.utils.TokenUtils.generalKey(..))")
    public void pointCut() {}

    @Before("pointCut()")
    public  void before() {
        String stringKey=configBean.getKey();
        long ttlMillis=configBean.getTtlMillis();
        TokenUtils.setStringKey(stringKey);
        TokenUtils.setTtlMillis(ttlMillis);
    }
}
