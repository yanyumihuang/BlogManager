package info.yymh.blogmanager.controller;

import info.yymh.blogmanager.annotation.ControllerLog;
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
public class ArticlesCategoryAction {
    private ArticlesCategoryService articlesCategoryService;

    public ArticlesCategoryAction(ArticlesCategoryService articlesCategoryService) {
        this.articlesCategoryService = articlesCategoryService;
    }

    @RequestMapping("/queryCategory")
    @ResponseBody
    @ControllerLog("查询文章分类")
    public ResultBean queryCategory(String pageNum,String limit){
       ResultBean resultBean=articlesCategoryService.queryCategory(pageNum,limit);
        return resultBean;
    }

    @RequestMapping("/updateCategory")
    @ResponseBody
    @ControllerLog("更新文章分类")
    public ResultBean updateCategory(@RequestParam String id,@RequestParam String category){
        ResultBean resultBean=articlesCategoryService.updateCategory(id,category);
        return  resultBean;
    }

    @RequestMapping("/deleteCategory")
    @ResponseBody
    @ControllerLog("删除文章分类")
    public ResultBean deleteCategory(@RequestParam String id){
        ResultBean resultBean=articlesCategoryService.deleteCategory(id);
        return  resultBean;
    }
    @RequestMapping("/insertCategory")
    @ResponseBody
    @ControllerLog("新增文章分类")
    public ResultBean insertCategory(@RequestParam String category){
        ResultBean resultBean=articlesCategoryService.insertCategory(category);
        return  resultBean;
    }
}
