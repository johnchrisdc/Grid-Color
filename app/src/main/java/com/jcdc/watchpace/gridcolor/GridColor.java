package com.jcdc.watchpace.gridcolor;

import android.content.Context;

import com.huami.watch.watchface.AbstractSlptClock;
import com.jcdc.watchpace.gridcolor.widget.BatteryWidget;
import com.jcdc.watchpace.gridcolor.widget.MainClock;

public class GridColor extends AbstractWatchFace {

    private Context context;

    public GridColor() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

        this.clock = new MainClock(context);
        this.widgets.add(new BatteryWidget(context));

        notifyStatusBarPosition(
                (float) 110,
                (float) 184
        );
    }

    @Override
    protected Class<? extends AbstractSlptClock> slptClockClass() {
        return GridColorSlpt.class;
    }
}
