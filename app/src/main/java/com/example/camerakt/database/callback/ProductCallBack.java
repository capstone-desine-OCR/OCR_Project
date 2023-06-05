package com.example.camerakt.database.callback;

import com.example.camerakt.database.model.OCRTable;

import java.util.List;

public interface ProductCallBack {
    void onSuccess(List<OCRTable> products);

    void onFailure(Exception e);
}
