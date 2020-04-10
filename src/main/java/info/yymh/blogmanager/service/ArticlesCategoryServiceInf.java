package info.yymh.blogmanager.service;

import info.yymh.blogmanager.dao.ArticlesCategoryDao;
import info.yymh.blogmanager.dao.ArticlesDao;
import info.yymh.blogmanager.utils.ResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 *文章分类service层
 * @author sikunliang
 * @date 2020/3/25
 *
 */
@Service
public class ArticlesCategoryServiceInf implements ArticlesCategoryService {
    private ArticlesCategoryDao articlesCategoryDao;
    private ArticlesDao articlesDao;
    public ArticlesCategoryServiceInf(ArticlesCategoryDao articlesCategoryDao, ArticlesDao articlesDao) {
        this.articlesCategoryDao = articlesCategoryDao;
        this.articlesDao = articlesDao;
    }

    @Override
    public ResultBean queryCategory() {
        Logger logger= LoggerFactory.getLogger(getClass());
        logger.info("开始进行文章分类查询");
        ResultBean resultBean=new ResultBean();
        List<HashMap<String,String>> result= articlesCategoryDao.queryCategory();
        if (result.size()==0){
            resultBean.setCode("200");
            resultBean.setMessage("没有分类信息");
            resultBean.setCount("0");
        }
        else {
            resultBean.setResultLists(result);
            resultBean.setCode("200");
            resultBean.setCount(String.valueOf(result.size()));
        }
        logger.info("文章分类查询完成");
        return resultBean;
    }

    @Override
    public ResultBean updateCategory(String category,String id) {
        Logger logger= LoggerFactory.getLogger(getClass());
        logger.info("开始进行文章分类更新");
        ResultBean resultBean=new ResultBean();
        Integer result= articlesCategoryDao.updateCategory(id,category);
        if (result!=0) {
            resultBean.setCode("200");
            resultBean.setMessage("更新成功");
        }
        logger.info("文章分类更新完成");
        return resultBean;
    }

    @Override
    public ResultBean deleteCategory(String id) {
        Logger logger= LoggerFactory.getLogger(getClass());
        logger.info("开始删除文章分类");
        ResultBean resultBean=new ResultBean();
        Integer result=articlesDao.queryByCategory(id);
        if (result>0){
            resultBean.setCode("200");
            resultBean.setStatue("0");
            resultBean.setMessage("删除失败，还有文章属于该分类");
        }
        else {
            result = articlesCategoryDao.deleteCategory(id);
            if (result > 0) {
                resultBean.setCode("200");
                resultBean.setStatue("1");
                resultBean.setMessage("删除成功");
            }
        }
        logger.info("文章分类删除完成");
        return resultBean;
    }

    @Override
    public ResultBean insertCategory(String category) {
        Logger logger= LoggerFactory.getLogger(getClass());
        logger.info("开始删除文章分类");
        ResultBean resultBean=new ResultBean();
        Integer result=articlesCategoryDao.insertCategory(category);
        if (result==0){
            resultBean.setCode("200");
            resultBean.setStatue("0");
            resultBean.setMessage("插入失败");
        }
        else {
                resultBean.setCode("200");
                resultBean.setStatue("1");
                resultBean.setMessage("插入成功");
        }
        logger.info("文章分类增加完成");
        return resultBean;
    }
}
