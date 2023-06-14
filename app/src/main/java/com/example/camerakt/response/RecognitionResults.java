package com.example.camerakt.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

// 이미지 인식 결과
public class RecognitionResults {

    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("inferResult")
    @Expose
    private String inferResult;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("validationResult")
    @Expose
    private ValidationResult validationResult;

    @SerializedName("tables")
    @Expose
    private List<ImageTable> tables = null;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInferResult() {
        return inferResult;
    }

    public void setInferResult(String inferResult) {
        this.inferResult = inferResult;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ValidationResult getValidationResult() {
        return validationResult;
    }

    public void setValidationResult(ValidationResult validationResult) {
        this.validationResult = validationResult;
    }

    /*public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }
*/
    public List<ImageTable> getTables() {
        return tables;
    }

    public void setTables(List<ImageTable> tables) {
        this.tables = tables;
    }

    @Override
    public String toString() {
        return "RecognitionResults{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", inferResult='" + inferResult + '\'' +
                ", message='" + message + '\'' +
                //", fields=" + fields +
                ", validationResult=" + validationResult +
                ", tables=" + tables +
                '}';
    }
}
