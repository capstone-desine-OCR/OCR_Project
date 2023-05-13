package com.example.camerakt.response;

import com.example.camerakt.response.CellTextLine;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Tablecell {
    @SerializedName("cellTextLines")
    @Expose
    private List<CellTextLine> cellTextLines = null;

    @SerializedName("boundingPoly")
    @Expose
    private BoundingPoly boundingPoly;

    @SerializedName("inferConfidence")
    @Expose
    private double inferConfidence;

    @SerializedName("rowSpan")
    @Expose
    private int rowSpan;

    @SerializedName("rowIndex")
    @Expose
    private int rowIndex;

    @SerializedName("columnSpan")
    @Expose
    private int columnSpan;

    @SerializedName("columnIndex")
    @Expose
    private int columnIndex;

    public List<CellTextLine> getCellTextLines() {
        return cellTextLines;
    }

    public void setCellTextLines(List<CellTextLine> cellTextLines) {
        this.cellTextLines = cellTextLines;
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

    public int getRowSpan() {
        return rowSpan;
    }

    public void setRowSpan(int rowSpan) {
        this.rowSpan = rowSpan;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColumnSpan() {
        return columnSpan;
    }

    public void setColumnSpan(int columnSpan) {
        this.columnSpan = columnSpan;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "cellTextLines=" + cellTextLines +
                ", boundingPoly=" + boundingPoly +
                ", inferConfidence=" + inferConfidence +
                ", rowSpan=" + rowSpan +
                ", rowIndex=" + rowIndex +
                ", columnSpan=" + columnSpan +
                ", columnIndex=" + columnIndex +
                '}';
    }
}
