package com.example.camerakt.database.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OCRTable {
    public static final Parcelable.Creator<OCRTable> CREATOR = new Parcelable.Creator<OCRTable>() {
        @Override
        public OCRTable createFromParcel(Parcel in) {
            return new OCRTable(in);
        }

        @Override
        public OCRTable[] newArray(int size) {
            return new OCRTable[size];
        }
    };
    //private int num;
    String code;
    String origin;
    String cultivar;
    private String indate;
    private String outdate;
    private int weight;
    private int count;
    private String price;
    private String won;
    private String extra;
    private String weightStr;
    private String countStr;

    public OCRTable(String code, String origin, String cultivar, String indate, String outdate, int weight, int count, String price, String won, String extra) {
        this.code = code;
        this.origin = origin;
        this.cultivar = cultivar;
        this.indate = indate;
        this.outdate = outdate;
        this.weight = weight;
        this.count = count;
        this.price = price;
        this.won = won;
        this.extra = extra;
        this.weightStr = String.valueOf(weight);
        this.countStr = String.valueOf(count);
    }

    public OCRTable() {
        this("", "", "", "", "", 0, 0, "", "", "");
    }

    protected OCRTable(Parcel in) {
        code = in.readString();
        origin = in.readString();
        cultivar = in.readString();
        indate = in.readString();
        outdate = in.readString();
        weight = in.readInt();
        count = in.readInt();
        price = in.readString();
        won = in.readString();
        extra = in.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(origin);
        dest.writeString(cultivar);
        dest.writeString(indate);
        dest.writeString(outdate);
        dest.writeInt(weight);
        dest.writeInt(count);
        dest.writeString(price);
        dest.writeString(won);
        dest.writeString(extra);
    }

    public int describeContents() {
        return 0;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> productData = new HashMap<>();

        //productData.put("번호", num);
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
            //arrayListOf("코드", "원산지", "품종", "수입날짜", "반입날짜", "중량", "수량", "단가", "금액","비고")

            // 첫번째 요소가 int로 변환 불가능한 경우
            /*code = input.get(0);
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
            }*/
        }
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

}