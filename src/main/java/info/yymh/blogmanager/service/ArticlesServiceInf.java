package info.yymh.blogmanager.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import info.yymh.blogmanager.annotation.ServiceLog;
import info.yymh.blogmanager.dao.ArticlesDao;
import info.yymh.blogmanager.pojo.Articles;
import info.yymh.blogmanager.utils.ResultBean;
import info.yymh.blogmanager.utils.TokenUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sikunliang
 * @date 2020/3/19
 */

@Service
public class ArticlesServiceInf implements ArticlesService {
    private final ArticlesDao articlesDao;
    private TokenUtils tokenUtils;

    public ArticlesServiceInf(ArticlesDao articlesDao, TokenUtils tokenUtils) {
        this.articlesDao = articlesDao;
        this.tokenUtils = tokenUtils;
    }

    @Override
    @ServiceLog("查询文章摘要")
    public ResultBean queryArticles(String pageNum, String token) {
        ResultBean result = new ResultBean();
        Map<String, Object> claim = tokenUtils.decodedJWT(token);
        String articleStatue = "0";
        if (claim.get("role") == "admin") {
            articleStatue = "1";
        }
        Page page = PageHelper.startPage(Integer.valueOf(pageNum), 5, true);
        List<HashMap<String, String>> articlesLists = articlesDao.queryArticles(articleStatue);
        PageInfo pageInfo = new PageInfo(articlesLists);
        result.setCode("200");
        result.setStatue("1");
        result.setResultLists(articlesLists);
        result.setRows(articlesLists.size());
        result.setCount(String.valueOf(pageInfo.getTotal()));
        return result;
    }

    @Override
    @ServiceLog("更新文章摘要")
    public ResultBean updateArticles(Articles articles) {
        ResultBean resultBean = new ResultBean();
        int num = articlesDao.updateArticles(articles);
        if (num > 0) {
            resultBean.setStatue("1");
            resultBean.setCode("200");
            resultBean.setMessage("更新成功");
        } else {
            resultBean.setCode("200");
            resultBean.setMessage("没有要更新的数据");
            resultBean.setStatue("0");
        }
        return resultBean;
    }

    @Override
    @ServiceLog("新增文章摘要")
    public ResultBean insertArticles(Articles articles) {
        ResultBean resultBean = new ResultBean();
        int num = articlesDao.insertArticles(articles);
        if (num > 0) {
            resultBean.setStatue("1");
            resultBean.setCode("200");
            resultBean.setMessage("插入成功");
        } else {
            resultBean.setCode("200");
            resultBean.setMessage("插入失败");
            resultBean.setStatue("0");
        }
        return resultBean;
    }
}
