package cn.ubot.app18.controller;

import cn.ubot.app18.common.AjaxResult;
import cn.ubot.app18.service.MicrolotService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author czw
 * @create 2020-08-17 17:33
 */
@RestController
@RequestMapping("/micro")
public class MicrolotController {

    @Autowired
    private MicrolotService microlotService;

    /*@GetMapping("getByLotNumber")
    public AjaxResult getByLotNumber(@RequestBody JSONObject obj){
        String lotNumber = obj.getString("lotNumber");
        return microlotService.getByLotNumber(lotNumber);
    }*/

//    @GetMapping("/{lotNumber}")
//    public AjaxResult getMicroInfoByLotNumber(@PathVariable("lotNumber") String lotNumber){
//        return microlotService.getMicroInfoByLotNumber(lotNumber);
//    }
//
//    @GetMapping("/{microlot}")
//    public AjaxResult getMicroInfoByMicrolot(@PathVariable("microlot") String microlot){
//        return microlotService.getMicroInfoByMicrolot(microlot);
//    }

    @GetMapping("/getMicroInfoByLotNumber/{lotNumber}")
    public AjaxResult getMicroInfoByLotNumber(@PathVariable("lotNumber") String lotNumber){
        return microlotService.getMicroInfoByLotNumber(lotNumber);
    }

    @GetMapping("/getMicroInfoByMicrolot/{microlot}")
    public AjaxResult getMicroInfoByMicrolot(@PathVariable("microlot") String microlot){
        return microlotService.getMicroInfoByMicrolot(microlot);
    }


}
