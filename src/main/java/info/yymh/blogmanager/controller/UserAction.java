package info.yymh.blogmanager.controller;

import info.yymh.blogmanager.service.UserService;
import info.yymh.blogmanager.utils.ResultBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author sikunliang
 * @Package info.yymh.blogmanager.controller
 * @ClassName:
 * @date 2020/3/25
 * @Description 用户登陆注册和管理
 */
@Controller
public class UserAction {
    private UserService userService;

    public UserAction(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/login")
    @ResponseBody
    public ResultBean  login(@RequestParam String userName,@RequestParam String passWord, HttpServletRequest request){
        String token=request.getHeader("token");
       ResultBean resultBean=userService.query(userName,passWord,token);
        return  resultBean;
    }
    public String  forgetPwd(){

        return null;
    }
}
