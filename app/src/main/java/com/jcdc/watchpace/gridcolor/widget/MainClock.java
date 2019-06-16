package com.jcdc.watchpace.gridcolor.widget;

import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;

import com.ingenic.iwds.slpt.view.digital.SlptWeekView;
import com.jcdc.watchpace.gridcolor.R;
import com.jcdc.watchpace.gridcolor.resource.ResourceManager;
import com.huami.watch.watchface.util.Util;
import com.ingenic.iwds.slpt.view.core.SlptLinearLayout;
import com.ingenic.iwds.slpt.view.core.SlptPictureView;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;
import com.ingenic.iwds.slpt.view.digital.SlptHourHView;
import com.ingenic.iwds.slpt.view.digital.SlptHourLView;
import com.ingenic.iwds.slpt.view.digital.SlptMinuteHView;
import com.ingenic.iwds.slpt.view.digital.SlptMinuteLView;
import com.ingenic.iwds.slpt.view.utils.SimpleFile;

import java.util.ArrayList;
import java.util.List;

public class MainClock extends DigitalClockWidget {

    private Context context;

    private TextPaint hourFont, minutesFont, secondsFont, indicatorFont, dateFont, dayFont, weekdayFont, monthFont, yearFont;
    private Bitmap background;

    private int temp_month;

    private String[] digitalNums = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private String[] months = {"JANU", "FEBR", "MARC", "APRI", "MAY ", "JUNE", "JULY", "AUGU", "SEPT", "OCT", "NOVE", "DECE"};
    private String[] days = {"SUND", "MOND", "TUES", "WEDN", "THUR", "FRID", "SATU"};
    private String[] dates = { "",
              " 1ST", " 2ND", " 3RD", " 4TH", " 5TH", " 6TH", " 7TH", " 8TH", " 9TH", "10TH"
            , "11TH", "12TH", "13TH", "14TH", "15TH", "16TH", "17TH", "18TH", "19TH", "20TH"
            , "21ST", "22ND", "23RD", "24TH", "25TH", "26TH", "27TH", "28TH", "29TH", "30TH"
            , "31ST"
    };

    public MainClock(Context context) {
        this.context = context;
    }

    @Override
    public void init(Service service) {
        this.background = Util.decodeImage(service.getResources(),"background.png");

        this.dayFont = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.dayFont.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
        this.dayFont.setTextSize(context.getResources().getDimension(R.dimen.font_size));
        this.dayFont.setColor(Color.WHITE);
        this.dayFont.setTextAlign(Paint.Align.LEFT);

        this.hourFont = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.hourFont.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
        this.hourFont.setTextSize(context.getResources().getDimension(R.dimen.font_size));
        this.hourFont.setColor(Color.RED);
        this.hourFont.setTextAlign(Paint.Align.LEFT);

        this.minutesFont = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.minutesFont.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
        this.minutesFont.setTextSize(context.getResources().getDimension(R.dimen.font_size));
        this.minutesFont.setColor(Color.RED);
        this.minutesFont.setTextAlign(Paint.Align.LEFT);

        this.secondsFont = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.secondsFont.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
        this.secondsFont.setTextSize(context.getResources().getDimension(R.dimen.font_size));
        this.secondsFont.setColor(Color.WHITE);
        this.secondsFont.setTextAlign(Paint.Align.LEFT);

        this.indicatorFont = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.indicatorFont.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
        this.indicatorFont.setTextSize(context.getResources().getDimension(R.dimen.font_size));
        this.indicatorFont.setColor(Color.WHITE);
        this.indicatorFont.setTextAlign(Paint.Align.LEFT);

        this.weekdayFont = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.weekdayFont.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
        this.weekdayFont.setTextSize(context.getResources().getDimension(R.dimen.font_size));
        this.weekdayFont.setColor(Color.WHITE);
        this.weekdayFont.setTextAlign(Paint.Align.LEFT);

        this.monthFont = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.monthFont.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
        this.monthFont.setTextSize(context.getResources().getDimension(R.dimen.font_size));
        this.monthFont.setColor(Color.RED);
        this.monthFont.setTextAlign(Paint.Align.LEFT);

    }

    // Screen open watch mode
    @Override
    public void onDrawDigital(Canvas canvas, float width, float height, float centerX, float centerY, int seconds, int minutes, int hours, int year, int month, int day, int week, int ampm) {
        // Draw background image
        //this.background.draw(canvas);

        Paint mGPaint = new Paint();
        mGPaint.setAntiAlias(false);
        mGPaint.setFilterBitmap(false);

        canvas.drawBitmap(this.background, 0f, 0f, mGPaint);


        // Draw day of week
        canvas.drawText(
                days[week - 1],
                context.getResources().getDimension(R.dimen.time_hour_left),
                context.getResources().getDimension(R.dimen.day_top),
                this.dayFont
        );

        // Draw hours
        canvas.drawText(
                Util.formatTime(hours) ,
                context.getResources().getDimension(R.dimen.time_hour_left),
                context.getResources().getDimension(R.dimen.time_top),
                this.hourFont
        );

        // Draw minutes
        canvas.drawText(
                Util.formatTime(minutes),
                context.getResources().getDimension(R.dimen.time_minutes_left),
                context.getResources().getDimension(R.dimen.time_top),
                this.minutesFont
        );

        // Draw Day of the month
        canvas.drawText(
                dates[day],
                context.getResources().getDimension(R.dimen.time_hour_left),
                context.getResources().getDimension(R.dimen.date_top),
                this.weekdayFont
        );

        // Draw month
        canvas.drawText(
                months[month - 1],
                context.getResources().getDimension(R.dimen.time_hour_left),
                context.getResources().getDimension(R.dimen.month_top),
                this.monthFont
        );

        temp_month = month;
    }


    // Screen locked/closed watch mode (Slpt mode)
    @Override
    public List<SlptViewComponent> buildSlptViewComponent(Service service) {
        return buildSlptViewComponent(service, false);
    }

    public List<SlptViewComponent> buildSlptViewComponent(Service service, boolean better_resolution) {
        List<SlptViewComponent> slpt_objects = new ArrayList<>();

        // Draw background image
        SlptPictureView background = new SlptPictureView();
        background.setImagePicture(SimpleFile.readFileFromAssets(service, "background_better_slpt.png"));

        slpt_objects.add(background);

        // Set font
        Typeface timeTypeFace = ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE);

        // Draw day
        SlptLinearLayout dayLayout = new SlptLinearLayout();
        dayLayout.add(new SlptWeekView());
        dayLayout.setStringPictureArrayForAll(this.days);

        dayLayout.setTextAttrForAll(
                context.getResources().getDimension(R.dimen.font_size),
                Color.WHITE,
                timeTypeFace
        );
        // Position based on screen on
        dayLayout.alignX = 2;
        dayLayout.alignY = 0;
        dayLayout.setRect(
                (int) (2 * ( (int) context.getResources().getDimension(R.dimen.slpt_left)) + 640),
                (int) (((float) 75 / 100) * context.getResources().getDimension(R.dimen.font_size))
        );
        dayLayout.setStart(
                -320,
                (int) (context.getResources().getDimension(R.dimen.day_top_slpt) - ((float) 75 / 100) * context.getResources().getDimension(R.dimen.font_size))
        );
        //Add it to the list
        slpt_objects.add(dayLayout);


        // Draw hours
        SlptLinearLayout hourLayout = new SlptLinearLayout();
        hourLayout.add(new SlptHourHView());
        hourLayout.add(new SlptHourLView());
        hourLayout.setStringPictureArrayForAll(this.digitalNums);

        hourLayout.setTextAttrForAll(
                context.getResources().getDimension(R.dimen.font_size),
                Color.RED,
                timeTypeFace
        );
        // Position based on screen on
        hourLayout.alignX = 2;
        hourLayout.alignY = 0;
        hourLayout.setRect(
                (int) (2 * ( (int) context.getResources().getDimension(R.dimen.time_hour_left_slpt)) + 640),
                (int) (((float) 75 / 100) * context.getResources().getDimension(R.dimen.font_size))
        );
        hourLayout.setStart(
                -320,
                (int) (context.getResources().getDimension(R.dimen.time_top_slpt) - ((float) 75 / 100) * context.getResources().getDimension(R.dimen.font_size))
        );
        //Add it to the list
        slpt_objects.add(hourLayout);

        // Draw minutes
        SlptLinearLayout minuteLayout = new SlptLinearLayout();
        minuteLayout.add(new SlptMinuteHView());
        minuteLayout.add(new SlptMinuteLView());
        minuteLayout.setStringPictureArrayForAll(this.digitalNums);
        minuteLayout.setTextAttrForAll(
                context.getResources().getDimension(R.dimen.font_size),
                Color.RED,
                timeTypeFace
        );
        // Position based on screen on
        minuteLayout.alignX = 2;
        minuteLayout.alignY = 0;
        minuteLayout.setRect(
                (int) (2 * ((int) context.getResources().getDimension(R.dimen.time_minutes_left_slpt)) + 640),
                (int) (((float) 75 / 100) * context.getResources().getDimension(R.dimen.font_size))
        );
        minuteLayout.setStart(
                -320,
                (int) (context.getResources().getDimension(R.dimen.time_top_slpt) - ((float) 75 / 100) * context.getResources().getDimension(R.dimen.font_size))
        );

        //Add it to the list
        slpt_objects.add(minuteLayout);


        return slpt_objects;
    }
}
