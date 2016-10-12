package com.bandung.disiplin.widget.loopview;

import android.view.MotionEvent;

/**
 * Created by asus pc on 08/12/2015.
 */
final class LoopViewGestureListener extends android.view.GestureDetector.SimpleOnGestureListener {

    final LoopView loopView;

    LoopViewGestureListener(LoopView loopview) {
        super();
        loopView = loopview;
    }

    @Override
    public final boolean onDown(MotionEvent motionevent) {
        loopView.cancelFuture();
        return true;
    }

    @Override
    public final boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        loopView.smoothScroll(velocityY);
        return true;
    }
}
