package info.yymh.blogmanager.dao;

import info.yymh.blogmanager.pojo.Comments;

import java.util.HashMap;
import java.util.List;

public interface CommentsDao {
    Integer postComments(Comments comments);
    Integer deleteComments(String id);
    List<HashMap<String,String>> queryComments();
}
