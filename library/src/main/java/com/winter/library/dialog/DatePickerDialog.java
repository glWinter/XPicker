package com.winter.library.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.winter.library.PickerSelectValueListener;
import com.winter.library.R;
import com.winter.library.utils.App;
import com.winter.library.wheelview.adapter.ArrayWheelAdapter;
import com.winter.library.wheelview.listener.OnItemSelectedListener;
import com.winter.library.wheelview.view.WheelView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class DatePickerDialog extends Dialog {
    private static int MIN_YEAR = 1900;
    private static int MAX_YEAR = 2100;

    public DatePickerDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    private static final class Params {
        private WheelView loopYear, loopMonth, loopDay;
        private PickerSelectValueListener callback;
    }

    public static class Builder {
        private final DatePickerDialog.Params params;

        private Integer minYear;
        private Integer maxYear;
        private Integer selectYear;
        private Integer selectMonth;
        private Integer selectDay;
        private Integer minMonth;
        private Integer maxMonth;
        private Integer minDay;
        private Integer maxDay;
        List<String> monthList = new ArrayList<>();
        List<String> leapYearFebruaryDay = new ArrayList<>();
        List<String> normalYearFebruaryDay = new ArrayList<>();
        List<String> longMonthDay = new ArrayList<>();
        List<String> shortMonthDay = new ArrayList<>();
        List<String> yearList = new ArrayList<>();
        List<String> currentList = new ArrayList<>();
        public Builder() {
            params = new DatePickerDialog.Params();
            monthList = d(1, 12);
            longMonthDay = d(1, 31);
            shortMonthDay = d(1, 30);
            yearList = d(MIN_YEAR, MAX_YEAR - MIN_YEAR + 1);
            leapYearFebruaryDay = d(1, 29);
            normalYearFebruaryDay = d(1, 28);

        }

        public Builder setRangeYear(int min_year, int max_year) {
            MIN_YEAR = min_year;
            MAX_YEAR = max_year;
            return this;
        }
        public Builder setSelectListener(PickerSelectValueListener listener){
            params.callback = listener;
            return this;
        }
        private boolean correctionDate(String y) {
            int year = Integer.parseInt(y);
            return ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0);
        }

        private boolean isLongMonth(int num) {
            return num == 0 || num == 2 || num == 4 || num == 6 || num == 7 || num == 9 || num == 11;
        }

        private final int[] getCurrDateValues() {
            int currYear = Integer.parseInt(yearList.get(params.loopYear.getCurrentItem()));
            int currMonth = Integer.parseInt(monthList.get(params.loopMonth.getCurrentItem()));
            int currDay = Integer.parseInt(currentList.get(params.loopDay.getCurrentItem()));
            return new int[]{currYear, currMonth, currDay};
        }

        public DatePickerDialog create(Context context) {
            final DatePickerDialog dialog = new DatePickerDialog(context, R.style.Theme_Light_NoTitle_Dialog);
            View view = LayoutInflater.from(context).inflate(R.layout.layout_picker_date, null);

            view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            Calendar c = Calendar.getInstance();

            final WheelView wheelViewYear = view.findViewById(R.id.loop_year);
            params.loopYear = wheelViewYear;
            wheelViewYear.setAdapter(new ArrayWheelAdapter(yearList));
            wheelViewYear.setCyclic(false);

            final WheelView wheelViewMonth = view.findViewById(R.id.loop_month);
            params.loopMonth = wheelViewMonth;
            wheelViewMonth.setAdapter(new ArrayWheelAdapter(monthList));
            wheelViewMonth.setCyclic(false);

            final WheelView wheelViewDay = view.findViewById(R.id.loop_day);
            params.loopDay = wheelViewDay;
            wheelViewDay.setAdapter(new ArrayWheelAdapter(longMonthDay));
            wheelViewDay.setCyclic(false);

            wheelViewMonth.setOnItemSelectedListener(index -> {
                if (isLongMonth(index)) {
                    wheelViewDay.setAdapter(new ArrayWheelAdapter(longMonthDay));
                    currentList = longMonthDay;
                } else {
                    if (index == 1) {
                        if (correctionDate(yearList.get(wheelViewYear.getCurrentItem()))) {
                            wheelViewDay.setAdapter(new ArrayWheelAdapter(leapYearFebruaryDay));
                            currentList = leapYearFebruaryDay;
                        } else {
                            wheelViewDay.setAdapter(new ArrayWheelAdapter(normalYearFebruaryDay));
                            currentList = normalYearFebruaryDay;
                        }
                    }else{
                        wheelViewDay.setAdapter(new ArrayWheelAdapter(shortMonthDay));
                        currentList = shortMonthDay;
                    }
                }

            });
            wheelViewYear.setOnItemSelectedListener(index -> {
                if (wheelViewMonth.getCurrentItem() == 1) {
                    if (correctionDate(yearList.get(index))) {
                        wheelViewDay.setAdapter(new ArrayWheelAdapter(leapYearFebruaryDay));
                        currentList = leapYearFebruaryDay;
                    } else {
                        wheelViewDay.setAdapter(new ArrayWheelAdapter(normalYearFebruaryDay));
                        currentList = normalYearFebruaryDay;
                    }
                }
            });


            view.findViewById(R.id.tx_finish).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    params.callback.selectValueResult(getCurrDateValues());
                }
            });


            Window win = dialog.getWindow();
            win.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

            win.setAttributes(lp);
            win.setGravity(Gravity.BOTTOM);
            win.setWindowAnimations(R.style.Animation_Bottom_Rising);

            dialog.setContentView(view);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setCancelable(true);


            return dialog;
        }
    }

    private static List<String> d(int startNum, int count) {
        String[] values = new String[count];
        for (int i = startNum; i < startNum + count; i++) {
            String tempValue = (i < 10 ? "0" : "") + i;
            values[i - startNum] = tempValue;
        }
        return Arrays.asList(values);
    }


}

