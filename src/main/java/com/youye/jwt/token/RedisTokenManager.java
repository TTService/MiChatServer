package com.youye.jwt.token;

import com.youye.util.AESUtil;
import com.youye.util.StringUtil;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Component;

/**
 * 通过Redis存储和验证token的实现类
 * @see com.youye.jwt.token.TokenManager
 * @author ScienJus
 */
@Component(value = "tokenManager")
public class RedisTokenManager implements TokenManager {

    private RedisTemplate<Object, Object> redis;
    private static final int EXPIRATION_TIME = 24 * 3;

    private static final String SALT = "com.michat@#_@#_";

    private static final String SPACER = "_#@#_";

    @Autowired
    public void setRedis(RedisTemplate<Object, Object> redis) {
        this.redis = redis;
        //泛型设置成Long后必须更改对应的序列化方案
        //redis.setKeySerializer();
        redis.setKeySerializer(new JdkSerializationRedisSerializer());
    }

    public TokenModel createToken(long userId) {
        //使用uuid作为源token
        String token = UUID.randomUUID().toString().replace("-", "");
        TokenModel model = new TokenModel(userId, token);
        //存储到redis并设置过期时间
        redis.boundValueOps(userId).set(token, EXPIRATION_TIME, TimeUnit.HOURS);
        return model;
    }

    public TokenModel createToken(String identifier) {
        // 创建随机的UUID
        String uuid = UUID.randomUUID().toString().replace("-", "");
        // uuid 通过分隔符与username 进行拼接
        String tempToken = identifier + SPACER + uuid;

        // 对tempToken进行AES加密生成传输token数据
        String token = AESUtil.aesEncode(tempToken);
        if (token == null)
            token = tempToken;

        TokenModel model = new TokenModel(identifier, token);

        String key = SALT + identifier;
        //原始token存储到redis并设置过期时间
        redis.boundValueOps(key).set(tempToken, EXPIRATION_TIME, TimeUnit.HOURS);
        return model;
    }

    public TokenModel getToken(String authentication) {
        if (authentication == null || authentication.length() == 0) {
            return null;
        }

        // TODO 对token 进行解密
        String token = AESUtil.aesDecode(authentication);
        if (token == null)
            token = authentication;

        String[] param = token.split(SPACER);
        if (param.length != 2) {
            return null;
        }
        //使用userId和源token简单拼接成的token，可以增加加密措施
        /*long userId = Long.parseLong(param[0]);
        String token = param[1];
        return new TokenModel(userId, token);*/
        String username = param[0];
        //String token = param[1];
        return new TokenModel(username, token);
    }

    public boolean checkToken(TokenModel model) {
        if (model == null) {
            return false;
        }

        try {
            String key = SALT + model.getIdentifier();
            //String token = redis.boundValueOps(model.getUserId()).get();
            //String token = (String) redis.boundValueOps(model.getUserId()).get();
            String token = (String) redis.boundValueOps(key).get();

            if (StringUtil.isEmpty(token) || !token.equals(model.getToken())) {
                return false;
            }

            //如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
            redis.boundValueOps(key).expire(EXPIRATION_TIME, TimeUnit.HOURS);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void deleteToken(long userId) {
        redis.delete(userId);
    }
}
