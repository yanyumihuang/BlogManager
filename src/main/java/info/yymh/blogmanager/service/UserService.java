package info.yymh.blogmanager.service;

import info.yymh.blogmanager.utils.ResultBean;

public interface UserService {
    ResultBean query(String userName, String passWord, String token);
}
