package com.example.camerakt.database.model;

import java.util.Date;

public class OCTTable {

    public static final String TABLE_NAME = "octtable";

    public static final String COLUMN_NUM = "num";
    public static final String COLUMN_CODE = "code";
    public static final String COLUMN_ORIGIN = "origin";
    public static final String COLUMN_CULTIVAR = "cultivar";
    public static final String COLUMN_INDATE = "indate";
    public static final String COLUMN_OUTDATE = "outdate";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_COUNT = "count";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_WON = "won";
    public static final String COLUMN_EXTRA = "extra";

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


    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_NUM + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_CODE + " TEXT," +
                    COLUMN_ORIGIN + " TEXT," +
                    COLUMN_CULTIVAR + " TEXT," +
                    COLUMN_INDATE + " DATE," +
                    COLUMN_OUTDATE + " DATE," +
                    COLUMN_WEIGHT + " INTEGER," +
                    COLUMN_COUNT + " INTEGER," +
                    COLUMN_PRICE + " TEXT," +
                    COLUMN_WON + " TEXT," +
                    COLUMN_EXTRA + " TEXT" +
                    ");";

    public OCTTable(){

    }

    public OCTTable(int num, String code, String origin, String cultivar, String indate,
                    String outdate, int weight, int count, String price, String won, String extra) {
        this.num = num;
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
    }

    public int getNum() {
        return num;
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

    @Override
    public String toString() {
        return "OCTTable{" +
                "num=" + num +
                ", code='" + code + '\'' +
                ", origin='" + origin + '\'' +
                ", cultivar='" + cultivar + '\'' +
                ", indate='" + indate + '\'' +
                ", outdate='" + outdate + '\'' +
                ", weight=" + weight +
                ", count=" + count +
                ", price='" + price + '\'' +
                ", won='" + won + '\'' +
                ", extra='" + extra + '\'' +
                '}';
    }
}
