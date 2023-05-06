package org.zerock.ocrpro.vo;

import java.sql.Date;
import java.util.List;

/**
 *  OCRAPI에서 List<String>으로 변환한 데이터를 다시 VO 형태로 만들어서 service에서 사용
 *  싱글톤 INSTANCE를 통해서 사용
 */
public enum ListToVO {

    INSTANCE;

    public TableVO listToVO(List<String> inputList) {
        TableVO vo = TableVO.builder()
                .number(Integer.valueOf(inputList.get(0)))
                .code(inputList.get(1))
                .origin(inputList.get(2))
                .cultivar(inputList.get(3))
                .inDate(Date.valueOf(inputList.get(4)).toLocalDate())
                .outDate(Date.valueOf(inputList.get(5)).toLocalDate())
                .weight(Integer.valueOf(inputList.get(6)))
                .count(Integer.valueOf(inputList.get(7)))
                .price(inputList.get(8))
                .won(inputList.get(9))
                .extra("null")
                .build();

        return vo;
    }
}
