package info.yymh.blogmanager.dao;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface LogDao {
    List<HashMap<String,String>> queryLog();
    void insertLog(@Param("ip")String ip,@Param("name")String name,
                   @Param("operationalName")String operationalName,@Param("methadName")String methadName,
                   @Param("parameter") String parameter);
}
