package com.example.camerakt.database;


import android.util.Log;
import com.example.camerakt.database.callback.ProductCallBack;
import com.example.camerakt.database.model.OCRTable;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OCRTableFirebaseHelper {
    FirebaseFirestore fireDB;
    CollectionReference productsCollection;

    public OCRTableFirebaseHelper() {
        fireDB = FirebaseFirestore.getInstance();
        productsCollection = fireDB.collection("products");
    }


    // Create - 문서 추가
    public void addProduct(Map<String, Object> productData) {
        String code = (String) productData.get("코드");
        DocumentReference docuReference = productsCollection.document(code);
        docuReference
                .set(productData)
                .addOnSuccessListener(documentReference -> {
                    Log.d("current", "Product added with ID: " + docuReference.getId());
                })
                .addOnFailureListener(e -> {
                    Log.e("current", "Error adding product", e);
                });
    }

    // firebase가 비동기 방식이라고 해서 callback을 통해 tableActivity oncreate으로
    public void getAllProducts(ProductCallBack callBack) {

        productsCollection.get()
                .addOnSuccessListener(querySnapshot -> {
                    List<OCRTable> result = new ArrayList<>();

                    for (DocumentSnapshot document : querySnapshot) {
                        Log.d("current ", document.getId() + " => " + document.getData());
                        OCRTable ocr = new OCRTable();
                        documentToOCRTable(ocr, document);
                        result.add(ocr);
                    }
                    callBack.onSuccess(result);
                })
                .addOnFailureListener(e -> {
                    Log.e("current", "Error getting products", e);
                    callBack.onFailure(e);
                });

    }

    // 개별 문서 조회
    public OCRTable getProduct(String productId) {
        OCRTable ocrTable = new OCRTable();
        DocumentReference productRef = productsCollection.document(productId);
        productRef.get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Log.d("current", "Document ID: " + documentSnapshot.getId());
                        Log.d("current", "Document data: " + documentSnapshot.getData());
                        documentToOCRTable(ocrTable, documentSnapshot);

                    } else {
                        Log.d("current", "Document does not exist");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("current", "Error getting product", e);
                });

        return ocrTable;
    }


    // Update - 문서 업데이트
    public void updateProduct(String productId, Map<String, Object> newData) {

        productsCollection.document(productId)
                .update(newData)
                .addOnSuccessListener(aVoid -> {
                    Log.d("current", "Product updated successfully");
                })
                .addOnFailureListener(e -> {
                    Log.e("current", "Error updating product", e);
                });
    }

    // Delete - 문서 삭제
    public void deleteProduct(String productId) {

        productsCollection.document(productId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Log.d("current", "Product deleted successfully");
                })
                .addOnFailureListener(e -> {
                    Log.e("current", "Error deleting product", e);
                });
    }


    public void documentToOCRTable(OCRTable result, DocumentSnapshot document) {
        result.setCode((String) document.get("코드"));
        result.setOrigin((String) document.get("원산지"));
        result.setCultivar((String) document.get("품종"));
        result.setIndate((String) document.get("수입날짜"));
        result.setOutdate((String) document.get("반입날짜"));
        result.setWeight(document.getLong("중량").intValue());
        result.setCount(document.getLong("수량").intValue());
        result.setPrice((String) document.get("단가"));
        result.setWon((String) document.get("금액"));
        result.setExtra((String) document.get("비고"));
    }


}