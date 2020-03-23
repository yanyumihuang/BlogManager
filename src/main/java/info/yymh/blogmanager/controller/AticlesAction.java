package info.yymh.blogmanager.controller;

import info.yymh.blogmanager.pojo.Articles;
import info.yymh.blogmanager.service.ArticlesService;
import info.yymh.blogmanager.utils.ResultBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping("/articlesquery")
    @ResponseBody
    public ResultBean query(String token, String pageNum){
        ResultBean articlesLists=articlesService.query(pageNum);
       return articlesLists;
    }
    @RequestMapping("/articlesupdate")
    @ResponseBody
    public ResultBean update(Articles articles){
        articlesService.update(articles);
        return  null;
    }
}
