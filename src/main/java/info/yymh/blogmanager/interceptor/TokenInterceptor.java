package info.yymh.blogmanager.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.yymh.blogmanager.utils.ResultBean;
import info.yymh.blogmanager.utils.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * 拦截所有请求进行token的判断
 * @author sikunliang
 * @date 2020/3/23
 */
public class TokenInterceptor implements HandlerInterceptor {
    private TokenUtils tokenUtils;

    public TokenInterceptor(TokenUtils tokenUtils) {
        this.tokenUtils = tokenUtils;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果访问的是管理页面那么校验失败的话应该重定向到登陆页面，这个等登陆页面写好再完善
        Logger logger= LoggerFactory.getLogger(getClass());
        logger.info("进入token校验连接器");
        String token=request.getHeader("token");
        logger.info("开始进行校验");
        ResultBean resultBean = new ResultBean();
        if (token==null||token.isEmpty()){
            resultBean.setCode("200");
            resultBean.setStatue("0");
            resultBean.setMessage("token error");
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(resultBean);
            PrintWriter pw = response.getWriter();
            pw.print(json);
            pw.flush();
            pw.close();
            logger.info("token校验完成");
            return false;
        }
        else {
            Map<String,Object> map=tokenUtils.decodedJWT(token);
            if (map.get("role")==""||tokenUtils.verifyJWT(token)){
                logger.info("token校验完成");
                return true;
            }
            else {
                resultBean.setCode("200");
                resultBean.setStatue("0");
                resultBean.setMessage("token error");
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(resultBean);
                PrintWriter pw = response.getWriter();
                pw.print(json);
                pw.flush();
                pw.close();
                logger.info("token校验完成");
                return false;
            }
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
