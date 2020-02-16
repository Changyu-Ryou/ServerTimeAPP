package com.example.servertime;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
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
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static final int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 1;
    private int checker = 3;      //http 헤더 받아오는 백그라운드 작동 ( 0=실행 , 1=중지, 초기설정 = 3 )
    private String url = "";
    int widFlag = 0;        //위젯이 작동하는지 확인 flag  0=오버레이x, 1=오버레이 동작중
    static View mView;      //view_in_service;
    TextView explain;
    TextView tv_outPut;
    TextView widSize;
    final Handler handler = new Handler();
    SeekBar sb;
    private View header;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        explain = (TextView) findViewById(R.id.Explain);
        tv_outPut = (TextView) findViewById(R.id.tv_outPut);
        widSize = (TextView) findViewById(R.id.WidSize);

        header = getLayoutInflater().inflate(R.layout.view_in_service, null, false);


    }


    @Override
    public void onBackPressed() {
        handler.removeMessages(0); //Handler 종료
        Toast.makeText(MainActivity.this, "종료", Toast.LENGTH_SHORT).show();
        moveTaskToBack(true);
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public void pastBtn(View view) {
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
                    NetworkTask networkTask = new NetworkTask();
                    networkTask.execute();

                    handler.postDelayed(this, 100);      //delayMillis마다 무한 반복
                }
            }
        }, 1000);

    }

    //중지 버튼
    public void stopBtn(View view) {
        checker = 1;      //handler 중지시키기
        widFlag = 0;      //위젯 중지시키기
        handler.removeCallbacks(null);      //handler 중지시키기

        explain.setText("서버 시간이 아래에 표시됩니다.");
        TextView tv_output = (TextView) findViewById(R.id.tv_outPut);
        tv_output.setText("");

        //오버레이 중지
        stopService(new Intent(MainActivity.this, MyService.class));

    }


    //위젯 버튼
    public void Overlay(View view) {
        //권한 확인후 인텐트 실행
        if(checker==0) {
            checkPermission();
            widFlag=1;
        }else
            Toast.makeText(MainActivity.this, "아직 시작하지 않으셨습니다.\n왼쪽 시작 버튼을 눌러 시계를 작동시켜주세요", Toast.LENGTH_SHORT).show();


    }

    public void SetWidget(View view) {
        if(widFlag==1)
            startActivity(new Intent(this, SetWidget.class));
        else
            Toast.makeText(MainActivity.this, "아직 위젯이 실행되지 않았습니다.\n위젯 버튼을 눌러 활성화 시켜주세요", Toast.LENGTH_SHORT).show();

    }


    public class NetworkTask extends AsyncTask<Void, Void, String> {
        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(Void... voids) {

            if (checker == 1) {
                //핸들러 remove로 해도 빠르게 중단되지 않아 다시한번 flag를 이용한 백그라운드 종료
                return null;
            }

            SimpleDateFormat format = new SimpleDateFormat("E, d MMM yyyy k:m:s z", Locale.US);
            SimpleDateFormat formatChange = new SimpleDateFormat("yyyy년 M월 d일\nkk시 mm분 ss초", Locale.KOREA);
            SimpleDateFormat formatCheck = new SimpleDateFormat("mm", Locale.KOREA);
            SimpleDateFormat formatForPopUp = new SimpleDateFormat("kk시 mm분 ss초", Locale.KOREA);
            SimpleDateFormat formatKorea = new SimpleDateFormat("E, d MMM yyyy k:m:s z", Locale.KOREA);
            String context = "";        //포멧 데이터를 스트링으로 저장
            String contextForPopUp="";        //팝업창 전용 포멧 데이터를 스트링으로 저장
            try {
                Log.d(this.getClass().getName(), "start the NetworkTask");
                URL obj = new URL(url);
                URLConnection conn = obj.openConnection();
                Map<String, List<String>> map = conn.getHeaderFields();

                System.out.println("\n---------Printing Response Header...\n");

                for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                    if (StringUtils.equals(entry.getKey(), "Date")) {
                        if (entry.getValue().size() > 0) {
                            String value = entry.getValue().get(0);
                            Date serverTime = format.parse(value);

                            if (formatCheck.format(serverTime) == null || formatCheck.format(serverTime) == "") {
                                Toast.makeText(MainActivity.this, "서버 시간을 가져올 수 없는 주소입니다.\n주소를 다시 확인해 주세요", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            System.out.println(value);

                            System.out.println(formatKorea.format(serverTime));
                            //context = formatKorea.format(serverTime);
                            context = formatChange.format(serverTime);
                            contextForPopUp= formatForPopUp.format(serverTime);
                            System.out.println("===============================");
                        }
                    }
                }
/*              //헤더들의 내용을 더 알 수 있음
                System.out.println("\n\n\nGet Response Header By Key ...\n");
                String server = conn.getHeaderField("Server");

                if (server == null) {
                    System.out.println("Key 'Server' is not found!");
                } else {
                    System.out.println("Server - " + server);
                }
                System.out.println("\n Done");
                context+="";
                System.out.println(context +"\n ---");

 */

                //TextView tv_outPut = (TextView) findViewById(R.id.tv_outPut);
                tv_outPut.setText(context);

                if(widFlag==1) {
                    TextView PopView = (TextView) MyService.mView.findViewById(R.id.textView); //오버레이에도 시간표시
                    PopView.setText(contextForPopUp);
                }

                System.out.println("done---");
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
}