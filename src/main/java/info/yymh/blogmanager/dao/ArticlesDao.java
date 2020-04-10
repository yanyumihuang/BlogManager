package info.yymh.blogmanager.dao;

import info.yymh.blogmanager.pojo.Articles;

import java.util.HashMap;
import java.util.List;

/**
 * 文章dao
 * @author sikunliang
 * @date 2020/3/20
 * @param
 * @return
 * @Description
 */
public interface ArticlesDao {
    /**
     * 查询文章摘要列表
     * @author sikunliang
     * @date 2020/3/19
     * @return java.lang.String
     */
    List<HashMap<String,String>> queryArticles(String articlesStatue);
    /**
     * 根据分类查询文章摘要列表
     * @author sikunliang
     * @date 2020/3/19
     * @return java.lang.String
     */
    Integer queryByCategory(String id);
   /**
    * 更新文章摘要
    * @author sikunliang
    * @date 2020/3/23
    * @param [id]
    * @return int
    * @Description
    */
   int updateArticles (Articles articles);
   int insertArticles (Articles articles);
}
