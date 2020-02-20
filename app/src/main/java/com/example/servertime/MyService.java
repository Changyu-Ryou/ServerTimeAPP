package com.example.servertime;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class MyService extends Service {

    //private TextView mPopupView;
    private float START_X, START_Y;
    private int PREV_X, PREV_Y;
    private int MAX_X = -1, MAX_Y = -1;

    static Context MainContext;

    private long btnPressTime = 0;        //더블클릭 확인용 변수
    static WindowManager wm;
    static View mView;
    static WindowManager.LayoutParams params;
    static TextView tView;
    static int widFlag = 3;     //위젯 백그라운드 작동 ( 0=실행 , 1=중지, 초기설정 = 3 )


    static int progress=0;      //위젯 크기 설정 변수
    static int selColor=0;      //위젯 색상 설정 변수



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        mView = inflate.inflate(R.layout.view_in_service, null);
        //팝업창에 대한 레이아웃설정
        params = new WindowManager.LayoutParams(
                /*ViewGroup.LayoutParams.MATCH_PARENT*/ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,        //WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.LEFT | Gravity.TOP;
        wm.addView(mView, params);


        //뷰 터치 리스너
        tView = (TextView) mView.findViewById(R.id.textView);

        //final View view = (View) mView.findViewById(R.id.view);
        final View view = (View) mView.findViewById(R.id.view);
        view.setOnLongClickListener(mViewLongClickListener);
        view.setOnClickListener(mViewClickListener);
        view.setOnTouchListener(mViewTouchListener);


    }

    public static void startWidget(Context context) {
        widFlag = 0;
        String widgetTime = MainActivity.gettime();
        String oriText = "시간이 표시됩니다.";
        MainContext = context;
        try {
            //tView.setText("시간표기");
            mHandler.sendEmptyMessageDelayed(0, 100);
        } catch (Exception e) {
            System.out.println("waitLoading() 오류");
        }

    }



    static Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (widFlag == 1) {
                mHandler.removeMessages(0);         //핸들러 종료
            }
            String widgetTime = MainActivity.gettime();
            tView.setText(widgetTime);
            System.out.println("위젯 스레드 작동중");
            /*
            if(ScheduleFlag==1){
                if(widgetTime.equals(ScheduleTime)){
                        //MainActivity.stopService();
                    //wm.removeView(mView);
                    //mView=null;
                    //wm = null;
                        //activity.finish();
                    widFlag = 1;
                    mHandler.removeMessages(0);
                }
            }*/

            // 메세지를 처리하고 또다시 핸들러에 메세지 전달 (1000ms 지연)
            mHandler.sendEmptyMessageDelayed(0, 100);
        }
    };

    public static void stopWidget() {
        widFlag = 1;
        mHandler.removeMessages(0);
    }


    private View.OnLongClickListener mViewLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            Toast.makeText(MyService.this, "서버시간 앱이 열립니다.\n 잠시만 기다려주세요",
                    Toast.LENGTH_SHORT).show();
            SetWidget.activity.finish();        //위젯세팅 액티비티가 열려있다면 종료
            Intent intent = getPackageManager().getLaunchIntentForPackage("com.example.servertime");
            //SetWidget.Destroy();

            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

            return false;
        }

    };


    private View.OnClickListener mViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (System.currentTimeMillis() > btnPressTime + 1000) {
                btnPressTime = System.currentTimeMillis();
                return;
            }
            if (System.currentTimeMillis() <= btnPressTime + 1000) {    //서비스 시작
                Toast.makeText(MyService.this, "서버시간 앱이 열립니다.\n 잠시만 기다려주세요",
                        Toast.LENGTH_SHORT).show();
                SetWidget.activity.finish();      //위젯세팅 액티비티가 열려있다면 종료
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.example.servertime");
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        }

    };


    private View.OnTouchListener mViewTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (MAX_X == -1)
                        setMaxPosition();
                    START_X = event.getRawX();
                    START_Y = event.getRawY();
                    PREV_X = params.x;
                    PREV_Y = params.y;
                    break;
                case MotionEvent.ACTION_MOVE:
                    int x = (int) (event.getRawX() - START_X);
                    int y = (int) (event.getRawY() - START_Y);

                    params.x = PREV_X + x;
                    params.y = PREV_Y + y;

                    optimizePosition();
                    wm.updateViewLayout(mView, params);
                    break;
            }
            return false;
        }
    };

    private void setMaxPosition() {
        DisplayMetrics matrix = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(matrix);

        MAX_X = matrix.widthPixels - mView.getWidth();
        MAX_Y = matrix.heightPixels - mView.getHeight();
    }

    private void optimizePosition() {
        if (params.x > MAX_X) params.x = MAX_X;
        if (params.y > MAX_Y) params.y = MAX_Y;
        if (params.x < 0) params.x = 0;
        if (params.y < 0) params.y = 0;
    }

    @Override
    public void onDestroy() {               //MainActivity 꺼지면 다 종료
        super.onDestroy();
        if (wm != null) {
            if (mView != null) {
                wm.removeView(mView);
                mView = null;
            }
            wm = null;
        }
    }
}