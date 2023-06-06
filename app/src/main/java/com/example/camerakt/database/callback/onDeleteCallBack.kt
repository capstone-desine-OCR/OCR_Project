package com.example.camerakt.database.callback

import com.example.camerakt.database.model.OCRTable

interface onDeleteCallBack {
    fun onDelete(contract: OCRTable)
    
}
