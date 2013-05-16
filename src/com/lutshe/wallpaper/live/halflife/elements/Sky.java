package com.lutshe.wallpaper.live.halflife.elements;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

public class Sky {

    public static final String LUTSHE = "lutshe";

    private static final int X_LAYOUT = 100;
    private static final int Y_LAYOUT = 15;
    private static final int RADIUS_1 = 80;
    private static final float SPEED_1 = 1f;
    private static final int RADIUS_2 = 140;
    private static final float SPEED_2 = 1f;
    private static final int RADIUS_3 = 200;
    private static final float SPEED_3 = 2f;

    private static int layoutX = X_LAYOUT;
    private static int layoutY = Y_LAYOUT;
    private static int radius1 = RADIUS_1;
    private static int radius2 = RADIUS_2;
    private static int radius3 = RADIUS_3;

    private static Bitmap unscaledCloud;
    private static Bitmap cloud;
    public static List<Cloud> clouds = new ArrayList<>();

    public Sky(Bitmap unscaledCloud) {
        this.unscaledCloud = unscaledCloud;
        cloud = this.unscaledCloud;
    }

    public void scaleClouds(float scale) {
        layoutX = (int) (X_LAYOUT * scale);
        layoutY = (int) (Y_LAYOUT * scale);
        radius1 = (int) (RADIUS_1 * scale);
        radius2 = (int) (RADIUS_2 * scale);
        cloud = Bitmap.createScaledBitmap(unscaledCloud, (int) (unscaledCloud.getWidth() * scale), (int) (unscaledCloud.getHeight() * scale), true);
        loadResourcesToMemory();
    }

    public static void loadResourcesToMemory() {
        clouds.clear();

        clouds.add(new Cloud(0, radius1, false, SPEED_1));
        clouds.add(new Cloud(60, radius1, false, SPEED_1));
        clouds.add(new Cloud(120, radius1, false, SPEED_1));
        clouds.add(new Cloud(180, radius1, false, SPEED_1));
        clouds.add(new Cloud(240, radius1, false, SPEED_1));
        clouds.add(new Cloud(300, radius1, false, SPEED_1));

        clouds.add(new Cloud(0, radius2, true, SPEED_2));
        clouds.add(new Cloud(45, radius2, true, SPEED_2));
        clouds.add(new Cloud(90, radius2, true, SPEED_2));
        clouds.add(new Cloud(135, radius2, true, SPEED_2));
        clouds.add(new Cloud(180, radius2, true, SPEED_2));
        clouds.add(new Cloud(225, radius2, true, SPEED_2));
        clouds.add(new Cloud(270, radius2, true, SPEED_2));
        clouds.add(new Cloud(315, radius2, true, SPEED_2));

        clouds.add(new Cloud(0, radius3, true, SPEED_3));
        clouds.add(new Cloud(30, radius3, true, SPEED_3));
        clouds.add(new Cloud(60, radius3, true, SPEED_3));
        clouds.add(new Cloud(90, radius3, true, SPEED_3));
        clouds.add(new Cloud(120, radius3, true, SPEED_3));
        clouds.add(new Cloud(150, radius3, true, SPEED_3));
        clouds.add(new Cloud(180, radius3, true, SPEED_3));
        clouds.add(new Cloud(210, radius3, true, SPEED_3));
        clouds.add(new Cloud(240, radius3, true, SPEED_3));
        clouds.add(new Cloud(270, radius3, true, SPEED_3));
        clouds.add(new Cloud(300, radius3, true, SPEED_3));
        clouds.add(new Cloud(330, radius3, true, SPEED_3));
    }

    public void onDraw(Canvas canvas) {
        for (Cloud cloud : clouds) {
            cloud.onDraw(canvas);
        }
    }

    public void recycle() {
        unscaledCloud.recycle();
        cloud.recycle();
    }

    public static class Cloud {
        private int A;
        private int B;
        private boolean rightSpin;
        private float startDegree;
        private float speed;
        private double x;
        private double y;

        public Cloud(float startDegree, int radius, boolean rightSpin, float speed) {
            setEllipseConstants(radius);
            this.rightSpin = rightSpin;
            this.startDegree = startDegree;
            this.speed = speed;
            if (!rightSpin) inverseRotate();
        }

        private void update() {
            if (rightSpin) startDegree += speed;
            else startDegree -= speed;
            double rad = Math.toRadians(startDegree);
            x = Math.cos(rad) * A + layoutX;
            y = Math.sin(rad) * B + layoutY;
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
            startDegree = -1f * startDegree;
        }

        private void setEllipseConstants(int radius) {
            A = radius;
            B = radius / 2;
        }

        public void recycleBitmap() {
            unscaledCloud.recycle();
            cloud.recycle();
        }
    }
}
