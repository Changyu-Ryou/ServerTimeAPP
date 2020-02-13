# ServerTimeAPP for Android
>확인하고 싶은 사이트의 링크를 입력받아 서버 시간을 알려주는 안드로이드 앱입니다.  
It is an app that shows the server time by receiving a link to the site from the user.  


## ✨ summary
http통신을 통해 사이트에 httprequest를 보내고,  
이후 서버에서 전달받은 헤더에 담겨져있는 서버시간을 앱 안에 출력합니다.  
Send httprequest to that site via http. After that, the server time which is contained in the header what user received from the server is printed in the app.
  
## 📖 Introduction  
수강신청이나 티켓팅을 할때면 버튼 클릭을 언제하냐에 따라서 희비가 오가는 경우가 많았습니다.
그럴때마다 네이버 시계를 옆에 띄어두고 정시에 맞춰 버튼을 클릭하는데요,  
사실 네이버 시계가 정확하다고는 하지만 사이트를 개발하며 네이버 시계를 서버의 시간으로 사용하지는않고 서버시간이라는 개념이 있어
서버의 시간에 맞춰 티켓이 열리거나 수강신청 페이지가 열린다고 봐야할 것입니다.

따라서 이번에는 서버시간을 알고싶은 사이트의 링크를 입력하면 서버의 시간을 보여주고 이후 인터넷 창을 켜더라도 화면 위에 위젯을 띄어주어 어떤 화면에서도 서버의 시간을 확인할 수 있게 도와주는 어플을 개발할 것입니다.


## 👨‍💻 System requirements
기본적으로 Android Studio에서 JAVA언어 기반으로 개발을 진행합니다.  
이를위해 Android Studio 설치가 필수적입니다.  
또한 안드로이드 SDK는 Android 8.1 오레오 API Level 21 이상에서 개발합니다.  

## 📝 Todo list
제작할 코드와 문서들입니다.(밑에 Emoji를 클릭하면 해당하는 Github page로 이동합니다)

- [x] [💻] 메인 타이틀
- [x] [💻] 앱 사용 설명 출력부
- [x] [💻] 링크 입력부
- [x] [💻] 시작, 중지 버튼 구현
- [x] [💻] 시간 출력부
- [x] [💻] 안내문 출력부
- [x] [📗] HTTP Request를 통한 Header 수신 기능 구현
- [x] [📗] Header부의 date 파싱을 통해 한국 표준시로 변경하고 출력
- [x] [📗] 시작 버튼 클릭시 HttpRequest는 중지시까지 background 실행
- [x] [📗] 중지 버튼 클릭시 실행중인 background 중지
- [ ] [🔨] 오버레이 기능 구현
- [ ] [🔨] 오버레이 뷰 디자인
- [ ] [🔨] 앱 전체적인 디자인 
- [ ] [🔨] 로고 제작
- [ ] [🔒] google AdMob광고 삽입 
