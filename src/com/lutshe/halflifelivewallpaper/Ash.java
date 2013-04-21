package com.lutshe.halflifelivewallpaper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import java.util.Random;

public class Ash {

    public int x;
    public int y;
    public int speed;
    public double angle;

    private static int width;
    private static int height;

    private Bitmap ash;
    private static Paint paint = new Paint();
    private LiveWallpaperPainting painting;
    private static Random random = new Random(System.currentTimeMillis());

    public Ash(LiveWallpaperPainting pm, Bitmap ash) {
        this.painting = pm;
        this.width = painting.width;
        this.height = painting.height;

        int ashScale = getRandomAshScaleFromScreenSize();
        this.ash = Bitmap.createScaledBitmap(ash, ashScale, ashScale, true);

        this.x = random.nextInt(width + width) - width;
        if (x > 0) this.y = random.nextInt(width) - width;
        else this.y = random.nextInt(height + width) - width;

        this.speed = ashScale / 3;
        this.angle = getRandomAngle();
        rotate(random.nextInt(270));
    }

    public void update() {
        y += Math.abs(speed * Math.cos(angle));
        x += Math.abs(speed * Math.sin(angle));
    }

    private int getRandomAngle() {
//        return random.nextInt(1) * 90 + 90 / 2 + random.nextInt(15) + 5;
        return 90;
    }

    public void onDraw(Canvas c) {
        update();
        paint.setAlpha(random.nextInt(100) + 100);
        c.drawBitmap(ash, x, y, paint);
    }

    public void rotate(int degrees) {
        if (degrees != 0 && ash != null) {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) ash.getWidth() / 2, (float) ash.getHeight() / 2);
            try {
                ash = Bitmap.createBitmap(ash, 0, 0, ash.getWidth(), ash.getHeight(), m, true);
            } catch (OutOfMemoryError ex) {
                throw ex;
            }
        }
    }

    private static int getRandomAshScaleFromScreenSize() {
        int pixels = width * height;
        int scale = pixels / 25600;
        int ashScale = random.nextInt(3 * scale) + scale;
        return ashScale;
    }

}
