package com.example.servertime;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {

    private static final int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 1;
    private static final String STATE_CHECKER = "CHECKER_VALUE";      //체커 값
    private static final String WIDGET_FLAG = "WIDGET_VALUE";       //위젯 플래그 값
    private static final String URL_STATE = "URL_STATE";        //URL 링크 주소
    private static int checker = 3;      //http 헤더 받아오는 백그라운드 작동 ( 0=실행 , 1=중지, 초기설정 = 3 )
    private static int widFlag = 3;      //위젯 백그라운드 작동 ( 0=실행 , 1=중지, 초기설정 = 3 )
    private String url = "";

    static TextView explain;
    static TextView tv_outPut;
    TextView widSize;
    EditText URL_Link;
    String context = "";        //포멧 데이터를 스트링으로 저장
    static final Handler handler = new Handler();

    private AdView mAdView;                     //구글 애드몹

    static String contextForPopUp = "";        //팝업창 전용 포멧 데이터를 스트링으로 저장
    public static Context Context1;                   //다른 액티비티에서 onResume호출용
    String formatForSchedule = "";          //예약종료 확인용 시간 포멧데이터를 스트링으로 저장


    static int ScheduleFlag = 0;         //예약종료설정 되어있는지 확인용 플래그
    static String ScheduleTime = "";     //예약설정 시간
    static int reward = 0;      //리워드 확인

    private final long FINISH_INTERVAL_TIME = 2000;     //두번누르면 종료시키기 위한 변수
    private long backPressedTime = 0;           //두번누르면 종료시키기 위한 변수


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context1 = this;
        ScheduleFlag = 0;         //예약종료설정 되어있는지 확인용 플래그 초기화
        ScheduleTime = "";        //예약종료시간 초기화
        reward = 0;               //리워드 초기화


        //구글 애드몹
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //init
        explain = (TextView) findViewById(R.id.Explain);
        tv_outPut = (TextView) findViewById(R.id.tv_outPut);
        widSize = (TextView) findViewById(R.id.WidSize);
        URL_Link = (EditText) findViewById(R.id.UrlText);

        //header.findViewById(R.id.textView);

        if (savedInstanceState != null) {
            checker = savedInstanceState.getInt(STATE_CHECKER);
            widFlag = savedInstanceState.getInt(WIDGET_FLAG);

            URL_Link.setText(savedInstanceState.getString(URL_STATE));
        }

    }

    //생명주기 관리

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_CHECKER, checker);
        outState.putInt(WIDGET_FLAG, widFlag);

        String URL = URL_Link.getText().toString();
        outState.putString(URL_STATE, URL);


        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        checker = savedInstanceState.getInt(STATE_CHECKER);
        widFlag = savedInstanceState.getInt(WIDGET_FLAG);

        URL_Link.setText(savedInstanceState.getString(URL_STATE));

    }


    public static String gettime() {

        return contextForPopUp;
    }

    @Override
    public void onDestroy() {               //MainActivity 꺼지면 다 종료
        super.onDestroy();
        handler.removeMessages(0); //Handler 종료
        Toast.makeText(MainActivity.this, "종료", Toast.LENGTH_SHORT).show();
        moveTaskToBack(true);
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }


    @Override
    public void onBackPressed() {

        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {

            handler.removeMessages(0); //Handler 종료
            Toast.makeText(MainActivity.this, "종료", Toast.LENGTH_SHORT).show();
            moveTaskToBack(true);
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
            super.onBackPressed();
        } else {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "한번 더 누르시면 앱이 종료됩니다.\n위젯 이용을 원하시면 홈버튼을 눌러주세요", Toast.LENGTH_SHORT).show();
        }


    }

    public void pastBtn(View view) {        //붙여넣기 버튼
        //클립보드 관리자 객체를 가져옴
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        //클립보드에 값이 없으면
        if (cm.hasPrimaryClip() == false) {
            Toast.makeText(MainActivity.this, "clipboard empty", Toast.LENGTH_SHORT).show();
            return;
        }

        //클립보드의 값이 텍스트가 아니면
        if (cm.getPrimaryClipDescription().hasMimeType(
                ClipDescription.MIMETYPE_TEXT_PLAIN) == false) {
            Toast.makeText(MainActivity.this, "clip is not text", Toast.LENGTH_SHORT).show();
            return;
        }
        //클립데이터를 읽는다.
        ClipData clip = cm.getPrimaryClip();
        ClipData.Item item = clip.getItemAt(0);//첫번째 데이터를 가져옴
        TextView urlText = (TextView) findViewById(R.id.UrlText);
        urlText.setText(item.getText());//텍스트뷰에 세팅해줌
    }


    //시작버튼
    public void startBtn(View view) throws IOException, ParseException {

        String http = "https://";
        String nothing = "";

        EditText urlText = (EditText) findViewById(R.id.UrlText);   //editText값 가져오기
        url = urlText.getText().toString();    // URL 설정

        if (url.equals(http) || url.equals(nothing)) {
            Toast.makeText(MainActivity.this, "입력하신 주소가 정확하지 않습니다. \n다시 입력해주세요", Toast.LENGTH_SHORT).show();
            explain.setText("서버 시간이 아래에 표시됩니다.");
            return;
        }

        explain.setText(url + "의 서버시간");
        checker = 0;

        //Handler 부분
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (checker == 0) {
                    GetHeader getHeader = new GetHeader();
                    getHeader.execute();

                    handler.postDelayed(this, 100);      //delayMillis마다 무한 반복
                }
            }
        }, 1000);

    }

    //중지 버튼
    public void stopBtn(View view) {
        MyService.progress = 0;
        MyService.selColor = 0;
        ScheduleFlag = 0;


        checker = 1;      //handler 중지시키기
        widFlag = 1;      //위젯 중지시키기
        handler.removeCallbacks(null);      //handler 중지시키기

        MyService.stopWidget();

        explain.setText("서버 시간이 아래에 표시됩니다.");
        TextView tv_output = (TextView) findViewById(R.id.tv_outPut);
        tv_output.setText("");

        //오버레이 중지
        stopService(new Intent(MainActivity.this, MyService.class));

    }


    void stopService() {
        MyService.progress = 0;
        MyService.selColor = 0;
        ScheduleFlag = 0;


        checker = 1;      //handler 중지시키기
        widFlag = 1;      //위젯 중지시키기
        handler.removeCallbacks(null);      //handler 중지시키기

        MyService.stopWidget();
        SetWidget.activity.finish();        //세팅위젯 액티비티 종료

        explain.setText("서버 시간이 아래에 표시됩니다.");
        tv_outPut.setText("");

        //오버레이 중지
        stopService(new Intent(this, MyService.class));

    }


    //위젯 버튼
    public void Overlay(View view) {
        //권한 확인후 인텐트 실행

        if (checker == 0) {
            MyService.startWidget(this);
            checkPermission();
            widFlag = 0;
            Toast.makeText(MainActivity.this, "위젯을 더블클릭하거나 길게 누르면.\n앱 메인화면으로 이동합니다", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "서버시간이 작동하고 있지 않습니다.\n시작 버튼을 눌러 작동시켜주세요", Toast.LENGTH_SHORT).show();
        }
    }

    public void SetWidget(View view) {
        if (widFlag == 0)
            startActivity(new Intent(this, SetWidget.class));


        else {
            Toast.makeText(MainActivity.this, "위젯이 켜져있지 않습니다.\n위젯 버튼을 눌러 작동시켜주세요", Toast.LENGTH_SHORT).show();
        }

    }


    public class GetHeader extends AsyncTask<Void, Void, String> {
        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(Void... voids) {

            if (checker == 1) {
                //핸들러 remove로 해도 빠르게 중단되지 않아 다시한번 flag를 이용한 백그라운드 종료
                return null;
            }
            //PopView= (TextView)MyService.mView.findViewById(R.id.textView);

            SimpleDateFormat format = new SimpleDateFormat("E, d MMM yyyy k:m:s z", Locale.US);
            SimpleDateFormat formatChange = new SimpleDateFormat("yyyy년 M월 d일\nkk시 mm분 ss초", Locale.KOREA);
            SimpleDateFormat formatCheck = new SimpleDateFormat("mm", Locale.KOREA);
            SimpleDateFormat formatForPopUp = new SimpleDateFormat("kk시 mm분 ss초", Locale.KOREA);
            SimpleDateFormat formatForSche = new SimpleDateFormat("k시 m분 ss초", Locale.KOREA);


            try {
                URL obj = new URL(url);
                URLConnection conn = obj.openConnection();
                Map<String, List<String>> map = conn.getHeaderFields();

                for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                    if (StringUtils.equals(entry.getKey(), "Date")) {
                        if (entry.getValue().size() > 0) {
                            String value = entry.getValue().get(0);
                            Date serverTime = format.parse(value);

                            if (formatCheck.format(serverTime) == null || formatCheck.format(serverTime) == "") {
                                Toast.makeText(MainActivity.this, "서버 시간을 가져올 수 없는 주소입니다.\n주소를 다시 확인해 주세요", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            context = formatChange.format(serverTime);
                            contextForPopUp = formatForPopUp.format(serverTime);
                            formatForSchedule = formatForSche.format(serverTime);
                        }
                    }
                }

                tv_outPut.setText(context);
                if (ScheduleFlag == 1)     //예약종료설정 되어있는지 확인용 플래그(1=설정됨, 0=미설정)
                    if (ScheduleTime.equals(formatForSchedule)) {
                        stopService();
                    }

            } catch (
                    Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    //오버레이 권한이 있는지 체크
    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {   // 마시멜로우 이상일 경우
            if (!Settings.canDrawOverlays(this)) {              // 체크
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
            } else {
                startService(new Intent(MainActivity.this, MyService.class));
            }
        } else {
            startService(new Intent(MainActivity.this, MyService.class));
        }
    }


    //결과 처리
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            if (!Settings.canDrawOverlays(this)) {
                // TODO 동의를 얻지 못했을 경우의 처리

            } else {
                startService(new Intent(MainActivity.this, MyService.class));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_actions, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                startActivity(new Intent(this, PopupGuideActivity.class));
                break;
            default:
                break;
        }
        return true;
    }
}