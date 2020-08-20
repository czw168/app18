package cn.ubot.app18.service;

import cn.hutool.core.collection.CollUtil;
import cn.ubot.app18.common.AjaxResult;
import cn.ubot.app18.mapper.MicrolotMapper;
import cn.ubot.app18.pojo.MicroResult;
import cn.ubot.app18.pojo.Microlot;
import cn.ubot.app18.pojo.MicrolotRework;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

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

    @Override
    public AjaxResult checkLotNumber(String lotNumber) {

        // 查看wo表是否存在此lotNumber
        // 待完善

        // 查看microlot表是否存在此lotNumber
        String lotNo = microlotMapper.checkLotNumber(lotNumber);
        if(!StringUtils.isEmpty(lotNo)){
            return AjaxResult.build(400,"已存在的Lot No.");
        }
        return AjaxResult.ok();
    }

    @Override
    public AjaxResult checkMicrolot(String micorlotString, Integer num) {
        // 验证扫描数量
        if(num == 25){
            //查看字符串长度，1个microlot长度为10，25个则总长度为250
            if(micorlotString.length() != 250){
                return AjaxResult.build(400,"扫描数量不足或超出25个！<br/>当前扫描数量为:" + micorlotString.length()/10);
            }
        }else if(num == 15){
            if(micorlotString.length() != 150){
                return AjaxResult.build(400,"扫描数量不足或超出15个！<br/>当前扫描数量为:" + micorlotString.length()/10);
            }
        }else if(num == 11){
            if(micorlotString.length() != 110){
                return AjaxResult.build(400,"扫描数量不足或超出11个！<br/>当前扫描数量为:" + micorlotString.length()/10);
            }
        }else{
            return AjaxResult.build(500,"Error");
        }

        // 存放已存在的microlot
        List<String> existMicrolots = new ArrayList<>();

        // 把字符串拆分成集合
        List<String> microlots = new ArrayList<>();
        int index = 0;
        for(int i=0; i<micorlotString.length()/10; i++){
            String microlot = micorlotString.substring(index, (index+10));
            microlots.add(microlot);
            // 查看是否存在此microlot
            String m = microlotMapper.checkMicrolot(microlot);
            if(!StringUtils.isEmpty(m)){
                existMicrolots.add(m);
            }
            index += 10;
        }
        if(!CollUtil.isEmpty(existMicrolots)){
            return AjaxResult.build(401,"已存在的microlot",existMicrolots);
        }

        // 查看是否有重复的microlot
        Set<String> set = new HashSet<String>();
        List<String> repeatMicrolots = new ArrayList<>();
        for(int i=0; i<microlots.size(); i++){
            String value = microlots.get(i);
            if(set.contains(value)){
                // 重复的microlot
                repeatMicrolots.add(value);
            } else {
                set.add(value);
            }
        }
        if(!CollUtil.isEmpty(repeatMicrolots)){
            return AjaxResult.build(402,"重复扫描的microlot",repeatMicrolots);
        }

        return AjaxResult.ok();
    }

    public static void main(String[] args) {
        // 初始化list
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(21);
        list.add(1);
        list.add(3);
        list.add(3);
        list.add(3);
        // set中存放du的是不可重复的元素
        HashSet<Integer> set = new HashSet<Integer>();
        // 这里存放的是所有重复的元素，如果你只想知道是哪几个数字重复了，不需要知道具体重复了几次，可以用HashSet
        List<Integer> repeatElements = new ArrayList<Integer>();
        for(int i=0; i<list.size(); i++){
            int value = list.get(i);
            if(set.contains(value)){
                // 重复元素
                repeatElements.add(value);
            } else {
                set.add(value);
            }
        }
        // 输出重复的元素
        for(int i=0; i<repeatElements.size(); i++){
            System.out.println(repeatElements.get(i));
        }
    }

}
