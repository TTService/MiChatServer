package com.ttsource.mapper;

import com.ttsource.model.anchor.AnchorInfoDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * **********************************************
 * <p/>
 * Date: 2019-01-21 10:47
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
public interface AnchorMapper {

    List<AnchorInfoDTO> listHotAnchor();
}
