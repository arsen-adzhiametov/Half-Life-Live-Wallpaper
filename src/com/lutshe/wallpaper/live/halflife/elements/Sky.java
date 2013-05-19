package com.lutshe.wallpaper.live.halflife.elements;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import com.lutshe.wallpaper.live.halflife.R;

import java.util.ArrayList;
import java.util.List;

public class Sky {

    public static final String LUTSHE = "lutshe";

    private static final int X_LAYOUT = 180;
    private static final int Y_LAYOUT = 50;
    private static final int DIAMETER_1 = 90;
    private static final float SPEED_1 = 1f;
    private static final int DIAMETER_2 = 150;
    private static final float SPEED_2 = 1f;
    private static final int DIAMETER_3 = 210;
    private static final float SPEED_3 = 1.3f;

    private static int layoutX = X_LAYOUT;
    private static int layoutY = Y_LAYOUT;
    private static int diameter1 = DIAMETER_1;
    private static int diameter2 = DIAMETER_2;
    private static int diameter3 = DIAMETER_3;

    private static Bitmap cloudBitmap;
    private static Bitmap cloud;
    public static List<Cloud> clouds = new ArrayList<>();

    public Sky(Context context, BitmapFactory.Options options) {
        this.cloudBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sky, options);
        cloud = cloudBitmap;
    }

    public void scaleClouds(float scale) {
        layoutX = (int) (X_LAYOUT * scale);
        layoutY = (int) (Y_LAYOUT * scale);
        diameter1 = (int) (DIAMETER_1 * scale);
        diameter2 = (int) (DIAMETER_2 * scale);
        diameter3 = (int) (DIAMETER_3 * scale);
        cloud = Bitmap.createScaledBitmap(cloudBitmap, (int) (cloudBitmap.getWidth() * scale), (int) (cloudBitmap.getHeight() * scale), true);
        loadResourcesToMemory();
    }

    public synchronized static void loadResourcesToMemory() {
        clouds.clear();

        clouds.add(new Cloud(0, diameter1, false, SPEED_1));
        clouds.add(new Cloud(60, diameter1, false, SPEED_1));
        clouds.add(new Cloud(120, diameter1, false, SPEED_1));
        clouds.add(new Cloud(180, diameter1, false, SPEED_1));
        clouds.add(new Cloud(240, diameter1, false, SPEED_1));
        clouds.add(new Cloud(300, diameter1, false, SPEED_1));

        clouds.add(new Cloud(0, diameter2, true, SPEED_2));
        clouds.add(new Cloud(45, diameter2, true, SPEED_2));
        clouds.add(new Cloud(90, diameter2, true, SPEED_2));
        clouds.add(new Cloud(135, diameter2, true, SPEED_2));
        clouds.add(new Cloud(180, diameter2, true, SPEED_2));
        clouds.add(new Cloud(225, diameter2, true, SPEED_2));
        clouds.add(new Cloud(270, diameter2, true, SPEED_2));
        clouds.add(new Cloud(315, diameter2, true, SPEED_2));

        clouds.add(new Cloud(0, diameter3, true, SPEED_3));
        clouds.add(new Cloud(30, diameter3, true, SPEED_3));
        clouds.add(new Cloud(60, diameter3, true, SPEED_3));
        clouds.add(new Cloud(90, diameter3, true, SPEED_3));
        clouds.add(new Cloud(120, diameter3, true, SPEED_3));
        clouds.add(new Cloud(150, diameter3, true, SPEED_3));
        clouds.add(new Cloud(180, diameter3, true, SPEED_3));
        clouds.add(new Cloud(210, diameter3, true, SPEED_3));
        clouds.add(new Cloud(240, diameter3, true, SPEED_3));
        clouds.add(new Cloud(270, diameter3, true, SPEED_3));
        clouds.add(new Cloud(300, diameter3, true, SPEED_3));
        clouds.add(new Cloud(330, diameter3, true, SPEED_3));
    }

    public synchronized void onDraw(Canvas canvas) {
        for (Cloud cloud : clouds) {
            cloud.onDraw(canvas);
        }
    }

    public void recycleBitmap() {
        cloudBitmap.recycle();
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

        private void setEllipseConstants(int diameter) {
            A = diameter;
            B = diameter * 7 / 12;
        }

    }
}
