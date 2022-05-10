package com.winter.library;

import android.content.Context;
import android.content.DialogInterface;

import com.winter.library.dialog.DatePickerDialog;
import com.winter.library.wheelview.listener.OnItemSelectedListener;

import java.util.Calendar;

public class XPicker {

    private PickerType pickerType;
    private PickerSelectValueListener pickerSelectorListener;
    private DialogInterface.OnKeyListener keyEventListener;
    private int startYear;
    private int endYear;
    private Calendar currentDate;
    private Context context;
    private int resId;
    private CustomListener customListener;

    public XPicker(Builder builder){
        this.pickerType = builder.pickerType;
        this.pickerSelectorListener = builder.pickerSelectorListener;
        this.keyEventListener = builder.keyEventListener;
        this.startYear = builder.startYear;
        this.endYear = builder.endYear;
        this.context = builder.context;
        this.resId = builder.resId;
        this.customListener = builder.customListener;
    }

    public void show(){
        Calendar calendar = Calendar.getInstance();
        if(pickerType == PickerType.PICKER_DATE){
            new DatePickerDialog.Builder()
                    .setSelectListener(pickerSelectorListener)
                    .setRangeYear(startYear,endYear)
                    .create(context).show();
        }
    }

    public static class Builder{

        private PickerType pickerType;
        private PickerSelectValueListener pickerSelectorListener;
        private DialogInterface.OnKeyListener keyEventListener;
        private int startYear;
        private int endYear;
        private Context context;
        private int resId;
        private CustomListener customListener;

        public Builder setPickerType(PickerType pickerType){
            this.pickerType = pickerType;
            return this;
        }
        public Builder setOptionsResLayout(int resId,CustomListener listener){
            this.resId = resId;
            this.customListener = listener;
            return this;
        }
        public Builder setPickerSelectListener(PickerSelectValueListener listener){
            this.pickerSelectorListener = listener;
            return this;
        }

        public Builder setKeyEventListener(DialogInterface.OnKeyListener listener){
            keyEventListener = listener;
            return this;
        }

        public Builder setRangDate(int start_year,int  end_year){
            this.startYear = start_year;
            this.endYear = end_year;
            return this;
        }

        public XPicker build(Context context){
            this.context = context;
            return new XPicker(this);
        }


    }
}
