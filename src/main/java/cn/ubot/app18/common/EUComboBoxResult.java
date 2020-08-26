package cn.ubot.app18.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author czw
 * @create 2020-08-25 9:42
 * EasyUI下拉框返回格式
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EUComboBoxResult {

    private String id;
    private String text;
    private String desc;

}
