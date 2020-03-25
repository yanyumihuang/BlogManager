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
 * @Package info.yymh.blogmanager.utils
 * @ClassName:
 * @date 2020/3/23
 * @Description 创建token，并对token进行校验
 */
@Component
public class TokenUtils {
    private static Long ttlMillis;
    private static String stringKey;

    public static String createToken(String id,String rolePemission,String userId,String userName){
       String key=generalKey();
        Algorithm algorithmHS = Algorithm.HMAC256(key);
        long nowMillis=System.currentTimeMillis();
        Date now=new Date(nowMillis);
        Map<String ,Object> claim=new HashMap<>(3);
        claim.put("uuid",userId);
        claim.put("name",userName);
        claim.put("role",rolePemission);
        long expMillis=nowMillis+172800000;
        Date expDate=new Date(expMillis);
        String token= JWT.create()
                .withClaim("info",claim)
                .withJWTId(id).withIssuedAt(now)
                .withExpiresAt(expDate)
                .sign(algorithmHS);
        return  token;
    }
    public static boolean verifyJWT(String token) throws Exception{
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
                if (createDate.before(now) && now.before(expDate)) {
                   return true;
                }
                return false;
            }
        } catch (JWTVerificationException exception) {
            return false;
        }
    }
    public static Map<String, Object>  decodedJWT(String token){
        DecodedJWT jwt = JWT.decode(token);
        Claim claim=jwt.getClaim("info");
        return claim.asMap();

    }
    public static  String generalKey(){
        byte[] encodedKey = Base64.decodeBase64("c2tuZm9hbm5m");
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key.toString();
    }

    public static void setTtlMillis(Long ttlMillis) {
        TokenUtils.ttlMillis = ttlMillis;
    }

    public static void setStringKey(String stringKey) {
        TokenUtils.stringKey = stringKey;
    }
}
