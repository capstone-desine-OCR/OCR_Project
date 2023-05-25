package com.example.camerakt.database;


import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.Map;

public class OCRTableFirebaseHelper{
    FirebaseFirestore fireDB;
    CollectionReference productsCollection;

    public OCRTableFirebaseHelper(){
        fireDB=FirebaseFirestore.getInstance();
        productsCollection=fireDB.collection("products");
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

    // Read - 전체 문서 조회
    public void getAllProducts() {

        productsCollection.get()
                .addOnSuccessListener(querySnapshot -> {
                    for (DocumentSnapshot document : querySnapshot) {
                        Log.d("current", document.getId() + " => " + document.getData());
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("current", "Error getting products", e);
                });
    }

    // 개별 문서 조회
    public void getProduct(String productId) {

        DocumentReference productRef = productsCollection.document(productId);
        productRef.get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Log.d("current", "Document ID: " + documentSnapshot.getId());
                        Log.d("current", "Document data: " + documentSnapshot.getData());

                    } else {
                        Log.d("current", "Document does not exist");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("current", "Error getting product", e);
                });
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





}