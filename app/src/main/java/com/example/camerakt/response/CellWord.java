package com.example.camerakt.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CellWord {

    @SerializedName("inferText")
    private String inferText;

    @SerializedName("boundingPoly")
    @Expose
    private BoundingPoly boundingPoly;

    @SerializedName("inferConfidence")
    @Expose
    private double inferConfidence;

    public String getInferText() {
        return inferText;
    }

    public void setInferText(String inferText) {
        this.inferText = inferText;
    }

    public BoundingPoly getBoundingPoly() {
        return boundingPoly;
    }

    public void setBoundingPoly(BoundingPoly boundingPoly) {
        this.boundingPoly = boundingPoly;
    }

    public double getInferConfidence() {
        return inferConfidence;
    }

    public void setInferConfidence(double inferConfidence) {
        this.inferConfidence = inferConfidence;
    }

    @Override
    public String toString() {
        return "CellWord{" +
                "inferText='" + inferText + '\'' +
                ", boundingPoly=" + boundingPoly +
                ", inferConfidence=" + inferConfidence +
                '}';
    }
}

