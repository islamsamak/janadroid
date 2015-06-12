package com.jana.android.slidingmenu.app;

import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.jana.android.slidingmenu.SlidingMenu;

public interface SlidingActivityBase {

    void setBehindLeftContentView(View v, LayoutParams p);

    void setBehindRightContentView(View v, LayoutParams p);

    SlidingMenu getSlidingMenu();

    void toggle(int side);

    void showAbove();

    void showBehind(int side);

}
