package com.jcdc.watchpace.gridcolor.widget;

import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.huami.watch.watchface.util.Util;
import com.ingenic.iwds.slpt.view.core.SlptLinearLayout;
import com.ingenic.iwds.slpt.view.core.SlptPictureView;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;
import com.ingenic.iwds.slpt.view.sport.SlptTotalDistanceFView;
import com.ingenic.iwds.slpt.view.sport.SlptTotalDistanceLView;
import com.ingenic.iwds.slpt.view.utils.SimpleFile;
import com.jcdc.watchpace.gridcolor.R;
import com.jcdc.watchpace.gridcolor.RitzBlue;
import com.jcdc.watchpace.gridcolor.data.DataType;
import com.jcdc.watchpace.gridcolor.data.TotalDistance;
import com.jcdc.watchpace.gridcolor.resource.ResourceManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SportTotalDistanceWidget extends AbstractWidget {
    private Context context;

    private TotalDistance total_distanceData;
    private Paint total_distancePaint;
    private Bitmap total_distanceIcon;
    private Service mService;

    public SportTotalDistanceWidget(Context context) {
        this.context = context;
    }

    // Screen-on init (runs once)
    public void init(Service service) {
        this.mService = service;

        this.total_distancePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.total_distancePaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.ROBOTO));
        this.total_distancePaint.setTextSize(context.getResources().getDimension(R.dimen.ritz_blue_distance_font_size));
        this.total_distancePaint.setColor(Color.WHITE);
        this.total_distancePaint.setTextAlign(Paint.Align.LEFT);

        this.total_distanceIcon = Util.decodeImage(service.getResources(), "icons/total_distance.png");
    }

    // Value updater
    public void onDataUpdate(DataType type, Object value) {
        // total Sport's Distance class
        this.total_distanceData = (TotalDistance) value;
    }

    // Register update listeners
    public List<DataType> getDataTypes() {
        return Collections.singletonList(DataType.TOTAL_DISTANCE);
    }

    // Draw screen-on
    public void draw(Canvas canvas, float width, float height, float centerX, float centerY) {
        if (this.total_distanceData == null) {
            return;
        }

        Paint mGPaint = RitzBlue.getPaint();

        canvas.drawBitmap(
                this.total_distanceIcon
                , context.getResources().getDimension(R.dimen.ritz_blue_total_distance_icon_left)
                , context.getResources().getDimension(R.dimen.ritz_blue_total_distance_icon_top)
                , mGPaint
        );

        // total Sport's Distance widget
        String units = "km";// if units are enabled
        String text = String.format("%s", this.total_distanceData.getDistance()) + units;
        canvas.drawText(
                text
                , context.getResources().getDimension(R.dimen.ritz_blue_total_distance_left)
                , context.getResources().getDimension(R.dimen.ritz_blue_total_distance_top)
                , total_distancePaint
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

        SlptPictureView total_distanceIcon = new SlptPictureView();
        total_distanceIcon.setImagePicture(SimpleFile.readFileFromAssets(service, "slpt_icons/total_distance.png"));
        total_distanceIcon.setStart(
                (int) context.getResources().getDimension(R.dimen.ritz_blue_total_distance_icon_left_slpt),
                (int) context.getResources().getDimension(R.dimen.ritz_blue_total_distance_icon_top)
        );
        slpt_objects.add(total_distanceIcon);

        SlptPictureView dot = new SlptPictureView();
        dot.setStringPicture(".");
        SlptPictureView kilometer = new SlptPictureView();
        kilometer.setStringPicture(" km");

        SlptLinearLayout distance = new SlptLinearLayout();
        distance.add(new SlptTotalDistanceFView());
        distance.add(dot);
        distance.add(new SlptTotalDistanceLView());

        distance.add(kilometer);
        distance.setTextAttrForAll(
                context.getResources().getDimension(R.dimen.ritz_blue_distance_font_size),
                Color.WHITE,
                ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.ROBOTO)
        );
        // Position based on screen on
        distance.alignX = 2;
        distance.alignY = 0;
        tmp_left = (int) context.getResources().getDimension(R.dimen.ritz_blue_total_distance_left_slpt);

//        // If text is centered, set rectangle
//        distance.setRect(
//                (int) (2 * tmp_left + 640),
//                (int) (((float) font_ratio / 100) * context.getResources().getDimension(R.dimen.ritz_blue_distance_font_size))
//        );
//        tmp_left = -320;

        distance.setStart(
                tmp_left,
                (int) (context.getResources().getDimension(R.dimen.ritz_blue_total_distance_top) - ((float) font_ratio / 100) * context.getResources().getDimension(R.dimen.ritz_blue_distance_font_size))
        );
        slpt_objects.add(distance);

        return slpt_objects;
    }
}
