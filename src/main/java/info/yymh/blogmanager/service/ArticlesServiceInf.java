package info.yymh.blogmanager.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import info.yymh.blogmanager.dao.ArticlesDao;
import info.yymh.blogmanager.pojo.Articles;
import info.yymh.blogmanager.utils.ResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author sikunliang
 * @date 2020/3/19
 */

@Service
public class ArticlesServiceInf implements ArticlesService{
    private final ArticlesDao articlesDao;

    public ArticlesServiceInf(ArticlesDao articlesDao) {
        this.articlesDao = articlesDao;
    }

    @Override
    public ResultBean query(String pageNum) {
        ResultBean result=new ResultBean();
        Logger logger=LoggerFactory.getLogger(ArticlesServiceInf.class);
        logger.info("开始进行文章摘要查询");
        //根据token判断是否展示私密文章
        String  articleStatue="0";
        try {
            Page page= PageHelper.startPage(Integer.valueOf(pageNum),5,true);
            List<HashMap<String,String>> articlesLists=articlesDao.query(articleStatue);
            PageInfo pageInfo=new PageInfo(articlesLists);
            result.setCode("200");
            result.setResultLists(articlesLists);
            result.setRows(articlesLists.size());
            result.setCount(String.valueOf(pageInfo.getTotal()));
            logger.info("文章摘要查询完成");
        }
        catch (Exception e){
            logger.info("查询失败,日志如下");
            result.setCode("500");
            result.setMessage("请稍后再试");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ResultBean update(Articles articles) {
        ResultBean resultBean=new ResultBean();
        Logger logger=LoggerFactory.getLogger(getClass());
        logger.info("开始更新文章摘要");
        try {
            int num=articlesDao.update(articles);
            if (num>0){
                resultBean.setCode("200");
                resultBean.setMessage("更新成功");
            }
            else{
                resultBean.setCode("400");
                resultBean.setMessage("没有要更新的数据");
            }
        }
        catch (Exception e){
            resultBean.setCode("500");
            resultBean.setMessage("更新失败，请稍后再试");
        }
        return resultBean;
    }
}
