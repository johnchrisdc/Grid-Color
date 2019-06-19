package com.jcdc.watchpace.gridcolor.widget;

import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.huami.watch.watchface.util.Util;
import com.ingenic.iwds.slpt.view.arc.SlptTodayDistanceArcAnglePicView;
import com.ingenic.iwds.slpt.view.core.SlptLinearLayout;
import com.ingenic.iwds.slpt.view.core.SlptPictureView;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;
import com.ingenic.iwds.slpt.view.sport.SlptTodaySportDistanceFView;
import com.ingenic.iwds.slpt.view.sport.SlptTodaySportDistanceLView;
import com.ingenic.iwds.slpt.view.utils.SimpleFile;
import com.jcdc.watchpace.gridcolor.R;
import com.jcdc.watchpace.gridcolor.RitzBlue;
import com.jcdc.watchpace.gridcolor.data.DataType;
import com.jcdc.watchpace.gridcolor.data.TodayDistance;
import com.jcdc.watchpace.gridcolor.resource.ResourceManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SportTodayDistanceWidget extends AbstractWidget {
    private TodayDistance today_distanceData;
    private Paint today_distancePaint;
    private Paint ring;
    private Bitmap today_distanceIcon;
    private Float today_distanceSweepAngle = 0f;
    private Integer angleLength;

    private Service mService;

    private Context context;

    public SportTodayDistanceWidget(Context context) {
        this.context = context;
    }

    // Screen-on init (runs once)
    public void init(Service service) {
        this.mService = service;

        this.today_distancePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.today_distancePaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.ROBOTO));
        this.today_distancePaint.setTextSize(context.getResources().getDimension(R.dimen.ritz_blue_distance_font_size));
        this.today_distancePaint.setColor(Color.WHITE);
        this.today_distancePaint.setTextAlign(Paint.Align.LEFT);

        this.today_distanceIcon = Util.decodeImage(service.getResources(), "icons/today_distance.png");
    }

    // Value updater
    public void onDataUpdate(DataType type, Object value) {
        // Today Sport's Distance class
        this.today_distanceData = (TodayDistance) value;
    }

    // Register update listeners
    public List<DataType> getDataTypes() {
        return Collections.singletonList(DataType.DISTANCE);
    }

    // Draw screen-on
    public void draw(Canvas canvas, float width, float height, float centerX, float centerY) {
        if (this.today_distanceData == null) {
            return;
        }

        Paint mGPaint = RitzBlue.getPaint();

        canvas.drawBitmap(
                this.today_distanceIcon
                , context.getResources().getDimension(R.dimen.ritz_blue_today_distance_icon_left)
                , context.getResources().getDimension(R.dimen.ritz_blue_today_distance_icon_top)
                , mGPaint
        );

        String units = "km";// if units are enabled
        String text = String.format("%s", this.today_distanceData.getDistance()) + units;
        canvas.drawText(
                text
                , context.getResources().getDimension(R.dimen.ritz_blue_today_distance_left)
                , context.getResources().getDimension(R.dimen.ritz_blue_today_distance_top)
                , today_distancePaint
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

        int font_ratio = 90;

        SlptPictureView today_distanceIcon = new SlptPictureView();
        today_distanceIcon.setImagePicture(SimpleFile.readFileFromAssets(service, "slpt_icons/today_distance.png"));
        today_distanceIcon.setStart(
                (int) context.getResources().getDimension(R.dimen.ritz_blue_today_distance_icon_left_slpt),
                (int) context.getResources().getDimension(R.dimen.ritz_blue_today_distance_icon_top)
        );

        slpt_objects.add(today_distanceIcon);

        SlptPictureView dot = new SlptPictureView();
        dot.setStringPicture(".");
        SlptPictureView kilometer = new SlptPictureView();
        kilometer.setStringPicture(" km");

        SlptLinearLayout distance = new SlptLinearLayout();
        distance.add(new SlptTodaySportDistanceFView());
        distance.add(dot);
        distance.add(new SlptTodaySportDistanceLView());

        distance.add(kilometer);

        distance.setTextAttrForAll(
                context.getResources().getDimension(R.dimen.ritz_blue_distance_font_size),
                Color.WHITE,
                ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.ROBOTO)
        );
        // Position based on screen on
        distance.alignX = 2;
        distance.alignY = 0;

        tmp_left = (int) context.getResources().getDimension(R.dimen.ritz_blue_today_distance_left_slpt);
//        if (!settings.today_distanceAlignLeft) {
//            // If text is centered, set rectangle
//            distance.setRect(
//                    (int) (2 * tmp_left + 640),
//                    (int) (((float) settings.font_ratio / 100) * settings.today_distanceFontSize)
//            );
//            tmp_left = -320;
//        }
        distance.setStart(
                tmp_left,
                (int) (context.getResources().getDimension(R.dimen.ritz_blue_today_distance_top) - ((float) font_ratio / 100) * context.getResources().getDimension(R.dimen.ritz_blue_distance_font_size))
        );
        slpt_objects.add(distance);

        return slpt_objects;
    }
}
