package com.example.camerakt.database.service;

import com.example.camerakt.database.OCRTableFirebaseHelper;
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

    public void getAllProducts() {
        dbHelper.getAllProducts();
    }

    public void getProduct(String productId) {
        dbHelper.getProduct(productId);
    }

    public void updateProduct(String productId, Map<String, Object> newData) {
        dbHelper.updateProduct(productId, newData);
    }

    public void deleteProduct(String productId) {
        dbHelper.deleteProduct(productId);
    }
}
