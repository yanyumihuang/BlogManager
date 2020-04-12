package info.yymh.blogmanager.controller;

import info.yymh.blogmanager.service.TokenService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author sikunliang
 * @Package info.yymh.blogmanager.controller
 * @ClassName:
 * @date 2020/3/23
 * @Description 第一次进入页面后生成token
 */
@Controller
public class TokenAction {
    private TokenService tokenService;

    public TokenAction(TokenService tokenService) {
        this.tokenService = tokenService;
    }
    @RequestMapping("/generictoken")
    @ResponseBody
    public String  genericToken(String id){
       String token= tokenService.genericToken(id);
        return token;
    }
}
