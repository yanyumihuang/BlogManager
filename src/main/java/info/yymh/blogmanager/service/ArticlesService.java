package info.yymh.blogmanager.service;

import info.yymh.blogmanager.pojo.Articles;
import info.yymh.blogmanager.utils.ResultBean;

/**
 * 文章service
 * @author sikunliang
 * @date 2020/3/20
 * @param
 * @return
 * @Description
 */
public interface ArticlesService {

    /**
     * 查询文章摘要
     * @author sikunliang
     * @date 2020/3/19
     * @param []
     * @return info.yymh.blogmanager.utils.ResultBean
     */
    ResultBean queryArticles(String pageNum,String token);

    /**
     * 更新文章摘要
     * @author sikunliang
     * @date 2020/3/13
     * @param {articles}
     * @return info.yymh.blogmanager.utils.ResultBean
     */
    ResultBean updateArticles(Articles articles);

    ResultBean insertArticles(Articles articles);
}
