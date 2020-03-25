package info.yymh.blogmanager.dao;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface ArticlesCategoryDao {
    List<HashMap<String,String>> query();
    Integer update(@Param("category") String category,@Param("id") String id);
    Integer delete(String id);
}
