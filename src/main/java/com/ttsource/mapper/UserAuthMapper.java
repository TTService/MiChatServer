package com.ttsource.mapper;

import com.ttsource.model.user.UserAuthDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * **********************************************
 * <p/>
 * Date: 2018-09-13 19:45
 * <p/>
 * Author: SinPingWu
 * <p/>
 * Email: wuxinping@ubinavi.com.cn
 * <p/>
 * brief:
 * <p/>
 * history:
 * <p/>
 * **********************************************
 */
@Mapper
public interface UserAuthMapper {

    void addUserAuth(UserAuthDO userAuthDO);

    UserAuthDO getByIdentifier(@Param("identifier") String identifier);

}
