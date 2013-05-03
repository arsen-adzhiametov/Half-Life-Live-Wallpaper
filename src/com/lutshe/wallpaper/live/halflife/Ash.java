package com.lutshe.wallpaper.live.halflife;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import java.util.Random;

public class Ash {

    int x;
    int y;
    private int speed;
    private double angle;
    private double dX;
    private double dY;

    private static int width;
    private static int height;

    private Bitmap ash;
    private static Paint paint = new Paint();
    private static Random random = new Random();

    public Ash(LiveWallpaperPainting painting, Bitmap ash) {
        this.width = painting.width;
        this.height = painting.height;
        int ashScale = random.nextInt(2 * 10) + 10;
        this.ash = Bitmap.createScaledBitmap(ash, ashScale, ashScale, true);
        setStartPosition();
        rotate(random.nextInt(270));
        paint.setAlpha(random.nextInt(100) + 100);
        this.speed = ashScale / 3;
        this.angle = 90;
        dX = Math.abs(speed * Math.sin(angle));
        dY = Math.abs(speed * Math.cos(angle));
    }

    void setStartPosition() {
        x = random.nextInt(width / 2) - width / 2;
        y = random.nextInt(height + height / 2) - height / 2;
    }

    public void updatePosition() {
        x += dX;
        y += dY;
    }

    public void onDraw(Canvas c) {
        updatePosition();
        c.drawBitmap(ash, x, y, paint);
    }

    public void rotate(int degrees) {
        if (degrees != 0) {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) ash.getWidth() / 2, (float) ash.getHeight() / 2);
            ash = Bitmap.createBitmap(ash, 0, 0, ash.getWidth(), ash.getHeight(), m, true);
        }
    }

}
