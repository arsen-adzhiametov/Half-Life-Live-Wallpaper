package com.lutshe.wallpaper.live.halflife.elements;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Tower {

    private final Bitmap tower;
    private final int X = 130;
    private final int Y = 100;

    private Bitmap scaledTower;
    public int x = 130;
    public int y = 100;

    public Tower(Bitmap tower) {
        this.tower = tower;
        scaledTower = this.tower;
    }

    public void scaleTower(float scale) {
        x = (int) (X * scale);
        y = (int) (Y * scale);
        scaledTower = Bitmap.createScaledBitmap(tower, (int) (tower.getWidth() * scale), (int) (tower.getHeight() * scale), true);
    }

    public void onDraw(Canvas canvas) {
        canvas.save();
        canvas.drawBitmap(scaledTower, x, y, null);
        canvas.restore();
    }

    public void recycle() {
        tower.recycle();
        scaledTower.recycle();
    }
}
