package info.yymh.blogmanager.service;

import info.yymh.blogmanager.dao.UserDao;
import info.yymh.blogmanager.pojo.User;
import info.yymh.blogmanager.utils.Md5Utils;
import info.yymh.blogmanager.utils.ResultBean;
import info.yymh.blogmanager.utils.TokenUtils;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * @author sikunliang
 * @Package info.yymh.blogmanager.service
 * @ClassName:
 * @date 2020/3/25
 * @Description
 */
@Service
public class UserServiceInf implements UserService {
    private UserDao userDao;

    public UserServiceInf(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public ResultBean  query(String userName,String passWord,String token) {
        ResultBean resultBean=new ResultBean();
        try {
            String hashPwd= Md5Utils.switchMd5(passWord);
            User user=userDao.query(userName,hashPwd);
            //应该返回用户id，登陆成功的话应该生成token并返回
            if (user!=null&&user.getId()!="") {
                String uuid= UUID.randomUUID().toString();
                token = TokenUtils.createToken(uuid, user.getRoles(),user.getId(),user.getName());
                resultBean.setMessage(token);
                resultBean.setStatue("1");
                resultBean.setCode("200");
                return resultBean;
            }
            else {
                resultBean.setMessage(token);
                resultBean.setStatue("0");
                resultBean.setCode("200");
                return resultBean;
            }
        } catch (NoSuchAlgorithmException e) {
            resultBean.setMessage(token);
            resultBean.setStatue("0");
            resultBean.setCode("500");
            return resultBean;
        }
    }
}
