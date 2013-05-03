package com.lutshe.wallpaper.live.halflife;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.List;

public class LiveWallpaperPainting extends Thread implements Runnable {

    public static final String LUTSHE = "lutshe";

    private SurfaceHolder surfaceHolder;

    private volatile boolean wait = false;
    private volatile boolean run = true;

    int width;
    int height;
    volatile float dx = 0.0f;
    float scale;

    private List<Ash> ashes = new ArrayList();

    private final Bitmap ash;
    Bitmap bg;
    Bitmap scaledBg;
    private final BackgroundAnimationLayer animationLayer;

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
            options.inPreferredConfig = Bitmap.Config.ARGB_4444;
            Log.i(LUTSHE, "ARGB_4444 color applying");
        }
//        options.inJustDecodeBounds =true;

        bg = BitmapFactory.decodeResource(context.getResources(), R.drawable.anim1, options);
        animationLayer = new BackgroundAnimationLayer(context, options);
        ash = BitmapFactory.decodeResource(context.getResources(), R.drawable.ash, options);
        Log.i(LUTSHE, "Engine constructoring finished. Background init " + bg.getWidth() + "x" + bg.getHeight() + " ash init " + ash.getWidth() + "x" + ash.getHeight());
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
            scaledBg = this.bg;
            scaledBg = Bitmap.createScaledBitmap(bg, (int) (bg.getWidth() / ((float) bg.getHeight() / height)), height, true);
            scale = scaledBg.getWidth() / (1.0f * bg.getWidth());
            animationLayer.setActualSize(scale);
            if (width < height) {
                Log.i(LUTSHE, "vertical, new background size " + scaledBg.getWidth() + "x" + scaledBg.getHeight() + " is set");
            } else {
                Log.i(LUTSHE, "horizontal, new background size " + scaledBg.getWidth() + "x" + scaledBg.getHeight() + " is set");
            }
        }
    }

    @Override
    public void run() {
        Log.i(LUTSHE, "running in thread " + Thread.currentThread().getName());
        this.run = true;
        Canvas canvas = null;
        while (run) {
//            Log.i(LUTSHE, "while cycle");
            try {
                canvas = this.surfaceHolder.lockCanvas(null);
                synchronized (this.surfaceHolder) {
                    Thread.sleep(50);
                    if (ashes.size() < 50)
                        ashes.add(new Ash(this, ash));
                    if (canvas != null) doDraw(canvas);
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
    }

    private void doDraw(Canvas canvas) {
        canvas.save();
        canvas.drawColor(Color.WHITE);
        canvas.translate(dx, 0);
//        canvas.scale(scale, scale);
        canvas.drawBitmap(scaledBg, 0, 0, null);
        canvas.drawBitmap(animationLayer.getNextImage(), 0, 0, null);

        for (int i = 0; i < ashes.size(); i++) {
            Ash ash = ashes.get(i);
            if (ash.y < scaledBg.getHeight() && ash.x < scaledBg.getWidth())
                ash.onDraw(canvas);
            else {
                ash.setStartPosition();
            }
        }
        canvas.restore();
    }

    /**
     * Pauses the livewallpaper animation
     */
    public void pausePainting() {
        Log.i(LUTSHE, "Pause painting, WAIT field set to true, notify() called in Thread " + Thread.currentThread().getName());
        this.wait = true;
        synchronized (this) {
            this.notify();
        }
    }

    /**
     * Resume the livewallpaper animation
     */
    public void resumePainting() {
        Log.i(LUTSHE, "Resume painting, WAIT field set to false, notify() called in Thread " + Thread.currentThread().getName());
        this.wait = false;
        synchronized (this) {
            this.notify();
        }
    }

    /**
     * Stop the livewallpaper animation
     */
    public void stopPainting() {
        Log.i(LUTSHE, "Stop painting, RUN field set to false, notify() called in Thread " + Thread.currentThread().getName());
        this.run = false;
        synchronized (this) {
            this.notify();
        }
    }
}