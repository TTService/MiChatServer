package com.youye.mapper;

import com.youye.model.BannerInfo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * **********************************************
 * <p/>
 * Date: 2018-04-26 15:47
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
public interface BannerMapper {

    @Select("select * from admin_banner")
    List<BannerInfo> findBanner();
}
