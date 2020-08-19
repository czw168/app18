package cn.ubot.app18.service;

import cn.ubot.app18.common.AjaxResult;

/**
 * @author czw
 * @create 2020-08-17 17:22
 */
public interface MicrolotService {

    /**
     * 根据lotNumber获取micro信息
     * @param lotNumber
     * @return
     */
    AjaxResult getMicroInfoByLotNumber(String lotNumber);

    /**
     * 根据microlot获取micro信息
     * @param microlot
     * @return
     */
    AjaxResult getMicroInfoByMicrolot(String microlot);

}
