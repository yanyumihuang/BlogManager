package info.yymh.blogmanager.service;

import info.yymh.blogmanager.annotation.ServiceLog;
import info.yymh.blogmanager.dao.UserDao;
import info.yymh.blogmanager.pojo.User;
import info.yymh.blogmanager.utils.Md5Utils;
import info.yymh.blogmanager.utils.ResultBean;
import info.yymh.blogmanager.utils.RoleLevel;
import info.yymh.blogmanager.utils.TokenUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.regex.Pattern;

/**
 * @author sikunliang
 * @date 2020/3/25
 */
@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private TokenUtils tokenUtils;

    public UserServiceImpl(UserDao userDao, TokenUtils tokenUtils) {
        this.userDao = userDao;
        this.tokenUtils = tokenUtils;
    }

    @Override
    @ServiceLog("查询用户")
    public ResultBean  query(String userName,String passWord,String token)  {
        ResultBean resultBean=new ResultBean();
            String hashPwd= Md5Utils.switchMd5(passWord);
            User user=userDao.query(userName,hashPwd);
            //登陆成功的话应该生成token并返回
            if (user.getRoles().equals(RoleLevel.admin.toString())) {
                String uuid= UUID.randomUUID().toString();
                token = tokenUtils.createToken(uuid,"admin",user.getId(),user.getName());
                resultBean.setMessage(token);
                resultBean.setStatue("1");
            }
            else {
                resultBean.setMessage("");
                resultBean.setStatue("0");
            }
            resultBean.setCode("200");
            return resultBean;
    }

    @Override
    public ResultBean regeist(String userName, String passWord, String email, String token) {
        ResultBean resultBean = new ResultBean();
        String patternEmail = "^([A-Za-z0-9_\\-.])+@([A-Za-z0-9_\\-.])+.([A-Za-z]{2,4})$";
        String patterPassWord = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
        if (Pattern.matches(patternEmail, email) && Pattern.matches(patterPassWord, passWord)) {
            String hashPwd = Md5Utils.switchMd5(passWord);
            User user = userDao.queryByEmail(email, hashPwd);
            String uuid = UUID.randomUUID().toString();
            if (user != null) {
                token = tokenUtils.createToken(uuid, RoleLevel.geneleRole.toString(), user.getId(), userName);
                resultBean.setStatue("1");
            } else {
                Integer id = userDao.insert(userName, hashPwd,email);
                if (id > 0) {
                    token = tokenUtils.createToken(uuid, RoleLevel.geneleRole.toString(), id.toString(), userName);
                    resultBean.setStatue("1");
                } else {
                    resultBean.setStatue("0");
                }
            }
            resultBean.setCode("200");
            resultBean.setStatue("1");
            resultBean.setMessage(token);
            return resultBean;
        }
        else {
            resultBean.setCode("200");
            resultBean.setStatue("0");
            resultBean.setMessage("邮箱或密码格式不对");
            return resultBean;
        }
        }


}
