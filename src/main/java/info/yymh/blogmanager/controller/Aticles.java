package info.yymh.blogmanager.controller;

import info.yymh.blogmanager.service.ArticlesService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * @author sikunliang
 * @Package info.yymh.blogmanager.controller
 * @ClassName:
 * @date 2020/1/19
 * @Description
 */
@Controller
public class Aticles {
    private final ArticlesService articlesService;

    public Aticles(ArticlesService articlesService) {
        this.articlesService = articlesService;
    }

    @RequestMapping("/articles")
    public void query(HttpServletResponse response){
        articlesService.query();
    }
}
