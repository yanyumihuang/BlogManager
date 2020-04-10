package info.yymh.blogmanager.interceptor;

import info.yymh.blogmanager.dao.UserDao;
import info.yymh.blogmanager.utils.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private UserDao userDao;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Logger logger= LoggerFactory.getLogger(getClass());
        logger.info("进入权限校验拦截器");
        boolean result=false;
        String url=request.getRequestURI();
        String token=request.getHeader("token");
        Map<String,Object> map=TokenUtils.decodedJWT(token);
        String role= (String) map.get("role");
        logger.info("开始进行权限校验");
        String perNumber=userDao.queryNum(role);
        String[] num = perNumber.split(",");
        List<HashMap<String,String>> persimeUrl=userDao.queryPre(num);
        for (int i=0;i<persimeUrl.size();i++){
            if (persimeUrl.get(i).get("pemissionValue")==url){
                result=true;
                break;
            }
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
