package com.ttsource.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ttsource.model.anchor.AnchorInfoDTO;
import com.ttsource.model.result.ResultInfo;
import com.ttsource.model.result.ResultInfoPage;
import com.ttsource.service.AnchorService;
import com.ttsource.util.ErrCode;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * **********************************************
 * <p/>
 * Date: 2019-01-21 10:32
 * <p/>
 * Author: SinPingWu
 * <p/>
 * Email: wuxinping@ubinavi.com.cn
 * <p/>
 * brief: 主播管理对外接口
 * <p/>
 * history:
 * <p/>
 * **********************************************
 */
@RestController
@RequestMapping(value = "/anchor")
public class AnchorController {

    private AnchorService anchorService;

    @Value("${default.pageNum}")
    private Integer defaultPageNum;

    @Value("${default.pageSize}")
    private Integer defaultPageSize;

    public AnchorController(AnchorService anchorService) {
        this.anchorService = anchorService;
    }

    /**
     * 获取当红主播列表。
     * 按页加载
     *
     * 参数格式：JSON
     *
     *
     * @return 返回当前主播的列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResultInfo getHotAnchorList(@RequestBody HashMap<String, Object> params) {
        Integer pageNum = null;
        Integer pageSize = null;
        try {
            pageNum = (int) params.get("pageNum");
            pageSize = (int) params.get("pageSize");
        } catch (Exception e) {
            // nothing
        }

        if (pageNum == null) {
            pageNum = defaultPageNum;
        }
        if (pageSize == null) {
            pageSize = defaultPageSize;
        }
        Page<AnchorInfoDTO> page =  PageHelper.startPage(pageNum, pageSize);
        List<AnchorInfoDTO> anchorInfoList = anchorService.listHotAnchor();

        return new ResultInfoPage(ErrCode.OK, anchorInfoList, "获取成功", page.getTotal(), page.getPageNum(), page.getPageSize());
    }
}
