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
        //String firstElement = input.get(0);
        try {
            //num = Integer.parseInt(firstElement);
            code = input.get(0);
            origin = input.get(1);
            cultivar = input.get(2);
            indate = input.get(3);
            outdate = input.get(4);
            weight = Integer.valueOf(input.get(5));
            count = Integer.valueOf(input.get(6));
            price = input.get(7);
            won = input.get(8);
            extra = input.get(10);
            /*if (input.size() > 10) {
                extra = input.get(10);
            }*/
        } catch (NumberFormatException e) {

        }
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getCultivar() {
        return cultivar;
    }

    public void setCultivar(String cultivar) {
        this.cultivar = cultivar;
    }

    public String getIndate() {
        return indate;
    }

    public void setIndate(String indate) {
        this.indate = indate;
    }

    public String getOutdate() {
        return outdate;
    }

    public void setOutdate(String outdate) {
        this.outdate = outdate;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getWon() {
        return won;
    }

    public void setWon(String won) {
        this.won = won;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}

