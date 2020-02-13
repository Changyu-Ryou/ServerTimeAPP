package com.example.servertime;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private int checker = 3;      //http 헤더 받아오는 백그라운드 작동 ( 0=실행 , 1=중지, 초기설정 = 3 )
    private String url = "";
    TextView explain;
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        explain = (TextView) findViewById(R.id.Explain);
    }
    @Override
    public void onBackPressed()
    {
        handler.removeMessages(0); //Handler 종료

        Toast.makeText(getApplicationContext(), "종료", Toast.LENGTH_SHORT).show();

        moveTaskToBack(true);
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    protected void onResume() {
        super.onResume();
        NetworkTask networkTask = new NetworkTask();
        networkTask.execute();
    }

    public void startBtn(View view) throws IOException, ParseException {

        String http = "https://";
        String nothing = "";

        EditText urlText = (EditText) findViewById(R.id.UrlText);   //editText값 가져오기
        url = urlText.getText().toString();    // URL 설정

        if (url.equals(http) || url.equals(nothing)) {
            //Toast.makeText(getApplicationContext(), "입력하신 주소가 정확하지 않습니다. 다시 입력해주세요", Toast.LENGTH_SHORT).show();
            explain.setText("서버 시간이 아래에 표시됩니다.");
            return;
        }

        //TextView explain = (TextView) findViewById(R.id.Explain);
        explain.setText(url+"의 서버시간");
        checker = 0;
        //onResume();

        //final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (checker == 0) {
                    NetworkTask networkTask = new NetworkTask();
                    networkTask.execute();
                    //onResume();
                    handler.postDelayed(this, 100);      //delayMillis마다 반복
                }
            }
        }, 1000);

    }

    public void stopBtn(View view) {
        checker = 1;      //resume 중지시키기
        handler.removeCallbacks(null);
        //TextView explain = (TextView) findViewById(R.id.Explain);
        explain.setText("서버 시간이 아래에 표시됩니다.");
        TextView tv_output = (TextView) findViewById(R.id.tv_outPut);
        tv_output.setText("");
    }


    public class NetworkTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {

            if(checker==1){
                //Toast.makeText(getApplicationContext(), "백그라운드 실행이 중지되었습니다.", Toast.LENGTH_SHORT).show();
                return null;
            }

            SimpleDateFormat format = new SimpleDateFormat("E, d MMM yyyy k:m:s z", Locale.US);
            SimpleDateFormat formatChange = new SimpleDateFormat("yyyy년 M월 d일\nkk시 mm분 ss초", Locale.KOREA);
            SimpleDateFormat formatCheck = new SimpleDateFormat("mm", Locale.KOREA);
            SimpleDateFormat formatKorea = new SimpleDateFormat("E, d MMM yyyy k:m:s z", Locale.KOREA);
            String context = "";
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

                            if(formatCheck.format(serverTime)==null || formatCheck.format(serverTime)==""){
                                Toast.makeText(getApplicationContext(), "서버 시간을 가져올 수 없는 주소입니다.\n주소를 다시 확인해 주세요", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            System.out.println(value);

                            System.out.println(formatKorea.format(serverTime));
                            //context = formatKorea.format(serverTime);
                            context = formatChange.format(serverTime);
                            System.out.println("===============================");
                        }
                    }
                }
/*
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

                TextView tv_outPut = (TextView) findViewById(R.id.tv_outPut);
                tv_outPut.setText(context);
                System.out.println("done---");
            } catch (
                    Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }


}