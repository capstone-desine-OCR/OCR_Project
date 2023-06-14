package com.example.camerakt.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

//요청 바디
public class Example {

    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("requestId")
    @Expose
    private String requestId;
    @SerializedName("timestamp")
    @Expose
    private Long timestamp;
    @SerializedName("images")
    @Expose
    private List<RecognitionResults> images = null;

    @SerializedName("enableTableDetection")
    @Expose
    private boolean enableTableDetection=true;

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

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public List<RecognitionResults> getImages() {
        return images;
    }

    public void setImages(List<RecognitionResults> images) {
        this.images = images;
    }

    public boolean getEnableTableDetection() {
        return enableTableDetection;
    }

    public void setEnableTableDetection(boolean enableTableDetection) {
        this.enableTableDetection = enableTableDetection;
    }

    @Override
    public String toString() {
        return "Example{" +
                "version='" + version + '\'' +
                ", requestId='" + requestId + '\'' +
                ", timestamp=" + timestamp +
                ", images=" + images +
                ", enableTableDetection="+ enableTableDetection+
                '}';
    }
}