package com.jcdc.watchpace.gridcolor.widget;

import android.app.Service;
import android.graphics.Canvas;

import com.jcdc.watchpace.gridcolor.data.MultipleWatchDataListener;

public interface Widget extends MultipleWatchDataListener, HasSlptViewComponent {

    int getX();

    int getY();

    void setX(int x);

    void setY(int y);

    void init(Service service);

    void draw(Canvas canvas, float width, float height, float centerX, float centerY);
}
