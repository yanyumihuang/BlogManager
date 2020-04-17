package info.yymh.blogmanager.controller;

import info.yymh.blogmanager.annotation.ControllerLog;
import info.yymh.blogmanager.pojo.Articles;
import info.yymh.blogmanager.service.ArticlesService;
import info.yymh.blogmanager.utils.ResultBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author sikunliang
 * @Package info.yymh.blogmanager.controller
 * @ClassName:
 * @date 2020/1/19
 * @Description
 */
@Controller
public class AticlesAction {
    private final ArticlesService articlesService;

    public AticlesAction(ArticlesService articlesService) {
        this.articlesService = articlesService;
    }

    @RequestMapping("/queryArticles")
    @ResponseBody
    @ControllerLog("查询文章摘要")
    public ResultBean query(HttpServletRequest request, String pageNum){
        String token=request.getHeader("token");
        ResultBean articlesLists=articlesService.queryArticles(pageNum,token);
       return articlesLists;
    }
    @RequestMapping("/updateArticles")
    @ResponseBody
    @ControllerLog("更新文章摘要")
    public ResultBean update(Articles articles){
        ResultBean resultBean=articlesService.updateArticles(articles);
        return  resultBean;
    }
    @RequestMapping("/insertArticles")
    @ResponseBody
    @ControllerLog("新增文章摘要")
    public ResultBean insert(Articles articles){
        ResultBean resultBean=articlesService.insertArticles(articles);
        return  resultBean;
    }

}
