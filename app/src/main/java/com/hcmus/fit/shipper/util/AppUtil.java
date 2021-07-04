package com.hcmus.fit.shipper.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AppUtil {
    public static Calendar parseCalendar(String s) {
        String[] arr = s.split("T");

        String[] dates = arr[0].split("-");
        int year = Integer.parseInt(dates[0]);
        int month = Integer.parseInt(dates[1]);
        int day = Integer.parseInt(dates[2]);

        String[] times = arr[1].split(":");
        int hour = Integer.parseInt(times[0]);
        int minute = Integer.parseInt(times[1]);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);
        return calendar;
    }

    public static String getDateString(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH)
                + "/" + calendar.get(Calendar.YEAR);
    }

    public static String getTimeString(Calendar calendar) {
        return calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE);
    }

    public static String convertCurrency(int money) {
        StringBuilder s = new StringBuilder("đ");

        while (money / 1000 > 0) {
            int mod = money % 1000;

            if (mod == 0) {
                s.insert(0, "000");
            } else if (mod < 10) {
                s.insert(0, mod);
                s.insert(0,"00");
            } else if (mod < 100) {
                s.insert(0, mod);
                s.insert(0, "0");
            } else {
                s.insert(0, mod);
            }

            money = money / 1000;

            s.insert(0, ",");
        }

        s.insert(0, money);

        return s.toString();
    }

    public static String roundCurrency(int money) {
        return money / 1000 + "K";
    }

    public static Bitmap roundedCornerBitmap(Bitmap bitmap, float roundPx) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

//    public static Map<String, String> getTimeIncome(int month) {
//        Map<String, String> params = new HashMap<>();
//        int monthA = 1;
//        int monthB = 1;
//
//        switch (month) {
//            case 1:
//                mon
//        }
//    }
}
