package org.zerock.ocrpro.vo;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *  List를 domain형태로 변환해서 사용하기 위해서 작성한 클래스
 *  기능들을 위해서 lombok을 사용함. 설치 필요!
 */
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TableVO {
    private int number;

    private String code;

    private String origin;

    private String cultivar;

    private LocalDate inDate;

    private LocalDate outDate;

    private int weight;

    private int count;

    private String price;

    private String won;

    private String extra;
}
