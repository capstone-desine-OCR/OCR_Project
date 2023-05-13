package com.example.camerakt.demand;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

//요청 바디
public class RecognitionRequest {
    // @SerializedName: JSON으로 serialize 될 때 매칭되는 이름을 명시하는 목적으로 사용
    // @Expose: object 중 해당 값이 null일 경우, json으로 만들 필드를 자동 생략

    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("requestId")
    @Expose
    private String requestId;
    @SerializedName("timestamp")
    @Expose
    private Integer timestamp;
    /*
    @SerializedName("lang")
    @Expose
    private String lang;
     */
    @SerializedName("images")
    @Expose
    private List<ImageFields> images = null;

    //enableTableDetection
    @SerializedName("enableTableDetection")
    @Expose
    private Boolean enableTableDetection=true;


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public List<ImageFields> getImages() {
        return images;
    }

    public void setImages(List<ImageFields> images) {
        this.images = images;
    }

    public Boolean getEnableTableDetection() {
        return enableTableDetection;
    }

    public void setEnableTableDetection(Boolean enableTableDetection) {
        this.enableTableDetection = enableTableDetection;
    }

}
