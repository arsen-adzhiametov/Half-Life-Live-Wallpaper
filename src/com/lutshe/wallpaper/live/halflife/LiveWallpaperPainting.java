package com.lutshe.wallpaper.live.halflife;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.SurfaceHolder;
import com.lutshe.wallpaper.live.halflife.elements.*;

import java.util.ArrayList;
import java.util.List;

public class LiveWallpaperPainting extends Thread {

    public static final String LUTSHE = "lutshe";

    private SurfaceHolder surfaceHolder;

    private volatile boolean wait = false;
    private volatile boolean run = true;

    public int width;
    public int height;
    volatile float dx = 0.0f;
    float scale;

    private final Bitmap ash;
    private List<Ash> ashes = new ArrayList();
    private Tower tower;
    private Sky sky;
    private Lightning lightning;
    private Display display;
    private CitadelTopFire citadelTopFire;
    Background background;

    public LiveWallpaperPainting(SurfaceHolder surfaceHolder, Context context, boolean isBetterImage) {
        this.surfaceHolder = surfaceHolder;
        this.wait = true;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        options.inDither = false;
        if (isBetterImage) {
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;//better image!!! tested on 5" display
            Log.i(LUTSHE, "ARGB_8888 color applying");
        } else {
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            Log.i(LUTSHE, "ARGB_4444 color applying");
        }

        background = new Background(context, options);
        tower = new Tower(context, options);
        sky = new Sky(context, options);
        lightning = new Lightning(context, options);
        display = new Display(context, options);
        citadelTopFire = new CitadelTopFire(context, options);
        ash = BitmapFactory.decodeResource(context.getResources(), R.drawable.ash, options);

//        Log.i(LUTSHE, "Engine constructoring finished. Background init " + bg.getWidth() + "x" + bg.getHeight() + " ash init " + ash.getWidth() + "x" + ash.getHeight());
    }


    /**
     * Invoke when the surface dimension change
     *
     * @param width
     * @param height
     */
    public void setSurfaceSize(int width, int height) {
        Log.i(LUTSHE, "setScreenOrientation called with " + width + "x" + height);
        this.width = width;
        this.height = height;
        synchronized (this) {
            this.notify();

            scale = height / (1.0f * background.bg.getHeight());
            background.scaleBackground(scale);
            tower.scaleTower(scale);
            sky.scaleClouds(scale);
            lightning.scaleLights(scale);
            display.scaleLights(scale);
            citadelTopFire.scaleFire(scale);
        }
    }

    @Override
    public void run() {
        Log.i(LUTSHE, "running in thread " + Thread.currentThread().getName());
        this.run = true;
        Canvas canvas = null;
        while (run) {
            try {
                canvas = this.surfaceHolder.lockCanvas(null);
                synchronized (this.surfaceHolder) {

                    if (ashes.size() < 50)
                        ashes.add(new Ash(this, ash));

                    long timestamp = System.currentTimeMillis();
                    if (canvas != null) doDraw(canvas);
                    long drawingTime = System.currentTimeMillis() - timestamp;
                    long sleepTime = 25 - drawingTime;
                    if (sleepTime > 0) Thread.sleep(sleepTime);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
//                        surfaceHolder.lockCanvas(); // Это взято отсюда http://stackoverflow.com/questions/12758703/illegalargumentexception-in-unlockcanvasandpost-android-live-wallpaper
                    }

                } catch (IllegalArgumentException exception) {
                    exception.printStackTrace();
                }
            }
            // pause if no need to animate
            synchronized (this) {
                if (wait) {
                    try {
                        Log.i(LUTSHE, "WHILE cycle is waiting.... in thread " + Thread.currentThread().getName());
                        wait();
                        Log.i(LUTSHE, "WHILE cycle is woke up.... in thread " + Thread.currentThread().getName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        recycleBitmaps();
    }

    private void doDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.translate(dx, 0);
        background.onDraw(canvas);
        citadelTopFire.onDraw(canvas);
        sky.onDraw(canvas);
        tower.onDraw(canvas);
        lightning.onDraw(canvas);
        display.onDraw(canvas);

        for (int i = 0; i < ashes.size(); i++) {
            Ash ash = ashes.get(i);
            if (ash.y < background.scaledBg.getHeight() && ash.x < background.scaledBg.getWidth())
                ash.onDraw(canvas);
            else {
                ash.setStartPosition();
            }
        }
    }

    public void recycleBitmaps() {
        for (Ash ash : ashes) ash.recycleBitmap();
        ashes.clear();
        ash.recycle();
        background.recycleBitmap();
        sky.recycleBitmap();
        tower.recycleBitmap();
        lightning.recycleBitmap();
        citadelTopFire.recycleBitmap();
        display.recycleBitmap();
    }

    /**
     * Pauses the livewallpaper animation
     */
    public void pausePainting() {
        Log.i(LUTSHE, "Pause painting, WAIT field set to true, notify() called");
        this.wait = true;
        synchronized (this) {
            this.notify();
        }
    }

    /**
     * Resume the livewallpaper animation
     */
    public void resumePainting() {
        Log.i(LUTSHE, "Resume painting, WAIT field set to false, notify() called");
        this.wait = false;
        synchronized (this) {
            this.notify();
        }
    }

    /**
     * Stop the livewallpaper animation
     */
    public void stopPainting() {
        Log.i(LUTSHE, "Stop painting, RUN field set to false, notify() called");
        this.run = false;
        synchronized (this) {
            this.notify();
        }
    }
}