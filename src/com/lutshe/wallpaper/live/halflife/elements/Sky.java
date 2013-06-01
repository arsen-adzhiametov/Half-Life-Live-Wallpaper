package com.lutshe.wallpaper.live.halflife.elements;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import com.lutshe.wallpaper.live.halflife.R;

import java.util.ArrayList;
import java.util.List;

public class Sky {

    private static final int X_LAYOUT = 315;
    private static final int Y_LAYOUT = 120;
    private static final int DIAMETER_1 = 100;
    private static final float SPEED_1 = 1f;
    private static final int DIAMETER_2 = 165;
    private static final float SPEED_2 = 1f;
    private static final int DIAMETER_3 = 230;
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
        this.cloudBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sky_opa_opa, options);
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
        clouds.add(new Cloud(40, diameter2, true, SPEED_2));
        clouds.add(new Cloud(80, diameter2, true, SPEED_2));
        clouds.add(new Cloud(120, diameter2, true, SPEED_2));
        clouds.add(new Cloud(160, diameter2, true, SPEED_2));
        clouds.add(new Cloud(200, diameter2, true, SPEED_2));
        clouds.add(new Cloud(240, diameter2, true, SPEED_2));
        clouds.add(new Cloud(280, diameter2, true, SPEED_2));
        clouds.add(new Cloud(320, diameter2, true, SPEED_2));

        clouds.add(new Cloud(0, diameter3, true, SPEED_3));
        clouds.add(new Cloud(20, diameter3, true, SPEED_3));
        clouds.add(new Cloud(40, diameter3, true, SPEED_3));
        clouds.add(new Cloud(60, diameter3, true, SPEED_3));
        clouds.add(new Cloud(80, diameter3, true, SPEED_3));
        clouds.add(new Cloud(100, diameter3, true, SPEED_3));
        clouds.add(new Cloud(120, diameter3, true, SPEED_3));
        clouds.add(new Cloud(140, diameter3, true, SPEED_3));
        clouds.add(new Cloud(160, diameter3, true, SPEED_3));
        clouds.add(new Cloud(180, diameter3, true, SPEED_3));
        clouds.add(new Cloud(200, diameter3, true, SPEED_3));
        clouds.add(new Cloud(220, diameter3, true, SPEED_3));
        clouds.add(new Cloud(240, diameter3, true, SPEED_3));
        clouds.add(new Cloud(260, diameter3, true, SPEED_3));
        clouds.add(new Cloud(280, diameter3, true, SPEED_3));
        clouds.add(new Cloud(300, diameter3, true, SPEED_3));
        clouds.add(new Cloud(320, diameter3, true, SPEED_3));
        clouds.add(new Cloud(340, diameter3, true, SPEED_3));
    }

    public synchronized void onDraw(Canvas canvas) {
        for (Cloud cloud : clouds) {          //concurent modification exception
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
        private float degreePosition;
        private float speed;
        private double x;
        private double y;

        public Cloud(float degreePosition, int radius, boolean rightSpin, float speed) {
            setEllipseConstants(radius);
            this.rightSpin = rightSpin;
            this.degreePosition = degreePosition;
            this.speed = speed;
            if (!rightSpin) inverseRotate();
        }

        private void update() {
            if (rightSpin) degreePosition += speed;
            else degreePosition -= speed;
            double rad = Math.toRadians(degreePosition);
            x = Math.cos(rad) * A + layoutX;
            y = Math.sin(rad) * B + layoutY;
            if (Math.abs(degreePosition) > 359) degreePosition = 0;
        }

        public void onDraw(Canvas canvas) {
            update();
            canvas.save();
            canvas.translate((float) x, (float) y);
            float f = resizeFromPosition(y);
            canvas.rotate(degreePosition, (float) cloud.getWidth() * f / 2f, (float) cloud.getHeight() * f / 2f);
            canvas.scale(f, f);
            canvas.drawBitmap(cloud, 0, 0, null);
            canvas.restore();
        }

        private static final float K = (float) (Math.tan(Math.toRadians(340d)));

        private float resizeFromPosition(double y) {
            float f = (float) ((K * y) + 170) / 100;
            return f;
        }

        private void inverseRotate() {
            degreePosition = -1f * degreePosition;
        }

        private void setEllipseConstants(int diameter) {
            A = diameter;
            B = diameter * 6 / 12;
        }

    }
}
