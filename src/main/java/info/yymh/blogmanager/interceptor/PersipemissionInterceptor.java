package info.yymh.blogmanager.interceptor;

import info.yymh.blogmanager.dao.UserDao;
import info.yymh.blogmanager.utils.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sikunliang
 * @Package info.yymh.blogmanager.interceptor
 * @ClassName:
 * @date 2020/4/5
 * @Description 权限验证
 */
@Component
public class PersipemissionInterceptor implements HandlerInterceptor {
    private  UserDao userDao;
    private TokenUtils tokenUtils;

    public PersipemissionInterceptor(UserDao userDao, TokenUtils tokenUtils) {
        this.userDao = userDao;
        this.tokenUtils = tokenUtils;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Logger logger= LoggerFactory.getLogger(getClass());
        logger.info("进入权限校验拦截器");
        boolean result=false;
        String url=request.getRequestURI();
        String token=request.getHeader("token");
        Map<String,Object> map=tokenUtils.decodedJWT(token);
        String role= (String) map.get("role");
        logger.info("开始进行权限校验");
        String perNumber=userDao.queryNum(role);
        String[] num = perNumber.split(",");
        List<HashMap<String,String>> persipeUrl=userDao.queryPre(num);
        for (HashMap<String, String> stringStringHashMap : persipeUrl) {
            if (stringStringHashMap.get("pemissionValue").equals(url)) {
                result = true;
                logger.info("权限校验完成");
                logger.info("开始进入" + stringStringHashMap.get("pemissionName"));
                break;
            }
        }
        if (!result){
            response.setStatus(700);
        }
        return result;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
