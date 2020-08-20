package cn.ubot.app18.controller;

import cn.ubot.app18.common.AjaxResult;
import cn.ubot.app18.service.MicrolotService;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Micro相关
 * @author czw
 * @create 2020-08-17 17:33
 */
@RestController
@RequestMapping("/micro")
@RequiresRoles(value={"admin","product"},logical = Logical.OR)
public class MicrolotController {

    @Autowired
    private MicrolotService microlotService;

    @GetMapping("/getMicroInfoByLotNumber/{lotNumber}")
    public AjaxResult getMicroInfoByLotNumber(@PathVariable("lotNumber") String lotNumber){
        return microlotService.getMicroInfoByLotNumber(lotNumber);
    }

    @GetMapping("/getMicroInfoByMicrolot/{microlot}")
    public AjaxResult getMicroInfoByMicrolot(@PathVariable("microlot") String microlot){
        return microlotService.getMicroInfoByMicrolot(microlot);
    }

    @GetMapping("/checkLotNumber/{lotNumber}")
    public AjaxResult checkLotNumber(@PathVariable("lotNumber") String lotNumber){
        return microlotService.checkLotNumber(lotNumber);
    }

    @PostMapping("/checkMicrolot")
    public  AjaxResult checkMicrolot(@RequestBody JSONObject obj){
        String microlotString = obj.getString("microlotString");
        Integer num = obj.getInteger("num");
        return microlotService.checkMicrolot(microlotString,num);
    }

}
