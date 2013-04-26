package com.lutshe.wallpaper.live.halflife;

import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;

public class LiveWallpaperService extends WallpaperService {

    public static final String LUTSHE = "lutshe";
    private SampleEngine engine;

    @Override
    public Engine onCreateEngine() {
        Log.i(LUTSHE, "onCreateEngine called in WallpaperService, new SampleEngine creating...");
        if (engine != null) {
            engine.painting.stopPainting();
            engine = null;
            Log.i(LUTSHE, "engine recreated successfully!");
        }
        engine = new SampleEngine();
        return engine;
    }

    @Override
    public void onCreate() {
        Log.i(LUTSHE, "onCreate method called in WallpaperService");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.i(LUTSHE, "onDestroy method called in WallpaperService");
        super.onDestroy();
    }

    public class SampleEngine extends Engine {

        private LiveWallpaperPainting painting;

        private SampleEngine() {
            SurfaceHolder holder = getSurfaceHolder();
            painting = new LiveWallpaperPainting(holder, getApplicationContext());
            Log.i(LUTSHE, "new SampleEngine created");
        }


        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            Log.i(LUTSHE, "onCreate method called in SampleEngine");
            super.onCreate(surfaceHolder);
        }

        @Override
        public void onDestroy() {
            Log.i(LUTSHE, "onCreate method called in SampleEngine");
            super.onDestroy();
            // remove listeners and callbacks here
            painting.stopPainting();
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Log.i(LUTSHE, "onSurfaceChanged method called in SampleEngine, " + width + "x" + height);
            super.onSurfaceChanged(holder, format, width, height);
            painting.setSurfaceSize(width, height);
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            Log.i(LUTSHE, "onSurfaceCreated method called in SampleEngine");
            super.onSurfaceCreated(holder);
            painting.start();
            Log.i(LUTSHE, "Runnable started");
        }


        @Override
        public void onVisibilityChanged(boolean visible) {
            Log.i(LUTSHE, "onVisibilityChanged method called in SampleEngine to " + visible);
            if (visible) {
                painting.resumePainting();
            } else {
                // remove listeners and callbacks here
                painting.pausePainting();
            }
        }


        @Override
        public void onOffsetsChanged(float xOffset,
                                     float yOffset,
                                     float xStep,
                                     float yStep,
                                     int xPixels,
                                     int yPixels) {
            painting.dx = (painting.width - (painting.scaledBg.getWidth())) * xOffset;
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            Log.i(LUTSHE, "onSurfaceDestroyed method called in SampleEngine");
            super.onSurfaceDestroyed(holder);
            boolean retry = true;
            painting.stopPainting();
            while (retry) {
                try {
                    painting.join();
                    retry = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}