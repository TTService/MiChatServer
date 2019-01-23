package com.ttsource.service.impl;

import com.ttsource.mapper.AnchorMapper;
import com.ttsource.model.anchor.AnchorInfoDTO;
import com.ttsource.service.AnchorService;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * **********************************************
 * <p/>
 * Date: 2019-01-21 10:37
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
@Service
public class AnchorServiceImpl implements AnchorService {

    private AnchorMapper anchorMapper;

    public AnchorServiceImpl(AnchorMapper anchorMapper) {
        this.anchorMapper = anchorMapper;
    }

    @Override
    public List<AnchorInfoDTO> listHotAnchor() {
        return anchorMapper.listHotAnchor();
    }
}
