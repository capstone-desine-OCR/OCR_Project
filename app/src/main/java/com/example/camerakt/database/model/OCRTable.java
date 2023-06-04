package com.example.camerakt.database.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OCRTable {
    private int num;
    private String code;
    private String origin;
    private String cultivar;
    private String indate;
    private String outdate;
    private int weight;
    private int count;
    private String price;
    private String won;
    private String extra;

    public Map<String, Object> toMap() {
        Map<String, Object> productData = new HashMap<>();

        productData.put("번호", num);
        productData.put("코드", code);
        productData.put("원산지", origin);
        productData.put("품종", cultivar);
        productData.put("수입날짜", indate);
        productData.put("반입날짜", outdate);
        productData.put("중량", weight);
        productData.put("수량", count);
        productData.put("단가", price);
        productData.put("금액", won);
        if (extra != null) {
            productData.put("비고", extra);
        } else {
            productData.put("비고", null);
        }

        return productData;
    }

    public void fromList(List<String> input) {
        String firstElement = input.get(0);
        try {
            num = Integer.parseInt(firstElement);
            code = input.get(1);
            origin = input.get(2);
            cultivar = input.get(3);
            indate = input.get(4);
            outdate = input.get(5);
            weight = Integer.valueOf(input.get(6));
            count = Integer.valueOf(input.get(7));
            price = input.get(8);
            won = input.get(9);

            if (input.size() > 10) {
                extra = input.get(10);
            }
        } catch (NumberFormatException e) {
            //arrayListOf("코드", "원산지", "품종", "수입날짜", "반입날짜", "중량", "수량", "단가", "금액","비고")

            // 첫번째 요소가 int로 변환 불가능한 경우
            code = input.get(0);
            origin = input.get(1);
            cultivar = input.get(2);
            indate = input.get(3);
            outdate = input.get(4);
            weight = Integer.valueOf(input.get(5));
            count = Integer.valueOf(input.get(6));
            price = input.get(7);
            won = input.get(8);
            if (input.size() > 9) {
                extra = input.get(9);
            }
        }
    }
    
    public void setNum(int num) {
        this.num = num;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setCultivar(String cultivar) {
        this.cultivar = cultivar;
    }

    public void setIndate(String indate) {
        this.indate = indate;
    }

    public void setOutdate(String outdate) {
        this.outdate = outdate;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setWon(String won) {
        this.won = won;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}