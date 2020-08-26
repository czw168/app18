package cn.ubot.app18.pojo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author czw
 * @create 2020-08-04 14:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    private long id;
    private String name;
    private String desc;
    private List<Permission> permissions;

}
