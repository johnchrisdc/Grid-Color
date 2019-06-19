package com.jcdc.watchpace.gridcolor;

import android.content.Context;
import android.graphics.Paint;

import com.huami.watch.watchface.AbstractSlptClock;
import com.jcdc.watchpace.gridcolor.widget.BatteryWidget;
import com.jcdc.watchpace.gridcolor.widget.RitzBlueMainClock;
import com.jcdc.watchpace.gridcolor.widget.SportTodayDistanceWidget;
import com.jcdc.watchpace.gridcolor.widget.SportTotalDistanceWidget;

public class RitzBlue extends AbstractWatchFace {

    private Context context;

    private static Paint paint;

    public RitzBlue() {
        super();
    }

    @Override
    public void onCreate() {
        context = getApplicationContext();

        paint = new Paint();
        paint.setAntiAlias(false);
        paint.setFilterBitmap(false);

        this.clock = new RitzBlueMainClock(context);

        this.widgets.add(new BatteryWidget(context));
        this.widgets.add(new SportTotalDistanceWidget(context));
        this.widgets.add(new SportTodayDistanceWidget(context));

        notifyStatusBarPosition(
                (float) 110,
                (float) 184
        );

        super.onCreate();
    }

    @Override
    protected Class<? extends AbstractSlptClock> slptClockClass() {
        return RitzBlueSlpt.class;
    }

    public static Paint getPaint() {
        return RitzBlue.paint;
    }
}
