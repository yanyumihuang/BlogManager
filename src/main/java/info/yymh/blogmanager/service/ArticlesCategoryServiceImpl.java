package info.yymh.blogmanager.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import info.yymh.blogmanager.annotation.ServiceLog;
import info.yymh.blogmanager.dao.ArticlesCategoryDao;
import info.yymh.blogmanager.dao.ArticlesDao;
import info.yymh.blogmanager.utils.ResultBean;
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
public class ArticlesCategoryServiceImpl implements ArticlesCategoryService {
    private ArticlesCategoryDao articlesCategoryDao;
    private ArticlesDao articlesDao;
    public ArticlesCategoryServiceImpl(ArticlesCategoryDao articlesCategoryDao, ArticlesDao articlesDao) {
        this.articlesCategoryDao = articlesCategoryDao;
        this.articlesDao = articlesDao;
    }

    @Override
    @ServiceLog("查询文章分类")
    public ResultBean queryCategory(String pageNum,String limit) {
        ResultBean resultBean=new ResultBean();
        List<HashMap<String,String>> result=null;
        if (pageNum != null && limit != null) {
            Page page = PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(limit), true);
            result= articlesCategoryDao.queryCategory();
        }
        else{
            result= articlesCategoryDao.queryCategory();
        }
        if (result.size()==0){
            resultBean.setCode("200");
            resultBean.setStatue("0");
            resultBean.setMessage("没有分类信息");
            resultBean.setCount("0");
        }
        else {
            PageInfo pageInfo=new PageInfo(result);
            resultBean.setResultLists(result);
            resultBean.setCode("200");
            resultBean.setCount(String.valueOf(pageInfo.getTotal()));
            resultBean.setStatue("1");
        }
        return resultBean;
    }

    @Override
    @ServiceLog("更新文章分类")
    public ResultBean updateCategory(String category,String id) {
        ResultBean resultBean=new ResultBean();
        Integer result= articlesCategoryDao.updateCategory(id,category);
        if (result!=0) {
            resultBean.setCode("200");
            resultBean.setMessage("更新成功");
        }
        return resultBean;
    }

    @Override
    @ServiceLog("删除文章分类")
    public ResultBean deleteCategory(String id) {
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
        return resultBean;
    }

    @Override
    @ServiceLog("新增文章分类")
    public ResultBean insertCategory(String category) {
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
        return resultBean;
    }
}
