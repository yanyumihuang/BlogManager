package info.yymh.blogmanager.controller;

import info.yymh.blogmanager.service.ArticlesCategoryService;
import info.yymh.blogmanager.utils.ResultBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 文章分类action
 * @author sikunliang
 * @date 2020/3/25
 */
@Controller
public class ArticlescategoryAction {
    private ArticlesCategoryService articlesCategoryService;

    public ArticlescategoryAction(ArticlesCategoryService articlesCategoryService) {
        this.articlesCategoryService = articlesCategoryService;
    }

    @RequestMapping("/queryCategory")
    @ResponseBody
    public ResultBean queryCategory(){
       ResultBean resultBean=articlesCategoryService.queryCategory();
        return resultBean;
    }

    @RequestMapping("/updateCategory")
    @ResponseBody
    public ResultBean updateCategory(@RequestParam String id,@RequestParam String category){
        ResultBean resultBean=articlesCategoryService.updateCategory(id,category);
        return  resultBean;
    }

    @RequestMapping("/deleteCategory")
    @ResponseBody
    public ResultBean deleteCategory(@RequestParam String id){
        ResultBean resultBean=articlesCategoryService.deleteCategory(id);
        return  resultBean;
    }
    @RequestMapping("/insertCategory")
    @ResponseBody
    public ResultBean insertCategory(@RequestParam String category){
        ResultBean resultBean=articlesCategoryService.insertCategory(category);
        return  resultBean;
    }
}
