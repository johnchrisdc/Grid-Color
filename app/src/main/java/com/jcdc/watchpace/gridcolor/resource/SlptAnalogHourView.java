package com.jcdc.watchpace.gridcolor.resource;

import com.ingenic.iwds.slpt.view.analog.SlptAnalogTimeView;

public class SlptAnalogHourView extends SlptAnalogTimeView {
    public SlptAnalogHourView() {
    }

    protected short initType() {
        return SVIEW_ANALOG_HOUR;
    }
}