package cn.ubot.app18.service;

import cn.hutool.core.collection.CollUtil;
import cn.ubot.app18.common.AjaxResult;
import cn.ubot.app18.mapper.MicrolotMapper;
import cn.ubot.app18.pojo.MicroResult;
import cn.ubot.app18.pojo.Microlot;
import cn.ubot.app18.pojo.MicrolotRework;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author czw
 * @create 2020-08-17 17:27
 */
@Service
@Slf4j
public class MicrolotServiceImpl implements MicrolotService{

    @Resource
    private MicrolotMapper microlotMapper;

    @Override
    public AjaxResult getMicroInfoByLotNumber(String lotNumber) {

        // 获取micro信息
        List<Microlot> microlots = microlotMapper.getMicro(lotNumber);
        if(CollUtil.isEmpty(microlots)){
            return AjaxResult.build(400,"没有匹配的LotNo.");
        }

        MicroResult microResult = new MicroResult();

        // 获取Micro已出货总数量
        Integer microShippedCount = microlotMapper.getMicroShippedCount(lotNumber);
        // 获取Micro未出货总数量
        Integer microUnShippedCount = microlotMapper.getMicroUnShippedCount(lotNumber);
        // 获取MicroRework时间列表
        List<String> microReworkDates = microlotMapper.getMicroReworkDates(lotNumber);

        List<MicrolotRework> mrs = null;

        if(!CollUtil.isEmpty(microReworkDates)){

            mrs = new ArrayList<>();

            for (String microReworkDate : microReworkDates) {

                MicrolotRework microlotRework = new MicrolotRework();

                // 获取MicroRework信息
                List<MicrolotRework> microReworks = microlotMapper.getMicroRework(lotNumber, microReworkDate);
                // 获取MicroRework总数量
                Integer microReworkCount = microlotMapper.getMicroReworkCount(lotNumber, microReworkDate);

                microlotRework.setCreateTime(microReworkDate);
                microlotRework.setMicrolotReworks(microReworks);
                microlotRework.setMicroReworkCount(microReworkCount);

                mrs.add(microlotRework);

            }
        }

        // 封装数据
        microResult.setMicrolots(microlots);
        microResult.setMicroShippedCount(microShippedCount);
        microResult.setMicroUnShippedCount(microUnShippedCount);
        if (mrs != null){
            microResult.setMicrolotReworks(mrs);
        }

        return AjaxResult.ok(microResult);
    }

    @Override
    public AjaxResult getMicroInfoByMicrolot(String microlot) {
        String lotNumber = microlotMapper.getLotNumber(microlot);
        if(StringUtils.isEmpty(lotNumber)){
            return AjaxResult.build(400,"没有匹配的LotNo.");
        }
        List<String> microlots = microlotMapper.getMicrolots(lotNumber);

        Map<String,Object> map = new HashMap<>();
        map.put("lotNumber",lotNumber);
        map.put("microlots",microlots);

        return AjaxResult.ok(map);
    }

}
