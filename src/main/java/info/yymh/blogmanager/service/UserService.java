package info.yymh.blogmanager.service;

import info.yymh.blogmanager.utils.ResultBean;

/**
 * @author sikunliang
 */
public interface UserService {
    /**
     * 查询用户
     * @author sikunliang
     * @date 2020/4/12
     * @param userName	用户名
     * @param passWord	密码
     * @param token	    token
     * @return info.yymh.blogmanager.utils.ResultBean
     */
    ResultBean query(String userName, String passWord, String token);
    ResultBean regeist(String userName, String passWord,String email,String token);
}
