package com.youye.controller;

import com.youye.mapper.BannerMapper;
import com.youye.model.BannerInfo;
import com.youye.model.result.ResultInfo;
import com.youye.service.BannerService;
import com.youye.util.ErrCode;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * **********************************************
 * <p/>
 * Date: 2018-04-26 15:34
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
@RestController
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    private BannerMapper bannerMapper;

    @Autowired
    private BannerService bannerService;

    /**
     * 根据性别获取Banner信息
     */
    @RequestMapping(value = "/{sex}")
    public ResultInfo getBannerBySex(@PathVariable Integer sex) {
        List<BannerInfo> bannerInfos = bannerMapper.findBanner();
        return new ResultInfo(ErrCode.OK, bannerInfos, "获取成功");
    }

}
