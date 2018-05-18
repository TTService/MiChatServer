package com.youye.jwt.token;

import com.youye.service.UserService;
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

    @Autowired
    private UserService userService;

    private RedisTemplate<Object, Object> redis;
    private static final int EXPIRATION_TIME = 72;

    private static final String SALT = "com.michat@#_@#";

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

    public TokenModel createToken(String username) {
        //使用uuid作为源token
        String token = UUID.randomUUID().toString().replace("-", "");

        TokenModel model = new TokenModel(username, token);

        String key = SALT + username;
        //存储到redis并设置过期时间
        redis.boundValueOps(key).set(token, 72, TimeUnit.HOURS);
        return model;
    }

    public TokenModel getToken(String authentication) {
        if (authentication == null || authentication.length() == 0) {
            return null;
        }
        String[] param = authentication.split("_");
        if (param.length != 2) {
            return null;
        }
        //使用userId和源token简单拼接成的token，可以增加加密措施
        /*long userId = Long.parseLong(param[0]);
        String token = param[1];
        return new TokenModel(userId, token);*/
        String username = param[0];
        String token = param[1];
        return new TokenModel(username, token);
    }

    public boolean checkToken(TokenModel model) {
        if (model == null) {
            return false;
        }
        //String token = redis.boundValueOps(model.getUserId()).get();
        String token;
        try {
            //token = (String) redis.boundValueOps(model.getUserId()).get();
            String key = SALT + model.getUsername();
            token = (String) redis.boundValueOps(key).get();
        } catch (Exception e) {
            token = null;
        }
        if (token == null || !token.equals(model.getToken())) {
            return false;
        }
        //如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
        redis.boundValueOps(model.getUserId()).expire(72, TimeUnit.HOURS);
        return true;
    }

    public void deleteToken(long userId) {
        redis.delete(userId);
    }
}
