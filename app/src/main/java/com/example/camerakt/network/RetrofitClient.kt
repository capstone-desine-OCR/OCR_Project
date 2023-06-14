package com.example.camerakt.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*

REST API 통신을 위해 구현된 통신 라이브러리로, 대부분의 개발자들이 이용
REST 기반의 웹 서비스를 통해 JSON 구조의 데이터를 쉽게 가져오고 업로드할 수 있다.
RetrofitClient를 사용하기 위해서는 다음과 같은 3가지 요소가 필요하다.

    1. JSON 형태의 모델 클래스 (kotlin 에서는 data class 를 사용, 여기서는 demand/ response package에 있는 class들이 이에 해당한다.)

    2. HTTP 작업을 정의하는(onSuccess/onFail) 인터페이스 -> RetrofitService 인터페이스

    3. Retrofit.Builder를 선언한 클래스 (baseUrl과 Converter등을 선언한다. Interceptor를 추가하여 응답을 가공할수도 있다.) -> RetrofitClient 오브젝트
 */

// Retrofit 인스턴스를 생성하고 API 요청을 실행합니다:
object RetrofitClient {
    private var instance: Retrofit? = null
    private val gson = GsonBuilder().setLenient().create()

    //CLOVA OCR Invoke URL(자동 연동 설정 후 생성된 url인 ~/general에서 gelneral을 제거해주어야함, 수동 연동했을 때 나오는 Invoke url 아님)
    const val BASE_URL = "" // invoke_url 넣는곳

    //Secret Key
    const val KEY = ""

    //Retrofit.Builder()를 통해 baseURL설정, converter를 GsonConverter로 설정
    fun getInstance(): Retrofit {
        if (instance == null) {
            instance = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        return instance!!
    }
}