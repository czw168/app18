package cn.ubot.app18.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * micro查询返回结果
 * @author czw
 * @create 2020-08-18 14:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MicroResult {

    private List<Microlot> microlots;
    private Integer microShippedCount;
    private Integer microUnShippedCount;
    private List<MicrolotRework> microlotReworks;

}
