package info.yymh.blogmanager.service;

import info.yymh.blogmanager.utils.ResultBean;

public interface CommentsService {
    ResultBean postCommetns(String userName,String passWord,String comments,String token);
    ResultBean deleteComments(String id);
    ResultBean queryComments();
}
