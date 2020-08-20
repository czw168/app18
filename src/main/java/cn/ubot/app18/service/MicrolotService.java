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

    /**
     * 查看是否存在此LotNo
     * @param lotNumber
     * @return
     */
    AjaxResult checkLotNumber(String lotNumber);

    /**
     * microlot扫描校验
     * @param micorlotString
     * @param num
     * @return
     */
    AjaxResult checkMicrolot(String micorlotString, Integer num);

}
