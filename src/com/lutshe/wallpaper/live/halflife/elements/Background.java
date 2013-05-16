package com.lutshe.wallpaper.live.halflife.elements;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background {

    public final Bitmap bg;
    public Bitmap scaledBg;

    public Background(Bitmap bg) {
        this.bg = bg;
        scaledBg = this.bg;
    }

    public void scaleBackground(float scale) {
        scaledBg = Bitmap.createScaledBitmap(bg, (int) (bg.getWidth() * scale), (int) (bg.getHeight() * scale), true);
    }

    public void onDraw(Canvas canvas) {
        canvas.save();
        canvas.drawBitmap(scaledBg, 0, 0, null);
        canvas.restore();
    }

    public void recycle() {
        bg.recycle();
        scaledBg.recycle();
    }
}
