package com.lutshe.wallpaper.live.halflife;

import android.content.SharedPreferences;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;

public class LiveWallpaperService extends WallpaperService {

    public static final String PREFERENCES = "com.lutshe.wallpaper.live.halflife";
    public static final String PREFERENCE_IMAGE = "preference_image";

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
//        Debug.startMethodTracing("halflifeRGB565");
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
//        Debug.stopMethodTracing();
        super.onDestroy();
    }

    public class SampleEngine extends Engine implements SharedPreferences.OnSharedPreferenceChangeListener {

        private LiveWallpaperPainting painting;
        private SharedPreferences prefs;

        private SampleEngine() {
            SurfaceHolder holder = getSurfaceHolder();
            prefs = LiveWallpaperService.this.getSharedPreferences(PREFERENCES, MODE_MULTI_PROCESS);
            prefs.registerOnSharedPreferenceChangeListener(this);
            painting = new LiveWallpaperPainting(holder, getApplicationContext(), Boolean.parseBoolean(prefs.getString(PREFERENCE_IMAGE, "true")));
            Log.i(LUTSHE, "new SampleEngine created");
        }


        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            Log.i(LUTSHE, "onCreate method called in SampleEngine");
            super.onCreate(surfaceHolder);
        }

        @Override
        public void onDestroy() {
            Log.i(LUTSHE, "onDestroy method called in SampleEngine");
            super.onDestroy();
            // remove listeners and callbacks here
            painting.stopPainting();
            painting.interrupt();
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
        public void onOffsetsChanged(float xOffset, float yOffset, float xStep, float yStep, int xPixels, int yPixels) {
            painting.dx = (painting.width - (painting.background.scaledBg.getWidth())) * xOffset;
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

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        }
    }

}