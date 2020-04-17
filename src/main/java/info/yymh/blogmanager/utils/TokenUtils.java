package info.yymh.blogmanager.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sikunliang
 * @date 2020/3/23
 */
@Component
public class TokenUtils {
    private ConfigBean configBean;

    public TokenUtils(ConfigBean configBean) {
        this.configBean = configBean;
    }

    public  String createToken(String id,String role,String userId,String userName){
        String key=generalKey();
        Algorithm algorithmHS = Algorithm.HMAC256(key);
        long nowMillis=System.currentTimeMillis();
        Date now=new Date(nowMillis);
        Map<String ,Object> claim=new HashMap<>(3);
        claim.put("id",userId);
        claim.put("name",userName);
        claim.put("role",role);
        long expMillis=nowMillis+configBean.getTtlMillis();
        Date expDate=new Date(expMillis);
        String token= JWT.create()
                .withClaim("info",claim)
                .withJWTId(id).withIssuedAt(now)
                .withExpiresAt(expDate)
                .sign(algorithmHS);
        return  token;
    }
    //校验通过后应该刷新过期时间
    public  boolean verifyJWT(String token) throws Exception{
        try {
            String key=generalKey();
            Algorithm algorithm = Algorithm.HMAC256(key);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT jwt=verifier.verify(token);
            Claim claim = jwt.getClaim("info");
            Map<String, Object> map=claim.asMap();
            if(map.get("role")==""||map.get("role")==null) {
                return true;
            }
            else {
                Date expDate = jwt.getExpiresAt();
                Date createDate = jwt.getIssuedAt();
                Date now = new Date();
                return createDate.before(now) && now.before(expDate);
            }
        } catch (JWTVerificationException exception) {
            return false;
        }
    }
    public  Map<String, Object>  decodedJWT(String token){
        DecodedJWT jwt = JWT.decode(token);
        Claim claim=jwt.getClaim("info");
        return claim.asMap();

    }
    public   String generalKey(){
        byte[] encodedKey = Base64.decodeBase64(configBean.getKey());
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key.toString();
    }

}
