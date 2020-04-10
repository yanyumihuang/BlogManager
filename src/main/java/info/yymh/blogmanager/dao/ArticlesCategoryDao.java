package info.yymh.blogmanager.dao;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface ArticlesCategoryDao {
    List<HashMap<String,String>> queryCategory();
    Integer updateCategory(@Param("category") String category,@Param("id") String id);
    Integer deleteCategory(String id);
    Integer insertCategory(String category);
}
