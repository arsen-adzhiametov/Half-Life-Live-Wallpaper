package com.lutshe.halflifelivewallpaper;

import android.content.Context;
import android.graphics.*;
import android.util.Log;
import android.view.SurfaceHolder;
import com.halflifelivewallpaper.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LiveWallpaperPainting extends Thread implements Runnable {

    public static final String LUTSHE = "lutshe";

    private SurfaceHolder surfaceHolder;

    private boolean wait;
    private boolean run;

    int width;
    int height;
    float dx = 0.0f;

    private List<Ash> ashs = new ArrayList();
    private Random random = new Random(10);
    private Context context;

    private final Bitmap bg;
    private final Bitmap ash;

    Bitmap scaledBg;

    public LiveWallpaperPainting(SurfaceHolder surfaceHolder, Context context) {
        this.surfaceHolder = surfaceHolder;
        this.wait = true;
        this.context = context;
        ash = BitmapFactory.decodeResource(context.getResources(), R.drawable.untitled1);
        bg = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
        Log.i(LUTSHE, "background init "+bg.getWidth()+"x"+bg.getHeight()+" ash init "+ash.getWidth()+"x"+ash.getHeight());
    }

    /**
     * Invoke when the surface dimension change
     *
     * @param width
     * @param height
     */
    public void setSurfaceSize(int width, int height) {
        Log.i(LUTSHE, "screen orientation " + width + "x" + height);
        this.width = width;
        this.height = height;
        synchronized (this) {
            this.notify();

            scaledBg = this.bg;

            if (width < height) {
                scaledBg = Bitmap.createScaledBitmap(bg, (int) (bg.getWidth() / ((float)bg.getHeight()/height)), height, true);
                Log.i(LUTSHE, "vertical, new background size "+scaledBg.getWidth()+"x"+scaledBg.getHeight());
            } else {
                scaledBg = Bitmap.createScaledBitmap(bg, width, (int) (bg.getHeight() / ((float)bg.getWidth()/width)), true);
                Log.i(LUTSHE, "horizontal, new background size "+scaledBg.getWidth()+"x"+scaledBg.getHeight());
            }
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

                    Thread.sleep(50);
                    if (random.nextInt(11) % 5 == 0)
                        ashs.add(new Ash(this, ash));
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

    private void doDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.translate(dx, 0);
        canvas.drawBitmap(scaledBg, 0, 0, null);
        for (int i = 0; i < ashs.size(); i++) {
            if (ashs.get(i).y < scaledBg.getHeight() && ashs.get(i).x < scaledBg.getWidth())
                ashs.get(i).onDraw(canvas);
            else {
                ashs.remove(i);
            }
        }
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


}