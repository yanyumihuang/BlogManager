package info.yymh.blogmanager.dao;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface CommentsDao {
    String postComments(@Param("comments") String comments,@Param("userId") Integer userId);
    Integer deleteComments(String id);
    List<HashMap<String,String>> queryComments();
}
