package info.yymh.blogmanager.dao;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface TokenDao {
    Map<String,String> queryRole(@Param("id") String id);
    String queryRolePemission(@Param("id") String id);
}
