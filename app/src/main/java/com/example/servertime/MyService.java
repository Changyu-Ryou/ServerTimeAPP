package com.example.servertime;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class MyService extends Service {

    //private TextView mPopupView;
    WindowManager wm;
    static View mView;
    static WindowManager.LayoutParams params;

    @Override
    public IBinder onBind(Intent intent) { return null; }

    @Override
    public void onCreate() {
        super.onCreate();
        LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);

        //팝업창에 대한 레이아웃설정
        params = new WindowManager.LayoutParams(
                /*ViewGroup.LayoutParams.MATCH_PARENT*/ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,        //WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        |WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        |WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.LEFT | Gravity.TOP;

        mView = inflate.inflate(R.layout.view_in_service, null);



        //뷰 터치 리스너
        //final TextView textView = (TextView) mView.findViewById(R.id.textView);
        //final View view = (View) mView.findViewById(R.id.view);
        final View view = (View) mView.findViewById(R.id.view);
        view.setOnTouchListener(mViewTouchListener);


/*         //버튼 클릭 이벤트
        final ImageButton bt =  (ImageButton) mView.findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt.setImageResource(R.mipmap.ic_launcher_round);
                textView.setText("on click!!");
            }
        });*/
        wm.addView(mView, params);
    }
    private float START_X, START_Y;
    private int PREV_X, PREV_Y;
    private int MAX_X = -1, MAX_Y = -1;



    private View.OnTouchListener mViewTouchListener = new View.OnTouchListener() {
        @Override public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if(MAX_X == -1)
                        setMaxPosition();
                    START_X = event.getRawX();
                    START_Y = event.getRawY();
                    PREV_X = params.x;
                    PREV_Y = params.y;
                    break;
                case MotionEvent.ACTION_MOVE:
                    int x = (int)(event.getRawX() - START_X);
                    int y = (int)(event.getRawY() - START_Y);

                    params.x = PREV_X + x;
                    params.y = PREV_Y + y;

                    optimizePosition();
                    wm.updateViewLayout(mView, params);
                    break;
            }

            return true;
        }
    };


    private void setMaxPosition() {
        DisplayMetrics matrix = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(matrix);

        MAX_X = matrix.widthPixels - mView.getWidth();
        MAX_Y = matrix.heightPixels - mView.getHeight();
    }

    private void optimizePosition() {
        //ÃÖ´ë°ª ³Ñ¾î°¡Áö ¾Ê°Ô ¼³Á¤
        if(params.x > MAX_X) params.x = MAX_X;
        if(params.y > MAX_Y) params.y = MAX_Y;
        if(params.x < 0) params.x = 0;
        if(params.y < 0) params.y = 0;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(wm != null) {
            if(mView != null) {
                wm.removeView(mView);
                mView = null;
            }
            wm = null;
        }
    }
}