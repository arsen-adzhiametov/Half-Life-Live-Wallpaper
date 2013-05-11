package com.lutshe.wallpaper.live.halflife.elements;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Cloud {

    public static final String LUTSHE = "lutshe";

    private final Bitmap unscaledCloud;
    private Bitmap cloud;
    private int A;
    private int B;
    private boolean rightSpin;
    private int startDegree;
    private double rad;
    private double x;
    private double y;

    public Cloud(Bitmap cloud, int startDegree, int radius, boolean rightSpin) {
        setEllipseConstants(radius);
        this.rightSpin = rightSpin;
        this.unscaledCloud = cloud;
        setActualSize(1f);
        this.startDegree = startDegree;
        if (!rightSpin) inverseRotate();
    }

    public void setActualSize(float scale) {
        cloud = Bitmap.createScaledBitmap(unscaledCloud, (int) (unscaledCloud.getWidth() * scale), (int) (unscaledCloud.getHeight() * scale), true);
    }

    private void update() {
        if (rightSpin) startDegree += 1;
        else startDegree -= 1;
        rad = Math.toRadians(startDegree);
        x = Math.cos(rad) * A + 100;
        y = Math.sin(rad) * B + 15;
        if (Math.abs(startDegree) > 359) startDegree = 0;
    }

    public void onDraw(Canvas canvas) {
        update();
        canvas.save();
        canvas.translate((float) x, (float) y);
        canvas.rotate(startDegree, (float) cloud.getWidth() / 2, (float) cloud.getHeight() / 2);
        canvas.drawBitmap(cloud, 0, 0, null);
        canvas.restore();
    }

    private void inverseRotate() {
        startDegree = -1 * startDegree;
    }

    private void setEllipseConstants(int radius) {
        A = radius;
        B = radius * 60 / 120;
    }

    public void recycleBitmap() {
        unscaledCloud.recycle();
        cloud.recycle();
    }
}