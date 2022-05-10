package com.winter.xpicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.winter.library.CustomListener;
import com.winter.library.PickerSelectValueListener;
import com.winter.library.PickerType;
import com.winter.library.XPicker;
import com.winter.library.wheelview.listener.OnItemSelectedListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //年月日选择器
                new XPicker.Builder()
                        .setPickerType(PickerType.PICKER_DATE)
                        .setOptionsResLayout(R.layout.activity_main,new CustomListener() {
                            @Override
                            public void customLayout(View view) {

                            }
                        })
                        .setPickerSelectListener(new PickerSelectValueListener() {
                            @Override
                            public void selectValueResult(int[] values) {
                                for (int value : values) {
                                    Log.d("test","value = "+value);
                                }
                            }
                        })
                        .setKeyEventListener(new DialogInterface.OnKeyListener() {
                            @Override
                            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                                return false;
                            }
                        })
                        .setRangDate(1950,2050)
                        .build(MainActivity.this)
                        .show();
            }
        });


        //时间选择器

        //省市区选择器

        //单项选择器

        //自定义布局选择器
    }

}