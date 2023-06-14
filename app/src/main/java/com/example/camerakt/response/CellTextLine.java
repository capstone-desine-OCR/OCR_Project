package com.example.camerakt.response;

import com.example.camerakt.response.CellWord;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CellTextLine {
    @SerializedName("cellWords")
    @Expose
    private List<CellWord> cellWords = null;

    @SerializedName("boundingPoly")
    @Expose
    private BoundingPoly boundingPoly;

    @SerializedName("inferConfidence")
    @Expose
    private double inferConfidence;

    public List<CellWord> getCellWords() {
        return cellWords;
    }

    public void setCellWords(List<CellWord> cellWords) {
        this.cellWords = cellWords;
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
        return "CellTextLine{" +
                "cellWords=" + cellWords +
                ", boundingPoly=" + boundingPoly +
                ", inferConfidence=" + inferConfidence +
                '}';
    }
}
