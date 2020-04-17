package info.yymh.blogmanager.service;

import info.yymh.blogmanager.utils.ResultBean;

public interface ArticlesCategoryService {
    ResultBean queryCategory(String pageNum,String limit);

    /**
     * 更新文章分类
     * @author sikunliang
     * @date 2020/4/10
     * @param category 分类
     * @param id 分类id
     * @return info.yymh.blogmanager.utils.ResultBean
     */
    ResultBean updateCategory(String id,String category);

    ResultBean deleteCategory(String id);
    ResultBean insertCategory(String category);
}
