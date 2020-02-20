package com.example.servertime;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import org.w3c.dom.Text;

import static com.example.servertime.R.color.Black;

public class SetWidget extends AppCompatActivity {

    int progresses = 0;
    int selColor = 0;
    int selColorT = 0;
    private View widview;
    TextView PopView;
    Spinner spinner;
    Spinner TimeSpinner;
    RelativeLayout mpopView;
    TextView widSize;
    public static Activity activity;        //static에서 종료하기위해
    private static RewardedAd rewardedAd;          //애드몹 보상형 광고
    private AdView mAdView;                     //구글 애드몹 배너형 광고

    TimePicker mTimePicker;
    Spinner mSecondPicker;
    String Hour;
    String Minute;
    String Second;
    String scheduleTime;
    TextView SchTime;
    TextView exTime;
    Button adbtn;
    Button schbtn;

    static RewardedAdLoadCallback adLoadCallback;
    static int adloadFlag =0;      //로드면 1 아니면 0


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_widget);

        activity = this;      //static에서 종료하기위해

        rewardedAd = new RewardedAd(this,
                "ca-app-pub-3940256099942544/5224354917");
        //보상형 광고 로드
        adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
            }

            @Override
            public void onRewardedAdFailedToLoad(int errorCode) {
                // Ad failed to load.
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);


        //구글 배너 애드몹
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        widSize = (TextView) findViewById(R.id.WidSize);
        widview = (View) MyService.mView.findViewById(R.id.view);

        SeekBar sb = (SeekBar) findViewById(R.id.seekBar);

        //스피너 제어 부
        //위젯 색상선택 스피너
        spinner = (Spinner) findViewById(R.id.spinner);

        String[] colorList = new String[5];
        colorList[0] = "White";
        colorList[1] = "Black";
        colorList[2] = "Red";
        colorList[3] = "Blue";
        colorList[4] = "Pink";

        //using ArrayAdapter
        ArrayAdapter spinnerAdapter;
        spinnerAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, colorList);
        spinner.setAdapter(spinnerAdapter);

        //event listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String aa = spinner.getSelectedItem().toString();
                selColor = spinner.getSelectedItemPosition();
                MyService.selColor = selColor;

                switch (selColor) {
                    case 0: {
                        findViewById(R.id.Color_image).setBackground(
                                getResources().getDrawable(R.drawable.myshape_white)
                        );
                        Toast.makeText(SetWidget.this, "selected White", Toast.LENGTH_SHORT).show();
                        widview.setBackgroundResource(R.color.White);
                        break;
                    }
                    case 1: {
                        findViewById(R.id.Color_image).setBackground(
                                getResources().getDrawable(R.drawable.myshape_black)
                        );

                        widview.setBackgroundResource(R.color.Black);
                        Toast.makeText(SetWidget.this, "selected Black", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case 2: {
                        findViewById(R.id.Color_image).setBackground(
                                getResources().getDrawable(R.drawable.myshape_red)
                        );
                        Toast.makeText(SetWidget.this, "selected Red", Toast.LENGTH_SHORT).show();
                        widview.setBackgroundResource(R.color.Red);
                        break;
                    }
                    case 3: {
                        findViewById(R.id.Color_image).setBackground(
                                getResources().getDrawable(R.drawable.myshape_blue)
                        );
                        Toast.makeText(SetWidget.this, "selected Blue", Toast.LENGTH_SHORT).show();
                        widview.setBackgroundResource(R.color.Blue);
                        break;
                    }
                    case 4: {
                        findViewById(R.id.Color_image).setBackground(
                                getResources().getDrawable(R.drawable.myshape_pink)
                        );
                        Toast.makeText(SetWidget.this, "selected Pink", Toast.LENGTH_SHORT).show();
                        widview.setBackgroundResource(R.color.Pink);
                        break;
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (MyService.selColor == 0 && MyService.progress == 0) {          //셋팅 없이 초기값인 경우

            sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                public void onStopTrackingTouch(SeekBar seekBar) {
                    MyService.progress = progresses;
                    mpopView.setLayoutParams(new WindowManager.LayoutParams(PopView.getWidth() + 5, PopView.getHeight()));
                    widview.setLayoutParams(new RelativeLayout.LayoutParams(PopView.getWidth() + 20, PopView.getHeight() + 5));
                }

                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {
                    widSize.setText("위젯 크기 : " + progress);
                    progresses = progress;

                    ViewGroup.LayoutParams wid = widview.getLayoutParams();
                    mpopView = (RelativeLayout) MyService.mView.findViewById(R.id.mPopView);

                    PopView = (TextView) MyService.mView.findViewById(R.id.textView); //오버레이
                    PopView.setTextSize(24 + progress);

                    mpopView.setLayoutParams(new WindowManager.LayoutParams(PopView.getWidth() + 5, PopView.getHeight()));
                    widview.setLayoutParams(new RelativeLayout.LayoutParams(PopView.getWidth() + 20, PopView.getHeight() + 5));
                }
            });


        } else {       //새롭게 정한 설정이 있는경우

            //System.out.println("selected set------");
            widSize.setText("위젯 크기 : " + MyService.progress);
            sb.setProgress(MyService.progress);

            sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                public void onStopTrackingTouch(SeekBar seekBar) {
                    MyService.progress = progresses;
                    mpopView.setLayoutParams(new WindowManager.LayoutParams(PopView.getWidth() + 5, PopView.getHeight()));
                    widview.setLayoutParams(new RelativeLayout.LayoutParams(PopView.getWidth() + 20, PopView.getHeight() + 5));
                }

                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {
                    widSize.setText("위젯 크기 : " + progress);
                    progresses = progress;
                    MyService.progress = progress;
                    widview = (View) MyService.mView.findViewById(R.id.view);
                    ViewGroup.LayoutParams wid = widview.getLayoutParams();
                    mpopView = (RelativeLayout) MyService.mView.findViewById(R.id.mPopView);

                    PopView = (TextView) MyService.mView.findViewById(R.id.textView); //오버레이
                    PopView.setTextSize(24 + progress);

                    mpopView.setLayoutParams(new WindowManager.LayoutParams(PopView.getWidth() + 5, PopView.getHeight()));
                    widview.setLayoutParams(new RelativeLayout.LayoutParams(PopView.getWidth() + 20, PopView.getHeight() + 5));
                }
            });

            spinner.setSelection(MyService.selColor);
        }

        mTimePicker = (TimePicker) findViewById(R.id.TiemPicker);       //오전 오후 스피너
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Hour = mTimePicker.getHour() + "";
            Minute = mTimePicker.getMinute() + "";
        } else {
            Hour = mTimePicker.getCurrentHour() + "";
            Minute = mTimePicker.getCurrentMinute() + "";
        }


        TimeSpinner = (Spinner) findViewById(R.id.TimeSpinner); //시간초용 스피너

        String[] secondList = new String[12];
        secondList[0] = "00";
        secondList[1] = "05";
        secondList[2] = "10";
        secondList[3] = "15";
        secondList[4] = "20";
        secondList[5] = "25";
        secondList[6] = "30";
        secondList[7] = "35";
        secondList[8] = "40";
        secondList[9] = "45";
        secondList[10] = "50";
        secondList[11] = "55";

        //using ArrayAdapter
        ArrayAdapter TimeSpinnerAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, secondList);
        TimeSpinner.setAdapter(TimeSpinnerAdapter);

        exTime = (TextView) findViewById(R.id.ex_Time);
        SchTime = (TextView) findViewById(R.id.text_SchTime);

        System.out.println("-------------//" + MainActivity.ScheduleFlag);
        if (MainActivity.ScheduleFlag == 1) {
            String aa = MainActivity.ScheduleTime;
            SchTime.setText(aa);
            System.out.println("-------------//셋팅됨///" + MainActivity.ScheduleTime);
            mTimePicker.setVisibility(View.INVISIBLE);
            TimeSpinner.setVisibility(View.INVISIBLE);
            TextView textsecond = (TextView) findViewById(R.id.TextSecond);
            textsecond.setVisibility(View.INVISIBLE);
            exTime.setVisibility(View.VISIBLE);
            SchTime.setVisibility(View.VISIBLE);
        } else {

            TimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Second = parent.getItemAtPosition(position) + "";
                    scheduleTime = Hour + "시 " + Minute + "분 " + Second + "초";
                    SchTime.setText(scheduleTime);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });

            mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                @Override
                public void onTimeChanged(TimePicker timePicker, int hour, int min) {
                    //Toast.makeText(this, "hour : " + hour + ", min : " + min, Toast.LENGTH_LONG).show();
                    Hour = hour + "";
                    Minute = min + "";
                    scheduleTime = Hour + "시 " + Minute + "분 " + Second + "초";
                    SchTime.setText(scheduleTime);
                }
            });
            exTime.setVisibility(View.INVISIBLE);
            SchTime.setVisibility(View.INVISIBLE);

        }


        //버튼 활성화에 대한 부분
        adbtn = (Button) findViewById(R.id.adBtn);
        schbtn = (Button) findViewById(R.id.ScheduleBtn);

        if (MainActivity.reward == 0 && MainActivity.ScheduleFlag == 0) {     //예약도 안되어있고 광고도 안봄
            adbtn.setVisibility(View.VISIBLE);
            schbtn.setVisibility(View.INVISIBLE);
        } else if (MainActivity.reward == 0 && MainActivity.ScheduleFlag == 1) {  //현재 예약이 있음
            adbtn.setVisibility(View.INVISIBLE);
            schbtn.setVisibility(View.INVISIBLE);
        } else if (MainActivity.reward == 1 && MainActivity.ScheduleFlag == 0) {  //광고를 봤지만 예약은 하지않음
            adbtn.setVisibility(View.INVISIBLE);
            schbtn.setVisibility(View.VISIBLE);
        } else {
            Log.d("버튼에러", "button error");
        }


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("SELECTED_COLOR", selColor);
        outState.putInt("SELECTED_SIZE", progresses);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        selColor = savedInstanceState.getInt("SELECTED_COLOR");
        progresses = savedInstanceState.getInt("SELECTED_SIZE");

        spinner.setSelection(selColor);
        //PopView.setTextSize(24 + progresses);

    }


    @Override
    public void onBackPressed() {        //안드로이드 뒤로가기 버튼 눌렸을때
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {       //상단 액션바 뒤로가기 버튼 눌렸을때
        switch (item.getItemId()) {

            case android.R.id.home:
                finish(); //close the activty
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void Schedule(View view) {           //예약하기 버튼이 눌렸을때
        MainActivity.ScheduleFlag = 1;
        MainActivity.ScheduleTime = scheduleTime;
        Button SchBtn = (Button) findViewById(R.id.ScheduleBtn);
        SchBtn.setVisibility(View.INVISIBLE);
        MainActivity.reward = 0;
        Toast.makeText(this, scheduleTime + "에 종료 예약 되었습니다.", Toast.LENGTH_SHORT).show();
        mTimePicker.setVisibility(View.INVISIBLE);
        TimeSpinner.setVisibility(View.INVISIBLE);
        TextView textsecond = (TextView) findViewById(R.id.TextSecond);
        textsecond.setVisibility(View.INVISIBLE);
        exTime.setVisibility(View.VISIBLE);
        SchTime.setVisibility(View.VISIBLE);

    }

    public void Adview(View view) {
        if (rewardedAd.isLoaded()) {
            Activity activityContext = this;
            RewardedAdCallback adCallback = new RewardedAdCallback() {
                public void onRewardedAdOpened() {
                    // Ad opened.
                    adloadFlag=0;
                }

                public void onRewardedAdClosed() {
                    // Ad closed.

                }

                public void onUserEarnedReward(@NonNull RewardItem reward) {
                    // User earned reward.
                    MainActivity.reward = 1;
                    adbtn.setVisibility(View.INVISIBLE);
                    schbtn.setVisibility(View.VISIBLE);

                }

                public void onRewardedAdFailedToShow(int errorCode) {
                    // Ad failed to display

                }
            };
            rewardedAd.show(activityContext, adCallback);
        } else {
            Log.d("TAG", "The rewarded ad wasn't loaded yet.");
            Toast.makeText(this, "광고를 로드중에 있습니다.\n잠시후 로딩이 끝납니다.", Toast.LENGTH_LONG).show();
            //onRewardedAdClosed();
        }
        if (adloadFlag == 1){

        }else {
            onRewardedAdClosed();
            adloadFlag=1;
        }
    }


    public static RewardedAd createAndLoadRewardedAd() {
        RewardedAd rewardedAd = new RewardedAd(activity,
                "ca-app-pub-3940256099942544/5224354917");
        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
            }

            @Override
            public void onRewardedAdFailedToLoad(int errorCode) {
                // Ad failed to load.
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
        return rewardedAd;
    }

    //@Override
    public static void onRewardedAdClosed() {
        rewardedAd = createAndLoadRewardedAd();
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
        adloadFlag=1;
    }
}