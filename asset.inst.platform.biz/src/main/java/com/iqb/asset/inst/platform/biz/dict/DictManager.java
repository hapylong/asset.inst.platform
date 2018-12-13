package com.iqb.asset.inst.platform.biz.dict;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iqb.asset.inst.platform.common.base.biz.BaseBiz;
import com.iqb.asset.inst.platform.common.constant.DictConstant.DictTypeCodeEnum;
import com.iqb.asset.inst.platform.data.bean.dict.DictEntity;
import com.iqb.asset.inst.platform.data.dao.dict.DictRepository;

@Component
public class DictManager extends BaseBiz {

    @Autowired
    private DictRepository dictRepository;

    public DictEntity getDictByDTCAndDC(DictTypeCodeEnum dtc, String dc) {
        setDb(0, super.SLAVE);
        return dictRepository.getDictByDTCAndDC(dtc, dc);
    }

}
