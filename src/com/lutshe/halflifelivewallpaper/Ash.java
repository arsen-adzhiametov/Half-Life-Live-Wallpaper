package com.lutshe.halflifelivewallpaper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

public class Ash {

	public int x;
	public int y;
	public int speed;
	public double angle;

    private int width;
    private int height;
	
	Bitmap bitmap;
	LiveWallpaperPainting painting;
    private final Random rnd = new Random(System.currentTimeMillis());

    public Ash(LiveWallpaperPainting pm, Bitmap bitmap) {
        this.painting = pm;
        this.width = painting.width;
        this.height = painting.height;

        int ashScale = getRandomAshScaleFromScreenSize();
        this.bitmap = Bitmap.createScaledBitmap(bitmap, ashScale, ashScale, true);
		


		this.x = rnd.nextInt(width + width) - width;
        if (x > 0) this.y = rnd.nextInt(width) - width;
        else this.y = rnd.nextInt(height + width) - width;
		
		this.speed = ashScale/3;
		
		angle = getRandomAngle();
	}
	
	public void update() {
		y += Math.abs(speed * Math.cos(angle));
		x += Math.abs(speed * Math.sin(angle));
	}
	
	private int getRandomAngle() {
        Random rnd = new Random(System.currentTimeMillis());
//        return rnd.nextInt(1) * 90 + 90 / 2 + rnd.nextInt(15) + 5;
        return 90;
    }
	
	public void onDraw(Canvas c) {
		update();
        Paint paint = new Paint();
        paint.setAlpha(rnd.nextInt(100)+100);
		c.drawBitmap(bitmap, x, y, paint);
	}

    private int getRandomAshScaleFromScreenSize(){
        int pixels = width*height;
        int scale = pixels/25600;
        int ashScale = rnd.nextInt(3*scale)+scale;
        return ashScale;
    }

}
