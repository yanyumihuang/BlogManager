package info.yymh.blogmanager.service;

import info.yymh.blogmanager.dao.TokenDao;
import info.yymh.blogmanager.utils.TokenUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

/**
 * @author sikunliang
 * @date 2020/3/23
 */
@Service
public class TokenServiceImpl implements TokenService {
    private TokenDao tokenDao;

    public TokenServiceImpl(TokenDao tokenDao) {
        this.tokenDao = tokenDao;
    }

    @Override
    public String genericToken(String id) {
        UUID uuid=UUID.randomUUID();
        String uid=uuid.toString();
        String token;
        if (id!=null&& !"".equals(id)) {
             Map<String,String> userInf = tokenDao.queryRole(id);
            token= TokenUtils.createToken(uid, userInf.get("roles"),userInf.get("id"),userInf.get("name"));
        }
        else {
            token = TokenUtils.createToken(uid, "","","");
        }
        return token;
    }
}
