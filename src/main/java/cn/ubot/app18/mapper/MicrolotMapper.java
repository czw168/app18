package cn.ubot.app18.mapper;

import cn.ubot.app18.pojo.Microlot;
import cn.ubot.app18.pojo.MicrolotRework;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author czw
 * @create 2020-08-17 17:05
 */
@Mapper
public interface MicrolotMapper {

    /**
     * 根据lotNumber获取Micro信息
     * @param lotNumber
     * @return
     */
    List<Microlot> getMicro(String lotNumber);

    /**
     * 获取Micro已出货总数量
     * @param lotNumber
     * @return
     */
    Integer getMicroShippedCount(String lotNumber);

    /**
     * 获取Micro未出货总数量
     * @param lotNumber
     * @return
     */
    Integer getMicroUnShippedCount(String lotNumber);

    /**
     * 获取MicroRework时间列表
     * @param lotNumber
     * @return
     */
    List<String> getMicroReworkDates(String lotNumber);

    /**
     * 获取MicroRework信息
     * @param lotNumber
     * @param createTime
     * @return
     */
    List<MicrolotRework> getMicroRework(String lotNumber, String createTime);

    /**
     * 获取MicroRework总数量
     * @param lotNumber
     * @return
     */
    Integer getMicroReworkCount(String lotNumber, String createTime);

    /**
     * 根据microlot获取lotNumber
     * @param microlot
     * @return
     */
    String getLotNumber(String microlot);

    /**
     * 根据lotNumber获取所有microlot
     * @param lotNumber
     * @return
     */
    List<String> getMicrolots(String lotNumber);

}
