package com.jcdc.watchpace.gridcolor;

import android.content.Context;

import com.huami.watch.watchface.AbstractSlptClock;
import com.jcdc.watchpace.gridcolor.widget.BatteryWidget;
import com.jcdc.watchpace.gridcolor.widget.RitzBlueMainClock;

public class RitzBlue extends AbstractWatchFace {

    private Context context;

    public RitzBlue() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

        this.clock = new RitzBlueMainClock(context);
        this.widgets.add(new BatteryWidget(context));

        notifyStatusBarPosition(
                (float) 110,
                (float) 184
        );
    }

    @Override
    protected Class<? extends AbstractSlptClock> slptClockClass() {
        return RitzBlueSlpt.class;
    }
}
