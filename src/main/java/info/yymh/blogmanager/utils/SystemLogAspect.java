package info.yymh.blogmanager.utils;

import info.yymh.blogmanager.annotation.ControllerLog;
import info.yymh.blogmanager.annotation.ServiceLog;
import info.yymh.blogmanager.dao.LogDao;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author sikunliang
 * @date 2020/4/15
 */
@Component
@Aspect
public class SystemLogAspect {
    private TokenUtils tokenUtils;
    private LogDao logDao;

    public SystemLogAspect(TokenUtils tokenUtils, LogDao logDao) {
        this.tokenUtils = tokenUtils;
        this.logDao = logDao;
    }

    @Pointcut("@annotation(info.yymh.blogmanager.annotation.ControllerLog)")
    public void controllerAspect(){}

    @Pointcut("@annotation(info.yymh.blogmanager.annotation.ServiceLog)")
    public void serviceAspect(){}

    @Before(value = "controllerAspect()")
    public void doBefore(JoinPoint joinPoint){
        Logger logger= LoggerFactory.getLogger(this.getClass());
        HttpServletRequest request=
                ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        try {
        String ip=request.getRemoteAddr();
        String token=request.getHeader("token");
        String methadName=joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()";
        String userName=tokenUtils.decodedJWT(token).get("name").toString();
        StringBuilder parameter= new StringBuilder();
        String[] paramNames = ((CodeSignature)joinPoint.getSignature()).getParameterNames();
        Object[] oo=joinPoint.getArgs();
            if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
                for (int i = 0; i < joinPoint.getArgs().length; i++) {
                    if (paramNames[i].equals("request")) {

                    } else {
                        parameter.append(paramNames[i] + ":" + joinPoint.getArgs()[i]).append(",");
                    }
                }
            }
            else if (paramNames.length>0){
                for (int i = 0; i <paramNames.length; i++) {
                    if (paramNames[i].equals("request")){
                        break;
                    }
                    parameter.append(paramNames[i]+":" + "null").append(",");
                }

            }
        String operationalName=SystemLogAspect.getControllerMethodDescription(joinPoint);
        logDao.insertLog(ip,userName,operationalName,methadName, parameter.toString());
        }
        catch (Exception e){
            logger.warn("日志记录异常");
            logger.error("异常信息:{}", e.getMessage());
        }
    }
    @Around(value = "serviceAspect()",argNames = "joinPoint")
    public ResultBean doAfterThrowing(ProceedingJoinPoint joinPoint)  {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        String operationalName = "";
        ResultBean resultBean=null;
        try {
            operationalName = SystemLogAspect.getServiceMthodDescription(joinPoint);
            logger.info("开始进行" + operationalName);
            resultBean = (ResultBean) joinPoint.proceed();
        }
        catch (Throwable throwable){
            logger.warn(String.format("异常已被拦截, 异常信息为: %s", throwable));
            if (resultBean==null){
                resultBean=new ResultBean();
            }
            resultBean.setCode("500");
            resultBean.setStatue("0");
            resultBean.setResultLists(null);
            resultBean.setMessage("出了问题请稍后再试");
        }
        logger.info(operationalName + "操作结束");
        return resultBean;
    }
           /* HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            //获取请求ip
            String ip = request.getRemoteAddr();
            String token=request.getHeader("token");
            String userName=tokenUtils.decodedJWT(token).get("name").toString();
            //获取用户请求方法的参数并组织成字符串
            StringBuilder params = new StringBuilder();
            if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
                for (int i = 0; i < joinPoint.getArgs().length; i++) {
                    params.append(joinPoint.getArgs()[i]).append(",");
                }
            }
            try {
                System.out.println("异常名字:" + e.getClass().getName());
                System.out.println("异常信息:" + e.getMessage());
                System.out.println("操作用户:" + userName);
                System.out.println("异常方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
                System.out.println("请求IP:" + ip);
                System.out.println("请求参数:" + params);
            } catch (Exception ex) {
                logger.error("全局异常捕获异常");
                logger.error("异常信息:{}", ex);
            }
            logger.error("异常方法:{}异常代码:{}异常信息:{}参数:{}", joinPoint.getTarget().getClass().getName() + joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage(), params.toString());*/

    /**
     * 获取注解中对方法的描述信息 用于service层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     */
    public static String getServiceMthodDescription(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(ServiceLog.class).value();
                    break;
                }
            }
        }
        return description;
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public static String getControllerMethodDescription(JoinPoint joinPoint) throws ClassNotFoundException {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(ControllerLog.class).value();
                    break;
                }
            }
        }
        return description;
    }
    public static String getArgsAndValue(JoinPoint joinPoint) throws ClassNotFoundException {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        return "kk";
    }

}
