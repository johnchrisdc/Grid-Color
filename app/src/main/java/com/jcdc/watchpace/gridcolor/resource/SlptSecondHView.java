package com.jcdc.watchpace.gridcolor.resource;

import com.ingenic.iwds.slpt.view.digital.SlptTimeView;

public class SlptSecondHView extends SlptTimeView {
    public SlptSecondHView() {
    }

    protected short initType() {
        return SVIEW_SECOND_H;
    }
}