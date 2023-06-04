package com.example.camerakt.database.service;

import com.example.camerakt.database.OCRTableFirebaseHelper;
import com.example.camerakt.database.model.OCRTable;

import java.util.List;
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

    public List<OCRTable> getAllProducts() {
        List<OCRTable> result = dbHelper.getAllProducts();
        return result;
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
