# ServerTimeAPP for Android
>확인하고 싶은 사이트의 링크를 입력받아 서버 시간을 알려주는 안드로이드 앱입니다.  
It is an app that shows the server time by receiving a link to the site from the user.

  <br/><br/>
## ✨ summary
http통신을 통해 사이트에 http request를 보내고,  
이후 서버에서 전달받은 헤더에 담겨져있는 서버시간을 앱 안에 출력합니다.  
Send http request to that site via http. After that, the server time which is contained in the header what user received from the server is printed in the app.
  <br/><br/>
## 📖 Introduction  
수강신청이나 티켓팅을 할 때면 버튼 클릭을 언제 하냐에 따라서 희비가 오가는 경우가 많았습니다. 그럴 때마다 네이버 시계를 옆에 띄어두고 정시에 맞춰 버튼을 클릭합니다. 네이버 시계가 정확하다고는 하지만 네이버 시계와 서버의 시간이 조금씩 다를때가 있습니다. 1초 차이라 할지라도 티켓팅이나 수강신청에서는 큰 영향을 주는 부분이라고 할 수 있습니다.

따라서 이번에는 서버 시간을 알고 싶은 사이트 링크를 입력하면 서버 시간을 보여주고 이후 앱 화면을 끄고 다른 창을 켜더라도 화면 위에 위젯을 띄어주어 어떤 화면에서도 서버의 시간을 확인할 수 있게 도와주는 어플을 개발할 것입니다.
  <br/><br/>
## 👨‍💻 System requirements
기본적으로 Android Studio에서 JAVA언어 기반으로 개발을 진행합니다.  
이를위해 Android Studio 설치가 필수적입니다.  
또한 안드로이드 SDK Android 8.1 오레오 API Level 21 이상에서 개발합니다.
  <br/><br/>
## 📝 Todo list
제작할 코드와 문서들입니다.

- [x] [💻] 메인 타이틀 (완료 ver 1.0)
- [x] [💻] 앱 사용 설명 출력부 (완료 ver 1.0)
- [x] [💻] 링크 입력부 (완료 ver 1.0)
- [x] [💻] 시작, 중지 버튼 구현 (완료 ver 1.0)
- [x] [💻] 시간 출력부 (완료 ver 1.0)
- [x] [💻] 안내문 출력부 (완료 ver 1.0)
- [x] [📗] HTTP Request를 통한 Header 수신 기능 구현 (완료 ver 1.0)
- [x] [📗] Header부의 date 파싱을 통해 한국 표준시로 변경하고 출력 (완료 ver 1.0)
- [x] [📗] 시작 버튼 클릭시 HttpRequest는 중지시까지 background 실행 (완료 ver 1.0)
- [x] [📗] 중지 버튼 클릭시 실행중인 background 중지 (완료 ver 1.0)
- [x] [📗] 클립보드에 저장되어져 있는 링크 주소 붙여넣기 버튼 (완료 ver 1.0)
- [x] [🔨] 오버레이 버튼 (완료 ver 1.1)
- [x] [🔨] 오버레이 위젯 구현 (완료 ver 1.1)
- [x] [🔨] 오버레이 위젯 자유이동 (완료 ver 1.1)
- [x] [🔨] 오버레이 위젯 크기조절 SeekBar (완료 ver 1.1)
- [x] [🔨] (추가) 오버레이 위젯 Setting Activity 제작 (완료 ver 1.2)
- [x] [🔨] (추가) 오버레이 위젯 크기조절 SeekBar를 setWidget Activity로 이동 (완료 ver 1.2)
- [x] [🔨] 오버레이 위젯 배경색 변경 기능 (완료 ver 1.2)
- [x] [🔨] (추가) 오버레이 위젯의 시간이 실시간 갱신되지 않는 문제 해결 (완료 ver 1.2.2)
- [x] [🔨] (추가) 오버레이 롱 클릭, 더블 클릭시 앱으로 이동 (완료 ver 1.2.3)
- [x] [🔨] (추가) 메인, 위젯 셋팅 액티비티에 대한 생명주기 관리 (완료 ver 1.2.4) (보완 ver 1.2.5)
- [x] [🔨] (추가) 오버레이 위젯의 예약 종료 기능을 위한 UI 구현 (완료 ver 1.3)
- [x] [🔨] (추가) 오버레이 위젯의 예약 종료 기능적 구현 (완료 ver 1.3.1)
- [x] [🔨] (추가) google AdMob 보상형 광고 삽입 (완료 ver 1.3.1)
- [x] [🔨] (추가) 위젯 셋팅 액티비티 내 google AdMob 배너 광고 삽입 (완료 ver 1.3.1)
- [x] [🔨] (추가) 사용자 가이드 팝업 액티비티 추가 (완료 ver 1.3.2)
- [x] [🔨] 오버레이 뷰 디자인 (최종 ver 1.4.0)
- [x] [🔨] 앱 전체적인 디자인 ( ver 1.4.0)
- [x] [🔨] 로고 제작 (완료 ver 1.4.0)
- [x] [🔒] google AdMob광고 배너 삽입 (완료 ver 1.2.1)
- [x] [🔓] Play Store 출시 (완료 ver 1.4.0)

  <br/> <br/>
  
## ☁️ Screen Shot
  
> ### ver 1.0 ( commit 2020-02-14 )  
메인 액티비티 구성 및 서버 시간 핵심 기능 구현  
<img src="https://user-images.githubusercontent.com/56837413/74477830-62cd6680-4eef-11ea-8d06-8c36afbba924.jpg" width="30%"></img> 
<img src="https://user-images.githubusercontent.com/56837413/74477619-fe120c00-4eee-11ea-82b8-749822bab159.jpg" width="30%"></img>
  
  <br/>
  <br/>
  <br/>
  
> ### ver 1.1 ( commit 2020-02-15 )  
오버레이 위젯 핵심 기능 구현<br/>
위젯 이동 구현 (출처 : http://blog.daum.net/mailss/35 )  
<img src="https://user-images.githubusercontent.com/56837413/74565281-40e9e780-4fb4-11ea-833e-55d963597bd4.jpg" width="30%"></img>
<img src="https://user-images.githubusercontent.com/56837413/74565288-447d6e80-4fb4-11ea-8a17-03bcf82eea31.jpg" width="30%"></img>
<img src="https://user-images.githubusercontent.com/56837413/74565293-48a98c00-4fb4-11ea-9b0f-7a4284aafe65.jpg" width="30%"></img>
  
  <br/>
  <br/>
  <br/>
  
> ### ver 1.2 ( commit 2020-02-16 )  
위젯 기능 강화  
<img src="https://user-images.githubusercontent.com/56837413/74595084-d9e63480-5080-11ea-8b41-86eb10df4357.jpg" width="30%"></img>
<img src="https://user-images.githubusercontent.com/56837413/74595082-d94d9e00-5080-11ea-827a-ceb99a5bc55f.jpg" width="30%"></img>
<img src="https://user-images.githubusercontent.com/56837413/74595081-d783da80-5080-11ea-8629-3facd3048981.jpg" width="30%"></img>
  
  <br/>
  <br/>
  <br/>
  
> ### ver 1.2.1 ( commit 2020-02-17 )  
메인 하단 구글 애드몹 배너 추가  
<img src="https://user-images.githubusercontent.com/56837413/74609777-ab279700-5130-11ea-9478-2e1e80f9fdd5.jpg" width="30%"></img>
 
  <br/>
  <br/>
  <br/>
  
> ### ver 1.2.2 ( commit 2020-02-17 )  
오버레이 위젯을 손으로 잡고있거나 이동시킬때만 시간이 갱신되는 문제를 수정. 이제 정상적으로 받아오는 서버시간에 맞춰 실시간으로 시간이 갱신되어 보여짐.  
<img src="https://user-images.githubusercontent.com/56837413/74612140-031cc880-5146-11ea-941b-8d4d111c05d9.jpg" width="30%"></img>
<img src="https://user-images.githubusercontent.com/56837413/74612139-01530500-5146-11ea-90d9-f619c452419f.jpg" width="30%"></img>
<img src="https://user-images.githubusercontent.com/56837413/74678607-cd87e600-51fe-11ea-8709-ad7d45d8f66c.gif" width="30%"></img>
 
  <br/>
  <br/>
  <br/>
  
> ### ver 1.2.3 ( commit 2020-02-18 )  
오버레이 위젯을 길게 누르고 있거나 더블클릭하면 Background의 Main Activity가 foreground로 변경되며 화면에 띄움
<img src="https://user-images.githubusercontent.com/56837413/74678590-c52fab00-51fe-11ea-8f4d-2727f1256398.gif" width="30%"></img>
 
  <br/>
  <br/>
  <br/>
  
> ### ver 1.2.4 ( commit 2020-02-18 )  
액티비티 별 생명주기 관리 및 제어 

  <br/>
  <br/>
  <br/>
  
> ### ver 1.2.5 ( commit 2020-02-19 )  
 - 위젯 셋팅 액티비티 화면에서 다른 앱화면이나 홈으로 전환 후 위젯 롱, 더블클릭을 통해 서버시간앱으로 전환시 메인 액티비티의 셋팅이 제대로 로드 되지 않는 문제가 있어, 어느 화면에서 위젯의 롱, 더블클릭을 통해 서버앱을 켜도 메인 액티비티의 기존 값들이 제대로 로드 되도록 수정  
<img src="https://user-images.githubusercontent.com/56837413/74784423-3482c900-52eb-11ea-9493-dca110116a22.gif" width="30%"></img>
  <br/>
  <br/>
  
  - 위젯 셋팅 후 다시 셋팅 액티비티 실행시 기존에 셋팅해 두었던 셋팅 값들이 초기화 되는 문제가 있어, 셋팅 버튼 클릭시 기존에 셋팅이 되어있으면 그 셋팅을 다시 로드 해오도록 수정  
<img src="https://user-images.githubusercontent.com/56837413/74784428-364c8c80-52eb-11ea-8c08-7e350f9346ce.gif" width="30%"></img>

  <br/>
  <br/>
  <br/>
  
> ### ver 1.3.0 ( commit 2020-02-20 )  
위젯 예약 종료 기능을 위한 UI구현<br/>
AdMob 보상형를 위한 광고 버튼 추가<br/>
(예약종료기능은 구현중)<br/>
<img src="https://user-images.githubusercontent.com/56837413/74933982-9006a100-5428-11ea-9264-51bf7ad8b13c.jpg" width="30%"></img>

  <br/>
  <br/>
  <br/>
  
> ### ver 1.3.1 ( commit 2020-02-21 )  
오버레이 위젯 예약종료 기능 구현 완료<br/>
보상형 광고 구현<br/>
위젯 세팅 액티비티 내 배너광고 구현<br/>  <br/>
 - 광고를 보지 않았다면, 광고보기 버튼이 / 광고를 봤지만 예약하지 않았다면, 예약하기 버튼이 활성화<br/>
<img src="https://user-images.githubusercontent.com/56837413/74975319-3aeb7f00-546a-11ea-953f-07151c732ef8.jpg" width="30%"></img>
<img src="https://user-images.githubusercontent.com/56837413/74975321-3c1cac00-546a-11ea-99a5-00ec782cf388.jpg" width="30%"></img>  
<br/>
<br/>  

 - 시간에 맞춰 예약을 하면 예약된 시간이 표기되고 시간에 맞춰 위젯과 서버 시간 받아오는 작업이 정지<br/>
<img src="https://user-images.githubusercontent.com/56837413/74975328-3de66f80-546a-11ea-8838-251feb6ba09f.jpg" width="30%"></img>
<img src="https://user-images.githubusercontent.com/56837413/74975332-3f179c80-546a-11ea-9e33-083340028fd4.gif" width="30%"></img>  

  <br/>
  <br/>
  <br/>
  
> ### ver 1.3.2 ( commit 2020-02-21 )  
액티비티의 상단 이름 변경<br/>
메인 액티비티 액션바 버튼추가<br/>
사용 가이드 팝업 액티비티 추가<br/>

<img src="https://user-images.githubusercontent.com/56837413/74981662-04b3fc80-5476-11ea-9f57-2564cea77ed7.jpg" width="30%"></img>
<img src="https://user-images.githubusercontent.com/56837413/74981659-0382cf80-5476-11ea-9662-cd8d48073fea.jpg" width="30%"></img>

  <br/>
  <br/>
  <br/>
  
> ### ver 1.4.0 ( commit 2020-02-22 )  
앱 로고 아이콘 추가<br/>
위젯 버그 수정<br/>
앱스토어 출시용 소개 이미지 제작<br/>
 - 깃허브 업로드 이후 AdMob app ID, 광고ID 기존의 '테스트 아이디'에서 '개인 아이디'로 변경
<br/>
> icon <br/>
<img src="https://user-images.githubusercontent.com/56837413/75077226-7c9d2800-5545-11ea-8e98-d0c6c27ad08e.png" width="30%"></img>
<br/><br/>

>앱 소개 이미지 <br/>
<img src="https://user-images.githubusercontent.com/56837413/75077281-9b9bba00-5545-11ea-8e2b-3b3a5d66f4f4.png" width="91%"></img><br/>
<img src="https://user-images.githubusercontent.com/56837413/75077270-950d4280-5545-11ea-9ef5-1ff4f7e7ae94.png" width="30%"></img>
<img src="https://user-images.githubusercontent.com/56837413/75077273-963e6f80-5545-11ea-84e7-bf75fbb9d43a.png" width="30%"></img>
<img src="https://user-images.githubusercontent.com/56837413/75077275-976f9c80-5545-11ea-8f45-a6f29181d1e9.png" width="30%"></img><br/>
<img src="https://user-images.githubusercontent.com/56837413/75077277-98a0c980-5545-11ea-9310-97ad88d24dd0.png" width="30%"></img>
<img src="https://user-images.githubusercontent.com/56837413/75077280-9b032380-5545-11ea-8b16-8414836c4861.png" width="30%"></img>



  <br/>
  <br/>
  <br/>
  
