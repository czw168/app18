package cn.ubot.app18.pojo;

import lombok.Data;

/**
 * microlot表实体类
 * @author czw
 * @create 2020-08-17 17:02
 */
@Data
public class Microlot {

    private Integer lotId;
    private String lotNumber;
    private String microlot;
    private String createTime;
    private String userId;
    private Integer gStatus;
    private String gCreateTime;
    private String gUserId;
    private Integer count;

}
