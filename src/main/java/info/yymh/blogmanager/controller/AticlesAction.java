package info.yymh.blogmanager.controller;

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
    public ResultBean query(HttpServletRequest request, String pageNum){
        String token=request.getHeader("token");
        ResultBean articlesLists=articlesService.queryArticles(pageNum,token);
       return articlesLists;
    }
    @RequestMapping("/updateArticles")
    @ResponseBody
    public ResultBean update(Articles articles){
        ResultBean resultBean=articlesService.updateArticles(articles);
        return  resultBean;
    }
    @RequestMapping("/insertArticles")
    @ResponseBody
    public ResultBean insert(Articles articles){
        ResultBean resultBean=articlesService.insertArticles(articles);
        return  resultBean;
    }

}
