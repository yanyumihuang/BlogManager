package info.yymh.blogmanager.service;

import info.yymh.blogmanager.dao.TokenDao;
import info.yymh.blogmanager.utils.RoleLevel;
import info.yymh.blogmanager.utils.TokenUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author sikunliang
 * @date 2020/3/23
 */
@Service
public class TokenServiceImpl implements TokenService {
    private TokenDao tokenDao;
    private TokenUtils tokenUtils;

    public TokenServiceImpl(TokenDao tokenDao, TokenUtils tokenUtils) {
        this.tokenDao = tokenDao;
        this.tokenUtils = tokenUtils;
    }

    @Override
    public String genericToken() {
        UUID uuid=UUID.randomUUID();
        String uid=uuid.toString();
        //根据id来查找用户类型，实际上是无用的做法，最好的做法应该是根据用户名密码来生成但首次生成的token应该都没有用户名密码，所以cratetoken方法应该要传参，
        //需要更新token等级的时候只有两个地方，1后台登陆 2发表评论，这都是需要输入用户名密码的。所以把id去掉
        //这里的逻辑也不需要要改根据用户名判断生成什么类型的id，直接访问/generictoken的都是普通用户，提权的用户都要先访问login所以在哪里进行提

        return tokenUtils.createToken(uid, RoleLevel.unregistered.toString(),"","");
    }
}
