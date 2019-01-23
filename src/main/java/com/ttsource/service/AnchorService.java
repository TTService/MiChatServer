package com.ttsource.service;

import com.ttsource.model.anchor.AnchorInfoDTO;
import java.util.List;

/**
 * **********************************************
 * <p/>
 * Date: 2019-01-21 10:35
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
public interface AnchorService {

    /**
     * 列举当前红火的主播信息
     */

    List<AnchorInfoDTO> listHotAnchor();
}
