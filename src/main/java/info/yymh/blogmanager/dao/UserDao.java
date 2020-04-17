package info.yymh.blogmanager.dao;

import info.yymh.blogmanager.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface UserDao {
    User query(@Param("userName") String userName, @Param("pwd") String pwd);
    User queryByEmail(@Param("email") String email, @Param("pwd") String pwd);
    Integer insert(@Param("userName") String userName, @Param("pwd") String pwd,@Param("email") String email);
    String queryNum(String role);
    List<HashMap<String,String>> queryPre(String [] num);
}
