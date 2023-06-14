package com.example.camerakt.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImageTable {

    @SerializedName("cells")
    @Expose
    private List<Tablecell> cells;

    @SerializedName("boundingPoly")
    @Expose
    private BoundingPoly boundingPoly;

    @SerializedName("inferConfidence")
    @Expose
    private Double inferConfidence;

    public List<Tablecell> getCells() {
        return cells;
    }

    public void setCells(List<Tablecell> cells) {
        this.cells = cells;
    }

    public BoundingPoly getBoundingPoly() {
        return boundingPoly;
    }

    public void setBoundingPoly(BoundingPoly boundingPoly) {
        this.boundingPoly = boundingPoly;
    }

    public Double getInferConfidence() {
        return inferConfidence;
    }

    public void setInferConfidence(Double inferConfidence) {
        this.inferConfidence = inferConfidence;
    }

}
