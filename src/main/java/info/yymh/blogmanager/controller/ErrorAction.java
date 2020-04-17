package info.yymh.blogmanager.controller;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author sikunliang
 * @date 2020/4/17
 */
public class ErrorAction {
    @RequestMapping("/404")
    public String error404() {
        return "html/404";
    }

    /**
     * 500 error
     * @return
     */
    @RequestMapping("/500")
    public String error500() {
        return "html/500";
    }
}
