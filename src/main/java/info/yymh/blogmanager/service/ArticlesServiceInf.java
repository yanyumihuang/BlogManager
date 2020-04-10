package info.yymh.blogmanager.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import info.yymh.blogmanager.dao.ArticlesDao;
import info.yymh.blogmanager.pojo.Articles;
import info.yymh.blogmanager.utils.ResultBean;
import info.yymh.blogmanager.utils.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResultBean queryArticles(String pageNum,String token) {
        ResultBean result=new ResultBean();
        Logger logger=LoggerFactory.getLogger(ArticlesServiceInf.class);
        logger.info("开始进行文章摘要查询");
        Map<String,Object> claim= TokenUtils.decodedJWT(token);
        String  articleStatue="0";
        if (claim.get("role")=="admin"){
            articleStatue="1";
        }
        try {
            Page page= PageHelper.startPage(Integer.valueOf(pageNum),5,true);
            List<HashMap<String,String>> articlesLists=articlesDao.queryArticles(articleStatue);
            PageInfo pageInfo=new PageInfo(articlesLists);
            result.setCode("200");
            result.setStatue("1");
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
    public ResultBean updateArticles(Articles articles) {
        ResultBean resultBean=new ResultBean();
        Logger logger=LoggerFactory.getLogger(getClass());
        logger.info("开始更新文章摘要");
        try {
            int num=articlesDao.updateArticles(articles);
            if (num>0){
                resultBean.setStatue("1");
                resultBean.setCode("200");
                resultBean.setMessage("更新成功");
            }
            else{
                resultBean.setCode("200");
                resultBean.setMessage("没有要更新的数据");
                resultBean.setStatue("0");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            resultBean.setCode("500");
            resultBean.setStatue("0");
            resultBean.setMessage("更新失败，请稍后再试");
        }
        return resultBean;
    }

    @Override
    public ResultBean insertArticles(Articles articles) {
        ResultBean resultBean=new ResultBean();
        Logger logger=LoggerFactory.getLogger(getClass());
        logger.info("开始更新文章摘要");
        try {
            int num=articlesDao.insertArticles(articles);
            if (num>0){
                resultBean.setStatue("1");
                resultBean.setCode("200");
                resultBean.setMessage("插入成功");
            }
            else{
                resultBean.setCode("200");
                resultBean.setMessage("插入失败");
                resultBean.setStatue("0");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            resultBean.setCode("500");
            resultBean.setStatue("0");
            resultBean.setMessage("插入失败，请稍后再试");
        }
        return resultBean;
    }
}
