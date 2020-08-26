package cn.ubot.app18.common;

import lombok.Data;

import java.util.List;

/**
 * @author czw
 * @create 2020-08-25 9:45
 * EasyUI数据表格返回格式（带底部统计）
 */
@Data
public class EUDataGridFootResult {

    private long total;
    private List<?> rows;
    private List<?> footer;

}
