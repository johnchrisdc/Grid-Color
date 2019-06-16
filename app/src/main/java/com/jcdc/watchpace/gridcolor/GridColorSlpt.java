package com.jcdc.watchpace.gridcolor;

import android.content.Context;
import android.content.Intent;

import com.ingenic.iwds.slpt.view.core.SlptAbsoluteLayout;
import com.ingenic.iwds.slpt.view.core.SlptLayout;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;
import com.jcdc.watchpace.gridcolor.widget.BatteryWidget;
import com.jcdc.watchpace.gridcolor.widget.MainClock;
import com.jcdc.watchpace.gridcolor.widget.Widget;

public class GridColorSlpt extends AbstractWatchFaceSlpt {
    private Context context;

    public GridColorSlpt() {
        super();
    }

    @Override
    public int onStartCommand(Intent intent, int i, int i1) {
        context = getApplicationContext();

        this.clock = new MainClock(context);

        this.widgets.add(new BatteryWidget(context));

        return super.onStartCommand(intent, i, i1);
    }

    @Override
    protected SlptLayout createClockLayout26WC() {
        SlptAbsoluteLayout result = new SlptAbsoluteLayout();
        for (SlptViewComponent component : clock.buildSlptViewComponent(this, true)) {
            result.add(component);
        }
        for (Widget widget : widgets) {
            for (SlptViewComponent component : widget.buildSlptViewComponent(this, true)) {
                result.add(component);
            }
        }

        return result;
    }

    @Override
    protected SlptLayout createClockLayout8C() {
        SlptAbsoluteLayout result = new SlptAbsoluteLayout();
        for (SlptViewComponent component : clock.buildSlptViewComponent(this)) {
            result.add(component);
        }
        for (Widget widget : widgets) {
            for (SlptViewComponent component : widget.buildSlptViewComponent(this)) {
                result.add(component);
            }
        }

        return result;
    }

    @Override
    protected void initWatchFaceConfig() {

    }

    @Override
    public boolean isClockPeriodSecond() {
        return super.isClockPeriodSecond();
    }
}
