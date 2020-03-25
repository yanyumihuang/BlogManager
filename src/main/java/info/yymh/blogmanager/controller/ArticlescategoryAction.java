package info.yymh.blogmanager.controller;

import info.yymh.blogmanager.service.ArticlesCategoryService;
import info.yymh.blogmanager.utils.ResultBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author sikunliang
 * @Package info.yymh.blogmanager.controller
 * @ClassName:
 * @date 2020/3/25
 * @Description 文章分类接口
 */
@Controller
public class ArticlescategoryAction {
    private ArticlesCategoryService articlesCategoryService;

    public ArticlescategoryAction(ArticlesCategoryService articlesCategoryService) {
        this.articlesCategoryService = articlesCategoryService;
    }
    @RequestMapping("/categoryquery")
    @ResponseBody
    public ResultBean categoryQuery(){
       ResultBean resultBean=articlesCategoryService.query();
        return resultBean;
    }
    @RequestMapping("/categoryupdate")
    @ResponseBody
    public ResultBean categoryUpdate(@RequestParam String id,@RequestParam String category){
        ResultBean resultBean=articlesCategoryService.update(id,category);
        return  resultBean;
    }
    @RequestMapping("/categorydelete")
    @ResponseBody
    public ResultBean categoryDelete(@RequestParam String id,@RequestParam String category){
        ResultBean resultBean=articlesCategoryService.delete(id,category);
        return  resultBean;
    }
}
