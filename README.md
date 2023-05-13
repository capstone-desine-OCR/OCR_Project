# OCR_Project

- 개발자 가이드 https://developer.android.com/guide?hl=ko
- 앱 아키텍쳐 가이드 https://developer.android.com/jetpack/guide?hl=ko

# 화면 전환 시 필요한 지식 요약 (by 개발자 가이드)

  ## 0. 활동
  대부분의 앱에는 여러 화면이 포함되어 있습니다. 즉, 대부분의 앱은 여러 활동으로 구성됩니다.
  일반적으로 앱에서 하나의 활동이 기본 활동으로 지정되며 이 기본 활동은 사용자가 앱을 실행할 때 표시되는 첫 번째 화면입니다.
  각 활동은 다양한 활동을 실행하기 위해 또 다른 활동을 시작할 수 있습니다. 예를 들어 간단한 이메일 앱의 기본 활동은 이메일 받은편지함을 표시하는 화면을 제공할 수 있습니다.
  여기에서 기본 활동은 이메일 작성 및 개별 이메일 열기와 같은 작업을 위한 화면을 제공하는 다른 활동들을 실행할 수 있습니다.

  ## 1. manifest 구성
  manifest에 활동 및 관련된 특정 속성을 선언해야 앱에서 활동을 사용할 수 있습니다
  활동을 선언하려면 manifest 파일을 열고 <activity> 요소를 <application> 요소의 하위 요소로 추가해야 합니다.
  필수 속성은 활동의 클래스 이름을 지정하는 android:name 입니다.

  ## 2. 인텐트 필터 선언
  <activity> 요소에서 <intent-filter> 속성을 선언함으로써 이 기능을 활용할 수 있습니다.
  예를 들어 다음 코드 스니펫은 텍스트 데이터를 전송하고 다른 활동들의 요청을 수신하는 활동을 구성하는 방법을 보여줍니다.
      <activity android:name=".ExampleActivity" android:icon="@drawable/app_icon">
        <intent-filter>
            <action android:name="android.intent.action.SEND" />
            <category android:name="android.intent.category.DEFAULT" />
            <data android:mimeType="text/plain" />
        </intent-filter>
    </activity>
    이 예에서 <action> 요소는 이 활동이 데이터를 전송하도록 지정합니다.
    <category> 요소를 DEFAULT로 선언하면 활동이 실행 요청을 수신할 수 있습니다.
    <data> 요소는 이 활동이 전송할 수 있는 데이터 유형을 지정합니다. 다음 코드 스니펫에서는 위에 설명된 활동을 호출하는 방법을 보여줍니다.

    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, textMessage)
    }
    startActivity(sendIntent)
    
  ## 3.권한 선언
  manifest의 <activity> 태그를 사용하여 특정 활동을 시작할 수 있는 앱을 제어할 수 있습니다.
  상위 활동과 하위 활동이 모두 각 manifest에서 동일한 권한을 가지고 있지 않다면 상위 활동이 하위 활동을 실행할 수 없습니다.
  상위 활동에서 <uses-permission> 요소를 선언할 때에는 각 하위 활동에 일치하는 <uses-permission> 요소가 있어야 합니다.

  예를 들어 앱에서 SocialApp이라는 가상의 앱을 사용하여 소셜 미디어의 게시물을 공유하려면,
  다음과 같이 게시물을 호출하는 앱이 보유해야 하는 권한을 SocialApp 자체에서 정의해야 합니다.


      <manifest>
      <activity android:name="...."
         android:permission=”com.google.socialapp.permission.SHARE_POST”

      />
     
 # 그 외
- onCreate()
시스템이 활동을 생성할 때 실행되는 이 콜백을 구현해야 합니다. 구현 시 활동의 필수 구성요소를 초기화해야 합니다.
예를 들어 앱은 여기에서 뷰를 생성하고 데이터를 목록에 결합해야 합니다.
이 콜백에서 setContentView()를 호출하여 활동의 사용자 인터페이스를 위한 레이아웃을 정의해야 하며 이 작업이 가장 중요합니다.

onCreate()가 완료되면 다음 콜백은 항상 onStart()입니다.

- onStart()
onCreate()가 종료되면 활동은 '시작됨' 상태로 전환되고 활동이 사용자에게 표시됩니다. 이 콜백에는 활동이 포그라운드로 나와서 대화형이 되기 위한 최종 준비에 준하는 작업이 포함됩니다.
    
