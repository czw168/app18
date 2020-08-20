package cn.ubot.app18.pojo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author czw
 * @create 2020-08-04 14:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission {

    private long id;
    private String name;
    private String desc;
    private String url;

}
