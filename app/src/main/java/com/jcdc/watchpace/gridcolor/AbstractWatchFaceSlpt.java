package com.jcdc.watchpace.gridcolor;

import com.huami.watch.watchface.AbstractSlptClock;
import com.jcdc.watchpace.gridcolor.widget.ClockWidget;
import com.jcdc.watchpace.gridcolor.widget.Widget;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Splt version of
 */

public abstract class AbstractWatchFaceSlpt extends AbstractSlptClock {

    public ClockWidget clock;
    final LinkedList<Widget> widgets = new LinkedList<>();

    protected AbstractWatchFaceSlpt(final ClockWidget clock, final Widget... widgets) {
        this.clock = clock;
        this.widgets.addAll(Arrays.asList(widgets));
    }

    protected AbstractWatchFaceSlpt() {

    }
}
