package info.yymh.blogmanager.controller;

import info.yymh.blogmanager.annotation.ControllerLog;
import info.yymh.blogmanager.service.UserService;
import info.yymh.blogmanager.utils.ResultBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 管理员登陆页面
 * @author sikunliang
 * @date 2020/3/25
 */
@Controller
public class UserAction {
    private UserService userService;

    public UserAction(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/login")
    @ResponseBody
    @ControllerLog("用户登陆")
    public ResultBean  login(@RequestParam String userName,@RequestParam String passWord, HttpServletRequest request){
        String token=request.getHeader("token");
       ResultBean resultBean=userService.query(userName,passWord,token);
        return  resultBean;
    }

    @RequestMapping("/logout")
    @ResponseBody
    @ControllerLog("用户退出")
    public ResultBean  logout(@RequestParam String userName,@RequestParam String passWord, HttpServletRequest request){
        String token=request.getHeader("token");
        ResultBean resultBean=userService.query(userName,passWord,token);
        return  resultBean;
    }
    @RequestMapping("/regeist")
    @ResponseBody
    @ControllerLog("用户注册")
    public ResultBean  regeist(@RequestParam String userName,@RequestParam String passWord
                               , @RequestParam String email,HttpServletRequest request){
        String token=request.getHeader("token");
        ResultBean resultBean=userService.regeist(userName,passWord,email,token);
        return  resultBean;
    }

}
