package com.youye.service;

import com.youye.model.BannerInfo;
import java.util.List;

/**
 * **********************************************
 * <p/>
 * Date: 2018-05-07 18:40
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
public interface BannerService {

    List<BannerInfo> findBanner();
}
