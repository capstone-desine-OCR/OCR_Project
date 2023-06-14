package com.example.camerakt.network

import com.example.camerakt.demand.RecognitionRequest
import com.example.camerakt.response.Example
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

// 네이버 클로바 API의 엔드포인트에 해당하는 메서드를 가진 인터페이스를 생성

interface RetrofitService {

    //요청 헤더
    @Headers(
        "Content-Type: application/json",
        "X-OCR-SECRET: "  // api key 넣는곳
    )

    @POST("general")
    fun postRequest(
        // RecognitionRequest 타입의 params라는 파라미터를 바디에 포함하여 요청을 전송
        @Body params: RecognitionRequest
    ): Call<Example> // Example : API에서 응답으로 받는 데이터 모델의 클래스명

    /*
        Call<Example>은 Retrofit에서 제공하는 비동기적인 네트워크 요청을 수행하는 객체이다.
        이 객체는 서버로부터 받은 응답을 처리하고,
        성공적인 응답인 경우에는 원하는 형태로 변환하여 반환할 수 있습니다

        Call<Example> 객체를 사용하여 네트워크 요청을 실행하려면,
        1.enqueue 메서드: 비동기적인 요청 실행
        2.execute 메서드: 동기적으로 요청을 실행, 메인스레드 x 백그라운드 스레드에서 실행할 것

        #동기 = 동기화로 생각

        # 동기적 요청: 요청을 보낸 후 서버로부터 응답을 받을 때까지 대기하는 방식이다.
                     응답이 오기 전까지 코드의 실행이 일시중지된다.
                     즉, 요청 완료 시까지 다른 작업의 수행이 불가능하다.
        # 비동기적 요청: 요청을 보낸 후에도 코드의 실행이 일시 중지되지 않고 계속해서 실행한다.
                      요청이 백그라운드에서 비동기적으로 처리되며,
                      서버로부터 응답이 도착하면 지정된 콜백 메서드가 호출된다.
    */
}