package com.dipoletech.wacos.util;/**
 * Created by DABBY(3pleMinds) on 01-Feb-16.
 */

import android.support.v7.widget.RecyclerView;

/**
 * DABBY(3pleMinds) 01-Feb-16 9:10 PM 2016 02
 * 01 21 10 Wacos
 **/
public abstract class HideViewOnScroll extends RecyclerView.OnScrollListener {
    private static final int HIDE_THRESHOLD = 20;
    private int scrollDistance = 0;
    private boolean controlsVisible = true;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (scrollDistance > HIDE_THRESHOLD && controlsVisible) {
            onHide();
            controlsVisible = false;
            scrollDistance = 0;
        } else if (scrollDistance < -HIDE_THRESHOLD && !controlsVisible) {
            onShow();
            controlsVisible = true;
            scrollDistance = 0;
        }

        if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) {
            //continuously add the change in y as that is what we are
            //interested in.
            scrollDistance += dy;
        }
    }

    protected abstract void onHide();

    protected abstract void onShow();
}
