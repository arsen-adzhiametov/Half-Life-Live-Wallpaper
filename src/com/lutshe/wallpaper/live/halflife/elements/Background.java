package com.lutshe.wallpaper.live.halflife.elements;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import com.lutshe.wallpaper.live.halflife.R;

public class Background {

    public final Bitmap bg;
    public Bitmap scaledBg;

    public Background(Context context, BitmapFactory.Options options) {
        this.bg = BitmapFactory.decodeResource(context.getResources(), R.drawable.background, options);
        scaledBg = bg;
    }

    public void scaleBackground(float scale) {
        scaledBg = Bitmap.createScaledBitmap(bg, (int) (bg.getWidth() * scale), (int) (bg.getHeight() * scale), true);
    }

    public void onDraw(Canvas canvas) {
        canvas.save();
        canvas.drawBitmap(scaledBg, 0, 0, null);
        canvas.restore();
    }

    public void recycleBitmap() {
        bg.recycle();
        scaledBg.recycle();
    }
}
