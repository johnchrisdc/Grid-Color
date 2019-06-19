package com.jcdc.watchpace.gridcolor.widget;

import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;

import androidx.core.content.ContextCompat;

import com.ingenic.iwds.slpt.view.analog.SlptAnalogMinuteView;
import com.ingenic.iwds.slpt.view.analog.SlptAnalogSecondView;
import com.ingenic.iwds.slpt.view.digital.SlptWeekView;
import com.jcdc.watchpace.gridcolor.R;
import com.jcdc.watchpace.gridcolor.RitzBlue;
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
import com.jcdc.watchpace.gridcolor.resource.SlptAnalogHourView;

import java.util.ArrayList;
import java.util.List;

public class RitzBlueMainClock extends DigitalClockWidget {

    private Context context;

    private TextPaint hourFont;
    private TextPaint minutesFont;

    private Bitmap hourHand, minuteHand, secondsHand, background;

    private String[] digitalNums = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    public RitzBlueMainClock(Context context) {
        this.context = context;
    }

    @Override
    public void init(Service service) {
        this.background = Util.decodeImage(service.getResources(),"background_black.png");

        this.hourFont = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.hourFont.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.ROBOTO));
        this.hourFont.setTextSize(context.getResources().getDimension(R.dimen.ritz_blue_font_size));
        this.hourFont.setColor(ContextCompat.getColor(context, R.color.ritz_blue_font_color));
        this.hourFont.setTextAlign(Paint.Align.LEFT);

        this.minutesFont = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.minutesFont.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.ROBOTO));
        this.minutesFont.setTextSize(context.getResources().getDimension(R.dimen.ritz_blue_font_size));
        this.minutesFont.setColor(ContextCompat.getColor(context, R.color.ritz_blue_font_color));
        this.minutesFont.setTextAlign(Paint.Align.LEFT);


        this.hourHand = Util.decodeImage(service.getResources(),"timehand/hour.png");
        this.minuteHand = Util.decodeImage(service.getResources(),"timehand/minute.png");
        this.secondsHand = Util.decodeImage(service.getResources(),"timehand/seconds.png");
    }

    // Screen open watch mode
    @Override
    public void onDrawDigital(Canvas canvas, float width, float height, float centerX, float centerY, int seconds, int minutes, int hours, int year, int month, int day, int week, int ampm) {
        // Draw background image
        //this.background.draw(canvas);

        Paint mGPaint = RitzBlue.getPaint();

        canvas.drawBitmap(this.background, 0f, 0f, mGPaint);

        // Draw hours
        canvas.drawText(
                Util.formatTime(hours) ,
                context.getResources().getDimension(R.dimen.ritz_blue_time_hour_left),
                context.getResources().getDimension(R.dimen.ritz_blue_time_top),
                this.hourFont
        );

        // Draw minutes
        canvas.drawText(
                Util.formatTime(minutes),
                context.getResources().getDimension(R.dimen.ritz_blue_time_minutes_left),
                context.getResources().getDimension(R.dimen.ritz_blue_time_top),
                this.minutesFont
        );

        //Analog

        canvas.save();
        canvas.rotate(((float) (hours * 30)) + ((((float) minutes) / 60.0f) * 30.0f), 160.0f + (false?20f:0f), 159.0f + (false?20f:0f));
        canvas.drawBitmap(this.hourHand, centerX - this.hourHand.getWidth() / 2f, centerY - this.hourHand.getHeight() / 2f, null);
        canvas.restore();
        canvas.save();
        canvas.rotate((float) (minutes * 6), 160.0f + (false?20f:0f), 159.0f + (false?20f:0f));
        canvas.drawBitmap(this.minuteHand, centerX - this.minuteHand.getWidth() / 2f, centerY - this.minuteHand.getHeight() / 2f, null);
        canvas.restore();
        if (true) {
            canvas.save();
            canvas.rotate((float) (seconds * 6), 160.0f + (false ? 20f : 0f), 159.0f + (false ? 20f : 0f));
            canvas.drawBitmap(this.secondsHand, centerX - this.secondsHand.getWidth() / 2f, centerY - this.secondsHand.getHeight() / 2f, null);
            canvas.restore();
        }
    }


    // Screen locked/closed watch mode (Slpt mode)
    @Override
    public List<SlptViewComponent> buildSlptViewComponent(Service service) {
        return buildSlptViewComponent(service, false);
    }

    public List<SlptViewComponent> buildSlptViewComponent(Service service, boolean better_resolution) {
        int font_ratio = 95;
        List<SlptViewComponent> slpt_objects = new ArrayList<>();

        // Draw background image
        SlptPictureView background = new SlptPictureView();
        background.setImagePicture(SimpleFile.readFileFromAssets(service, "background_better_slpt.png"));

        slpt_objects.add(background);

        // Set font
        Typeface timeTypeFace = ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.ROBOTO);

        // Draw hours
        SlptLinearLayout hourLayout = new SlptLinearLayout();
        hourLayout.add(new SlptHourHView());
        hourLayout.add(new SlptHourLView());
        hourLayout.setStringPictureArrayForAll(this.digitalNums);

        hourLayout.setTextAttrForAll(
                context.getResources().getDimension(R.dimen.ritz_blue_font_size),
                ContextCompat.getColor(context, R.color.cyan),
                timeTypeFace
        );
        // Position based on screen on
        hourLayout.alignX = 2;
        hourLayout.alignY = 0;
        hourLayout.setRect(
                (int) (2 * (context.getResources().getDimension(R.dimen.ritz_blue_time_hour_left)) + 640),
                (int) (((float) font_ratio / 100) * context.getResources().getDimension(R.dimen.ritz_blue_font_size))
        );
        hourLayout.setStart(
                -255,
                (int) (context.getResources().getDimension(R.dimen.ritz_blue_time_top) - ((float) font_ratio / 100) * context.getResources().getDimension(R.dimen.ritz_blue_font_size))
        );
        //Add it to the list
        slpt_objects.add(hourLayout);

        // Draw minutes
        SlptLinearLayout minuteLayout = new SlptLinearLayout();
        minuteLayout.add(new SlptMinuteHView());
        minuteLayout.add(new SlptMinuteLView());
        minuteLayout.setStringPictureArrayForAll(this.digitalNums);
        minuteLayout.setTextAttrForAll(
                context.getResources().getDimension(R.dimen.ritz_blue_font_size),
                ContextCompat.getColor(context, R.color.cyan),
                timeTypeFace
        );
        // Position based on screen on
        minuteLayout.alignX = 2;
        minuteLayout.alignY = 0;
        minuteLayout.setRect(
                (int) (2 * (context.getResources().getDimension(R.dimen.ritz_blue_time_minutes_left)) + 640),
                (int) (((float) font_ratio / 100) * context.getResources().getDimension(R.dimen.ritz_blue_font_size))
        );
        minuteLayout.setStart(
                -255,
                (int) (context.getResources().getDimension(R.dimen.ritz_blue_time_top) - ((float) font_ratio / 100) * context.getResources().getDimension(R.dimen.ritz_blue_font_size))
        );

        //Add it to the list
        slpt_objects.add(minuteLayout);

        SlptAnalogHourView slptAnalogHourView = new SlptAnalogHourView();
        slptAnalogHourView.setImagePicture(SimpleFile.readFileFromAssets(service, "timehand/hour.png"));
        slptAnalogHourView.alignX = (byte) 2;
        slptAnalogHourView.alignY = (byte) 2;
        slptAnalogHourView.setRect(320 + (false?40:0), 320 + (false?40:0));
        slpt_objects.add(slptAnalogHourView);

        SlptAnalogMinuteView slptAnalogMinuteView = new SlptAnalogMinuteView();
        slptAnalogMinuteView.setImagePicture(SimpleFile.readFileFromAssets(service, "timehand/minute.png"));
        slptAnalogMinuteView.alignX = (byte) 2;
        slptAnalogMinuteView.alignY = (byte) 2;
        slptAnalogMinuteView.setRect(320 + (false?40:0), 320 + (false?40:0));
        slpt_objects.add(slptAnalogMinuteView);

//        if(settings.secondsBool){
//            SlptAnalogSecondView slptAnalogSecondView = new SlptAnalogSecondView();
//            slptAnalogSecondView.setImagePicture(SimpleFile.readFileFromAssets(service, "timehand/second.png"));
//            slptAnalogSecondView.alignX = (byte) 2;
//            slptAnalogSecondView.alignY = (byte) 2;
//            slptAnalogSecondView.setRect(320 + (settings.isVerge()?40:0), 320 + (settings.isVerge()?40:0));
//            slpt_objects.add(slptAnalogSecondView);
//        }


        return slpt_objects;
    }
}
