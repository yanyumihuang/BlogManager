package info.yymh.blogmanager.dao;

import org.apache.ibatis.annotations.Param;

public interface UserDao {
    String query(@Param("userName") String userName, @Param("pwd") String pwd);
}
