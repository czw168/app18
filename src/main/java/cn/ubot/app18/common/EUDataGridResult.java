package cn.ubot.app18.common;

import lombok.Data;

import java.util.List;

/**
 * @author czw
 * @create 2020-08-25 9:43
 * EasyUI数据表格返回格式
 */
@Data
public class EUDataGridResult {

    private long total;
    private List<?> rows;

}
