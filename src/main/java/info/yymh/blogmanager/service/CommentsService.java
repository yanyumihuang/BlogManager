package info.yymh.blogmanager.service;

import info.yymh.blogmanager.pojo.Comments;
import info.yymh.blogmanager.utils.ResultBean;

public interface CommentsService {
    ResultBean postCommetns(Comments comments,String token);
    ResultBean deleteComments(String id);
    ResultBean queryComments(String id,String pageNum,String limit);
}
