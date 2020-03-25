package info.yymh.blogmanager.service;

import info.yymh.blogmanager.utils.ResultBean;

public interface ArticlesCategoryService {
    ResultBean query();
    ResultBean update(String id,String category);
    ResultBean delete(String id,String category);
}
