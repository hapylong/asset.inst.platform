package com.iqb.asset.inst.platform.data.dao.dict;

import org.apache.ibatis.annotations.Param;

import com.iqb.asset.inst.platform.common.constant.DictConstant.DictTypeCodeEnum;
import com.iqb.asset.inst.platform.data.bean.dict.DictEntity;

public interface DictRepository {

    DictEntity getDictByDTCAndDC(@Param("dtc") DictTypeCodeEnum dtc, @Param("dc") String dc);

}
