package com.ttsource.service.impl;

import com.ttsource.mapper.BannerMapper;
import com.ttsource.model.BannerInfo;
import com.ttsource.service.BannerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * **********************************************
 * <p/>
 * Date: 2018-05-07 18:41
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
@Service(value = "bannerService")
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public List<BannerInfo> findBanner() {
        try {
            return bannerMapper.findBanner();
        } catch (Exception e) {
            return null;
        }
    }
}
