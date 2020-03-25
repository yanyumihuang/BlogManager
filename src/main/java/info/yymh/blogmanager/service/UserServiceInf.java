package info.yymh.blogmanager.service;

import info.yymh.blogmanager.dao.UserDao;
import info.yymh.blogmanager.utils.Md5Utils;
import info.yymh.blogmanager.utils.ResultBean;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

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
    public ResultBean query(String userName,String passWord) {
        ResultBean resultBean=new ResultBean();
        try {
            String hashPwd= Md5Utils.switchMd5(passWord);
            String result=userDao.query(userName,passWord);
            if (result!=null&&result!="") {
                resultBean.setStatue("1");
                resultBean.setCode("200");
                resultBean.setMessage("用户查询成功");
                return resultBean;
            }
            else {
                resultBean.setStatue("0");
                resultBean.setCode("200");
                resultBean.setMessage("不存在该用户");
                return resultBean;
            }
        } catch (NoSuchAlgorithmException e) {
            resultBean.setStatue("0");
            resultBean.setCode("500");
            resultBean.setMessage("用户查询失败");
            return resultBean;
        }
    }
}
