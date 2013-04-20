package com.lutshe.halflifelivewallpaper;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

public class Bubble
{
	public int x;
	public int y;
	public int speed;
	public double angle;
	
	Bitmap bitmap;
	LiveWallpaperPainting painting;
	
	public Bubble(LiveWallpaperPainting pm, Bitmap bitmap) {
		this.painting = pm;
		this.bitmap = bitmap;
		
		Random rnd = new Random(System.currentTimeMillis());
		this.x = rnd.nextInt(480 + 300) - 300;
        if (x > 0) this.y = rnd.nextInt(480) - 480;
        else this.y = rnd.nextInt(800 + 480) - 480;
		
		this.speed = rnd.nextInt(10) + 5;
		
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
		c.drawBitmap(bitmap, x, y, null);
	}

}
