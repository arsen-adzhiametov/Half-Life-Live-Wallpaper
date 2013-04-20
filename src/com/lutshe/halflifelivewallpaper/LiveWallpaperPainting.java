package com.lutshe.halflifelivewallpaper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import com.halflifelivewallpaper.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LiveWallpaperPainting extends Thread implements Runnable {

    private SurfaceHolder surfaceHolder;

    float dx = 0.0f;

    private boolean wait;
    private boolean run;

    int width;
    int height;


    public List<Bubble> bubbles = new ArrayList();
    Random random = new Random(10);

    Bitmap bg;
    private Bitmap snowflake;

    public LiveWallpaperPainting(SurfaceHolder surfaceHolder, Context context) {
        this.surfaceHolder = surfaceHolder;
        this.wait = true;

        snowflake = BitmapFactory.decodeResource(context.getResources(), R.drawable.snow_flake);
        bg = BitmapFactory.decodeResource(context.getResources(), R.drawable.b);
    }

    /**
     * Pauses the livewallpaper animation
     */
    public void pausePainting() {
        this.wait = true;
        synchronized (this) {
            this.notify();
        }
    }

    /**
     * Resume the livewallpaper animation
     */
    public void resumePainting() {
        this.wait = false;
        synchronized (this) {
            this.notify();
        }
    }

    /**
     * Stop the livewallpaper animation
     */
    public void stopPainting() {
        this.run = false;
        synchronized (this) {
            this.notify();
        }
    }

    @Override
    public void run() {
        this.run = true;
        Canvas c = null;
        while (run) {
            try {
                c = this.surfaceHolder.lockCanvas(null);
                synchronized (this.surfaceHolder) {
                    c.translate(dx, 0);
                    Thread.sleep(40);
                    if (random.nextInt(11) % 5 == 0)
                        bubbles.add(new Bubble(this, snowflake));
                    doDraw(c);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (c != null) {
                    this.surfaceHolder.unlockCanvasAndPost(c);
                }
            }
            // pause if no need to animate
            synchronized (this) {
                if (wait) {
                    try {
                        wait();
                    } catch (Exception e) {
                    }
                }
            }
        }
    }

    /**
     * Invoke when the surface dimension change
     *
     * @param width
     * @param height
     */
    public void setSurfaceSize(int width, int height) {
        this.width = width;
        this.height = height;
        synchronized (this) {
            this.notify();
            bg = Bitmap.createScaledBitmap(bg, (int)(width*1.4), height, true);
        }
    }


    private void doDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bg, 0, 0, null);
//        System.out.println(bubbles.size());
        for (int i = 0; i < bubbles.size(); i++) {
            if (bubbles.get(i).y < bg.getHeight() && bubbles.get(i).x < bg.getWidth())
                bubbles.get(i).onDraw(canvas);
            else {
//                bubbles.remove(i);
            }
        }

    }

    private void doRandomWind() {

    }

}