package com.lutshe.wallpaper.live.halflife.elements;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import com.lutshe.wallpaper.live.halflife.R;

public class Tower {

    private final Bitmap towerBitmap;
    private final int X = 356;
    private final int Y = 250;

    private Bitmap tower;
    public int x = X;
    public int y = Y;

    public Tower(Context context, BitmapFactory.Options options) {
        this.towerBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.tower_wide, options);
        tower = towerBitmap;
    }

    public void scaleTower(float scale) {
        x = (int) (X * scale);
        y = (int) (Y * scale);
        tower = Bitmap.createScaledBitmap(towerBitmap, (int) (towerBitmap.getWidth() * scale), (int) (towerBitmap.getHeight() * scale), true);
    }

    public void onDraw(Canvas canvas) {
        canvas.save();
        canvas.drawBitmap(tower, x, y, null);
        canvas.restore();
    }

    public void recycleBitmap() {
        towerBitmap.recycle();
        tower.recycle();
    }
}
