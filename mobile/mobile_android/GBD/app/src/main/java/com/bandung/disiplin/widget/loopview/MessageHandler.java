package com.bandung.disiplin.widget.loopview;

import android.os.Handler;
import android.os.Message;

/**
 * Created by asus pc on 08/12/2015.
 */
final class MessageHandler extends Handler {

    final LoopView loopview;

    MessageHandler(LoopView loopview) {
        super();
        this.loopview = loopview;
    }

    @Override
    public final void handleMessage(Message paramMessage) {
        if (paramMessage.what == 1000)
            this.loopview.invalidate();
        while (true) {
            if (paramMessage.what == 2000)
                LoopView.smoothScroll(loopview);
            else if (paramMessage.what == 3000)
                this.loopview.itemSelected();
            super.handleMessage(paramMessage);
            return;
        }
    }

}
