package com.example.camerakt.database.service;

import com.example.camerakt.database.OCRTableFirebaseHelper;
import com.example.camerakt.database.callback.ProductCallBack;
import com.example.camerakt.database.model.OCRTable;

import java.util.Map;

public class OCRTableService {
    private final OCRTableFirebaseHelper dbHelper;

    public OCRTableService() {
        dbHelper = new OCRTableFirebaseHelper();
    }

    public void addProduct(OCRTable ocrTable) {
        Map<String, Object> productData = ocrTable.toMap();
        dbHelper.addProduct(productData);
    }

    public void getAllProducts(ProductCallBack callBack) {
        dbHelper.getAllProducts(callBack);
    }

    public OCRTable getProduct(String productId) {
        OCRTable ocr = dbHelper.getProduct(productId);
        return ocr;
    }

    public void updateProduct(String productId, Map<String, Object> newData) {
        dbHelper.updateProduct(productId, newData);
    }

    public void deleteProduct(String productId) {
        dbHelper.deleteProduct(productId);
    }
}
