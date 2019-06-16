package com.jcdc.watchpace.gridcolor.widget;

import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.huami.watch.watchface.util.Util;
import com.ingenic.iwds.slpt.view.arc.SlptPowerArcAnglePicView;
import com.ingenic.iwds.slpt.view.core.SlptBatteryView;
import com.ingenic.iwds.slpt.view.core.SlptLinearLayout;
import com.ingenic.iwds.slpt.view.core.SlptPictureView;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;
import com.ingenic.iwds.slpt.view.sport.SlptPowerNumView;
import com.ingenic.iwds.slpt.view.utils.SimpleFile;
import com.jcdc.watchpace.gridcolor.R;
import com.jcdc.watchpace.gridcolor.data.Battery;
import com.jcdc.watchpace.gridcolor.data.DataType;
import com.jcdc.watchpace.gridcolor.resource.ResourceManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class BatteryWidget extends AbstractWidget {
    private Context context;

    private Battery batteryData;
    private Paint batteryPaint;
    private Paint ring;

    private Float batterySweepAngle = 0f;
    private Integer angleLength;

    private Bitmap batteryIcon;
    private Bitmap icon;

    private Integer tempBattery=0;

    private Service mService;

    // Constructor
    public BatteryWidget(Context context) {
        this.context = context;
    }

    // Screen-on init (runs once)
    public void init(Service service) {
        this.mService = service;

        // Battery percent element
        this.icon = Util.decodeImage(mService.getResources(),"icons/battery.png");

        this.batteryPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.batteryPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
        this.batteryPaint.setTextSize(context.getResources().getDimension(R.dimen.battery_font_size));
        this.batteryPaint.setColor(Color.WHITE);
        this.batteryPaint.setTextAlign(Paint.Align.LEFT);
    }

    // Value updater
    public void onDataUpdate(DataType type, Object value) {
        // Battery class
        this.batteryData = (Battery) value;

        if(this.batteryData == null){
            return;
        }

        this.tempBattery = this.batteryData.getLevel()/10;
    }

    // Register update listeners
    public List<DataType> getDataTypes() {
        return Collections.singletonList(DataType.BATTERY);
    }

    // Draw screen-on
    public void draw(Canvas canvas, float width, float height, float centerX, float centerY) {
        Paint mGPaint = new Paint();
        mGPaint.setAntiAlias(false);
        mGPaint.setFilterBitmap(false);

        if (this.batteryData == null) {return;}

        // Battery % widget
        canvas.drawBitmap(
                this.icon,
                context.getResources().getDimension(R.dimen.battery_icon_left),
                context.getResources().getDimension(R.dimen.battery_icon_top),
                mGPaint
        );

        String text = Integer.toString(this.batteryData.getLevel() * 100 / this.batteryData.getScale())+"%";
        canvas.drawText(
                text,
                context.getResources().getDimension(R.dimen.battery_percent_left),
                context.getResources().getDimension(R.dimen.battery_percent_top),
                batteryPaint
        );
    }

    // Screen-off (SLPT)
    public List<SlptViewComponent> buildSlptViewComponent(Service service) {
        return buildSlptViewComponent(service, false);
    }

    // Screen-off (SLPT) - Better screen quality
    public List<SlptViewComponent> buildSlptViewComponent(Service service, boolean better_resolution) {
        List<SlptViewComponent> slpt_objects = new ArrayList<>();

        int tmp_left;

        // Show battery
        SlptPictureView battery_percentIcon = new SlptPictureView();
        battery_percentIcon.setImagePicture( SimpleFile.readFileFromAssets(service, "icons/battery.png"));
        battery_percentIcon.setStart(
                (int) context.getResources().getDimension(R.dimen.battery_icon_slpt_left),
                (int) context.getResources().getDimension(R.dimen.battery_icon_top)
        );
        slpt_objects.add(battery_percentIcon);

        SlptLinearLayout power = new SlptLinearLayout();
        SlptPictureView percentage = new SlptPictureView();
        percentage.setStringPicture("%");
        power.add(new SlptPowerNumView());
        power.add(percentage);
        power.setTextAttrForAll(
                context.getResources().getDimension(R.dimen.battery_font_size),
                Color.WHITE,
                ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE)
        );
        // Position based on screen on
        power.alignX = 2;
        power.alignY = 0;
        tmp_left = 0;

        // If text is centered, set rectangle
        power.setRect(
                (int) (2 * tmp_left + 640),
                (int) (((float)75/100)*context.getResources().getDimension(R.dimen.battery_font_size))
        );

        tmp_left = -320;
        power.setStart(
                tmp_left,
                (int) (context.getResources().getDimension(R.dimen.battery_percent_top)-((float)75/100)*context.getResources().getDimension(R.dimen.battery_font_size)
                )
        );

        slpt_objects.add(power);

        return slpt_objects;
    }
}
