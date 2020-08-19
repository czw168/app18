package cn.ubot.app18.pojo;

import lombok.Data;

import java.util.List;

/**
 * microlotRework表实体类
 * @author czw
 * @create 2020-08-17 17:02
 */
@Data
public class MicrolotRework {

    private String lotNumber;
    private String microlot;
    private String createBy;
    private String createTime;
    private Integer count;
    List<MicrolotRework> microlotReworks;
    private Integer microReworkCount;

}
