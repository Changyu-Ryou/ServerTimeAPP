package com.example.servertime;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import org.w3c.dom.Text;

import static com.example.servertime.R.color.Black;

public class SetWidget extends AppCompatActivity {


    int progresses = 0;
    int selColor = 0;
    int selColorT = 0;
    private View widview;
    TextView PopView;
    Spinner spinner;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_widget);
        final TextView widText = (TextView) MyService.mView.findViewById(R.id.textView);
        View widView = (View) MyService.mView.findViewById(R.id.view);
        //String color=widView.getResources().getString(R.color.White);
        //String color2= widView.getDrawa
        //String oriColor = "#" + Integer.toHexString(ContextCompat.getColor(widV, R.color.White) & 0x00ffffff);

        String color22 = ((ColorDrawable) widView.getBackground()).getColor() + "";
        ;
        String oriColor = "-1";

        SeekBar sb = (SeekBar) findViewById(R.id.seekBar);
        final TextView widSize = (TextView) findViewById(R.id.WidSize);


        //System.out.println("widText.getTextSize()="+widText.getTextSize()+"\ncolor22.equals(oriColor)=="+color22.equals(oriColor)+"-----------------------------------------------");
        if (widText.getTextSize() == 70.0 && color22.equals(oriColor)) {               //켜져있는 위젯이 초기 설정과 같은경우
            sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                public void onStopTrackingTouch(SeekBar seekBar) {
                    System.out.println("***********widText.getTextSize()"+widText.getTextSize());

                }

                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {
                    widSize.setText("위젯 크기 : " + progress);
                    progresses = progress;
                    widview = (View) MyService.mView.findViewById(R.id.view);
                    ViewGroup.LayoutParams wid = widview.getLayoutParams();
                    RelativeLayout mpopView = (RelativeLayout) MyService.mView.findViewById(R.id.mPopView);


                    PopView = (TextView) MyService.mView.findViewById(R.id.textView); //오버레이
                    PopView.setTextSize(24 + progress);

                    mpopView.setLayoutParams(new WindowManager.LayoutParams(PopView.getWidth() + 5, PopView.getHeight()));
                    widview.setLayoutParams(new RelativeLayout.LayoutParams(PopView.getWidth() + 20, PopView.getHeight() + 5));
                }
            });


        } else {          //새롭게 정한 설정이 있는경우

            sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                public void onStopTrackingTouch(SeekBar seekBar) {

                }

                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {
                    widSize.setText("위젯 크기 : " + progress);
                    progresses = progress;
                    widview = (View) MyService.mView.findViewById(R.id.view);
                    ViewGroup.LayoutParams wid = widview.getLayoutParams();
                    RelativeLayout mpopView = (RelativeLayout) MyService.mView.findViewById(R.id.mPopView);

                    //PopView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, widText.getTextSize());

                    //PopView.getTextSize()
                    //PopView = (TextView) MyService.mView.findViewById(R.id.textView); //오버레이
                    //PopView.setTextSize(widText.getTextSize());
                    PopView = (TextView) MyService.mView.findViewById(R.id.textView); //오버레이
                    PopView.setTextSize(24 + progress);

                    mpopView.setLayoutParams(new WindowManager.LayoutParams(PopView.getWidth() + 5, PopView.getHeight()));
                    widview.setLayoutParams(new RelativeLayout.LayoutParams(PopView.getWidth() + 20, PopView.getHeight() + 5));
                }
            });


        }


        //스피너 제어 부

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
                String aa = spinner.getSelectedItem().toString();
                selColor = spinner.getSelectedItemPosition();

                widview = (View) MyService.mView.findViewById(R.id.view);


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
                //Toast.makeText(SetWidget.this, "111111111111="+selColor, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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
        PopView.setTextSize(24 + progresses);

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


}